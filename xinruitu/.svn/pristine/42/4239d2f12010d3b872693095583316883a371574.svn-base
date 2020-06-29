package com.yx.xinruitu.controller;

import com.yx.xinruitu.controller.base.BaseController;
import com.yx.xinruitu.entity.Const;
import com.yx.xinruitu.entity.User;
import com.yx.xinruitu.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class IndexController extends BaseController {
	
	@Value("${admin.name}")
	private String adminName;

	@Autowired
	private IUserService userService;
	
	/**
	 * 入口
	 * @return
	 */
	@RequestMapping(value={"/","/toLogin"},method=RequestMethod.GET)
	public String toLogin(){
		return "login";
	}
	
	/**
	 * 首页
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/index")
	public String index(Model model){
		try {
			model.addAttribute("adminName", adminName);
			model.addAttribute("userName", ((User)this.getSession().getAttribute(Const.SESSION_USER)).getName());
			model.addAttribute("userStatus", "在线");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "index";
	}
	
	
	/**
	 * 用户登录
	 * @return
	 */
	@RequestMapping(value={"/login"},method=RequestMethod.POST)
	@ResponseBody
	public Object login(){
		return userService.login(this.getParameterMap(), this.getSession());
	}
	
	/**
	 * 用户注销
	 * @return
	 */
	@RequestMapping("/logout")
	public String logout(){
		return userService.logout(this.getSession());
	}
	
	
}
