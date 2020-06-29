package com.yx.xinruitu.dao;

import com.yx.xinruitu.entity.User;
import com.yx.xinruitu.util.ParameterMap;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserDao {
	User getUserInfo(ParameterMap pm);
	List<ParameterMap> getUserList();
	List<ParameterMap> getUserListByPage(ParameterMap pm);
	Integer getUserListCount(ParameterMap pm);
	void saveLoginTime(String userId);
	void saveUser(ParameterMap pm);
	void editUser(ParameterMap pm);
	void delUser(String userId);
	ParameterMap findUser(String userId);
	ParameterMap findUserByUsername(String username);

	/**
	 * 根据员工姓名和电话查询员工信息
	 * @param pm
	 * @return
	 */
    User checkUser(ParameterMap pm);

	/**
	 * 微信端用户注册
	 * @param pm
	 */
	void empRegister(ParameterMap pm);

	/**
	 * 微信端用户登陆
	 * @param pm
	 * @return
	 */
    User empLogin(ParameterMap pm);

	/**
	 * 根据真实姓名和电话查询账号
	 * @param pm
	 * @return
	 */
	ParameterMap checkNo(ParameterMap pm);

	/**
	 * 员工修改密码
	 * @param pm
	 * @return
	 */
	Integer updatePwd(ParameterMap pm);

	/**
	 * 根据openId查询用户
	 * @param openId
	 * @return
	 */
    User checkOpenId(String openId);

	/**
	 * 关注微信的同属注册
	 * @param pm
	 */
	void subscribeRegister(ParameterMap pm);

	/**
	 * 启用用户
	 * @param userId
	 */
    void reUser(String userId);

	/**
	 * 获取所有员工姓名
	 * @return
	 */
	List<User> showUserNameList();

	/**
	 * 转为普通工
	 * @param userId
	 */
    void cutOfEmp(String userId);

	/**
	 * 转为管理员
	 * @param userId
	 */
	void cutOfAdmin(String userId);
}
