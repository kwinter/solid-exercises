package com.theladders.solid.isp.viewjob;


public class JobRepository
{
  public Job getJob(@SuppressWarnings("unused") JobLocationId id)
  {
    return new Job();
  }
}
