package cn.web.rabbitMQ;

public class SubMessage
{
  private String userID;
  private String artID;
  
  public String getUserID()
  {
    return this.userID;
  }
  
  public void setUserID(String userID)
  {
    this.userID = userID;
  }
  
  public String getArtID()
  {
    return this.artID;
  }
  
  public void setArtID(String artID)
  {
    this.artID = artID;
  }
}
