package cn.web.control;

import cn.web.model.Article;
import cn.web.service.ArticleService;
import cn.web.service.UserService;
import cn.web.util.LoginState;
import com.github.pagehelper.PageInfo;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ForSearch
{
  @Autowired
  private ArticleService as;
  @Autowired
  private UserService us;
  @Autowired
  private LoginState loginState;
  private int pageNum;
  
  @RequestMapping(value={"/forsearch"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String Search(HttpServletRequest req, Model model)
  {
    String value = req.getParameter("search").trim();
    String title = "%" + value + "%";
    try
    {
      this.pageNum = Integer.parseInt(req.getParameter("pageNum"));
    }
    catch (Exception e)
    {
      this.pageNum = 1;
    }
    PageInfo<Article> pageInfo = this.as.getLikeArticle(this.pageNum, 8, title);
    model.addAttribute("pageNum", Integer.valueOf(pageInfo.getPageNum()));
    model.addAttribute("pageSize", Integer.valueOf(pageInfo.getPageSize()));
    model.addAttribute("isFirstPage", Boolean.valueOf(pageInfo.isIsFirstPage()));
    model.addAttribute("totalPages", Integer.valueOf(pageInfo.getPages()));
    model.addAttribute("isLastPage", Boolean.valueOf(pageInfo.isIsLastPage()));
    model.addAttribute("articles", pageInfo.getList());
    model.addAttribute("search", value);
    
    model = this.loginState.addLoginMessage(model, req);
    return "searchresult";
  }
}
