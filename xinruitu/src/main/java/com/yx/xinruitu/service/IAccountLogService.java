package com.yx.xinruitu.service;

import com.yx.xinruitu.util.ParameterMap;

import java.util.HashMap;

/**
 * ClassName: IAccountLogService
 * Package: com.yx.xinruitu.service
 * Decription:
 * <p>
 * Date: 2019/12/12 8:45
 * Author: lzfresh
 */
public interface IAccountLogService {

    /**
     * 按要求获取生产日志信息列表
     * @param map
     * @param currIndex
     * @return
     */
    HashMap<String,Object> getAccLogListByPage(ParameterMap map, int currIndex);

    /**
     * 添加生产日志
     * @param pm
     * @return
     */
    HashMap<String, Object> add(ParameterMap pm);

    /**
     * 根据日志ID查看日志信息
     * @param parameterMap
     * @return
     */
    HashMap<String, Object> find(ParameterMap parameterMap);

    /**
     * 根据用户id查询该用户添加的最新日志
     * @param id
     * @return
     */
    HashMap<String, Object> lastLogByUid(String id);

    /**
     * 更新日志信息
     * @param parameterMap
     * @return
     */
    HashMap<String, Object> edit(ParameterMap parameterMap);

    /**
     * 审核通过日志信息
     * @param pm
     * @return
     */
    HashMap<String, Object> check(ParameterMap pm);

    /**
     * 回退日志信息
     * @param pm
     * @return
     */
    HashMap<String, Object> reply(ParameterMap pm);


}
