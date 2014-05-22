package com.theladders.solid.srp;

import com.theladders.solid.srp.apply.ApplicationResultPresenter;
import com.theladders.solid.srp.apply.ApplyWorkflow;
import com.theladders.solid.srp.http.HttpRequest;
import com.theladders.solid.srp.http.HttpResponse;
import com.theladders.solid.srp.jobseeker.Jobseeker;
import com.theladders.solid.srp.web.WebApplicationResultPresenter;

public class ApplyController
{
  private final ApplyWorkflow applyWorkflow;

  public ApplyController(ApplyWorkflow applyWorkflow)
  {
    this.applyWorkflow = applyWorkflow;
  }

  public HttpResponse handle(HttpRequest request,
                             HttpResponse response,
                             String origFileName)
  {
    Jobseeker jobseeker = request.getSession().getJobseeker();
    String jobIdString = request.getParameter("jobId");
    int jobId = Integer.parseInt(jobIdString);

    boolean useNewResume = !"existing".equals(request.getParameter("whichResume"));
    boolean makeResumeActive = "yes".equals(request.getParameter("makeResumeActive"));

    return apply(response, origFileName, jobseeker, jobId, useNewResume, makeResumeActive);
  }

  private HttpResponse apply(HttpResponse response,
                             String origFileName,
                             Jobseeker jobseeker,
                             int jobId,
                             boolean useNewResume,
                             boolean makeResumeActive)
  {
    ApplicationResultPresenter<Result> presenter = new WebApplicationResultPresenter();
    Result result = applyWorkflow.apply(origFileName, jobseeker, jobId, useNewResume, makeResumeActive, presenter);
    response.setResult(result);
    return response;
  }

}
