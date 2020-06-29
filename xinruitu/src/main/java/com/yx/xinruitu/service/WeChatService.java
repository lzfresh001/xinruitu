package com.yx.xinruitu.service;

import com.yx.xinruitu.util.ParameterMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

public interface WeChatService {
    String processRequest(HttpServletRequest request);

    /**
     * 微信端用户注册
     * @param pm
     * @return
     */
    HashMap<String, Object> register(ParameterMap pm);

    /**
     * 微信端用户登陆
     * @param pm
     * @return
     */
    HashMap<String, Object> login(ParameterMap pm, HttpSession session);

    /**
     * 微信端登陆成功跳向员工日志列表
     * @param pm
     * @param pageNo
     * @return
     */
    HashMap<String, Object> getEmpLog(ParameterMap pm, int pageNo);

    /**
     * 管理员微信端登陆成功跳向管理员日志列表
     * @param map
     * @param pageNo
     * @return
     */
    HashMap<String, Object> getAdminLog(ParameterMap map, int pageNo);

    /**
     * 根据真实姓名和电话查询账号
     * @param pm
     * @return
     */
    HashMap<String, Object> checkNo(ParameterMap pm);

    /**
     * 员工修改密码
     * @param pm
     * @return
     */
    HashMap<String, Object> updatePwd(ParameterMap pm);

    /**
     * 管理员审核通过日志
     * @param pm
     * @return
     */
    HashMap<String, Object> adCheckLog(ParameterMap pm);

    /**
     * 管理员审核回退日志
     * @param pm
     * @return
     */
    HashMap<String, Object> adReplyLog(ParameterMap pm);

    /**
     * 管理员审核通过日志(批量)
     * @param pm
     * @return
     */
    HashMap<String, Object> checkAccLog(ParameterMap pm);
}
