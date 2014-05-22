package com.theladders.solid.srp.resume;

public class SelectedResume
{
  public String  origFileName;
  public boolean useNewResume;
  public boolean makeResumeActive;

  public SelectedResume(String origFileName,
                        boolean useNewResume,
                        boolean makeResumeActive)
  {
    this.origFileName = origFileName;
    this.useNewResume = useNewResume;
    this.makeResumeActive = makeResumeActive;
  }
}