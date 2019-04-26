package cn.web.model;

public class Article
{
  private int id;
  private String author;
  private String title;
  private String content;
  private String src;
  private String description;
  private String subject;
  private String time;
  private int snum;
  private int znum;
  private int authorid;
  
  public int getAuthorid()
  {
    return this.authorid;
  }
  
  public void setAuthorid(int authorid)
  {
    this.authorid = authorid;
  }
  
  public int getSnum()
  {
    return this.snum;
  }
  
  public int getZnum()
  {
    return this.znum;
  }
  
  public void setSnum(int snum)
  {
    this.snum = snum;
  }
  
  public void setZnum(int znum)
  {
    this.znum = znum;
  }
  
  public String getTime()
  {
    return this.time;
  }
  
  public void setTime(String time)
  {
    this.time = time;
  }
  
  public String getSubject()
  {
    return this.subject;
  }
  
  public void setSubject(String subject)
  {
    this.subject = subject;
  }
  
  public String getAuthor()
  {
    return this.author;
  }
  
  public void setAuthor(String author)
  {
    this.author = author;
  }
  
  public String getTitle()
  {
    return this.title;
  }
  
  public void setTitle(String title)
  {
    this.title = title;
  }
  
  public String getContent()
  {
    return this.content;
  }
  
  public void setContent(String content)
  {
    this.content = content;
  }
  
  public int getId()
  {
    return this.id;
  }
  
  public void setId(int id)
  {
    this.id = id;
  }
  
  public String getSrc()
  {
    return this.src;
  }
  
  public void setSrc(String src)
  {
    this.src = src;
  }
  
  public String getDescription()
  {
    return this.description;
  }
  
  public void setDescription(String description)
  {
    this.description = description;
  }
}
