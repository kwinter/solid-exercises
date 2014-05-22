package com.theladders.solid.srp.apply;

import java.util.List;

public interface ApplicationResultPresenter<T>
{
  T invalidJob(int jobId);

  T applicationFailed(List<String> errList);

  T needsToCompleteResume(int jobId,
                          String jobTitle);

  T success(int jobId,
            String jobTitle);

}
