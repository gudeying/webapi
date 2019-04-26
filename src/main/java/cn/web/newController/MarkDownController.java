package cn.web.newController;

import cn.web.model.Article;
import cn.web.model.ResultMap;
import cn.web.model.User;
import cn.web.service.UserService;
import cn.web.util.LoginState;
import cn.web.util.uploadFile;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class MarkDownController
{
  @Value("${web.upload-path}")
  private String path;
  @Autowired
  private UserService userService;
  @Autowired
  private LoginState loginState;
  @Autowired
  private uploadFile upload;
  
  @RequestMapping({"/markdown/loadImg"})
  public ResultMap AjaxUploadPic(@RequestParam("editormd-image-file") MultipartFile file, HttpServletRequest request)
  {
    ResultMap resultMap = new ResultMap();
    resultMap.setSuccess(1);
    User user = this.loginState.getLoginedUser(request);
    StringBuilder builder = new StringBuilder("/");
    builder.append("markdown/").append(user.getId()).append("/");
    String originFileName = file.getOriginalFilename();
    Date date = new Date(System.currentTimeMillis());
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMddyyyy-HHmmss");
    int random = (int)(Math.random() * 100.0D);
    String tempPath = dateFormat.format(date) + random;
    builder.append(tempPath).append("/");
    String realPath = this.path + builder.toString();
    if (this.upload.upfile(file, realPath, originFileName).booleanValue()) {
      resultMap.setSuccess(1).setMessage("上传成功！").setResult(Boolean.valueOf(true)).setUrl(originFileName);
    } else {
      resultMap.setResult(Boolean.valueOf(false)).setSuccess(0).setMessage("图片上传失败！");
    }
    return resultMap;
  }
  
  public ResultMap MarkDownWrite()
  {
    ResultMap<Article> resultMap = new ResultMap();
    
    return resultMap;
  }
}
