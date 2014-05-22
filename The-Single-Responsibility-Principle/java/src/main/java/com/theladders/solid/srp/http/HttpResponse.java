package com.theladders.solid.srp.http;

import com.theladders.solid.srp.Result;

public class HttpResponse
{
  private Result result;

  public String getResultType()
  {
    return result.getType();
  }

  public void setResult(Result result)
  {
    this.result = result;
  }

  public Object getValueFor(String key)
  {
    return result.get(key);
  }

  public boolean modelIsEmpty()
  {
    return result.isEmpty();
  }
}
