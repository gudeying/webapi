package cn.web.control;

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
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("gallery_cor-old")
@Deprecated
/**
 * 	建议使用newController中的GalleryController
 */
public class GalleryController {
	@Autowired
	private GalleryService gs;
	@Autowired
	private UserService us;
	@Autowired
	private LoginState loginState;
	private int pageNum;

	@RequestMapping({ "/gallery" })
	public String toGallery(Model model, HttpServletRequest req) {
		try {
			this.pageNum = Integer.parseInt(req.getParameter("pageNum"));
		} catch (Exception e) {
			this.pageNum = 1;
		}
		PageInfo<Gallery> pagegallery = this.gs.getPageGalleryByUser(this.pageNum, 15, "gudeying");
		List<List<Gallery>> gallerys = new ListGrouper().groupListByQuantity(pagegallery.getList(), 3);

		model.addAttribute("gallerys", gallerys);
		model.addAttribute("pageNum", Integer.valueOf(pagegallery.getPageNum()));
		model.addAttribute("pageSize", Integer.valueOf(pagegallery.getPageSize()));
		model.addAttribute("isFirstPage", Boolean.valueOf(pagegallery.isIsFirstPage()));
		model.addAttribute("totalPages", Integer.valueOf(pagegallery.getPages()));
		model.addAttribute("isLastPage", Boolean.valueOf(pagegallery.isIsLastPage()));

		Map<String, String> topmap = new HashMap<>();
		topmap.put("href", "");
		topmap.put("text", "画廊");
		topmap.put("src", "images/default.png");
		model.addAttribute("top", topmap);

		String name = "";
		Map<String, Object> map = this.loginState.getUserState(req);
		model.addAttribute("islogin", map.get("islogin"));
		if (((Boolean) map.get("islogin")).booleanValue()) {
			name = map.get("username").toString();
			User user = this.us.getUserByName(name);
			model.addAttribute("user", user);
		}
		return "newGallery";
	}
}
