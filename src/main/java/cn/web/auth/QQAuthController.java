package cn.web.auth;

import cn.web.model.User;
import cn.web.service.UserService;
import cn.web.util.CookieUtil;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/auth"})
public class QQAuthController
{
  @Autowired
  private QQAuthService qqAuthService;
  @Autowired
  private UserService userService;
  @Autowired
  private DownloadByURL download;
  
  @RequestMapping(value={"/qqlogin"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public void qqLogin(HttpServletRequest req, HttpServletResponse res)
    throws Exception
  {
    String uri = this.qqAuthService.getAuthorizationUrl();
    res.sendRedirect(uri);
  }
  
  @RequestMapping({"/qq"})
  public String getQQCode(String code, HttpServletRequest request, HttpServletResponse response, Model model)
    throws Exception
  {
    String accessToken = this.qqAuthService.getAccessToken(code);
    
    Cookie cookie = new Cookie("accessToken", accessToken);
    cookie.setMaxAge(43200);
    response.addCookie(cookie);
    
    String openId = this.qqAuthService.getOpenId(accessToken);
    System.out.println("openid是:" + openId);
    User auser = this.userService.getUserByOpenid(openId);
    if (auser == null)
    {
      JSONObject userinfo = this.qqAuthService.getUserInfo(accessToken, openId);
      System.out.println("user是:" + userinfo);
      User user = new User();
      String username = userinfo.get("nickName").toString();
      if (this.userService.getUserByName(username) != null)
      {
        int random = (int)(Math.random() * 10.0D);
        username = username + random;
      }
      user.setName(username);
      user.setOpenid(openId);
      
      Date date = new Date(System.currentTimeMillis());
      SimpleDateFormat dateFormat = new SimpleDateFormat("MMddyy-HHmmss");
      int random = (int)(Math.random() * 100.0D);
      String Name = dateFormat.format(date) + random + ".jpg";
      this.download.download(userinfo.get("avatar").toString(), "/mysite/images/userlogo/", Name);
      user.setUserlogo(Name);
      user.setPasw(openId);
      this.userService.save(user);
      
      CookieUtil cookieutil = new CookieUtil();
      cookieutil.addLoginState(Boolean.valueOf(true), username, 0, response, request);
      model.addAttribute("username", userinfo.get("nickName").toString());
      model.addAttribute("userlogo", userinfo.get("avatar").toString());
      model.addAttribute("openid", openId);
      return "qqregister";
    }
    CookieUtil cookie1 = new CookieUtil();
    try
    {
      cookie1.addLoginState(Boolean.valueOf(true), auser.getName(), auser.getId(), response, request);
    }
    catch (UnsupportedEncodingException e)
    {
      e.printStackTrace();
    }
    response.sendRedirect("/");
    return null;
  }
}
