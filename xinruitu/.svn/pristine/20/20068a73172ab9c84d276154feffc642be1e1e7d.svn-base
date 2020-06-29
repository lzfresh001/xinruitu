package com.yx.xinruitu.intercept;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yx.xinruitu.entity.Const;
import com.yx.xinruitu.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 *
 * @author
 *
 */
@Component
public class UrlInterceptor implements HandlerInterceptor{

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object obj, ModelAndView mv)
			throws Exception {
		//添加增删改查权限
		if(mv != null){
			mv.addObject(Const.SESSION_QX, request.getSession().getAttribute(Const.SESSION_QX));
		}
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		try {
			HttpSession session = request.getSession();
			String path = request.getServletPath();
			System.out.println("path="+path);
			User user =  (User) session.getAttribute(Const.SESSION_USER);
			if(null == user || "".equals(user)){
				response.sendRedirect("/");
				return false;
			}else{
				path = path.substring(1, path.length());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}


	//json 字符串返回
	private void outputJson(HttpServletResponse response, HashMap<String,Object> model) throws IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = null ;
		String json =  new ObjectMapper().writeValueAsString(model);
		out = response.getWriter();
		out.append(json);
	}


}
