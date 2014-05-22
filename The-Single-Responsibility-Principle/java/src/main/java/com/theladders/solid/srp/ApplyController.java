package com.theladders.solid.srp;

import com.theladders.solid.srp.apply.ApplyWorkflow;
import com.theladders.solid.srp.http.HttpRequest;
import com.theladders.solid.srp.http.HttpResponse;
import com.theladders.solid.srp.jobseeker.Jobseeker;
import com.theladders.solid.srp.resume.SelectedResume;
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
    int jobId = jobIdIn(request);
    SelectedResume selectedResume = selectedResumeFrom(request, origFileName);

    return applicationResponseFor(jobId, jobseeker, selectedResume, response);
  }

  private static int jobIdIn(HttpRequest request)
  {
    String jobIdString = request.getParameter("jobId");
    return Integer.parseInt(jobIdString);
  }

  private static SelectedResume selectedResumeFrom(HttpRequest request,
                                            String origFileName)
  {
    boolean useNewResume = !"existing".equals(request.getParameter("whichResume"));
    boolean makeResumeActive = "yes".equals(request.getParameter("makeResumeActive"));
    return new SelectedResume(origFileName, useNewResume, makeResumeActive);
  }

  private HttpResponse applicationResponseFor(int jobId,
                             Jobseeker jobseeker,
                             SelectedResume selectedResume,
                             HttpResponse response)
  {
    Result result = apply(jobId, jobseeker, selectedResume);
    response.setResult(result);
    return response;
  }

  private Result apply(int jobId,
                       Jobseeker jobseeker,
                       SelectedResume selectedResume)
  {
    return applyWorkflow.apply(jobId, jobseeker, selectedResume, new WebApplicationResultPresenter());
  }

}
