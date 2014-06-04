package com.theladders.solid.isp.viewjob;


public class ViewJobWorkflow
{
  private JobRepository repository;

  public Object view(JobLocationId id,
                     Jobseeker jobseeker,
                     JobPresenter presenter)
  {
    Job job = repository.getJob(id);
    Object presentation = presentationFor(job, jobseeker, presenter);

    job.recordViewBy(jobseeker);

    return presentation;
  }

  private static Object presentationFor(Job job,
                                        Jobseeker jobseeker,
                                        JobPresenter presenter)
  {
    JobRepresentation jobRepresentation = job.representationFor(jobseeker);

    if (hitsPaywallFor(jobseeker, job))
    {
      return presenter.forbidden(jobRepresentation);
    }

    return presenter.success(jobRepresentation);
  }

  private static boolean hitsPaywallFor(Jobseeker jobseeker,
                                        Job job)
  {
    return job.requiresPermissions() && jobseeker.doesNotHavePermissionsFor(job);
  }
}
