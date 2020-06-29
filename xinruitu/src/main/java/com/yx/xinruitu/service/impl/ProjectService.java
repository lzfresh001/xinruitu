package com.yx.xinruitu.service.impl;

import com.yx.xinruitu.dao.ProjectDAO;
import com.yx.xinruitu.entity.PaginationList;
import com.yx.xinruitu.entity.Project;
import com.yx.xinruitu.entity.ResponseModel;
import com.yx.xinruitu.service.IProjectService;
import com.yx.xinruitu.util.DateUtil;
import com.yx.xinruitu.util.FileEveryDaySerialNumber;
import com.yx.xinruitu.util.ParameterMap;
import com.yx.xinruitu.util.Tools;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.HashMap;
import java.util.List;

/**
 * ClassName: ProjectService
 * Package: com.yx.xinruitu.service.impl
 * Decription:
 * <p>
 * Date: 2019/12/9 15:18
 * Author: lzfresh
 */
@Service
public class ProjectService implements IProjectService {

    @Autowired
    private ProjectDAO projectDAO;

    private Logger log = Logger.getLogger(this.getClass());

    /**
     * 根据页面条件获取项目列表信息
     * @param pm
     * @param pageSize
     * @param currIndex
     * @return
     */
    @Override
    public HashMap<String,Object> getProListByPage(ParameterMap pm, int pageSize, int currIndex) {
        try {
            pm.put("currIndex",(currIndex-1)*pageSize);
            pm.put("pageSize",pageSize);
            List<ParameterMap> list = projectDAO.getProListByPage(pm);
            Integer count = projectDAO.getProListCount(pm);
            PaginationList page = new PaginationList();
            page.setList(list);
            page.setDraw(currIndex);
            page.setPageSize(pageSize);
            page.setRecordsTotal(count!=null?count:0);
            page.setTotalpage(page.getRecordsTotal() % pageSize > 0 ? (page.getRecordsTotal() / pageSize) + 1 : (page.getRecordsTotal() / pageSize));
            return ResponseModel.getModel("ok", "success",page);
        }catch(Exception e) {
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            log.error("get ProList error", e);
            return ResponseModel.getModel("error", "falied", null);
        }
    }

    /**
     * 生成项目编号路径
     * @return
     */
    @Override
    public HashMap<String,Object> createPrjCode() {
        try {
            String code= FileEveryDaySerialNumber.getValue3();
            return ResponseModel.getModel("ok", "success",code);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            log.error("error:"+e.getMessage(), e);
            return ResponseModel.getModel("项目编码生成失败", "failed", null);
        }
    }


    /**
     * 添加项目
     * @param pm
     * @return
     */
    @Override
    @Transactional
    public HashMap<String, Object> add(ParameterMap pm) {
        try {
//            String prj_name =  pm.getString("prj_name");
//            Project project = projectDAO.getProjectByPrjName(prj_name);
//            if(project != null){
//                return ResponseModel.getModel("项目已存在", "falied", null);
//            }
            pm.put("add_time", DateUtil.getTime());
            projectDAO.saveProject(pm);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            log.error("error:"+e.getMessage(), e);
            return ResponseModel.getModel("提交失败", "failed", null);
        }
        return ResponseModel.getModel("ok", "success", null);
    }

    /**
     * 根据项目ID查询项目信息
     * @param pm
     * @return
     */
    @Override
    public HashMap<String, Object> find(ParameterMap pm) {
        try {
            String prj_id = pm.getString("prj_id");
            if(Tools.isEmpty(prj_id)){
                return ResponseModel.getModel("你请求的是冒牌接口", "falied", null);
            }
            ParameterMap map=projectDAO.findProject(prj_id);
            return ResponseModel.getModel("ok", "success",map);
        } catch (Exception e) {
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            log.error("error:"+e.getMessage(), e);
            return ResponseModel.getModel("提交失败", "failed", null);
        }
    }

    /**
     * 编辑项目信息
     * @param pm
     * @return
     */
    @Override
    public HashMap<String, Object> edit(ParameterMap pm) {
        try {
            projectDAO.editProject(pm);
        } catch (Exception e) {
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            log.error("error:"+e.getMessage(), e);
            return ResponseModel.getModel("提交失败", "failed", null);
        }
        return ResponseModel.getModel("ok", "success", null);
    }

    /**
     * 获取所有项目的名称和ID
     * @return
     */
    @Override
    public HashMap<String, Object> getPrjName() {
        try {
            List<ParameterMap> list = projectDAO.getPrjName();
            return ResponseModel.getModel("ok", "success",list);
        } catch (Exception e) {
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            log.error("error:"+e.getMessage(), e);
            return ResponseModel.getModel("获取失败", "failed", null);
        }
    }

    /**
     * 获取所有项目名称
     * @return
     */
    @Override
    public List<Project> showPrjNameList() {
        return projectDAO.showPrjNameList();
    }

    /**
     * 获取所有项目二级名称
     * @return
     */
    @Override
    public List<Project> showPrj2ndNameList() {
        return projectDAO.showPrj2ndNameList();
    }

    /**
     * 根据项目名称和二级名称获取项目ID
     * @param pm
     * @return
     */
    @Override
    public HashMap<String, Object> getPrjIdValue(ParameterMap pm) {
        try {
            Long prjId = projectDAO.getPrjIdValue(pm);
            return ResponseModel.getModel("ok", "success",prjId);
        } catch (Exception e) {
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            log.error("error:"+e.getMessage(), e);
            return ResponseModel.getModel("获取失败", "failed", null);
        }
    }
}
