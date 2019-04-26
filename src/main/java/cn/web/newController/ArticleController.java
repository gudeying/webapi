package cn.web.newController;

import cn.web.model.Article;
import cn.web.model.Comment;
import cn.web.model.User;
import cn.web.service.ArticleService;
import cn.web.service.CommentService;
import cn.web.service.EnshrineService;
import cn.web.service.SubscribeService;
import cn.web.service.UserService;
import cn.web.sqldeal.CommentMapper;
import cn.web.sqldeal.NewComment;
import cn.web.util.LoginState;

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
@RequestMapping({"/article"})
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private UserService userService;
    @Autowired
    private LoginState loginState;
    @Autowired
    private CommentService cs;
    @Autowired
    private EnshrineService enshrineService;
    @Autowired
    private SubscribeService subscribeService;
    
    @Autowired 
    private CommentMapper commentMapper;

    @GetMapping({"/list.php"})
    public String article(HttpServletRequest request, Model model) {
        model = this.loginState.addLoginMessage(model, request);
        model.addAttribute("title", "文章列表");
        model.addAttribute("topic", null);
        model.addAttribute("username", null);
        List hotart = this.articleService.getHotArticles();
        model.addAttribute("hot", hotart);
        return "newList";
    }

    @GetMapping({"/detail/{id}"})
    public String newDetail(@PathVariable("id") int id, HttpServletRequest request, Map<String, Object> m) {
        Map<String, Object> map = this.loginState.getUserState(request);
//        List<Comment> comments = this.cs.getLast5Comments(id);
        List<NewComment> comments = commentMapper.getAllComments(id);
        Article a = this.articleService.getArticleById(id);
        Article next = this.articleService.getNextBySubject(a.getSubject(), id);
        Article previous = this.articleService.getPreviousBySubject(a.getSubject(), id);
        User owner = this.userService.getUserById(a.getAuthorid());
        if (((Boolean) map.get("islogin")).booleanValue()) {
            String name = map.get("username").toString();
            User user = this.userService.getUserByName(name);
            m.put("user", user);
            m.put("collectionNum", Integer.valueOf(this.enshrineService.getCount(String.valueOf(user.getId()))));
            m.put("SubscribeToRead", Integer.valueOf(this.subscribeService.toReadeSize(user.getId())));
        }
        m.put("article", a);
        m.put("comments", comments);
        m.put("islogin", map.get("islogin"));
        m.put("previous", previous);
        m.put("next", next);
        m.put("ownerId", owner.getId());

        return "newDetailWithComment";
    }

    @GetMapping({"/topic/{subject}"})
    public String topicArticle(@PathVariable("subject") String subjectCode, Model model, HttpServletRequest request) {
        String subject = subjectCode;
        model = this.loginState.addLoginMessage(model, request);
        model.addAttribute("title", subject);
        model.addAttribute("topic", subject);
        model.addAttribute("username", null);
        List hotart = this.articleService.getHotArticles();
        model.addAttribute("hot", hotart);
        return "newList";
    }

    @GetMapping({"/user/{userId}"})
    public String userArticles(@PathVariable("userId") int userId, HttpServletRequest request, Model model) {
        User user = this.userService.getUserById(userId);
        String username = this.userService.getUserById(userId).getName();
        model.addAttribute("title", username + "的文章");
        model = this.loginState.addLoginMessage(model, request);
        model.addAttribute("topic", null);
        model.addAttribute("author", username);
        model.addAttribute("username", username);

        List hotart = this.articleService.getUserHotArticles(userId);
        model.addAttribute("hot", hotart);
        return "newList";
    }
}
