package cn.web.manager;

import cn.web.service.ArticleService;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

public class ManagerTest
{
  @Autowired
  private ArticleService service;
  
  @RequestMapping({"/easyUI"})
  public List getArticle(HttpServletRequest request)
  {
    Map<String, String[]> parameterMap = request.getParameterMap();
    Iterator<Map.Entry<String, String[]>> entries = parameterMap.entrySet().iterator();
    
    String pageNum = request.getParameter("page");
    String pageSize = request.getParameter("rows");
    String sortBy = request.getParameter("sort");
    String order = request.getParameter("order");
    String title = request.getParameter("title");
    System.out.println(title);
    return this.service.getAllArticles();
  }
}
