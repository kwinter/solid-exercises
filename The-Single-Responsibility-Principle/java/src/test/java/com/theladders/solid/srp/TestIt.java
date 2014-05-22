package com.theladders.solid.srp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.theladders.solid.srp.apply.ApplyWorkflow;
import com.theladders.solid.srp.http.HttpRequest;
import com.theladders.solid.srp.http.HttpResponse;
import com.theladders.solid.srp.http.HttpSession;
import com.theladders.solid.srp.job.Job;
import com.theladders.solid.srp.job.JobRepository;
import com.theladders.solid.srp.job.JobSearchService;
import com.theladders.solid.srp.job.application.JobApplicationRepository;
import com.theladders.solid.srp.job.application.JobApplicationSystem;
import com.theladders.solid.srp.job.application.SuccessfulApplication;
import com.theladders.solid.srp.jobseeker.Jobseeker;
import com.theladders.solid.srp.jobseeker.JobseekerProfile;
import com.theladders.solid.srp.jobseeker.JobseekerProfileManager;
import com.theladders.solid.srp.jobseeker.JobseekerProfileRepository;
import com.theladders.solid.srp.jobseeker.ProfileStatus;
import com.theladders.solid.srp.resume.ActiveResumeRepository;
import com.theladders.solid.srp.resume.MyResumeManager;
import com.theladders.solid.srp.resume.Resume;
import com.theladders.solid.srp.resume.ResumeManager;
import com.theladders.solid.srp.resume.ResumeRepository;

public class TestIt
{
  private static final int           VALID_JOB_ID       = 5;
  private static final int           INVALID_JOB_ID     = 555;
  private static final String        SHARED_RESUME_NAME = "A Resume";
  private static final int           WITH_RESUME        = 777;
  private static final int           INCOMPLETE         = 888;
  private static final int           APPROVED           = 1010;
  private static final boolean       PREMIUM            = true;
  private static final boolean       BASIC              = false;

  private ApplyController            controller;
  private JobRepository              jobRepository;
  private ResumeRepository           resumeRepository;
  private JobApplicationRepository   jobApplicationRepository;
  private JobseekerProfileRepository jobseekerProfileRepository;
  private ActiveResumeRepository     activeResumeRepository;

  private SuccessfulApplication      existingApplication;

  private int                        jobseekerId;
  private HttpSession                session;
  private final HttpResponse         response           = new HttpResponse();
  private final Map<String, String>  parameters         = new HashMap<>();


  @Test
  public void requestWithValidJob()
  {
    givenApprovedPremiumJobseeker();
    givenValidJobIdParameter();

    whenHandlingRequestWithResumeName(SHARED_RESUME_NAME);

    thenTheResponseIsSuccessful();
  }


  @Test
  public void requestWithValidJobByBasic()
  {
    givenApprovedBasicJobseeker();
    givenValidJobIdParameter();

    whenHandlingRequestWithResumeName(SHARED_RESUME_NAME);

    thenTheResponseIsSuccessful();
  }


  @Test
  public void applyUsingExistingResume()
  {
    givenPremiumJobseekerWithResume();
    givenValidJobIdParameter();
    givenExistingResumeParameter();

    whenHandlingRequestWithResumeName(SHARED_RESUME_NAME);

    thenTheResponseIsSuccessful();
  }


  @Test
  public void requestWithInvalidJob()
  {
    givenApprovedPremiumJobseeker();
    givenInvalidJobIdParameter();

    whenHandlingRequestWithResumeName(SHARED_RESUME_NAME);

    thenResponseIsInvalidJob();
  }


  @Test
  public void requestWithNoResume()
  {
    givenApprovedPremiumJobseeker();
    givenValidJobIdParameter();

    whenHandlingRequestWithResumeName(null);

    thenResponseIsError();
  }


  @Test
  public void reapplyToJob()
  {
    givenApprovedPremiumJobseeker();

    givenPreviouslyAppliedJobIdParameter();

    whenHandlingRequestWithResumeName(SHARED_RESUME_NAME);

    thenResponseIsError();
  }


  @Test
  public void unapprovedBasic()
  {
    givenAnIncompleteBasicJobseeker();

    givenValidJobIdParameter();

    whenHandlingRequestWithResumeName(SHARED_RESUME_NAME);

    thenTheResponseIsPleaseCompleteResume();
  }


  @Test
  public void resumeIsSaved()
  {
    givenApprovedPremiumJobseeker();

    givenValidJobIdParameter();

    whenHandlingRequestWithResumeName(SHARED_RESUME_NAME);

    thenResumeRepositoryContains(SHARED_RESUME_NAME);
  }


  @Test
  public void resumeIsMadeActive()
  {
    givenApprovedPremiumJobseeker();
    givenValidJobIdParameter();
    givenMakeResumeActiveParameter("yes");

    whenHandlingRequestWithResumeName("Save Me Seymour");

    thenActiveResumeNameIs("Save Me Seymour");
  }


  @Before
  public void setup()
  {
    setupJobseekerProfileRepository();
    setupJobRepository();
    setupResumeRepository();
    setupActiveResumeRepository();
    setupJobApplicationRepository();
    setupController();
  }


  private void givenApprovedPremiumJobseeker()
  {
    givenJobseeker(PREMIUM, APPROVED);
  }


  private void givenApprovedBasicJobseeker()
  {
    givenJobseeker(BASIC, APPROVED);
  }


  private void givenPremiumJobseekerWithResume()
  {
    givenJobseeker(PREMIUM, WITH_RESUME);
  }


  private void givenAnIncompleteBasicJobseeker()
  {
    givenJobseeker(BASIC, INCOMPLETE);
  }


  private void givenJobseeker(boolean isPremium,
                              int id)
  {
    jobseekerId = id;
    Jobseeker jobseeker = new Jobseeker(id, isPremium);
    session = new HttpSession(jobseeker);
  }


  private void givenValidJobIdParameter()
  {
    parameters.put("jobId", String.valueOf(VALID_JOB_ID));
  }


  private void givenInvalidJobIdParameter()
  {
    parameters.put("jobId", String.valueOf(INVALID_JOB_ID));
  }


  private void givenPreviouslyAppliedJobIdParameter()
  {
    parameters.put("jobId", "15");
  }


  private void givenExistingResumeParameter()
  {
    parameters.put("whichResume", "existing");
  }


  private void givenMakeResumeActiveParameter(String value)
  {
    parameters.put("makeResumeActive", value);
  }


  private void whenHandlingRequestWithResumeName(String fileName)
  {
    HttpRequest request = new HttpRequest(session, parameters);

    controller.handle(request, response, fileName);
  }


  private void thenTheResponseIsSuccessful()
  {
    assertEquals("success", response.getResultType());
    assertEquals(VALID_JOB_ID, response.getValueFor("jobId"));
    assertEquals("This is a job with id:" + VALID_JOB_ID, response.getValueFor("jobTitle"));
  }


  private void thenResponseIsInvalidJob()
  {
    assertEquals("invalidJob", response.getResultType());
    assertEquals(INVALID_JOB_ID, response.getValueFor("jobId"));
  }


  private void thenResponseIsError()
  {
    assertEquals("error", response.getResultType());
    assertTrue(response.modelIsEmpty());
    assertEquals("We could not process your application.", response.firstError());
  }


  private void thenTheResponseIsPleaseCompleteResume()
  {
    assertEquals("completeResumePlease", response.getResultType());
    assertEquals(VALID_JOB_ID, response.getValueFor("jobId"));
    assertEquals("This is a job with id:" + VALID_JOB_ID, response.getValueFor("jobTitle"));
  }


  private void thenActiveResumeNameIs(String name)
  {
    assertEquals(new Resume(name), activeResumeRepository.activeResumeFor(jobseekerId));
  }


  private void thenResumeRepositoryContains(String resumeName)
  {
    assertTrue(resumeRepository.contains(new Resume(resumeName)));
  }


  private void setupJobseekerProfileRepository()
  {
    jobseekerProfileRepository = new JobseekerProfileRepository();

    addToJobseekerProfileRepository(APPROVED, ProfileStatus.APPROVED);
    addToJobseekerProfileRepository(INCOMPLETE, ProfileStatus.INCOMPLETE);
    addToJobseekerProfileRepository(WITH_RESUME, ProfileStatus.APPROVED);
  }


  private void addToJobseekerProfileRepository(int id,
                                               ProfileStatus status)
  {
    JobseekerProfile profile = new JobseekerProfile(id, status);
    jobseekerProfileRepository.addProfile(profile);
  }


  private void setupJobRepository()
  {
    jobRepository = new JobRepository();

    addJobToRepository(5);
    addJobToRepository(15);
    addJobToRepository(51);
    addJobToRepository(57);
    addJobToRepository(501);
    addJobToRepository(1555);
    addJobToRepository(5012);
    addJobToRepository(50111);
  }


  private void addJobToRepository(int jobId)
  {
    if (jobId != INVALID_JOB_ID)
    {
      jobRepository.addJob(new Job(jobId));
    }
  }


  private void setupResumeRepository()
  {
    resumeRepository = new ResumeRepository();
  }


  private void setupActiveResumeRepository()
  {
    activeResumeRepository = new ActiveResumeRepository();

    activeResumeRepository.makeActive(WITH_RESUME, new Resume("Blammo"));
  }


  private void setupJobApplicationRepository()
  {
    jobApplicationRepository = new JobApplicationRepository();

    addToJobApplicationRepository();
  }


  private void addToJobApplicationRepository()
  {
    Jobseeker JOBSEEKER = new Jobseeker(APPROVED, true);
    Job job = new Job(15);
    Resume resume = new Resume("foo");

    existingApplication = new SuccessfulApplication(JOBSEEKER, job, resume);

    jobApplicationRepository.add(existingApplication);
  }


  private void setupController()
  {
    JobseekerProfileManager jobseekerProfileManager = new JobseekerProfileManager(jobseekerProfileRepository);
    JobSearchService jobSearchService = new JobSearchService(jobRepository);
    JobApplicationSystem jobApplicationSystem = new JobApplicationSystem(jobApplicationRepository);
    ResumeManager resumeManager = new ResumeManager(resumeRepository);
    MyResumeManager myResumeManager = new MyResumeManager(activeResumeRepository);

    ApplyWorkflow applyWorkflow = new ApplyWorkflow(jobseekerProfileManager,
                                                    jobSearchService,
                                                    jobApplicationSystem,
                                                    resumeManager,
                                                    myResumeManager);
    controller = new ApplyController(applyWorkflow);
  }
}
