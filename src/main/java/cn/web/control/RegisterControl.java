package cn.web.control;

import cn.web.model.User;
import cn.web.service.UserService;
import cn.web.tools.HttpClientForMail;
import cn.web.tools.checkEmail;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterControl
{
  @Autowired
  private UserService us;
  
  @GetMapping({"/register"})
  public String toRegister()
  {
    return "register";
  }
  
  @PostMapping({"/forregister"})
  public String forregister(HttpServletRequest req, HttpServletResponse res, Model model)
  {
    String mail = req.getParameter("mail");
    String pwd = req.getParameter("pwd");
    String name = req.getParameter("name");
    
    checkEmail check = new checkEmail();
    if (!check.checkmail(mail))
    {
      model.addAttribute("url", "/register");
      model.addAttribute("text", "邮箱不正确");
      return "waystation";
    }
    if (this.us.getUserByMailBox(mail) != null)
    {
      model.addAttribute("url", "/register");
      model.addAttribute("text", "邮箱已经注册过了，请直接登陆，忘记密码或者申诉请联系管理员");
      return "waystation";
    }
    if (this.us.getUserByName(name) != null)
    {
      model.addAttribute("url", "/register");
      model.addAttribute("text", "昵称已经被占用！");
      return "waystation";
    }
    String openid = null;
    HttpClientForMail formail = new HttpClientForMail();
    try
    {
      openid = formail.sendMail(mail);
    }
    catch (IOException e)
    {
      model.addAttribute("url", "/register");
      model.addAttribute("text", "邮件发送失败，请重试！");
      return "waystation";
    }
    if (openid != null)
    {
      User user = new User();
      user.setMail(mail);
      user.setName(name);
      user.setOpenid(openid);
      user.setPasw(pwd);
      user.setLevel(0);
      user.setUserlogo("defaultuser.jpg");
      this.us.save(user);
    }
    model.addAttribute("url", "/login");
    model.addAttribute("text", name + "注册成功,尽快登陆邮箱进行确认才能获得写文章的权限，现在请登录");
    return "waystation";
  }
}
