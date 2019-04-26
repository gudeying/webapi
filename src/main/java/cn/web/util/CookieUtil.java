package cn.web.util;

import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CookieUtil
{
  public void addLoginState(Boolean rember, String username, int id, HttpServletResponse response, HttpServletRequest request)
    throws UnsupportedEncodingException
  {
    HttpSession session = request.getSession();
    session.setAttribute("user", username);
  }
}
