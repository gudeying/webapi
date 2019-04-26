package cn.web.wordfilter;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class SensitivewordFilter
{
  private Map sensitiveWordMap = null;
  public static int minMatchTYpe = 1;
  public static int maxMatchType = 2;
  
  public SensitivewordFilter()
  {
    this.sensitiveWordMap = new SensitiveWordInit().initKeyWord();
  }
  
  public boolean isContaintSensitiveWord(String txt, int matchType)
  {
    boolean flag = false;
    for (int i = 0; i < txt.length(); i++)
    {
      int matchFlag = CheckSensitiveWord(txt, i, matchType);
      if (matchFlag > 0) {
        flag = true;
      }
    }
    return flag;
  }
  
  public Set<String> getSensitiveWord(String txt, int matchType)
  {
    Set<String> sensitiveWordList = new HashSet();
    for (int i = 0; i < txt.length(); i++)
    {
      int length = CheckSensitiveWord(txt, i, matchType);
      if (length > 0)
      {
        sensitiveWordList.add(txt.substring(i, i + length));
        i = i + length - 1;
      }
    }
    return sensitiveWordList;
  }
  
  public String replaceSensitiveWord(String txt, int matchType, String replaceChar)
  {
    String resultTxt = txt;
    Set<String> set = getSensitiveWord(txt, matchType);
    Iterator<String> iterator = set.iterator();
    String word = null;
    String replaceString = null;
    while (iterator.hasNext())
    {
      word = (String)iterator.next();
      replaceString = getReplaceChars(replaceChar, word.length());
      resultTxt = resultTxt.replaceAll(word, replaceString);
    }
    return resultTxt;
  }
  
  private String getReplaceChars(String replaceChar, int length)
  {
    String resultReplace = replaceChar;
    for (int i = 1; i < length; i++) {
      resultReplace = resultReplace + replaceChar;
    }
    return resultReplace;
  }
  
  public int CheckSensitiveWord(String txt, int beginIndex, int matchType)
  {
    boolean flag = false;
    int matchFlag = 0;
    char word = '\000';
    Map nowMap = this.sensitiveWordMap;
    for (int i = beginIndex; i < txt.length(); i++)
    {
      word = txt.charAt(i);
      nowMap = (Map)nowMap.get(Character.valueOf(word));
      if (nowMap == null) {
        break;
      }
      matchFlag++;
      if ("1".equals(nowMap.get("isEnd")))
      {
        flag = true;
        if (minMatchTYpe == matchType) {
          break;
        }
      }
    }
    if ((matchFlag < 2) || (!flag)) {
      matchFlag = 0;
    }
    return matchFlag;
  }
}
