package com.yx.xinruitu.controller.base;

import com.yx.xinruitu.util.ParameterMap;
import org.apache.log4j.Logger;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class BaseController {
	
	protected Logger log = Logger.getLogger(this.getClass());
	protected int pageNo;
	protected int pageSize;

	public BaseController() {
		pageNo=1;
		pageSize=12;
	}

	/**
	 * springMVC 获取requset
	 *
	 * @return
	 */
	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		return request;
	}

	/**
	 * 获取response
	 * 
	 * @return
	 */
	public HttpServletResponse getResponse() {
		HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getResponse();
		return response;
	}

	/**
	 * 获取session
	 * 
	 * @return
	 */
	public HttpSession getSession() {
		HttpSession session = this.getRequest().getSession();
		return session;
	}

	/**
	 * 获取ServletContext
	 * 
	 * @return
	 */
	public ServletContext getServletContent() {
		// ServletContext application= request.getServletContext();

		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		ServletContext servletContext = webApplicationContext.getServletContext();
		return servletContext;
	}

	/**
	 * 获取ModelAndView
	 * 
	 * @return
	 */
	public ModelAndView getModelAndView() {
		return new ModelAndView();
	}

	public ModelAndView get404ModelAndView() {
		ModelAndView view = new ModelAndView();
		view.setViewName("404");
		return view;
	}

	/**
	 * 过滤参数
	 * 
	 * @return
	 */
	public ParameterMap getParameterMap() {
		ParameterMap pm = new ParameterMap(this.getRequest());
		pm.put("rip", getRemortIP());
		return pm;
	}

	/**
	 * 获取ip
	 * 
	 * @return
	 */
	public String getRemortIP() {
		HttpServletRequest request = this.getRequest();
		String ip = "";
		if (request.getHeader("x-forwarded-for") == null) {
			ip = request.getRemoteAddr();
		} else {
			ip = request.getHeader("x-forwarded-for");
		}
		return ip;
	}

	/**
	 * 获取port
	 * 
	 * @return
	 */
	public int getPort() {
		return this.getRequest().getServerPort();
	}

	/**
	 * 获取ip
	 *
	 * @return
	 */
	public String getIpAddr() {
		HttpServletRequest request = this.getRequest();
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}
