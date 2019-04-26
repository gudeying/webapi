package cn.web.control;

import cn.web.model.User;
import cn.web.service.UserService;
import cn.web.util.LoginState;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CorMapping
{
  @Autowired
  private LoginState loginstate;
  @Autowired
  private UserService us;
  
  @RequestMapping({"/login"})
  public String tologin(HttpServletRequest req, Model model)
  {
    Map<String, Object> map = this.loginstate.getUserState(req);
    model.addAttribute("islogin", map.get("islogin"));
    String name = "";
    if (((Boolean)map.get("islogin")).booleanValue())
    {
      name = map.get("username").toString();
      User user = this.us.getUserByName(name);
      model.addAttribute("user", user);
    }
    String back = "/";
    String s = req.getParameter("back");
    if (s != null) {
      back = "/" + s;
    }
    HttpSession session = req.getSession();
    if ((session == null) || (session.getAttribute("urlback") == null)) {
      session.setAttribute("urlback", back);
    }
    return "login";
  }
  
  public String writearticle(Model model)
  {
    model.addAttribute("text", "出现错误，请联系管理员");
    model.addAttribute("url", "/");
    
    return "waystation";
  }
  
  @RequestMapping({"/qqregister"})
  public String register()
  {
    return "/auth/qqLoginPage";
  }
}
