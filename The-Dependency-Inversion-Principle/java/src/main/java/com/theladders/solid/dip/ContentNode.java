package com.theladders.solid.dip;

import java.util.HashMap;
import java.util.Map;

public class ContentNode
{
  private Map<String, Object> properties = new HashMap<>();

  public Object getProperty(String key)
  {
    return properties.get(key);
  }

  public void addProperty(String key, Object value)
  {
    properties.put(key, value);
  }
}
