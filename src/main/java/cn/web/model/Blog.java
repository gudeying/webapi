package cn.web.model;

public class Blog
{
  private int id;
  private int zan;
  private String title;
  private String detail;
  private String author;
  private String time;
  private String emotion;
  private String src;
  
  public int getId()
  {
    return this.id;
  }
  
  public void setId(int id)
  {
    this.id = id;
  }
  
  public int getZan()
  {
    return this.zan;
  }
  
  public void setZan(int zan)
  {
    this.zan = zan;
  }
  
  public String getTitle()
  {
    return this.title;
  }
  
  public void setTitle(String title)
  {
    this.title = title;
  }
  
  public String getDetail()
  {
    return this.detail;
  }
  
  public void setDetail(String detail)
  {
    this.detail = detail;
  }
  
  public String getAuthor()
  {
    return this.author;
  }
  
  public void setAuthor(String author)
  {
    this.author = author;
  }
  
  public String getTime()
  {
    return this.time;
  }
  
  public void setTime(String time)
  {
    this.time = time;
  }
  
  public String getEmotion()
  {
    return this.emotion;
  }
  
  public void setEmotion(String emotion)
  {
    this.emotion = emotion;
  }
  
  public String getSrc()
  {
    return this.src;
  }
  
  public void setSrc(String src)
  {
    this.src = src;
  }
}
