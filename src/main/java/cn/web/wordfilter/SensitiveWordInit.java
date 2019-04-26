package cn.web.wordfilter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class SensitiveWordInit
{
  private String ENCODING = "UTF-8";
  public HashMap sensitiveWordMap;
  
  public Map initKeyWord()
  {
    try
    {
      Set<String> keyWordSet = readSensitiveWordFile();
      
      addSensitiveWordToHashMap(keyWordSet);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return this.sensitiveWordMap;
  }
  
  private void addSensitiveWordToHashMap(Set<String> keyWordSet)
  {
    this.sensitiveWordMap = new HashMap(keyWordSet.size());
    String key = null;
    Map nowMap = null;
    Map<String, String> newWorMap = null;
    
    Iterator<String> iterator = keyWordSet.iterator();
    while (iterator.hasNext())
    {
      key = (String)iterator.next();
      nowMap = this.sensitiveWordMap;
      for (int i = 0; i < key.length(); i++)
      {
        char keyChar = key.charAt(i);
        Object wordMap = nowMap.get(Character.valueOf(keyChar));
        if (wordMap != null)
        {
          nowMap = (Map)wordMap;
        }
        else
        {
          newWorMap = new HashMap();
          newWorMap.put("isEnd", "0");
          nowMap.put(Character.valueOf(keyChar), newWorMap);
          nowMap = newWorMap;
        }
        if (i == key.length() - 1) {
          nowMap.put("isEnd", "1");
        }
      }
    }
  }
  
  private Set<String> readSensitiveWordFile()
    throws Exception
  {
    Set<String> set = null;
    
    File file = new File("/mysite/SensitiveWord.txt");
    InputStreamReader read = new InputStreamReader(new FileInputStream(file), this.ENCODING);
    try
    {
      if ((file.isFile()) && (file.exists()))
      {
        set = new HashSet();
        BufferedReader bufferedReader = new BufferedReader(read);
        String txt = null;
        while ((txt = bufferedReader.readLine()) != null) {
          set.add(txt);
        }
      }
      else
      {
        throw new Exception("敏感词库文件不存在");
      }
    }
    catch (Exception e)
    {
      throw e;
    }
    finally
    {
      read.close();
    }
    return set;
  }
}
