package cn.web.auth;

import java.io.PrintStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class QQAuthService
  extends DefaultAuthServiceImpl
{
  private static final String AUTHORIZATION_URL = "https://graph.qq.com/oauth2.0/authorize?response_type=code&client_id=%s&redirect_uri=%s&scope=%s";
  private static final String ACCESS_TOKEN_URL = "https://graph.qq.com/oauth2.0/token?grant_type=authorization_code&client_id=%s&client_secret=%s&code=%s&redirect_uri=%s";
  private static final String OPEN_ID_URL = "https://graph.qq.com/oauth2.0/me?access_token=%s";
  private static final String USER_INFO_URL = "https://graph.qq.com/user/get_user_info?access_token=%s&oauth_consumer_key=%s&openid=%s";
  private static final String CALLBACK_URL = "http://127.0.0.1/auth/qq";
  private static final String API_KEY = "1106301147";
  private static final String API_SECRET = "OpmGW8Js8vRfhvaV";
  private static final String SCOPE = "get_user_info";
  
  public String getAuthorizationUrl()
  {
    String url = String.format("https://graph.qq.com/oauth2.0/authorize?response_type=code&client_id=%s&redirect_uri=%s&scope=%s", new Object[] { "1106301147", "http://127.0.0.1/auth/qq", "get_user_info" });
    return url;
  }
  
  public String getAccessToken(String code)
  {
    String url = String.format("https://graph.qq.com/oauth2.0/token?grant_type=authorization_code&client_id=%s&client_secret=%s&code=%s&redirect_uri=%s", new Object[] { "1106301147", "OpmGW8Js8vRfhvaV", code, "http://127.0.0.1/auth/qq" });
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
    URI uri = builder.build().encode().toUri();
    
    String resp = (String)getRestTemplate().getForObject(uri, String.class);
    if (resp.contains("access_token"))
    {
      Map<String, String> map = getParam(resp);
      String access_token = (String)map.get("access_token");
      return access_token;
    }
    try
    {
      throw new Exception();
    }
    catch (Exception e) {}
    return null;
  }
  
  private Map<String, String> getParam(String string)
  {
    Map<String, String> map = new HashMap();
    String[] kvArray = string.split("&");
    for (int i = 0; i < kvArray.length; i++)
    {
      String[] kv = kvArray[i].split("=");
      map.put(kv[0], kv[1]);
    }
    return map;
  }
  
  public JSONObject ConvertToJson(String string)
    throws JSONException
  {
    string = string.substring(string.indexOf("(") + 1, string.length());
    string = string.substring(0, string.indexOf(")"));
    JSONObject jsonObject = new JSONObject(string);
    JSONObject json = new JSONObject();
    json.put("result", jsonObject);
    return json.getJSONObject("result");
  }
  
  public String getOpenId(String accessToken)
  {
    String url = String.format("https://graph.qq.com/oauth2.0/me?access_token=%s", new Object[] { accessToken });
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
    URI uri = builder.build().encode().toUri();
    
    String resp = (String)getRestTemplate().getForObject(uri, String.class);
    if (resp.contains("openid"))
    {
      JSONObject jsonObject = null;
      try
      {
        jsonObject = ConvertToJson(resp);
      }
      catch (JSONException e)
      {
        e.printStackTrace();
      }
      String openid = null;
      try
      {
        openid = jsonObject.getString("openid");
      }
      catch (JSONException e)
      {
        e.printStackTrace();
      }
      return openid;
    }
    return null;
  }
  
  public JSONObject getUserInfo(String accessToken, String openId)
  {
    openId = getOpenId(accessToken);
    String url = String.format("https://graph.qq.com/user/get_user_info?access_token=%s&oauth_consumer_key=%s&openid=%s", new Object[] { accessToken, "1106301147", openId });
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
    URI uri = builder.build().encode().toUri();
    
    String resp = (String)getRestTemplate().getForObject(uri, String.class);
    System.out.println("请求user返回：" + resp);
    JSONObject data = null;
    try
    {
      data = new JSONObject(resp);
    }
    catch (JSONException e)
    {
      e.printStackTrace();
    }
    JSONObject result = new JSONObject();
    try
    {
      result.put("id", openId);
      
      result.put("nickName", data.getString("nickname"));
      result.put("avatar", data.getString("figureurl_qq_1"));
    }
    catch (JSONException e)
    {
      e.printStackTrace();
    }
    return result;
  }
  
  public String refreshToken(String code)
  {
    return null;
  }
}
