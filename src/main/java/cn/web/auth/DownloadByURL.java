package cn.web.auth;

import cn.web.tools.HttpURLConnectionUtil;
import java.io.File;
import java.io.InputStream;
import org.springframework.stereotype.Service;

@Service
public class DownloadByURL
{
  public void download(String url, String filepath, String fileName)
  {
    File file = new File(filepath, fileName);
    
    InputStream inputStream = HttpURLConnectionUtil.getInputStreamByGet(url);
    HttpURLConnectionUtil.saveData(inputStream, file);
  }
}
