package cn.web.control;

import cn.web.model.Article;
import cn.web.service.ArticleService;
import cn.web.service.UserService;
import cn.web.util.LoginState;
import com.github.pagehelper.PageInfo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *  user {@link cn.web.newController.ArticleController}
 */
@Deprecated
@Controller
public class ArticleMapi
{
  @Autowired
  private UserService us;
  @Autowired
  private ArticleService as;
  @Autowired
  private LoginState loginState;
  private int pageNum;
  
  @RequestMapping({"/article"})
  public String article(Model model, HttpServletRequest req)
  {
    try
    {
      this.pageNum = Integer.parseInt(req.getParameter("pageNum"));
    }
    catch (Exception e)
    {
      this.pageNum = 1;
    }
    PageInfo<Article> pageInfo = this.as.getPageArticle(this.pageNum, 5);
    model.addAttribute("pageNum", Integer.valueOf(pageInfo.getPageNum()));
    model.addAttribute("pageSize", Integer.valueOf(pageInfo.getPageSize()));
    model.addAttribute("isFirstPage", Boolean.valueOf(pageInfo.isIsFirstPage()));
    model.addAttribute("totalPages", Integer.valueOf(pageInfo.getPages()));
    model.addAttribute("isLastPage", Boolean.valueOf(pageInfo.isIsLastPage()));
    model.addAttribute("articles", pageInfo.getList());
    model.addAttribute("title", "文章列表");
    Map<String, String> topmap = new HashMap();
    topmap.put("href", "/article");
    topmap.put("text", "zonghengnet");
    topmap.put("src", "images/default.png");
    model = this.loginState.addLoginMessage(model, req);
    model.addAttribute("top", topmap);
    model.addAttribute("topic", null);
    model.addAttribute("username", null);
    
    List hotart = this.as.getHotArticles();
    model.addAttribute("hot", hotart);
    return "newList";
  }
  
  @Deprecated
  @RequestMapping({"/topic"})
  public String getSubjectArticle(@RequestParam("subject") String subject, HttpServletRequest req, Model model)
  {
    try
    {
      this.pageNum = Integer.parseInt(req.getParameter("pageNum"));
    }
    catch (Exception e)
    {
      this.pageNum = 1;
    }
    PageInfo<Article> pageInfo = this.as.getSubjectArticle(this.pageNum, 6, subject);
    model.addAttribute("pageNum", Integer.valueOf(pageInfo.getPageNum()));
    model.addAttribute("pageSize", Integer.valueOf(pageInfo.getPageSize()));
    model.addAttribute("isFirstPage", Boolean.valueOf(pageInfo.isIsFirstPage()));
    model.addAttribute("totalPages", Integer.valueOf(pageInfo.getPages()));
    model.addAttribute("isLastPage", Boolean.valueOf(pageInfo.isIsLastPage()));
    model.addAttribute("articles", pageInfo.getList());
    model.addAttribute("title", subject);
    
    Map<String, String> topmap = new HashMap();
    topmap.put("href", "/article");
    topmap.put("text", subject);
    topmap.put("src", "images/default.png");
    model.addAttribute("top", topmap);
    model = this.loginState.addLoginMessage(model, req);
    model.addAttribute("topic", subject);
    model.addAttribute("username", null);
    List hotart = this.as.getHotArticles();
    model.addAttribute("hot", hotart);
    return "newList";
  }
}
