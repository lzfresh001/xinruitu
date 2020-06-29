package com.yx.xinruitu.service;

import com.yx.xinruitu.entity.Project;
import com.yx.xinruitu.util.ParameterMap;

import java.util.HashMap;
import java.util.List;

/**
 * ClassName: IProjectService
 * Package: com.yx.xinruitu.service
 * Decription:
 * <p>
 * Date: 2019/12/9 15:20
 * Author: lzfresh
 */
public interface IProjectService {

    /**
     * 根据页面条件获取项目列表信息
     * @param map
     * @param pageSize
     * @param currIndex
     * @return
     */
    HashMap<String,Object> getProListByPage(ParameterMap map, int pageSize, int currIndex);

    /**
     * 生成项目编号
     * @return
     */
    HashMap<String,Object> createPrjCode();

    /**
     * 添加项目
     * @param pm
     * @return
     */
    HashMap<String, Object> add(ParameterMap pm);

    /**
     * 根据项目ID查询项目信息
     * @param pm
     * @return
     */
    HashMap<String, Object> find(ParameterMap pm);

    /**
     * 编辑项目信息
     * @param parameterMap
     * @return
     */
    HashMap<String, Object> edit(ParameterMap parameterMap);

    /**
     * 获取所有项目的名称和ID
     * @return
     */
    HashMap<String, Object> getPrjName();

    /**
     * 获取所有项目名称
     * @return
     */
    List<Project> showPrjNameList();

    /**
     * 获取所有名称二级名称
     * @return
     */
    List<Project> showPrj2ndNameList();

    /**
     * 根据项目名称和二级名称获取项目ID
     * @param pm
     * @return
     */
    HashMap<String, Object> getPrjIdValue(ParameterMap pm);


}
