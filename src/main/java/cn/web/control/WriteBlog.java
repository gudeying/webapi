package cn.web.control;

import cn.web.model.Blog;
import cn.web.service.BlogService;
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
public class WriteBlog
{
  @Autowired
  private BlogService bs;
  @Autowired
  private LoginState loginState;
  
  @RequestMapping(value={"/dealblog"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String dealUpGallery(@RequestParam("src") MultipartFile file, @RequestParam("title") String title, @RequestParam("emotion") String emotion, @RequestParam("content") String detail, HttpServletRequest req, Model model)
  {
    SensitivewordFilter filter = new SensitivewordFilter();
    Set<String> set = filter.getSensitiveWord(detail, 1);
    if (set.size() != 0)
    {
      model.addAttribute("text", "发布失败\n因为包含敏感词：" + set + "[请注意你的言行，如果次数过多将直接封号！]");
      model.addAttribute("url", "/");
      return "waystation";
    }
    Blog b = new Blog();
    String auther = this.loginState.getUserState(req).get("username").toString();
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyy年MM月dd日");
    String time = sdf.format(date);
    b.setTime(time);
    b.setAuthor(auther);
    b.setDetail(detail.trim());
    b.setEmotion(emotion.trim());
    b.setTitle(title.trim());
    String fileName = null;
    if (file.isEmpty())
    {
      int random = (int)(Math.random() * 6.0D);
      fileName = random + ".jpg";
      b.setSrc(fileName);
      this.bs.save(b);
      model.addAttribute("text", "发布成功！");
      model.addAttribute("url", "/userblog?user=" + auther);
      return "waystation";
    }
    fileName = file.getOriginalFilename();
    
    String suffixName = fileName.substring(fileName.lastIndexOf("."));
    
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMddyyyy-HHmmss");
    int random = (int)(Math.random() * 100.0D);
    String Name = "bl" + dateFormat.format(date) + random;
    fileName = Name + suffixName;
    
    String filePath = "/mysite/images/";
    if ((!suffixName.equalsIgnoreCase(".jpg")) && (!suffixName.equalsIgnoreCase(".png")) && (!suffixName.equalsIgnoreCase(".jpeg")))
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
      b.setSrc(fileName);
      this.bs.save(b);
      model.addAttribute("text", "发布成功！");
      model.addAttribute("url", "/userblog?user=" + auther);
      return "waystation";
    }
    catch (IllegalStateException e)
    {
      e.printStackTrace();
      model.addAttribute("text", "上传失败");
      model.addAttribute("url", "/manage?write=writeblog");
      return "waystation";
    }
    catch (IOException e)
    {
      e.printStackTrace();
      model.addAttribute("text", "上传失败");
      model.addAttribute("url", "/manage?write=writeblog");
    }
    return "waystation";
  }
}
