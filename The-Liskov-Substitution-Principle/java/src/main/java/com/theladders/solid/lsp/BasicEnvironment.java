package com.theladders.solid.lsp;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

public class BasicEnvironment extends Environment
{
  private final HashMap<Object, Object> map = new HashMap<>();

  @Override
  public Object get(Object key)
  {
    return map.get(key);
  }

  public void put(Object key,
                  Object value)
  {
    map.put(key, value);
  }

  @Override
  public Set<Entry<Object, Object>> entrySet()
  {
    return map.entrySet();
  }
}
