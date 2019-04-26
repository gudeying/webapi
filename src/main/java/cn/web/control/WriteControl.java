package cn.web.control;

import cn.web.model.Article;
import cn.web.model.Gallery;
import cn.web.model.User;
import cn.web.service.ArticleService;
import cn.web.service.GalleryService;
import cn.web.service.UserService;
import cn.web.util.CutImage;
import cn.web.util.LoginState;
import cn.web.wordfilter.SensitivewordFilter;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@Deprecated
public class WriteControl
{
  @Autowired
  private ArticleService as;
  @Autowired
  private GalleryService gs;
  @Autowired
  private LoginState loginState;
  @Autowired
  private UserService us;
  
  @RequestMapping(value={"/dealwrite"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String dealWrite(@RequestParam("src") MultipartFile file, HttpServletRequest req, Model model)
  {
    String str = req.getParameter("description");
    SensitivewordFilter filter = new SensitivewordFilter();
    Set<String> set = filter.getSensitiveWord(str, 1);
    if (set.size() != 0)
    {
      model.addAttribute("text", "发布失败\n因为包含敏感词：" + set + "[请注意你的言行，如果次数过多将直接封号！]");
      model.addAttribute("url", "/");
      return "waystation";
    }
    String title = req.getParameter("title").trim();
    String author = this.loginState.getUserState(req).get("username").toString();
    String content = req.getParameter("content");
    String description = null;
    User user = this.us.getUserByName(author);
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
    if ((file.isEmpty()) || (user.getLevel() < 2))
    {
      int random = (int)(Math.random() * 6.0D);
      fileName = random + ".jpg";
      article.setSrc(fileName);
      this.as.save(article);
      model.addAttribute("text", "发布成功！");
      model.addAttribute("url", "/article");
      return "waystation";
    }
    fileName = file.getOriginalFilename();
    
    Date date = new Date(System.currentTimeMillis());
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMddyyyy-HHmmss");
    int random = (int)(Math.random() * 100.0D);
    String Name = "ar" + dateFormat.format(date) + random;
    
    String suffixName = fileName.substring(fileName.lastIndexOf("."));
    fileName = Name + suffixName;
    
    String filePath = "/mysite/images/";
    if ((!suffixName.equalsIgnoreCase(".jpg")) && (!suffixName.equalsIgnoreCase(".png")) && 
      (!suffixName.equalsIgnoreCase(".jpeg")))
    {
      model.addAttribute("text", "只允许jpg jpeg和png格式的图片");
      model.addAttribute("url", "/manage?write=writeblog");
      return "waystation";
    }
    File dest = new File(filePath + fileName);
    if (!dest.getParentFile().exists()) {
      dest.getParentFile().mkdirs();
    }
    try
    {
      file.transferTo(dest);
      article.setSrc(fileName);
      this.as.save(article);
      model.addAttribute("text", "发布成功！");
      model.addAttribute("url", "/article");
      return "waystation";
    }
    catch (IllegalStateException e)
    {
      e.printStackTrace();
      model.addAttribute("text", "上传失败");
      model.addAttribute("url", "/manage?write=writearticle");
      return "waystation";
    }
    catch (IOException e)
    {
      e.printStackTrace();
      model.addAttribute("text", "上传失败");
      model.addAttribute("url", "/manage?write=writearticle");
    }
    return "waystation";
  }
  
  @RequestMapping(value={"/dealupgallery"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String dealUpGallery(@RequestParam("picture") MultipartFile picture, @RequestParam("desc") String desc, HttpServletRequest req, Model model)
  {
    User user = this.us.getUserByName(this.loginState.getUserState(req).get("username").toString());
    if ((picture.isEmpty()) || (user.getLevel() < 2))
    {
      model.addAttribute("text", "图片不能为空！");
      model.addAttribute("url", "/manage?write=upgallery");
      return "waystation";
    }
    String fileName = picture.getOriginalFilename();
    String suffixName = fileName.substring(fileName.lastIndexOf("."));
    
    String filePath = "/mysite/images/";
    
    Date date = new Date(System.currentTimeMillis());
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMddyyyy-HHmmss");
    int random = (int)(Math.random() * 100.0D);
    String Name = "ga" + dateFormat.format(date) + random;
    fileName = Name + suffixName;
    
    String cutsrc = filePath + fileName;
    String comfile = "com" + fileName;
    String cutdest = filePath + comfile;
    if ((!suffixName.equalsIgnoreCase(".jpg")) && (!suffixName.equalsIgnoreCase(".png")) && 
      (!suffixName.equalsIgnoreCase(".jpeg")))
    {
      model.addAttribute("text", "只允许jpg jpeg和png格式的图片");
      model.addAttribute("url", "/manage?write=upgallery");
      return "waystation";
    }
    File dest = new File(filePath + fileName);
    if (!dest.getParentFile().exists()) {
      dest.getParentFile().mkdirs();
    }
    try
    {
      picture.transferTo(dest);
      try
      {
        CutImage.zoomImage(cutsrc, cutdest);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
      Gallery g = new Gallery();
      g.setUser(this.loginState.getUserState(req).get("username").toString());
      g.setDes(desc);
      g.setSrc(fileName);
      g.setComsrc(comfile);
      this.gs.save(g);
      
      model.addAttribute("text", "上传成功");
      model.addAttribute("url", "/gallery");
      return "waystation";
    }
    catch (IllegalStateException e)
    {
      e.printStackTrace();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    model.addAttribute("text", "上传失败");
    model.addAttribute("url", "/manage?write=upgallery");
    return "waystation";
  }
}
