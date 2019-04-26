package cn.web.control;

import cn.web.model.Article;
import cn.web.model.Blog;
import cn.web.model.User;
import cn.web.service.ArticleService;
import cn.web.service.BlogService;
import cn.web.service.UserService;
import cn.web.util.LoginState;
import java.io.IOException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DeleteControl
{
  @Autowired
  private ArticleService as;
  @Autowired
  private BlogService bs;
  @Autowired
  private UserService us;
  @Autowired
  private LoginState loginState;
  
  @RequestMapping({"/deletearticle"})
  public String deletearticle(@RequestParam("id") String id, HttpServletRequest req, HttpServletResponse res, Model model)
  {
    int articleid = Integer.parseInt(id);
    Article article = this.as.getArticleById(articleid);
    Map<String, Object> state = this.loginState.getUserState(req);
    String text = "";
    String url = "/";
    if (((Boolean)state.get("islogin")).booleanValue())
    {
      User user = this.us.getUserByName(state.get("username").toString());
      if ((user.getLevel() == 3) || (user.getName().equals(article.getAuthor())))
      {
        this.as.deleteArticle(articleid);
        text = "删除成功！";
        url = "/article";
      }
      else
      {
        text = "权限不足！";
        url = "/article";
      }
      model.addAttribute("text", text);
      model.addAttribute("url", url);
      return "waystation";
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
  
  @RequestMapping({"/deleteblog"})
  public String deleteblog(@RequestParam("id") String id, HttpServletRequest req, HttpServletResponse res, Model model)
  {
    int blogid = Integer.parseInt(id);
    Blog blog = this.bs.getBlogById(blogid);
    Map<String, Object> state = this.loginState.getUserState(req);
    String text = "";
    String url = "/";
    if (((Boolean)state.get("islogin")).booleanValue())
    {
      User user = this.us.getUserByName(state.get("username").toString());
      if ((user.getLevel() == 3) || (user.getName().equals(blog.getAuthor())))
      {
        this.bs.deleteById(blogid);
        text = "删除成功！";
        url = "/userblog?user=" + blog.getAuthor();
      }
      else
      {
        text = "权限不足！";
        url = "/userblog?user=" + blog.getAuthor();
      }
      model.addAttribute("text", text);
      model.addAttribute("url", url);
      return "waystation";
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
}
