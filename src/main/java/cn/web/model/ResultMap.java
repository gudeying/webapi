package cn.web.model;

public class ResultMap<T>
{
  private Boolean result;
  private int success;
  private String message;
  private T data;
  private String url;
  
  public String getUrl()
  {
    return this.url;
  }
  
  public void setUrl(String url)
  {
    this.url = url;
  }
  
  public Boolean getResult()
  {
    return this.result;
  }
  
  public ResultMap<T> setResult(Boolean result)
  {
    this.result = result;
    return this;
  }
  
  public int getSuccess()
  {
    return this.success;
  }
  
  public ResultMap<T> setSuccess(int success)
  {
    this.success = success;
    return this;
  }
  
  public String getMessage()
  {
    return this.message;
  }
  
  public ResultMap<T> setMessage(String message)
  {
    this.message = message;
    return this;
  }
  
  public T getData()
  {
    return (T)this.data;
  }
  
  public void setData(T data)
  {
    this.data = data;
  }
}
