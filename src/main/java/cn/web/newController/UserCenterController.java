package cn.web.newController;

import cn.web.model.Article;
import cn.web.model.User;
import cn.web.service.ArticleService;
import cn.web.service.EnshrineService;
import cn.web.service.SubscribeService;
import cn.web.service.UserService;
import cn.web.util.LoginState;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserCenterController {
    @Autowired
    private LoginState loginState;
    @Autowired
    private UserService userService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private EnshrineService enshrineService;
    @Autowired
    private SubscribeService subscribeService;

    @GetMapping({"/{openId}/home"})
    public String userHome(@PathVariable("openId") String openId, Model model, HttpServletRequest request) {
        User requestUser = this.userService.getUserByOpenid(openId);
        String userName = requestUser.getName();
        model = this.loginState.addLoginMessage(model, request);
        model.addAttribute("reqUser", requestUser);
        model.addAttribute("username", userName);
        model.addAttribute("title", userName);
        List<Article> hotArt = this.articleService.getUserHotArticles(requestUser.getId());
        Article show = (Article) hotArt.get(0);
        model.addAttribute("topic", null);
        model.addAttribute("username", userName);
        model.addAttribute("hot", hotArt);
        model.addAttribute("show", show);
        return "newUserHome";
    }

    @GetMapping("/home")
    public void defaultHome(HttpServletRequest request, HttpServletResponse response) {
        if (!loginState.isIslogin(request)) {
            try {
                response.sendRedirect("/login");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        User user = loginState.getLoginedUser(request);
        try {
            response.sendRedirect("/" + user.getOpenid() + "/home");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping({"/user/{userId}/home"})
    public String userHomeById(@PathVariable("userId") int userId, Model model, HttpServletRequest request) {
        User requestUser = this.userService.getUserById(userId);
        String userName = requestUser.getName();
        model = this.loginState.addLoginMessage(model, request);
        model.addAttribute("reqUser", requestUser);
        model.addAttribute("username", userName);
        model.addAttribute("title", userName);
        List<Article> hotArt = this.articleService.getUserHotArticles(requestUser.getId());
        Article show = (Article) hotArt.get(0);
        model.addAttribute("topic", null);
        model.addAttribute("username", userName);
        model.addAttribute("hot", hotArt);
        model.addAttribute("show", show);
        return "newUserHome";
    }


    /**
     *  用户特点话题的文章
     * @param userId
     * @param subjectCode
     * @param request
     * @param model
     * @return
     */
    @RequestMapping({"/user/{userId}/topic/{topic}/list"})
    public String userTopicArticles(@PathVariable("userId") int userId, @PathVariable("topic") String subjectCode, HttpServletRequest request, Model model) {
        String name = "";

        String subject = subjectCode;
        User requestUser = this.userService.getUserById(userId);
        model = this.loginState.addLoginMessage(model, request);
        model.addAttribute("title", subject);
        model.addAttribute("topic", subject);
        model.addAttribute("author", requestUser.getName());
        model.addAttribute("username", requestUser.getName());

        List hotart = this.articleService.getUserHotArticles(requestUser.getId());
        model.addAttribute("hot", hotart);
        return "newList";
    }
}
