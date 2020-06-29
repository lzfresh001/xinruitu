package com.yx.xinruitu.service;

import com.yx.xinruitu.entity.User;
import com.yx.xinruitu.util.ParameterMap;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

public interface IUserService {
	public HashMap<String, Object> login(ParameterMap pm,HttpSession session);
	public String logout(HttpSession session);
	public List<ParameterMap> getUserList();
	public HashMap<String, Object> getUserListByPage(ParameterMap pm,Integer pageSize,Integer  currIndex);
	public HashMap<String, Object> add(ParameterMap pm);
	public HashMap<String, Object> edit(ParameterMap pm);
	public HashMap<String, Object> del(ParameterMap pm);
	public HashMap<String, Object> find(ParameterMap pm);
	public HashMap<String, Object> findbyusername(ParameterMap pm);
	public HashMap<String, Object> createUsername();
	public User getUserInfo(ParameterMap pm);


	/**
	 * 启用用户
	 * @param pm
	 * @return
	 */
	HashMap<String, Object> reUser(ParameterMap pm);

	/**
	 * 获取所有员工姓名
	 * @return
	 */
	List<User> showUserNameList();

	/**
	 * 转为普通工
	 * @param pm
	 * @return
	 */
    Object cutOfEmp(ParameterMap pm);

	/**
	 * 转为管理员
	 * @param pm
	 * @return
	 */
	Object cutOfAdmin(ParameterMap pm);
}
