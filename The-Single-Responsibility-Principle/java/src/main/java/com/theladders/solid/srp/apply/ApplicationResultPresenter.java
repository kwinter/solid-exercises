package com.theladders.solid.srp.apply;


public interface ApplicationResultPresenter<T>
{
  T invalidJob(int jobId);

  T applicationFailed();

  T needsToCompleteResume(int jobId,
                          String jobTitle);

  T success(int jobId,
            String jobTitle);

}
