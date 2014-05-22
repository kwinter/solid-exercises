package com.theladders.solid.srp.apply;

import com.theladders.solid.srp.jobseeker.Jobseeker;
import com.theladders.solid.srp.resume.MyResumeManager;
import com.theladders.solid.srp.resume.Resume;
import com.theladders.solid.srp.resume.ResumeManager;

public class ApplicationResumeManager
{

  private final ResumeManager   resumeManager;
  private final MyResumeManager myResumeManager;

  public ApplicationResumeManager(ResumeManager resumeManager,
                                   MyResumeManager myResumeManager)
  {
    this.resumeManager = resumeManager;
    this.myResumeManager = myResumeManager;
  }

  public Resume saveNewOrRetrieveExistingResume(String newResumeFileName,
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
