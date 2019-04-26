package cn.web.control;

import cn.web.model.User;
import cn.web.service.UserService;
import cn.web.util.LoginState;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Deprecated
public class ManageMent
{
  @Autowired
  private UserService us;
  @Autowired
  private LoginState loginState;
  private String name;
  
  @RequestMapping({"/manage{write}"})
  public String ManageWrite(@RequestParam("write") String write, HttpServletRequest req, Model model)
  {
    Map<String, Object> map = this.loginState.getUserState(req);
    model.addAttribute("islogin", map.get("islogin"));
    if (((Boolean)map.get("islogin")).booleanValue())
    {
      this.name = map.get("username").toString();
      User user = this.us.getUserByName(this.name);
      model.addAttribute("user", user);
      if ((write.equals("upgallery")) && (user.getLevel() < 3))
      {
        model.addAttribute("user", user);
        model.addAttribute("text", "您还没有该权限，请联系管理员进行申请");
        model.addAttribute("url", "/");
        return "waystation";
      }
      if ((write.equals("writearticle")) && (user.getLevel() < 1))
      {
        model.addAttribute("text", "您还没有该权限，请联系管理员进行申请");
        model.addAttribute("url", "/");
        return "waystation";
      }
      if ((write.equals("writeblog")) && (user.getLevel() < 1))
      {
        model.addAttribute("text", "您还没有该权限，请联系管理员进行申请");
        model.addAttribute("url", "/");
        return "waystation";
      }
      return write;
    }
    return "login";
  }
}
