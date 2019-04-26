package cn.web.control;

import cn.web.model.Article;
import cn.web.model.Blog;
import cn.web.model.Gallery;
import cn.web.model.User;
import cn.web.service.ArticleService;
import cn.web.service.BlogService;
import cn.web.service.GalleryService;
import cn.web.service.UserService;
import cn.web.util.ListGrouper;
import cn.web.util.LoginState;
import cn.web.util.uploadFile;
import com.github.pagehelper.PageInfo;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@Deprecated
public class UserHome
{
  @Autowired
  private UserService us;
  @Autowired
  private BlogService bs;
  @Autowired
  private ArticleService as;
  @Autowired
  private GalleryService gs;
  @Autowired
  private LoginState loginState;
  @Autowired
  private uploadFile uploadFile;
  
  @RequestMapping({"/usercenter"})
  public String UserCenter(@RequestParam("user") String username, HttpServletRequest req, Model model)
  {
    PageInfo<Article> articles = this.as.getPageArticleByUser(1, 6, username);
    PageInfo<Blog> blogs = this.bs.getPageBlogByUser(1, 3, username);
    PageInfo<Gallery> gallerypage = this.gs.getPageGalleryByUser(1, 9, username);
    List<List<Gallery>> gallerys = new ListGrouper().groupListByQuantity(gallerypage.getList(), 3);
    
    model.addAttribute("gallerys", gallerys);
    
    model.addAttribute("articles", articles.getList());
    model.addAttribute("blogs", blogs.getList());
    model.addAttribute("auser", this.us.getUserByName(username));
    model.addAttribute("title", username + "的全部文章");
    
    User requser = this.us.getUserByName(username);
    Map<String, String> topmap = new HashMap();
    topmap.put("href", "/usercenter?user=" + username);
    topmap.put("text", username);
    topmap.put("src", "images/userlogo/" + this.us.getUserByName(username).getUserlogo());
    topmap.put("autograph", requser.getAutograph());
    
    String name = "";
    Map<String, Object> map = this.loginState.getUserState(req);
    model.addAttribute("islogin", map.get("islogin"));
    if (((Boolean)map.get("islogin")).booleanValue())
    {
      name = map.get("username").toString();
      User user = this.us.getUserByName(name);
      model.addAttribute("user", user);
    }
    model.addAttribute("top", topmap);
    List<Article> hotArt = this.as.getUserHotArticles(this.us.getUserByName(username).getId());
    model.addAttribute("topic", null);
    model.addAttribute("username", username);
    model.addAttribute("hot", hotArt);
    return "userhome";
  }
  
  @RequestMapping({"/userarticle"})
  public String userartice(@RequestParam("user") String username, HttpServletRequest req, Model model)
  {
    int pageNum = 1;
    try
    {
      pageNum = Integer.parseInt(req.getParameter("pageNum"));
    }
    catch (Exception e)
    {
      pageNum = 1;
    }
    PageInfo<Article> pageInfo = this.as.getPageArticleByUser(pageNum, 8, username);
    model.addAttribute("pageNum", Integer.valueOf(pageInfo.getPageNum()));
    model.addAttribute("pageSize", Integer.valueOf(pageInfo.getPageSize()));
    model.addAttribute("isFirstPage", Boolean.valueOf(pageInfo.isIsFirstPage()));
    model.addAttribute("totalPages", Integer.valueOf(pageInfo.getPages()));
    model.addAttribute("isLastPage", Boolean.valueOf(pageInfo.isIsLastPage()));
    model.addAttribute("articles", pageInfo.getList());
    model.addAttribute("title", username + "的文章");
    
    Map<String, String> topmap = new HashMap();
    topmap.put("href", "/usercenter?user=" + username);
    topmap.put("text", username);
    topmap.put("src", "images/userlogo/" + this.us.getUserByName(username).getUserlogo());
    
    User requser = this.us.getUserByName(username);
    topmap.put("autograph", requser.getAutograph());
    String name = "";
    Map<String, Object> map = this.loginState.getUserState(req);
    model.addAttribute("islogin", map.get("islogin"));
    if (((Boolean)map.get("islogin")).booleanValue())
    {
      name = map.get("username").toString();
      User user = this.us.getUserByName(name);
      model.addAttribute("user", user);
    }
    model.addAttribute("top", topmap);
    model.addAttribute("topic", null);
    model.addAttribute("author", username);
    model.addAttribute("username", username);
    
    List hotart = this.as.getHotArticles();
    model.addAttribute("hot", hotart);
    return "newList";
  }
  
  @RequestMapping({"/usertopic"})
  public String getAuthorSubject(@RequestParam("subject") String subject, @RequestParam("author") String author, HttpServletRequest req, Model model)
  {
    int pageNum = 1;
    try
    {
      pageNum = Integer.parseInt(req.getParameter("pageNum"));
    }
    catch (Exception e)
    {
      pageNum = 1;
    }
    PageInfo<Article> pageInfo = this.as.getBySubjectAuthor(pageNum, 6, author, subject);
    model.addAttribute("pageNum", Integer.valueOf(pageInfo.getPageNum()));
    model.addAttribute("pageSize", Integer.valueOf(pageInfo.getPageSize()));
    model.addAttribute("isFirstPage", Boolean.valueOf(pageInfo.isIsFirstPage()));
    model.addAttribute("totalPages", Integer.valueOf(pageInfo.getPages()));
    model.addAttribute("isLastPage", Boolean.valueOf(pageInfo.isIsLastPage()));
    model.addAttribute("articles", pageInfo.getList());
    model.addAttribute("title", subject);
    
    Map<String, String> topmap = new HashMap();
    User requser = this.us.getUserByName(author);
    topmap.put("autograph", requser.getAutograph());
    topmap.put("href", "/usercenter?user=" + author);
    topmap.put("text", author + "--" + subject);
    topmap.put("src", "images/userlogo/" + this.us.getUserByName(author).getUserlogo());
    
    String name = "";
    Map<String, Object> map = this.loginState.getUserState(req);
    model.addAttribute("islogin", map.get("islogin"));
    if (((Boolean)map.get("islogin")).booleanValue())
    {
      name = map.get("username").toString();
      User user = this.us.getUserByName(name);
      model.addAttribute("user", user);
    }
    model.addAttribute("top", topmap);
    model.addAttribute("topic", subject);
    model.addAttribute("author", author);
    model.addAttribute("subject", subject);
    model.addAttribute("username", author);
    
    List hotart = this.as.getHotArticles();
    model.addAttribute("hot", hotart);
    return "newList";
  }
  
  @RequestMapping({"/userblog"})
  public String userblog(@RequestParam("user") String username, HttpServletRequest req, Model model)
  {
    int pageNum = 1;
    try
    {
      pageNum = Integer.parseInt(req.getParameter("pageNum"));
    }
    catch (Exception e)
    {
      pageNum = 1;
    }
    PageInfo<Blog> pageInfo = this.bs.getPageBlogByUser(pageNum, 8, username);
    model.addAttribute("pageNum", Integer.valueOf(pageInfo.getPageNum()));
    model.addAttribute("pageSize", Integer.valueOf(pageInfo.getPageSize()));
    model.addAttribute("isFirstPage", Boolean.valueOf(pageInfo.isIsFirstPage()));
    model.addAttribute("totalPages", Integer.valueOf(pageInfo.getPages()));
    model.addAttribute("isLastPage", Boolean.valueOf(pageInfo.isIsLastPage()));
    model.addAttribute("blogs", pageInfo.getList());
    model.addAttribute("title", username + "的全部博客");
    
    Map<String, String> topmap = new HashMap();
    topmap.put("href", "/usercenter?user=" + username);
    topmap.put("text", username);
    topmap.put("src", "images/userlogo/" + this.us.getUserByName(username).getUserlogo());
    
    String name = "";
    Map<String, Object> map = this.loginState.getUserState(req);
    model.addAttribute("islogin", map.get("islogin"));
    if (((Boolean)map.get("islogin")).booleanValue())
    {
      name = map.get("username").toString();
      User user = this.us.getUserByName(name);
      model.addAttribute("user", user);
    }
    model.addAttribute("top", topmap);
    return "bloglist";
  }
  
  @RequestMapping({"/usergallery"})
  public String usergallery(@RequestParam("user") String username, HttpServletRequest req, Model model)
  {
    int pageNum = 1;
    try
    {
      pageNum = Integer.parseInt(req.getParameter("pageNum"));
    }
    catch (Exception e)
    {
      pageNum = 1;
    }
    PageInfo<Gallery> pagegallery = this.gs.getPageGalleryByUser(pageNum, 15, username);
    List<List<Gallery>> gallerys = new ListGrouper().groupListByQuantity(pagegallery.getList(), 3);
    
    model.addAttribute("gallerys", gallerys);
    model.addAttribute("pageNum", Integer.valueOf(pagegallery.getPageNum()));
    model.addAttribute("pageSize", Integer.valueOf(pagegallery.getPageSize()));
    model.addAttribute("isFirstPage", Boolean.valueOf(pagegallery.isIsFirstPage()));
    model.addAttribute("totalPages", Integer.valueOf(pagegallery.getPages()));
    model.addAttribute("isLastPage", Boolean.valueOf(pagegallery.isIsLastPage()));
    model.addAttribute("title", username + "的相册");
    
    Map<String, String> topmap = new HashMap();
    topmap.put("href", "/usercenter?user=" + username);
    topmap.put("text", username);
    topmap.put("src", "images/userlogo/" + this.us.getUserByName(username).getUserlogo());
    
    String name = "";
    Map<String, Object> map = this.loginState.getUserState(req);
    model.addAttribute("islogin", map.get("islogin"));
    if (((Boolean)map.get("islogin")).booleanValue())
    {
      name = map.get("username").toString();
      User user = this.us.getUserByName(name);
      model.addAttribute("user", user);
    }
    model.addAttribute("top", topmap);
    return "gallerylist";
  }
  
  @GetMapping({"/completeuser"})
  public String completeuser(HttpServletRequest req, HttpServletResponse res, Model model)
  {
    Map<String, Object> map = this.loginState.getUserState(req);
    model.addAttribute("islogin", map.get("islogin"));
    if (((Boolean)map.get("islogin")).booleanValue())
    {
      String name = map.get("username").toString();
      User user = this.us.getUserByName(name);
      model.addAttribute("user", user);
      return "updateuser";
    }
    try
    {
      res.sendRedirect("/login");
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    return null;
  }
  
  @PostMapping({"/updateuser"})
  public String updateuser(@RequestParam("logo") MultipartFile file, HttpServletRequest req, HttpServletResponse res, Model model)
    throws IOException
  {
    Map<String, Object> state = this.loginState.getUserState(req);
    User user = this.us.getUserByName(state.get("username").toString());
    User auser = new User();
    if (((Boolean)state.get("islogin")).booleanValue())
    {
      String s = req.getParameter("age").trim();
      if (s.length() > 0)
      {
        int age = Integer.parseInt(s);
        auser.setAge(age);
      }
      String sex = req.getParameter("sex");
      
      auser.setSex(sex);
      auser.setId(user.getId());
      String text = "更改成功";
      if (!file.isEmpty())
      {
        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        if ((suffixName.equalsIgnoreCase(".jpg")) || (suffixName.equalsIgnoreCase(".png")) || (suffixName.equalsIgnoreCase(".jpeg")))
        {
          SimpleDateFormat dateFormat = new SimpleDateFormat("MMddyyyy-HHmmss");
          int random = (int)(Math.random() * 100.0D);
          Date date = new Date();
          String Name = dateFormat.format(date) + random;
          String filepath = "/mysite/images/userlogo/";
          fileName = Name + suffixName;
          if (this.uploadFile.upfile(file, filepath, fileName).booleanValue()) {
            auser.setUserlogo(fileName);
          } else {
            text = "头像上传失败，图片不能超过5M";
          }
        }
        else
        {
          text = "只允许jpg jpeg和png格式的图片";
        }
      }
      this.us.UpdateUser(auser);
      model.addAttribute("text", text);
      model.addAttribute("url", "/usercenter?user=" + user.getName());
      return "waystation";
    }
    res.sendRedirect("/login");
    return null;
  }
}
