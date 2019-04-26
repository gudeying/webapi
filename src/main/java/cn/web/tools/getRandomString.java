package cn.web.tools;

public class getRandomString
{
  private static String string = "0abc1def2ghi3jkl4mno5pqr6stu7vwx8yz9";
  
  private static int getRandom(int count)
  {
    return (int)Math.round(Math.random() * count);
  }
  
  public static String GetRandomString(int length)
  {
    StringBuffer sb = new StringBuffer();
    int len = string.length();
    for (int i = 0; i < length; i++) {
      sb.append(string.charAt(getRandom(len - 1)));
    }
    return sb.toString();
  }
}
