package com.yx.xinruitu.service;

import com.yx.xinruitu.entity.Account;
import com.yx.xinruitu.util.ParameterMap;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

/**
 * ClassName: IAccountService
 * Package: com.yx.xinruitu.service
 * Decription:
 * <p>
 * Date: 2019/12/10 14:55
 * Author: lzfresh
 */
public interface IAccountService {

    /**
     * 按要求获取台账信息列表
     * @param map
     * @param pageSize
     * @param currIndex
     * @return
     */
    HashMap<String,Object> getAccListByPage(ParameterMap map, int pageSize, int currIndex);

    /**
     * 添加台账
     * @param pm
     * @return
     */
    HashMap<String, Object> add(ParameterMap pm);

    /**
     * 根据台账ID查询台账信息
     * @param parameterMap
     * @return
     */
    HashMap<String, Object> find(ParameterMap parameterMap);

    /**
     * 编辑台账信息
     * @param parameterMap
     * @return
     */
    HashMap<String, Object> edit(ParameterMap parameterMap);

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
    List<Account>  showPartNoList();

    /**
     * 根据图号、名称、件号获取长、宽、厚
     * @param pm
     * @return
     */
    HashMap<String, Object> getTHWValue(ParameterMap pm);

    /**
     * 导入台账信息
     * @param file
     * @return
     */
    void importAccountExcel(MultipartFile file , ParameterMap parameterMap, HttpServletResponse response) throws Exception;
}
