package cn.web.control;

import cn.web.model.Gallery;
import cn.web.model.User;
import cn.web.service.GalleryService;
import cn.web.service.UserService;
import cn.web.util.ListGrouper;
import cn.web.util.LoginState;
import com.github.pagehelper.PageInfo;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AjaxGallery {
	@Value("${web.upload-path}")
	private String path;
	@Autowired
	private GalleryService gs;
	@Autowired
	private UserService us;
	@Autowired
	private LoginState loginState;
	private int pageNum;

	@ResponseBody
	@PostMapping({ "/AjaxGallery" })
	public Map<String, Object> AjxGetGallery(@RequestParam("user") String user, HttpServletRequest request) {
		try {
			this.pageNum = Integer.parseInt(request.getParameter("pageNum"));
		} catch (Exception e) {
			this.pageNum = 1;
		}
		Map<String, Object> map = new HashMap<>();
		PageInfo<Gallery> pagegallery = this.gs.getPageGalleryByUser(this.pageNum, 15, "gudeying");
		List<List<Gallery>> gallerys = new ListGrouper().groupListByQuantity(pagegallery.getList(), 3);
		map.put("page", pagegallery);
		map.put("gallerys", gallerys);
		return map;
	}

	@PostMapping({ "/deleteimg" })
	@ResponseBody
	public Map<String, Object> deletePic(@RequestParam("file") int fileid, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		User user = null;
		if (!this.loginState.isIslogin(request)) {
			map.put("result", Boolean.valueOf(false));
			map.put("message", "请登录！");
			return map;
		}
		String ownerName = this.gs.getById(fileid).getUser();
		user = this.loginState.getLoginedUser(request);
		if (!(user.getLevel() > 2 | user.getName().equals(ownerName))) {
			map.put("result", Boolean.valueOf(false));
			map.put("message", "没有权限！");
			return map;
		}
		Gallery gallery = this.gs.getById(fileid);
		File file = new File(this.path + "images/" + gallery.getSrc());
		File comFile = new File(this.path + "images/" + gallery.getComsrc());
		System.out.println(this.path + "/" + gallery.getSrc());
		if ((file.exists()) && (file.isFile())) {
			if ((file.delete()) && (comFile.delete())) {
				this.gs.getDao().deleteById(fileid);
				map.put("result", Boolean.valueOf(true));
				map.put("message", "删除成功！");
			} else {
				map.put("result", Boolean.valueOf(false));
				map.put("message", "删除出错，请稍后重试！");
			}
		} else {
			map.put("result", Boolean.valueOf(false));
			map.put("message", "文件不存在！");
		}
		return map;
	}
}
