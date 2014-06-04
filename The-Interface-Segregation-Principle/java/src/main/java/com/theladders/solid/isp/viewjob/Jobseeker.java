package com.theladders.solid.isp.viewjob;

public class Jobseeker
{
  public boolean doesNotHavePermissionsFor(@SuppressWarnings("unused") Job job)
  {
    return false;
  }
}
