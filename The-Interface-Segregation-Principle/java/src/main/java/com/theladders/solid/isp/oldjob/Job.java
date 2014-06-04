package com.theladders.solid.isp.oldjob;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import com.theladders.solid.isp.oldjob.stubs.City;
import com.theladders.solid.isp.oldjob.stubs.JobFunction;
import com.theladders.solid.isp.oldjob.stubs.JobStatus;
import com.theladders.solid.isp.oldjob.stubs.PositionLevel;
import com.theladders.solid.isp.oldjob.stubs.Sector;

/**
 * Job Interface.
 */
public interface Job extends JobCommon, Serializable
{
  /**
   * Get the City for this job.
   *
   * @return the City for this job.
   */
  City getCity();

  /**
   * Get the (internally set) editor's note.
   *
   * @return editor's note.
   */
  String getEditorNote();

  /**
   * Returns a unique identifier for this job. In the web application, this currently maps to
   * job_location_id in the Database. Scripts may use other values.
   *
   * @return unique identifier for this job.
   */
  int getJobId();

  /**
   * Return the jobsite id for this job.
   *
   * @return jobsite id for this job.
   */
  int getJobSiteId();

  /**
   * Get the date this job was originally published
   *
   * @return the Date the job was originally published
   */
  Date getOriginalPublicationDate();

  /**
   * Returns the real job_id.
   *
   * @return job id
   */
  Integer getParentJobId();

  PositionLevel getPositionLevel();

  /**
   * Get the sector for this job.
   *
   * @return the sector for this job.
   */
  Sector getSector();

  /**
   * Get this job's short description.
   *
   * @return a summary description of this job.
   */
  String getShortDescription();

  /**
   * Get the URL for this job. This is only valid for external (harvested) jobs (! isJobReq).
   *
   * @return URL for this job.
   */
  String getUrl();

  /**
   * Does the job have a particular status? There's a legacy thing where a job could have more than
   * one status, hence this method... Status should be moved out of PublicationInfo though, and this
   * should be a getStatus() method...
   *
   * @param status
   *          status to check against.
   * @return true if job has this status, false otherwise.
   */
  boolean hasStatus(JobStatus status);

  boolean isDeleted();

  boolean isExpired();

  /**
   * Is this job filled?
   *
   * @return true if this job is filled, false otherwise.
   */
  boolean isFilled();

  /**
   * Is this job a Marketing job? If this flag is set, basic access is allowed to this job (where
   * otherwise it would be premium) from certain landing pages.
   *
   * @return true if this is marked for marketing, false otherwise.
   */
  // TODO: This should only ever be true for JobReq, refactor into the JobReq interface
  boolean isMarketing();

  Collection<JobFunction> getJobFunctions();
}
