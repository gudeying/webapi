package cn.web.control;

import cn.web.model.Article;
import cn.web.model.Blog;
import cn.web.model.Gallery;
import cn.web.service.ArticleService;
import cn.web.service.BlogService;
import cn.web.service.GalleryService;
import cn.web.service.UserService;
import cn.web.util.ListGrouper;
import cn.web.util.LoginState;
import com.github.pagehelper.PageInfo;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController
{
  @Autowired
  private BlogService bs;
  @Autowired
  private ArticleService as;
  @Autowired
  private GalleryService gs;
  @Autowired
  private UserService us;
  @Autowired
  private LoginState loginState;
  private List<Article> articles;
  private List<Blog> blogs;
  
  @RequestMapping({"/"})
  public String index(Model model, HttpServletRequest req)
  {
    this.articles = this.as.getPageArticle(1, 6).getList();
    this.blogs = this.bs.getPageBlogByUser(1, 5, "gudeying").getList();
    
    model = this.loginState.addLoginMessage(model, req);
    List<Gallery> gallery = this.gs.getLast6Gallery();
    List<List<Gallery>> gallerys = new ListGrouper().groupListByQuantity(gallery, 3);
    model.addAttribute("gallerys", gallerys);
    
    model.addAttribute("articles", this.articles);
    model.addAttribute("blogs", this.blogs);
    List<Article> hotart = this.as.getHotArticles();
    model.addAttribute("hot", hotart);
    
    model.addAttribute("topic", null);
    model.addAttribute("username", null);
    return "newCenter";
  }
}
