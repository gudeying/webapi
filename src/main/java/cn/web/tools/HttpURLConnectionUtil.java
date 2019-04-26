package cn.web.tools;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpURLConnectionUtil
{
  public static InputStream getInputStreamByGet(String url)
  {
    try
    {
      HttpURLConnection conn = (HttpURLConnection)new URL(url).openConnection();
      conn.setReadTimeout(5000);
      conn.setConnectTimeout(5000);
      conn.setRequestMethod("GET");
      if (conn.getResponseCode() == 200) {
        return conn.getInputStream();
      }
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    return null;
  }
  
  public static void saveData(InputStream is, File file)
  {
    try
    {
      BufferedInputStream bis = new BufferedInputStream(is);Throwable localThrowable6 = null;
      try
      {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));Throwable localThrowable7 = null;
        try
        {
          byte[] buffer = new byte['Ѐ'];
          int len = -1;
          while ((len = bis.read(buffer)) != -1)
          {
            bos.write(buffer, 0, len);
            bos.flush();
          }
        }
        catch (Throwable localThrowable1)
        {
          localThrowable7 = localThrowable1;throw localThrowable1;
        }
        finally {}
      }
      catch (Throwable localThrowable4)
      {
        localThrowable6 = localThrowable4;throw localThrowable4;
      }
      finally
      {
        if (bis != null) {
          if (localThrowable6 != null) {
            try
            {
              bis.close();
            }
            catch (Throwable localThrowable5)
            {
              localThrowable6.addSuppressed(localThrowable5);
            }
          } else {
            bis.close();
          }
        }
      }
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
}
