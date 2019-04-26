package cn.web.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class checkEmail
{
  public boolean checkmail(String email)
  {
    boolean flag = false;
    try
    {
      String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
      Pattern regex = Pattern.compile(check);
      Matcher matcher = regex.matcher(email);
      flag = matcher.matches();
    }
    catch (Exception e)
    {
      flag = false;
    }
    return flag;
  }
}
