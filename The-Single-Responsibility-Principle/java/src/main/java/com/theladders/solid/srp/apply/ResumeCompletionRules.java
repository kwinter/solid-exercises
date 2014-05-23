package com.theladders.solid.srp.apply;

import com.theladders.solid.srp.jobseeker.Jobseeker;
import com.theladders.solid.srp.jobseeker.JobseekerProfile;
import com.theladders.solid.srp.jobseeker.ProfileStatus;

public class ResumeCompletionRules
{
  public static boolean needsToCompleteResume(Jobseeker jobseeker,
                                              JobseekerProfile profile)
  {
    return !jobseeker.isPremium() && profileIsOk(profile);
  }

  private static boolean profileIsOk(JobseekerProfile profile)
  {
    return profile.getStatus().equals(ProfileStatus.INCOMPLETE) || profile.getStatus().equals(ProfileStatus.NO_PROFILE)
           || profile.getStatus().equals(ProfileStatus.REMOVED);
  }
}
