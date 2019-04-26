package cn.web.util;

import cn.web.model.Article;
import cn.web.model.ResultMap;
import cn.web.model.User;
import cn.web.service.ArticleService;
import cn.web.service.UserService;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class WriteArticleUtil
{
  private ResultMap<Object> resultMap = new ResultMap();
  @Value("${web.upload-path}")
  private String path;
  @Autowired
  private UserService userService;
  @Autowired
  private LoginState loginState;
  @Autowired
  private ArticleService articleService;
  @Autowired
  private uploadFile upload;
  
  public ResultMap utilWrite(MultipartFile file, HttpServletRequest req)
  {
    if (!this.loginState.isIslogin(req))
    {
      this.resultMap.setResult(Boolean.valueOf(false));
      this.resultMap.setMessage("请登录！");
      return this.resultMap;
    }
    String str = req.getParameter("description");
    
    String title = req.getParameter("title").trim();
    String author = this.loginState.getUserState(req).get("username").toString();
    String content = req.getParameter("content");
    String description = null;
    User user = this.loginState.getLoginedUser(req);
    String subject = req.getParameter("subject");
    if (str.length() < 195) {
      description = str;
    } else {
      description = str.substring(0, 190);
    }
    Article article = new Article();
    Date adate = new Date();
    String time = new SimpleDateFormat("yyyMMdd-HH:mm").format(adate);
    article.setSubject(subject);
    article.setTime(time);
    article.setTitle(title);
    article.setAuthor(author);
    article.setContent(content);
    article.setDescription(description);
    
    String fileName = null;
    if ((null == file) || (file.isEmpty()) || (user.getLevel() < 2))
    {
      int random = (int)(Math.random() * 6.0D);
      fileName = random + ".jpg";
      article.setSrc(fileName);
      int newId = this.articleService.saveAndReturn(article);
      this.resultMap.setResult(Boolean.valueOf(true));
      this.resultMap.setData(this.articleService.getArticleById(newId));
      this.resultMap.setMessage("发布成功");
      return this.resultMap;
    }
    fileName = file.getOriginalFilename();
    
    Date date = new Date(System.currentTimeMillis());
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMddyyyy-HHmmss");
    int random = (int)(Math.random() * 100.0D);
    String Name = "ar" + dateFormat.format(date) + random;
    
    String suffixName = fileName.substring(fileName.lastIndexOf("."));
    fileName = Name + suffixName;
    
    String filePath = this.path + "images/";
    if ((!suffixName.equalsIgnoreCase(".jpg")) && (!suffixName.equalsIgnoreCase(".png")) && 
      (!suffixName.equalsIgnoreCase(".jpeg")))
    {
      this.resultMap.setResult(Boolean.valueOf(false));
      this.resultMap.setMessage("不被允许的图片格式！");
      return this.resultMap;
    }
    if (this.upload.upfile(file, filePath, fileName).booleanValue()) {}
    return null;
  }
}
