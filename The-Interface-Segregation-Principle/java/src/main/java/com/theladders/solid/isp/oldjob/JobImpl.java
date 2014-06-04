package com.theladders.solid.isp.oldjob;

import java.util.Date;
import java.util.List;

import com.theladders.solid.isp.oldjob.stubs.Discipline;
import com.theladders.solid.isp.oldjob.stubs.Experience;
import com.theladders.solid.isp.oldjob.stubs.Industry;
import com.theladders.solid.isp.oldjob.stubs.Region;

public abstract class JobImpl implements JobCommon
{
  private String                   company;
  private Integer                  companySize;
  private String                   compensation;
  private String                   compensationBonus;
  private String                   compensationOther;
  private String                   compensationSalary;
  private List<Discipline>         disciplines;
  private Date                     entryDate;
  private Experience               experience;
  private Industry                 industry;
  private String                   location;
  private int                      oldJobId           = 0;
  private Date                     publicationDate;
  private Region                   region;
  private String                   reportsTo;
  private int                      subscriberId       = 0;
  private String                   title;
  private boolean                  anonymous          = false;
  private boolean                  confidential       = false;
  private boolean                  exclusive          = false;
  private boolean                  reimbursable       = false;
  private Date                     updateTime         = null;

  public String getCompany()
  {
    return company;
  }

  public void setCompany(String company)
  {
    this.company = company;
  }

  public Integer getCompanySize()
  {
    return companySize;
  }

  public void setCompanySize(Integer companySize)
  {
    this.companySize = companySize;
  }

  public String getCompensation()
  {
    return compensation;
  }

  public void setCompensation(String compensation)
  {
    this.compensation = compensation;
  }

  public String getCompensationBonus()
  {
    return compensationBonus;
  }

  public void setCompensationBonus(String compensationBonus)
  {
    this.compensationBonus = compensationBonus;
  }

  public String getCompensationOther()
  {
    return compensationOther;
  }

  public void setCompensationOther(String compensationOther)
  {
    this.compensationOther = compensationOther;
  }

  public String getCompensationSalary()
  {
    return compensationSalary;
  }

  public void setCompensationSalary(String compensationSalary)
  {
    this.compensationSalary = compensationSalary;
  }

  public List<Discipline> getDisciplines()
  {
    return disciplines;
  }

  public void setDisciplines(List<Discipline> disciplines)
  {
    this.disciplines = disciplines;
  }

  public Date getEntryDate()
  {
    return entryDate;
  }

  public void setEntryDate(Date entryDate)
  {
    this.entryDate = entryDate;
  }

  public Experience getExperience()
  {
    return experience;
  }

  public void setExperience(Experience experience)
  {
    this.experience = experience;
  }

  public Industry getIndustry()
  {
    return industry;
  }

  public void setIndustry(Industry industry)
  {
    this.industry = industry;
  }

  public String getLocation()
  {
    return location;
  }

  public void setLocation(String location)
  {
    this.location = location;
  }

  public int getOldJobId()
  {
    return oldJobId;
  }


  public void setOldJobId(int oldJobId)
  {
    this.oldJobId = oldJobId;
  }

  public Date getPublicationDate()
  {
    return publicationDate;
  }

  public void setPublicationDate(Date publicationDate)
  {
    this.publicationDate = publicationDate;
  }

  public void setRegion(Region region)
  {
    this.region = region;
  }

  public Region getRegion()
  {
    return region;
  }

  public String getReportsTo()
  {
    return reportsTo;
  }

  public void setReportsTo(String reportsTo)
  {
    this.reportsTo = reportsTo;
  }

  public int getSubscriberId()
  {
    return subscriberId;
  }

  public void setSubscriberId(Integer subscriberId)
  {
    this.subscriberId = subscriberId;
  }

  public String getTitle()
  {
    return title;
  }

  public void setTitle(String title)
  {
    this.title = title;
  }

  public boolean isAnonymous()
  {
    return anonymous;
  }

  public void setAnonymous(boolean anonymous)
  {
    this.anonymous = anonymous;
  }

  public boolean isConfidential()
  {
    return confidential;
  }

  public void setConfidential(boolean confidential)
  {
    this.confidential = confidential;
  }

  public boolean isExclusive()
  {
    return exclusive;
  }

  public void setExclusive(boolean exclusive)
  {
    this.exclusive = exclusive;
  }

  public boolean isReimbursable()
  {
    return reimbursable;
  }

  public void setReimbursable(boolean reimbursable)
  {
    this.reimbursable = reimbursable;
  }

  /**
   * WARNING: THIS IS NOT IMPLEMENTED EVERYWHERE.
   * I have not implemented getUpdateTime to be returned from the DAO anywhere except the job
   * search flow.
   *
   * This method is used by search to set the update time which is used for version information.
   * @return the last time this job was updated.
   */
  public Date getUpdateTime()
  {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime)
  {
    this.updateTime = updateTime;
  }
}
