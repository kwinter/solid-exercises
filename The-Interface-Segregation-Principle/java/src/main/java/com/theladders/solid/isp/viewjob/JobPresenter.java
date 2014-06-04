package com.theladders.solid.isp.viewjob;


public class JobPresenter
{
  public Response success(JobRepresentation jobRepresentation)
  {
    return buildResponse("OK", jobRepresentation);
  }

  public Response forbidden(JobRepresentation jobRepresentation)
  {
    return buildResponse("FORBIDDEN", jobRepresentation);
  }

  private static Response buildResponse(String status,
                                        JobRepresentation jobRepresentation)
  {
    return new Response(status, jobRepresentation);
  }
}
