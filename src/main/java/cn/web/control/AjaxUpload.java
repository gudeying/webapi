package cn.web.control;

import cn.web.model.Article;
import cn.web.model.Gallery;
import cn.web.model.User;
import cn.web.service.ArticleService;
import cn.web.service.GalleryService;
import cn.web.service.UserService;
import cn.web.util.CutImage;
import cn.web.util.LoginState;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class AjaxUpload {
	@Value("${web.upload-path}")
	private String path;
	@Autowired
	private UserService userService;
	@Autowired
	private LoginState loginState;
	@Autowired
	private GalleryService gs;
	@Autowired
	private ArticleService articleService;

	@RequestMapping(value = { "/ajaxUpload" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> dealUpGallery(@RequestParam("picture") MultipartFile picture,
			@RequestParam("desc") String desc, HttpServletRequest req) {
		Map<String, Object> result = new HashMap<>();
		User user = null;
		try {
			user = this.userService.getUserByName(this.loginState.getUserState(req).get("username").toString());
		} catch (NullPointerException e) {
			result.put("message", "请登陆！！");
			return result;
		}
		if ((null != user) && ((picture.isEmpty()) || (user.getLevel() < 2))) {
			result.put("result", Boolean.valueOf(false));
			return result;
		}
		result.put("result", Boolean.valueOf(true));
		String fileName = picture.getOriginalFilename();
		String suffixName = fileName.substring(fileName.lastIndexOf("."));
		String filePath = this.path + "images/";
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMddyyyy-HHmmss");
		int random = (int) (Math.random() * 100.0D);
		String Name = "ga" + dateFormat.format(date) + random;
		fileName = Name + suffixName;
		String cutsrc = filePath + fileName;
		String comfile = "com" + fileName;
		String cutdest = filePath + comfile;

		File dest = new File(filePath + fileName);
		if (!dest.getParentFile().exists()) {
			dest.getParentFile().mkdirs();
		}
		try {
			picture.transferTo(dest);
			try {
				CutImage.zoomImage(cutsrc, cutdest);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Gallery g = new Gallery();
			g.setUser(user.getName());
			g.setDes(desc);
			g.setSrc(fileName);
			g.setComsrc(comfile);
			this.gs.save(g);
			result.put("gallery", g);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	@RequestMapping(value = { "/uploadBase64" }, method = {
			org.springframework.web.bind.annotation.RequestMethod.POST })
	@ResponseBody
	public Map<String, String> base64UpLoad(@RequestBody Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) {
		String base64Data = (String) params.get("picture");
		try {
			String dataPrix = "";
			String data = "";
			if ((base64Data == null) || ("".equals(base64Data))) {
				throw new Exception("上传失败，上传图片数据为空");
			}
			String[] d = base64Data.split("base64,");
			if ((d != null) && (d.length == 2)) {
				dataPrix = d[0];
				data = d[1];
			} else {
				throw new Exception("上传失败，数据不合法");
			}
			String suffix = "";
			if ("data:image/jpeg;".equalsIgnoreCase(dataPrix)) {
				suffix = ".jpg";
			} else if ("data:image/x-icon;".equalsIgnoreCase(dataPrix)) {
				suffix = ".ico";
			} else if ("data:image/gif;".equalsIgnoreCase(dataPrix)) {
				suffix = ".gif";
			} else if ("data:image/png;".equalsIgnoreCase(dataPrix)) {
				suffix = ".png";
			} else {
				throw new Exception("上传图片格式不合法");
			}
			String tempFileName = "textbase64file" + suffix;

			byte[] bs = Base64Utils.decodeFromString(data);
			try {
				FileUtils.writeByteArrayToFile(new File("/test/", tempFileName), bs);
			} catch (Exception ee) {
				throw new Exception("上传失败，写入文件失败，" + ee.getMessage());
			}
			Map<String, String> map = new HashMap<>();
			map.put("tempFileName", tempFileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@PostMapping({ "/ajaxWriteArticle" })
	@ResponseBody
	public Map<String, Object> writeArticle(@RequestParam(value = "src", required = false) MultipartFile file,
			HttpServletRequest req) {
		Map<String, Object> map = new HashMap<>();
		if (!this.loginState.isIslogin(req)) {
			map.put("result", Boolean.valueOf(false));
			map.put("message", "请登录！");
			return map;
		}
		String str = req.getParameter("description");

		String title = req.getParameter("title").trim();
		String author = this.loginState.getUserState(req).get("username").toString();
		String content = req.getParameter("content");
		String description = null;
		User user = this.loginState.getLoginedUser(req);
		String subject = req.getParameter("subject");
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

		String fileName = null;
		if ((null == file) || (file.isEmpty()) || (user.getLevel() < 2)) {
			int random = (int) (Math.random() * 6.0D);
			fileName = random + ".jpg";
			article.setSrc(fileName);
			this.articleService.save(article);
			map.put("result", Boolean.valueOf(true));
			map.put("data", this.articleService.getLocal(article.getAuthor(), article.getTime(), article.getTitle()));
			return map;
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
			map.put("result", Boolean.valueOf(false));
			map.put("message", "只允许jpg、jpeg、png格式的图片");
			return map;
		}
		File dest = new File(filePath + fileName);
		if (!dest.getParentFile().exists()) {
			dest.getParentFile().mkdirs();
		}
		try {
			file.transferTo(dest);
			article.setSrc(fileName);
			this.articleService.save(article);
			map.put("result", Boolean.valueOf(true));
			map.put("data", this.articleService.getLocal(article.getAuthor(), article.getTime(), article.getTitle()));
			return map;
		} catch (IllegalStateException e) {
			e.printStackTrace();
			map.put("result", Boolean.valueOf(false));
			map.put("message", "图片上传出错！");
			return map;
		} catch (IOException e) {
			map.put("result", Boolean.valueOf(false));
			map.put("message", "图片上传出错！");
		}
		return map;
	}
}
