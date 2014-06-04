package com.theladders.solid.isp.viewjob;

public class Response
{
  private final String status;
  private final Object entity;

  public Response(String status, Object entity)
  {
    this.status = status;
    this.entity = entity;
  }

  public String getStatus()
  {
    return status;
  }

  public Object getEntity()
  {
    return entity;
  }
}
