package com.theladders.solid.srp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.theladders.solid.srp.apply.ApplicationResultPresenter;
import com.theladders.solid.srp.http.HttpRequest;
import com.theladders.solid.srp.http.HttpResponse;
import com.theladders.solid.srp.job.Job;
import com.theladders.solid.srp.job.JobSearchService;
import com.theladders.solid.srp.job.application.ApplicationFailureException;
import com.theladders.solid.srp.job.application.JobApplicationResult;
import com.theladders.solid.srp.job.application.JobApplicationSystem;
import com.theladders.solid.srp.job.application.UnprocessedApplication;
import com.theladders.solid.srp.jobseeker.Jobseeker;
import com.theladders.solid.srp.jobseeker.JobseekerProfile;
import com.theladders.solid.srp.jobseeker.JobseekerProfileManager;
import com.theladders.solid.srp.jobseeker.ProfileStatus;
import com.theladders.solid.srp.resume.MyResumeManager;
import com.theladders.solid.srp.resume.Resume;
import com.theladders.solid.srp.resume.ResumeManager;
import com.theladders.solid.srp.web.WebApplicationResultPresenter;

public class ApplyController
{
  private final JobseekerProfileManager jobseekerProfileManager;
  private final JobSearchService        jobSearchService;
  private final JobApplicationSystem    jobApplicationSystem;
  private final ResumeManager           resumeManager;
  private final MyResumeManager         myResumeManager;

  public ApplyController(JobseekerProfileManager jobseekerProfileManager,
                         JobSearchService jobSearchService,
                         JobApplicationSystem jobApplicationSystem,
                         ResumeManager resumeManager,
                         MyResumeManager myResumeManager)
  {
    this.jobseekerProfileManager = jobseekerProfileManager;
    this.jobSearchService = jobSearchService;
    this.jobApplicationSystem = jobApplicationSystem;
    this.resumeManager = resumeManager;
    this.myResumeManager = myResumeManager;
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
    Result result = applicationResult(origFileName,
                                      jobseeker,
                                      jobId,
                                      useNewResume,
                                      makeResumeActive,
                                      presenter);
    response.setResult(result);

    return response;
  }

  private <T> T applicationResult(String origFileName,
                                   Jobseeker jobseeker,
                                   int jobId,
                                   boolean useNewResume,
                                   boolean makeResumeActive,
                                  ApplicationResultPresenter<T> presenter)
  {
    JobseekerProfile profile = jobseekerProfileManager.getJobSeekerProfile(jobseeker);
    Job job = jobSearchService.getJob(jobId);
    if (job == null)
    {
      return presenter.invalidJob(jobId);
    }

    Map<String, Object> model = new HashMap<>();

    List<String> errList = new ArrayList<>();

    try
    {
      apply(jobseeker, job, origFileName, useNewResume, makeResumeActive);
    }
    catch (Exception e)
    {
      errList.add("We could not process your application.");
      return presenter.applicationFailed(errList);
    }

    model.put("jobId", job.getJobId());
    model.put("jobTitle", job.getTitle());

    if (needsToCompleteResume(jobseeker, profile))
    {
      return presenter.needsToCompleteResume(jobId, job.getTitle());
    }

    return presenter.success(jobId, job.getTitle());
  }
  private boolean needsToCompleteResume(Jobseeker jobseeker,
                                        JobseekerProfile profile)
  {
    return !jobseeker.isPremium() && (profile.getStatus().equals(ProfileStatus.INCOMPLETE) ||
                                   profile.getStatus().equals(ProfileStatus.NO_PROFILE) ||
                                   profile.getStatus().equals(ProfileStatus.REMOVED));
  }

  private void apply(
                     Jobseeker jobseeker,
                     Job job,
                     String fileName, boolean useNewResume, boolean makeResumeActive)
  {
    Resume resume = saveNewOrRetrieveExistingResume(fileName, jobseeker, useNewResume, makeResumeActive);
    UnprocessedApplication application = new UnprocessedApplication(jobseeker, job, resume);
    JobApplicationResult applicationResult = jobApplicationSystem.apply(application);

    if (applicationResult.failure())
    {
      throw new ApplicationFailureException(applicationResult.toString());
    }
  }

  private Resume saveNewOrRetrieveExistingResume(String newResumeFileName,
                                                 Jobseeker jobseeker,
                                                 boolean useNewResume,
                                                 boolean makeResumeActive)
  {
    Resume resume;

    if (useNewResume)
    {
      resume = resumeManager.saveResume(jobseeker, newResumeFileName);

      if (resume != null && makeResumeActive)
      {
        myResumeManager.saveAsActive(jobseeker, resume);
      }
    }
    else
    {
      resume = myResumeManager.getActiveResume(jobseeker.getId());
    }

    return resume;
  }
}
