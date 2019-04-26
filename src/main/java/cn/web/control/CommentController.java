package cn.web.control;

import cn.web.model.Comment;
import cn.web.model.User;
import cn.web.service.CommentService;
import cn.web.util.LoginState;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;

@Controller
public class CommentController {
    @Autowired
    private CommentService cs;
    @Autowired
    private LoginState loginState;

    @RequestMapping(value = {"/comment"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> doComment(HttpServletRequest req, HttpServletResponse resp, Model model) {
        String comment = req.getParameter("comment").trim();
        int id = Integer.parseInt(req.getParameter("id"));
        System.out.println(comment);
        Map<String, Object> map = new HashMap<>();

        Comment c = new Comment();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyy年MM月dd日 HH:mm:ss");
        String time = sdf.format(date);
        c.setUser(this.loginState.getUserState(req).get("username").toString());
        c.setTime(time);
        c.setArticleid(id);
        c.setContent(comment);
        this.cs.save(c);
        map.put("result", Boolean.valueOf(true));
        map.put("comment", c);
        System.out.println(c.getArticleid());
        return map;
    }

    /*
     * 长评论已使用Ajax调用
     */
    @RequestMapping({"/longcomment"})
    @Deprecated
    public String longComment(HttpServletRequest req, Model model) {
        String articleid = null;
        try {
            articleid = req.getParameter("id");
        } catch (Exception e) {
            return "/";
        }
        model.addAttribute("articleid", articleid);
        return "longcomment";
    }

    @PostMapping("/answerComment")
    @ResponseBody
    public Map<String, Object> answerComment(@RequestParam("articleId") int articleId,
                                             @RequestParam("parentId") int parentId, @RequestParam("content") String content,
                                             @RequestParam(value = ("towho"), required = false) String sendTowho, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String towho = sendTowho;
        if (loginState.isIslogin(request)) {
            User loginUser = loginState.getLoginedUser(request);
            Comment comment = new Comment();
            comment.setArticleid(articleId);
            comment.setContent(content);
            comment.setParentid(parentId);
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyy年MM月dd日 HH:mm:ss");
            String time = sdf.format(date);
            comment.setTime(time);
            if (towho != null) {
                comment.setTowho(towho);
            }
            comment.setUser(loginUser.getNickName());
            comment.setUserid(loginUser.getId());
            cs.save(comment);
            map.put("result", true);

        } else {
            map.put("result", false);
            map.put("message", "未登录!");
        }

        return map;
    }

    @RequestMapping("/comments/{articleId}/show/{commentId}")
    public String toCommentPage(@PathVariable("commentId") int commentId,
                                @PathVariable("articleId") int articleId,
                                Model model, HttpServletRequest request) {
        Comment comment = cs.getCommentById(commentId);
        int myPageNum = 1;
        PageInfo<Comment> pageInfoCom = cs.getPageChildrenComment(commentId, myPageNum, 6);
        model.addAttribute("comment", comment);
        model.addAttribute("childrenComments", pageInfoCom);
        model.addAttribute("articleId", articleId);
        return "AllCommentsModal";
    }

    @PostMapping("/comment/children")
    @ResponseBody
    public PageInfo<Comment> getChildrenCommentsPage(@RequestParam("parentId") int parentId,
                                                     @RequestParam("pageNum") int pageNum, HttpServletRequest request) {
        PageInfo<Comment> cInfo = cs.getPageChildrenComment(parentId, pageNum, 6);
        return cInfo;
    }
}
