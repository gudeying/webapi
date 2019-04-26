package cn.web.control;

import cn.web.model.User;
import cn.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MailBackControl
{
  @Autowired
  private UserService us;
  
  @RequestMapping({"/ensureuser"})
  public String updatelevel(@RequestParam("mail") String mail, @RequestParam("openid") String openid, Model model)
  {
    User user = new User();
    if (!this.us.getUserByMailBox(mail).getOpenid().equals(openid))
    {
      model.addAttribute("text", "确认失败！请不要更改链接");
      model.addAttribute("url", "/");
      return "waystation";
    }
    user.setLevel(1);
    user.setMail(mail);
    this.us.updateUserLevel(user);
    model.addAttribute("text", "你已经成功确认身份，欢迎你！");
    model.addAttribute("url", "/");
    return "waystation";
  }
}
