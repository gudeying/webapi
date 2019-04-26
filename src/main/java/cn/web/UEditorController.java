package cn.web;

import cn.baidu.ueditor.ActionEnter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UEditorController
{
  @RequestMapping({"/config"})
  public void config(HttpServletRequest request, HttpServletResponse response)
    throws JSONException
  {
    response.setContentType("application/json");
    String rootPath = request.getSession().getServletContext().getRealPath("/");
    try
    {
      String exec = new ActionEnter(request, rootPath).exec();
      PrintWriter writer = response.getWriter();
      writer.write(exec);
      writer.flush();
      writer.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
  
  @PostMapping({"/dealform"})
  public String dealform(HttpServletRequest req)
  {
    String content = req.getParameter("content");
    String title = req.getParameter("title");
    String description = req.getParameter("description");
    System.out.println(content + "\n" + title + "\n" + description);
    return "detail";
  }
}
