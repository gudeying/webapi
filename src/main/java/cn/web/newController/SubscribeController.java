package cn.web.newController;

import cn.web.model.Article;
import cn.web.model.User;
import cn.web.service.EnshrineService;
import cn.web.service.SubscribeService;
import cn.web.util.LoginState;
import java.io.IOException;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class SubscribeController
{
  @Autowired
  private SubscribeService subscribeService;
  @Autowired
  private LoginState loginState;
  @Autowired
  private EnshrineService enshrineService;
  
  @GetMapping({"/subscribe/{userId}/readList"})
  public String mySubscribedToReadList(@PathVariable("userId") int userId, HttpServletRequest request, HttpServletResponse response, Model model)
  {
    if (!this.loginState.isIslogin(request)) {
      try
      {
        response.sendRedirect("/");
        return null;
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }
    User loginedUser = this.loginState.getLoginedUser(request);
    if (loginedUser.getId() != userId) {
      try
      {
        response.sendRedirect("/subscribe/" + loginedUser.getId() + "/readList");
        return null;
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }
    Set<Article> articles = this.subscribeService.getToReadList(loginedUser.getId());
    int toReadSize = this.subscribeService.toReadeSize(loginedUser.getId());
    int choucangNum = this.enshrineService.getCount(String.valueOf(loginedUser.getId()));
    model.addAttribute("islogin", Boolean.valueOf(true));
    model.addAttribute("title", "订阅动态");
    model.addAttribute("user", loginedUser);
    model.addAttribute("readList", articles);
    model.addAttribute("toReadSize", Integer.valueOf(toReadSize));
    model.addAttribute("choucangNum", Integer.valueOf(choucangNum));
    return "subscribeList";
  }
}
