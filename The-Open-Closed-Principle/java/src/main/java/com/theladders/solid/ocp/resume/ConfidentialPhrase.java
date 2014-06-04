package com.theladders.solid.ocp.resume;

public class ConfidentialPhrase
{
  private Integer id;
  private String  phrase;
  private boolean isConfidential;


  public ConfidentialPhrase()
  {
    id = Integer.valueOf(0);
  }


  public Integer getId()
  {
    return id;
  }


  public void setId(Integer id)
  {
    this.id = id;
  }


  public ConfidentialPhrase(String phrase,
                            boolean isConfidential)
  {
    super();
    this.id = Integer.valueOf(0);
    this.phrase = phrase;
    this.isConfidential = isConfidential;
  }


  public String getPhrase()
  {
    return phrase;
  }


  public void setPhrase(String phrase)
  {
    this.phrase = phrase;
  }


  public boolean isConfidential()
  {
    return isConfidential;
  }


  public void setConfidential(boolean isConfidential)
  {
    this.isConfidential = isConfidential;
  }
}
