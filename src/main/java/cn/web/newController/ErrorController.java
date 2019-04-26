package cn.web.newController;

import java.util.HashMap;
import java.util.Map;

import org.springframework.messaging.handler.annotation.support.MethodArgumentTypeMismatchException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

//@ControllerAdvice
//@RestControllerAdvice
public class ErrorController {

	@ExceptionHandler(value = NullPointerException.class)
	public ModelAndView sysError(Exception exception) {
		ModelAndView modelAndView = new ModelAndView();
//		Map<String, Object> model = new HashMap<>();
//		model.put("result", false);
//		model.put("message", "系统内部出现异常");
//		return new ModelAndView("error", model);
		modelAndView.addObject("result", false);
		modelAndView.addObject("message", "未找到该项目");
		modelAndView.setViewName("errorTemplate");
		return modelAndView;

	}

	@ExceptionHandler(value = NumberFormatException.class)
	public ModelAndView postError(Exception exception) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("message", "路径不匹配！");
		modelAndView.setViewName("errorTemplate");
		return modelAndView;
	}
//	
//	@ResponseBody
//	@ExceptionHandler(value=Exception.class)
	public Map<String, Object> ajaxError(Exception exception){
		Map<String, Object> map = new HashMap<>();
		map.put("result", false);
		map.put("message", exception.getMessage());
		return map;
				
	}

}
