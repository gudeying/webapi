package cn.web.newController;

import cn.web.model.Article;
import cn.web.service.SubscribeService;
import cn.web.sqldeal.CommentMapper;
import cn.web.sqldeal.NewComment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	@Autowired
//  private SubscribeService subscribeService;
	private CommentMapper commentMapper;

	@RequestMapping({ "/test" })
	public Map<String, Object> test() {
		List<NewComment> list1 = commentMapper.get5Comments(116);
		Object object2 = commentMapper.getAllComments(116);
		Object object3 = commentMapper.getComentById(37);
		Map<String, Object> map = new HashMap<>();
		map.put("obj1", list1);
		map.put("obj2", object2);
		map.put("obj3", object3);
		return map;
	}
}
