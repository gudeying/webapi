package cn.web.newController;

import cn.web.model.Gallery;
import cn.web.model.User;
import cn.web.service.GalleryService;
import cn.web.service.UserService;
import cn.web.util.ListGrouper;
import cn.web.util.LoginState;
import com.github.pagehelper.PageInfo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/gallery"})
public class GalleryController
{
  @Autowired
  private UserService userService;
  @Autowired
  private GalleryService galleryService;
  @Autowired
  private LoginState loginState;
  
  @GetMapping({"/{userId}/{pageNum}"})
  public String userGallery(@PathVariable("userId") int userId, @PathVariable(value="pageNum", required=false) int pageNum, HttpServletRequest request, Model model)
  {
    if (pageNum == 0) {
      pageNum = 1;
    }
    User owner = this.userService.getUserById(userId);
    PageInfo<Gallery> pagegallery = this.galleryService.getPageGalleryByUser(pageNum, 15, owner.getName());
    List<List<Gallery>> gallerys = new ListGrouper().groupListByQuantity(pagegallery.getList(), 3);
    
    model.addAttribute("gallerys", gallerys);
    model.addAttribute("pageNum", Integer.valueOf(pagegallery.getPageNum()));
    model.addAttribute("pageSize", Integer.valueOf(pagegallery.getPageSize()));
    model.addAttribute("isFirstPage", Boolean.valueOf(pagegallery.isIsFirstPage()));
    model.addAttribute("totalPages", Integer.valueOf(pagegallery.getPages()));
    model.addAttribute("isLastPage", Boolean.valueOf(pagegallery.isIsLastPage()));
    model.addAttribute("title", owner.getName() + "的画廊");
    String name = "";
    Map<String, Object> map = this.loginState.getUserState(request);
    model.addAttribute("islogin", map.get("islogin"));
    if (((Boolean)map.get("islogin")).booleanValue())
    {
      name = map.get("username").toString();
      User loginuser = this.userService.getUserByName(name);
      model.addAttribute("user", loginuser);
    }
    Map<String, Object> top = new HashMap<>();
    top.put("src", "/images/userlogo/" + owner.getUserlogo());
    top.put("text", owner.getAutograph() != null ? owner.getAutograph() : "");
    model.addAttribute("top", top);
    model.addAttribute("reqUser", owner);
    return "newUserGallery";
  }
}
