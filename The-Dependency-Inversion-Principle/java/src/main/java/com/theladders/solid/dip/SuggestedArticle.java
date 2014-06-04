package com.theladders.solid.dip;

import java.util.Date;

// A SuggestedArticle is one instance of an article that has been
// recommended to a particular subscriber.

public class SuggestedArticle
{
  private Integer     suggestedArticleId;
  private Integer     subscriberId;
  private Integer     suggestedArticleSourceId;
  private String      articleExternalIdentifier;
  private Integer     suggestedArticleStatusId;
  private Date        createTime;
  private Integer     creatorId;
  private Date        updateTime;
  private Integer     updaterId;
  private String      note;
  private ContentNode content;

  private Integer     STATUS_UNREAD = 1;

  public SuggestedArticle() {}

  public SuggestedArticle(Integer subscriberId,
                          String articleExternalIdentifier,
                          String note,
                          Integer adminUserId)
  {
    this.subscriberId = subscriberId;
    this.articleExternalIdentifier = articleExternalIdentifier;
    this.suggestedArticleStatusId = STATUS_UNREAD;
    this.note = note;
    this.creatorId = adminUserId;
    this.createTime = new Date(); // current date
    this.updateTime = new Date(); // current date
  }

  @Column(name = "suggested_article_id")
  public Integer getSuggestedArticleId()
  {
    return suggestedArticleId;
  }

  public void setSuggestedArticleId(Integer suggestedArticleId)
  {
    this.suggestedArticleId = suggestedArticleId;
  }

  @Column(name = "subscriber_id")
  public Integer getSubscriberId()
  {
    return subscriberId;
  }

  public void setSubscriberId(Integer subscriberId)
  {
    this.subscriberId = subscriberId;
  }

  @Column(name = "suggested_article_source_id")
  public Integer getSuggestedArticleSourceId()
  {
    return suggestedArticleSourceId;
  }

  public void setSuggestedArticleSourceId(Integer suggestedArticleSourceId)
  {
    this.suggestedArticleSourceId = suggestedArticleSourceId;
  }

  @Column(name = "article_external_identifier")
  public String getArticleExternalIdentifier()
  {
    return articleExternalIdentifier;
  }

  public void setArticleExternalIdentifier(String articleExternalIdentifier)
  {
    this.articleExternalIdentifier = articleExternalIdentifier == null ? null : articleExternalIdentifier.trim();
  }

  @Column(name = "suggested_article_status_id")
  public Integer getSuggestedArticleStatusId()
  {
    return suggestedArticleStatusId;
  }

  public void setSuggestedArticleStatusId(Integer suggestedArticleStatusId)
  {
    this.suggestedArticleStatusId = suggestedArticleStatusId;
  }

  @Column(name = "create_time")
  public Date getCreateTime()
  {
    return createTime;
  }

  public void setCreateTime(Date createTime)
  {
    this.createTime = createTime;
  }

  @Column(name = "creator_id")
  public Integer getCreatorId()
  {
    return creatorId;
  }

  public void setCreatorId(Integer creatorId)
  {
    this.creatorId = creatorId;
  }

  @Column(name = "update_time")
  public Date getUpdateTime()
  {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime)
  {
    this.updateTime = updateTime;
  }

  @Column(name = "updater_id")
  public Integer getUpdaterId()
  {
    return updaterId;
  }

  public void setUpdaterId(Integer updaterId)
  {
    this.updaterId = updaterId;
  }

  @Column(name = "note")
  public String getNote()
  {
    return note;
  }

  public void setNote(String note)
  {
    this.note = note == null ? null : note.trim();
  }

  public ContentNode getContent()
  {
    return content;
  }

  public void setContent(ContentNode node)
  {
    this.content = node;
  }

  public boolean getIsRead()
  {
    if (this.getSuggestedArticleStatusId() == 2)
    {
      return true;
    }

    return false;
  }

  public void setIsRead(boolean read)
  {
    if (read)
    {
      this.setSuggestedArticleStatusId(2);
    }
    else
    {
      this.setSuggestedArticleStatusId(1);
    }
  }

}
