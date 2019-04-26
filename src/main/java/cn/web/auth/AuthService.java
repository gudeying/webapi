package cn.web.auth;

import java.io.UnsupportedEncodingException;
import org.json.JSONObject;

public abstract interface AuthService
{
  public abstract String getAccessToken(String paramString);
  
  public abstract String getOpenId(String paramString);
  
  public abstract String refreshToken(String paramString);
  
  public abstract String getAuthorizationUrl()
    throws UnsupportedEncodingException;
  
  public abstract JSONObject getUserInfo(String paramString1, String paramString2);
}
