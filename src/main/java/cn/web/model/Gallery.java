package cn.web.model;

public class Gallery
{
  private int id;
  private String des;
  private String comsrc;
  private String src;
  private String user;
  
  public String getSrc()
  {
    return this.src;
  }
  
  public void setSrc(String src)
  {
    this.src = src;
  }
  
  public int getId()
  {
    return this.id;
  }
  
  public void setId(int id)
  {
    this.id = id;
  }
  
  public String getDes()
  {
    return this.des;
  }
  
  public void setDes(String des)
  {
    this.des = des;
  }
  
  public String getComsrc()
  {
    return this.comsrc;
  }
  
  public void setComsrc(String comsrc)
  {
    this.comsrc = comsrc;
  }
  
  public String getUser()
  {
    return this.user;
  }
  
  public void setUser(String user)
  {
    this.user = user;
  }
}
