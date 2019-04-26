package cn.web.newController;

import cn.web.model.Article;
import cn.web.model.User;
import cn.web.service.EnshrineService;
import cn.web.util.LoginState;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class StarController
{
  @Autowired
  private EnshrineService service;
  @Autowired
  private LoginState loginState;
  
  @GetMapping({"/{userId}/favorite"})
  public String getStarList(@PathVariable("userId") int userId, HttpServletRequest request, Model model)
  {
    User currentUser = this.loginState.getLoginedUser(request);
    if ((currentUser != null) && (currentUser.getId() == userId))
    {
      model = this.loginState.addLoginMessage(model, request);
      List<Article> articles = this.service.getEnshrinedArticle(String.valueOf(userId));
      model.addAttribute("articles", articles);
      return "starList";
    }
    return null;
  }
}
