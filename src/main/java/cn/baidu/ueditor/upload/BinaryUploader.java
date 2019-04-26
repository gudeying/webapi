package cn.baidu.ueditor.upload;

import cn.baidu.ueditor.PathFormat;
import cn.baidu.ueditor.define.BaseState;
import cn.baidu.ueditor.define.FileType;
import cn.baidu.ueditor.define.State;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public class BinaryUploader
{
  public static final State save(HttpServletRequest request, Map<String, Object> conf)
  {
    if (!ServletFileUpload.isMultipartContent(request)) {
      return new BaseState(false, 5);
    }
    try
    {
      MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
      MultipartFile multipartFile = multipartRequest.getFile(conf.get("fieldName").toString());
      if (multipartFile == null) {
        return new BaseState(false, 7);
      }
      String savePath = (String)conf.get("savePath");
      
      String originFileName = multipartFile.getOriginalFilename();
      String suffix = FileType.getSuffixByFilename(originFileName);
      
      originFileName = originFileName.substring(0, originFileName
        .length() - suffix.length());
      savePath = savePath + suffix;
      
      long maxSize = ((Long)conf.get("maxSize")).longValue();
      if (!validType(suffix, (String[])conf.get("allowFiles"))) {
        return new BaseState(false, 8);
      }
      savePath = PathFormat.parse(savePath, originFileName);
      
      String basePath = (String)conf.get("basePath");
      String physicalPath = basePath + savePath;
      
      InputStream is = multipartFile.getInputStream();
      State storageState = StorageManager.saveFileByInputStream(is, physicalPath, maxSize);
      
      is.close();
      if (storageState.isSuccess())
      {
        storageState.putInfo("url", PathFormat.format(savePath));
        storageState.putInfo("type", suffix);
        storageState.putInfo("original", originFileName + suffix);
      }
      return storageState;
    }
    catch (IOException localIOException) {}
    return new BaseState(false, 4);
  }
  
  private static boolean validType(String type, String[] allowTypes)
  {
    List<String> list = Arrays.asList(allowTypes);
    
    return list.contains(type);
  }
}
