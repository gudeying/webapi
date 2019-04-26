package cn.web.control;

import cn.web.model.User;
import cn.web.service.UserService;
import cn.web.tools.getRandomString;
import cn.web.util.LoginState;
import cn.web.util.uploadFile;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class AjaxUserDetail
{
  @Value("${web.upload-path}")
  private String path;
  @Autowired
  private UserService userService;
  @Autowired
  private LoginState loginState;
  @Autowired
  private uploadFile uploadFile;
  
  @PostMapping({"/userDetail"})
  public Map<String,Object> updateUser(@RequestParam(value="userLogo", required=false) MultipartFile file, @RequestParam(value="userName", required=false) String userName, @RequestParam(value="userAge", required=false) Object age, @RequestParam(value="userEmail", required=false) String email, @RequestParam(value="userAutographContent", required=false) String autograph, HttpServletRequest request)
  {
    Map<String, Object> map = null;
    try
    {
      map = new HashMap<>();
      if (!this.loginState.isIslogin(request))
      {
        map.put("result", Boolean.valueOf(false));
        map.put("message", "请登陆！");
        return map;
      }
      User loginUser = this.loginState.getLoginedUser(request);
      User user = new User();
      user.setId(loginUser.getId());
      if (null != userName) {
        user.setName(userName);
      }
      if (null != email) {
        user.setMail(email);
      }
      if (null != age) {
        user.setAge(Integer.parseInt(String.valueOf(age)));
      }
      if (null != autograph) {
        user.setAutograph(autograph);
      }
      String fileName = "";
      if (null != file)
      {
        fileName = file.getOriginalFilename();
        String userUsedLogo = this.path + "images/userlogo/" + user.getUserlogo();
        File file1 = new File(userUsedLogo);
        if (file1.isFile()) {
          file1.delete();
        }
        String randomString = getRandomString.GetRandomString(10);
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMddyyyy-HHmmss");
        Date date = new Date();
        String newFileName = dateFormat.format(date) + randomString;
        fileName = newFileName + suffixName;
        user.setUserlogo(fileName);
        
        String filePath = this.path + "images/userlogo/";
        Boolean loadResult = this.uploadFile.upfile(file, filePath, fileName);
        if (!loadResult.booleanValue())
        {
          map.put("result", Boolean.valueOf(false));
          map.put("message", "文件上传失败！");
          return map;
        }
      }
      this.userService.UpdateUser(user);
      map.put("result", Boolean.valueOf(true));
    }
    catch (NumberFormatException e)
    {
      map.put("result", Boolean.valueOf(false));
      map.put("message", "年龄格式错误！");
    }
    return map;
  }
  
  @PostMapping({"/getUserInfo"})
  public Map<String, Object> getUserinfo(@RequestParam("userId") int targetUserId, HttpServletRequest request)
  {
    Map<String, Object> resultMap = new HashMap<>();
    if (!this.loginState.isIslogin(request))
    {
      resultMap.put("result", Boolean.valueOf(false));
      resultMap.put("message", "没有权限！");
      return resultMap;
    }
    User targetUser = this.userService.getUserById(targetUserId);
    targetUser.setPasw("看不见密码");
    targetUser.setMail("email");
    targetUser.setLevel(0);
    
    resultMap.put("result", Boolean.valueOf(true));
    resultMap.put("data", targetUser);
    return resultMap;
  }
}
