package com.yx.xinruitu.dao;

import com.yx.xinruitu.util.ParameterMap;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ClassName: AccountLogDAO
 * Package: com.yx.xinruitu.dao
 * Decription:
 * <p>
 * Date: 2019/12/12 8:53
 * Author: lzfresh
 */
@Repository
@Mapper
public interface AccountLogDAO {

    /**
     * 按要求获取生产日志列表信息
     * @param pm
     * @return
     */
    List<ParameterMap> getAccLogListByPage(ParameterMap pm);

    /**
     * 按要求获取生产日志列表信息条数
     * @param pm
     * @return
     */
    Integer getAccLogListCount(ParameterMap pm);

    /**
     * 添加生产日志
     * @param pm
     */
    void saveAccountLog(ParameterMap pm);

    /**
     * 根据日志ID查看日志信息
     * @param id
     * @return
     */
    ParameterMap findAccountLog(String id);

    /**
     * 根据用户id查询该用户添加的最新日志
     * @param id
     * @return
     */
    ParameterMap findLastLogByUid(String id);

    /**
     * 更新日志信息
     * @param pm
     */
    void editAccountLog(ParameterMap pm);

    /**
     * 根据用户id查询用户日志信息
     * @param pm
     * @return
     */
    List<ParameterMap> getEmpLogByUid(ParameterMap pm);

    /**
     * 根据用户id查询用户日志信息条数
     * @param pm
     * @return
     */
    Integer getEmpLogCountByUid(ParameterMap pm);

    /**
     * 管理员查询已提交的日志列表
     * @param pm
     * @return
     */
    List<ParameterMap> getAdminLog(ParameterMap pm);

    /**
     * 管理员查询已提交的日志列表条数
     * @param pm
     * @return
     */
    Integer getAdminLogCount(ParameterMap pm);

    /**
     * 管理员审核通过日志
     * @param pm
     */
    void adCheckLog(ParameterMap pm);

    /**
     * 管理员审核回退日志
     * @param pm
     */
    void adReplyLog(ParameterMap pm);

    /**
     * 管理员审核通过日志(批量)
     * @param pm
     */
    void checkAccLog(ParameterMap pm);
}
