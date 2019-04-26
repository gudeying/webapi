package cn.web.tools;

import java.io.IOException;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class HttpClientForMail
{
  private static final String mailurl = "http://goodin1.applinzi.com/email.php?touser=%s&openid=%s";
  
  public String sendMail(String mail)
    throws ClientProtocolException, IOException
  {
    new getRandomString();String openid = getRandomString.GetRandomString(15);
    String url = String.format("http://goodin1.applinzi.com/email.php?touser=%s&openid=%s", new Object[] { mail, openid });
    CloseableHttpClient client = HttpClients.createDefault();
    HttpGet get = new HttpGet(url);
    CloseableHttpResponse response = client.execute(get);
    int staus = response.getStatusLine().getStatusCode();
    if ((staus >= 200) && (staus <= 300)) {
      return openid;
    }
    return null;
  }
}
