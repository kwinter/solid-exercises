package com.theladders.solid.isp.oldjob;

import java.util.Date;
import java.util.List;

import com.theladders.solid.isp.oldjob.stubs.Discipline;
import com.theladders.solid.isp.oldjob.stubs.Experience;
import com.theladders.solid.isp.oldjob.stubs.Industry;
import com.theladders.solid.isp.oldjob.stubs.Region;

public interface JobCommon
{
  /**
   * @return the name of the company
   */
  String getCompany();

  /**
   * Gets the value of the company_size_id field.
   * This represents the id in the company size table for the description of
   * how large the company is.
   *
   * @return companySize
   */
  Integer getCompanySize();

  /**
   * Get this job's compensation (text).
   *
   * @return compensation for this job.
   */
  String getCompensation();

  /**
   * Get this job's compensationSalary (text).
   *
   * @return compensationSalary for this job.
   */
  String getCompensationSalary();

  /**
   * Get this job's compensationBonus (text).
   *
   * @return compensationBonus for this job.
   */
  String getCompensationBonus();

  /**
   * Get this job's compensationOther (text).
   *
   * @return compensationOther for this job.
   */
  String getCompensationOther();

  /**
   * Retrieves a list of disciplines for this job.
   *
   * @return List of Disciplines
   */
  List<Discipline> getDisciplines();

  /**
   * Get the date this job was entered into the system.
   *
   * @return the Date the job was entered.
   */
  Date getEntryDate();

  /**
   * Returns an object that represents the number of years of experience
   * that are required for this job.
   * @return experience object
   */
  Experience getExperience();

  /**
   * Get the Industry for this job.
   *
   * @return the Industry for this job.
   */
  Industry getIndustry();

  String getLocation();

  int getOldJobId();

  /**
   * Get the date this job was published.
   *
   * @return the Date the job was published.
   */
  Date getPublicationDate();

  /**
   * Get the region for this job.
   *
   * @return the region for this job.
   */
  Region getRegion();

  /**
   * Get the "reportsTo" field.
   *
   * @return reportsTo
   */
  String getReportsTo();

  int getSubscriberId();

 /**
   * Get this job's title.
   *
   * @return the title for this job.
   */
  String getTitle();

  /**
   * @return returns true if this job was posted anonymously
   */
  boolean isAnonymous();

  boolean isConfidential();

  boolean isExclusive();

  /**
   * Is this job a JobReq? JobReqs are jobs entered into our site directly by recruiters.
   *
   * @return true if job is a JobReq, false otherwise.
   */
  boolean isJobReq();

  boolean isReimbursable();

  /**
   * @return The last time this job was updated
   */
  Date getUpdateTime();

  /**
   * Refactored so it can be used by both job and JobReq
   * @return fullJobDescription()
   *
   */
  String getDescription();
}
