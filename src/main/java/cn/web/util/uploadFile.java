package cn.web.util;

import java.io.File;
import java.io.IOException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service("uploadFile")
public class uploadFile
{
  public Boolean upfile(MultipartFile file, String filepath, String filename)
  {
    File dest = new File(filepath + filename);
    if (!dest.getParentFile().exists()) {
      dest.getParentFile().mkdirs();
    }
    try
    {
      file.transferTo(dest);
      return Boolean.valueOf(true);
    }
    catch (IllegalStateException|IOException e) {}
    return Boolean.valueOf(false);
  }
}
