package com.theladders.solid.lsp;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A wrapper that allows some properties to be overriden on a per-request basis.
 *
 * @author Zhi-Da Zhong &lt;zz@theladders.com&gt;
 */

public class DynamicEnvironment extends Environment implements SeedableEnvironment
{
  private final Map<Object, Object> overrides = new HashMap<Object, Object>();
  private final Environment         base;
  private final Map<String, String> keyMap; // map insecure prop names to secure ones

  public DynamicEnvironment(Environment base, Map<String, String> propKeyMap)
  {
    this.base = base;
    this.keyMap = propKeyMap;
  }


  /**
   * This method uses a mapped version of the given key to access first its own Map then its
   * underlying Map.
   *
   * @param key
   *          An environment key like "home"
   * @return The value for the given key after mapping (e.g. "home" might be mapped to "secureHome")
   */

  @Override
  public Object get(Object key)
  {
    String realKey = keyMap.get(key);
    Object value = overrides.get(realKey != null ? realKey : key);
    if (value == null)
    {
      return base.get(realKey != null ? realKey : key);
    }
    return value;
  }

  public void append(Object key,
                     Object value)
  {
    put(key, get(key) + value.toString());
  }

  @Override
  public void copy(Object fromKey,
                   Object toKey)
  {
    put(toKey, get(fromKey));
  }

  @Override
  public void copyAndAppend(Object fromKey,
                            Object toKey,
                            Object toAppend)
  {
    copy(fromKey, toKey);
    append(toKey, toAppend);
  }
  private void put(Object key,
                   Object value)
  {
    overrides.put(key, value);
  }

  @Override
  public Set<Map.Entry<Object, Object>> entrySet()
  {
    Set<Map.Entry<Object, Object>> entrySet = new HashSet<>(overrides.entrySet());
    entrySet.addAll(base.entrySet());
    return Collections.unmodifiableSet(entrySet);
  }

  @Override
  public String toString()
  {
    return entrySet().toString();
  }

}
