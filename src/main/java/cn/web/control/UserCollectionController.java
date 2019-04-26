package cn.web.control;

import cn.web.model.Article;
import cn.web.model.User;
import cn.web.service.ArticleService;
import cn.web.service.UserService;
import cn.web.util.LoginState;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserCollectionController
{
  @Autowired
  private LoginState loginState;
  @Autowired
  private ArticleService articleService;
  @Autowired
  private UserService userService;
  
  @GetMapping({"/mylove"})
  public List<Article> getMyCollection(Model model, HttpServletRequest request, HttpServletResponse response)
  {
    Map<String, Object> userState = this.loginState.getUserState(request);
    List<Article> cols = null;
    if (((Boolean)userState.get("islogin")).booleanValue())
    {
      String username = (String)userState.get("username");
      User u = this.userService.getUserByName(username);
      return null;
    }
    try
    {
      response.sendRedirect("/login");
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    return null;
  }
}
