package cn.web.control;

import cn.web.model.User;
import cn.web.service.UserService;
import cn.web.util.CookieUtil;
import cn.web.util.uploadFile;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 	建议使用ajaxLogin
 * @author kavingu
 *
 */
@Controller
@Deprecated
public class LoginControl
{
  private String name;
  private String pasw;
  private boolean rember;
  @Autowired
  UserService us;
  @Autowired
  uploadFile uploadFile;
  
  @PostMapping({"/forlogin"})
  public void login(Model model, HttpServletRequest req, HttpServletResponse res)
  {
    this.pasw = req.getParameter("pasw");
    this.name = req.getParameter("name").trim();
    try
    {
      req.getParameter("rember");
      this.rember = true;
    }
    catch (Exception e)
    {
      this.rember = false;
    }
    User user = this.us.getUserByName(this.name);
    if ((user != null) && (this.pasw.trim().equals(user.getPasw())))
    {
      CookieUtil cookie = new CookieUtil();
      try
      {
        cookie.addLoginState(Boolean.valueOf(this.rember), this.name, user.getId(), res, req);
      }
      catch (UnsupportedEncodingException e)
      {
        e.printStackTrace();
      }
      HttpSession session = req.getSession();
      Object url = session.getAttribute("urlback");
      try
      {
        if ((session == null) || (url == null)) {
          res.sendRedirect("/");
        } else {
          res.sendRedirect(url.toString());
        }
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }
    else
    {
      try
      {
        res.getWriter().write("failed");
        res.sendRedirect("/login");
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }
  }
  
  @GetMapping({"/quit"})
  public void quit(HttpServletRequest req, HttpServletResponse res)
  {
    HttpSession session = req.getSession(false);
    session.removeAttribute("user");
    try
    {
      res.sendRedirect(req.getContextPath());
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
}
