package cn.web.control;

import cn.web.model.Article;
import cn.web.model.Comment;
import cn.web.model.User;
import cn.web.service.ArticleService;
import cn.web.service.CommentService;
import cn.web.service.UserService;
import cn.web.util.LoginState;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 建议使用新的restful链接
 * {@link cn.web.newController.ArticleController#newDetail(int, HttpServletRequest, Map)}
 */
@Controller
@Deprecated
public class DetailController {
    @Autowired
    private ArticleService as;
    @Autowired
    private CommentService cs;
    @Autowired
    private UserService us;
    @Autowired
    private LoginState loginState;

    @RequestMapping({"/detail"})
    public String toDetail(HttpServletRequest req, Map<String, Object> m) {
        Map<String, Object> map = this.loginState.getUserState(req);
        int id = Integer.parseInt(req.getParameter("id"));
        List<Comment> comments = this.cs.getLast5Comments(id);
        Article a = this.as.getArticleById(id);
        Article next = this.as.getNextBySubject(a.getSubject(), id);
        Article previous = this.as.getPreviousBySubject(a.getSubject(), id);
        if (((Boolean) map.get("islogin")).booleanValue()) {
            String name = map.get("username").toString();
            User user = this.us.getUserByName(name);
            m.put("user", user);
        }
        m.put("article", a);
        m.put("comments", comments);
        m.put("islogin", map.get("islogin"));
        m.put("previous", previous);
        m.put("next", next);
        return "newDetail";
    }
}
