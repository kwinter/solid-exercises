package com.theladders.solid.srp.apply;

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
import com.theladders.solid.srp.resume.Resume;
import com.theladders.solid.srp.resume.SelectedResume;

public class ApplyWorkflow
{
  private final JobseekerProfileManager  jobseekerProfileManager;
  private final JobSearchService         jobSearchService;
  private final JobApplicationSystem     jobApplicationSystem;
  private final ApplicationResumeManager applicationResumeManager;

  public ApplyWorkflow(JobseekerProfileManager jobseekerProfileManager,
                       JobSearchService jobSearchService,
                       JobApplicationSystem jobApplicationSystem,
                       ApplicationResumeManager applicationResumeManager)
  {
    this.jobseekerProfileManager = jobseekerProfileManager;
    this.jobSearchService = jobSearchService;
    this.jobApplicationSystem = jobApplicationSystem;
    this.applicationResumeManager = applicationResumeManager;
  }

  public <T> T apply(int jobId,
                     Jobseeker jobseeker,
                     SelectedResume selectedResume,
                     ApplicationResultPresenter<T> presenter)
  {
    Job job = jobSearchService.getJob(jobId);
    if (job == null)
    {
      return presenter.invalidJob(jobId);
    }

    try
    {
      apply(job, jobseeker, selectedResume);
    }
    catch (Exception e)
    {
      return presenter.applicationFailed();
    }

    if (needsToCompleteResume(jobseeker))
    {
      return presenter.needsToCompleteResume(jobId, job.getTitle());
    }

    return presenter.success(jobId, job.getTitle());
  }

  private void apply(Job job,
                     Jobseeker jobseeker,
                     SelectedResume selectedResume)
  {
    Resume resume = saveNewOrRetrieveExistingResume(jobseeker, selectedResume);
    UnprocessedApplication application = new UnprocessedApplication(jobseeker, job, resume);

    process(application);
  }

  private void process(UnprocessedApplication application)
  {
    JobApplicationResult applicationResult = jobApplicationSystem.apply(application);
    if (applicationResult.failure())
    {
      throw new ApplicationFailureException(applicationResult.toString());
    }
  }

  private Resume saveNewOrRetrieveExistingResume(Jobseeker jobseeker,
                                                 SelectedResume selectedResume)
  {
    return applicationResumeManager.saveNewOrRetrieveExistingResume(jobseeker,
                                                                    selectedResume.origFileName,
                                                                    selectedResume.useNewResume,
                                                                    selectedResume.makeResumeActive);
  }

  private boolean needsToCompleteResume(Jobseeker jobseeker)
  {
    JobseekerProfile profile = jobseekerProfileManager.getJobSeekerProfile(jobseeker);
    return !jobseeker.isPremium() && (profile.getStatus().equals(ProfileStatus.INCOMPLETE) || profile.getStatus()
                                                                                                     .equals(ProfileStatus.NO_PROFILE) || profile.getStatus()
                                                                                                                                                 .equals(ProfileStatus.REMOVED));
  }
}
