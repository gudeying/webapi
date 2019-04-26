package cn.web.newController;

import cn.web.model.Article;
import cn.web.model.User;
import cn.web.rabbitMQ.MQSender;
import cn.web.service.ArticleService;
import cn.web.service.UserService;
import cn.web.util.LoginState;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class WriteController {
	@Value("${web.upload-path}")
	private String path;
	@Autowired
	private MQSender mqSender;
	@Autowired
	private UserService us;
	@Autowired
	private LoginState loginState;
	private String name;
	@Autowired
	private ArticleService articleService;

	@GetMapping({ "/write" })
	public String toWrite(HttpServletRequest request, Model model) {
		Map<String, Object> result = new HashMap<>();
		Map<String, Object> map = this.loginState.getUserState(request);
		model.addAttribute("islogin", map.get("islogin"));
		if (!this.loginState.isIslogin(request)) {
			return "login";
		}
		User user = this.loginState.getLoginedUser(request);
		if (user.getLevel() < 1) {
			model.addAttribute("text", "您还没有该权限，请联系管理员进行申请");
			model.addAttribute("url", "/");
			return "waystation";
		}
		model = this.loginState.addLoginMessage(model, request);
		return "newWrite";
	}

	@PostMapping({ "/ajaxWrite" })
	@ResponseBody
	public Map ajaxWrite(@RequestParam(value = "src", required = false) MultipartFile file, HttpServletRequest request,
			Model model) {
		String str = request.getParameter("description");
		Map<String, Object> result = new HashMap<String,Object>();

		String title = request.getParameter("title").trim();
		String openId = request.getParameter("author");
		String author = "";
		if (!this.loginState.isIslogin(request)) {
			author = this.us.getUserByOpenid(openId).getName();
		} else {
			author = this.loginState.getUserState(request).get("username").toString();
		}
		String content = request.getParameter("content");
		String description = null;
		User user = this.loginState.getLoginedUser(request);
		String subject = request.getParameter("subject");
		if (str.length() < 195) {
			description = str;
		} else {
			description = str.substring(0, 190);
		}
		Article article = new Article();

		Date adate = new Date();
		String time = new SimpleDateFormat("yyyMMdd-HH:mm").format(adate);
		article.setSubject(subject);
		article.setTime(time);
		article.setTitle(title);
		article.setAuthor(author);
		article.setContent(content);
		article.setDescription(description);
		article.setAuthorid(this.loginState.getLoginedUser(request).getId());

		String fileName = null;
		if ((null == file) || (file.isEmpty()) || (user.getLevel() < 2)) {
			int random = (int) (Math.random() * 6.0D);

			fileName = random + ".jpg";
			article.setSrc(fileName);
			int newid = this.articleService.saveAndReturn(article);
			result.put("result", Boolean.valueOf(true));
			result.put("data", this.articleService.getArticleById(newid));
			this.mqSender.Sender(user.getId(), newid);
			return result;
		}
		fileName = file.getOriginalFilename();
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMddyyyy-HHmmss");
		int random = (int) (Math.random() * 100.0D);
		String Name = "ar" + dateFormat.format(date) + random;

		String suffixName = fileName.substring(fileName.lastIndexOf("."));
		fileName = Name + suffixName;

		String filePath = this.path + "images/";
		if ((!suffixName.equalsIgnoreCase(".jpg")) && (!suffixName.equalsIgnoreCase(".png"))
				&& (!suffixName.equalsIgnoreCase(".jpeg"))) {
			int rand = (int) (Math.random() * 6.0D);

			fileName = rand + ".jpg";
		}
		File dest = new File(filePath + fileName);
		if (!dest.getParentFile().exists()) {
			dest.getParentFile().mkdirs();
		}
		try {
			file.transferTo(dest);
			article.setSrc(fileName);
			int newID = this.articleService.saveAndReturn(article);
			result.put("result", Boolean.valueOf(true));
			result.put("data", this.articleService.getArticleById(newID));
			/**
			 * 通知订阅服务器，对订阅业务进行处理
			 */
//      this.mqSender.Sender(user.getId(), newID);
			return result;

		} catch (IllegalStateException e) {
			result.put("result", Boolean.valueOf(false));
			result.put("message", "发布失败，请重试！");
			return result;
		} catch (IOException e) {
			result.put("result", Boolean.valueOf(false));
			result.put("message", "文章logo上传失败，请重试！");
		}
		return result;
	}
}
