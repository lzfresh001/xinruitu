package com.yx.xinruitu.dao;

import com.yx.xinruitu.entity.Account;
import com.yx.xinruitu.util.ParameterMap;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ClassName: AccountDAO
 * Package: com.yx.xinruitu.dao
 * Decription:
 * <p>
 * Date: 2019/12/10 15:22
 * Author: lzfresh
 */
@Repository
@Mapper
public interface AccountDAO {

    /**
     * 按要求获取台账信息列表
     * @param pm
     * @return
     */
    List<ParameterMap> getAccListByPage(ParameterMap pm);

    /**
     * 按要求获取台账信息列表条数
     * @param pm
     * @return
     */
    Integer getAccListCount(ParameterMap pm);

    /**
     * 添加台账
     * @param pm
     */
    void saveAccount(ParameterMap pm);

    /**
     * 根据台账ID查询台账信息
     * @param ac_id
     * @return
     */
    ParameterMap findAccount(String ac_id);

    /**
     * 根据台账图号和名称检查台账信息
     * @param pm
     * @return
     */
    ParameterMap checkAccount(ParameterMap pm);

    /**
     * 编辑台账信息
     * @param pm
     */
    void editAccount(ParameterMap pm);

    /**
     * 获取所有台账的图号
     * @return
     */
    List<Account> showDrawingNoList();

    /**
     * 获取所有台账的名称
     * @return
     */
    List<Account> showAcNameList();

    /**
     * 获取所有台账的件号
     * @return
     */
    List<Account> showPartNoList();

    /**
     * 根据图号、名称、件号获取长、宽、厚
     * @param pm
     * @return
     */
    List<ParameterMap> getTHWValue(ParameterMap pm);
}
