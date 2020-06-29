package com.yx.xinruitu.service.impl;

import com.yx.xinruitu.dao.AccountLogDAO;
import com.yx.xinruitu.entity.PaginationList;
import com.yx.xinruitu.entity.ResponseModel;
import com.yx.xinruitu.service.IAccountLogService;
import com.yx.xinruitu.util.DateUtil;
import com.yx.xinruitu.util.ParameterMap;
import com.yx.xinruitu.util.Tools;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * ClassName: AccountLogService
 * Package: com.yx.xinruitu.service.impl
 * Decription:
 * <p>
 * Date: 2019/12/12 8:47
 * Author: lzfresh
 */
@Service
public class AccountLogService implements IAccountLogService {

    @Autowired
    private AccountLogDAO accountLogDAO;


    private Logger log = Logger.getLogger(this.getClass());

    /**
     * 按要求获取生产日志信息列表
     * @param pm
     * @param currIndex
     * @return
     */
    @Override
    public HashMap<String, Object> getAccLogListByPage(ParameterMap pm, int currIndex) {
        try {
            String drawing_no = pm.getString("drawing_no");
            if(null != drawing_no && drawing_no != ""){
                pm.put("drawing_no",drawing_no.toUpperCase());
            }
            int pageSize = 100;
            pm.put("currIndex",(currIndex-1)*pageSize);
            pm.put("pageSize",pageSize);
            List<ParameterMap> list = accountLogDAO.getAccLogListByPage(pm);
            Integer count = accountLogDAO.getAccLogListCount(pm);
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
     * 添加生产日志
     * @param pm
     * @return
     */
    @Override
    public HashMap<String, Object> add(ParameterMap pm) {
        try {
            if(null != pm.getString("drawing_no")){
                pm.put("drawing_no",pm.getString("drawing_no").toUpperCase());
            }
            if(null != pm.getString("part_no")){
                pm.put("part_no",pm.getString("part_no").toUpperCase());
            }
            pm.put("add_time", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            accountLogDAO.saveAccountLog(pm);
        } catch (Exception e) {
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            log.error("error:"+e.getMessage(), e);
            return ResponseModel.getModel("提交失败", "failed", null);
        }
        return ResponseModel.getModel("ok", "success", null);
    }

    /**
     * 根据日志ID查看日志信息
     * @param pm
     * @return
     */
    @Override
    public HashMap<String, Object> find(ParameterMap pm) {
        try {
            String id = pm.getString("id");
            if(Tools.isEmpty(id)){
                return ResponseModel.getModel("你请求的是冒牌接口", "falied", null);
            }
            ParameterMap map = accountLogDAO.findAccountLog(id);
            return ResponseModel.getModel("ok", "success",map);
        } catch (Exception e) {
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            log.error("error:"+e.getMessage(), e);
            return ResponseModel.getModel("提交失败", "failed", null);
        }
    }

    /**
     * 根据用户id查询该用户添加的最新日志
     * @param id
     * @return
     */
    @Override
    public HashMap<String, Object> lastLogByUid(String id) {
        try {
            if(Tools.isEmpty(id)){
                return ResponseModel.getModel("你请求的是冒牌接口", "falied", null);
            }
            ParameterMap map = accountLogDAO.findLastLogByUid(id);
            return ResponseModel.getModel("ok", "success",map);
        } catch (Exception e) {
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            log.error("error:"+e.getMessage(), e);
            return ResponseModel.getModel("提交失败", "failed", null);
        }
    }

    /**
     *  更新日志信息
     * @param pm
     * @return
     */
    @Override
    public HashMap<String, Object> edit(ParameterMap pm) {
        try {
            String logId = pm.getString("id");
            if(StringUtils.isEmpty(logId)){
                return ResponseModel.getModel("提交失败，请重新操作", "failed", null);
            }
            if(null != pm.getString("drawing_no")){
                pm.put("drawing_no",pm.getString("drawing_no").toUpperCase());
            }
            if(null != pm.getString("part_no")){
                pm.put("part_no",pm.getString("part_no").toUpperCase());
            }
            pm.put("update_time",new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            accountLogDAO.editAccountLog(pm);
        } catch (Exception e) {
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            log.error("error:"+e.getMessage(), e);
            return ResponseModel.getModel("提交失败", "failed", null);
        }
        return ResponseModel.getModel("ok", "success", null);
    }

    /**
     * 审核通过日志信息
     * @param pm
     * @return
     */
    @Override
    public HashMap<String, Object> check(ParameterMap pm) {
        try {
            String logId = pm.getString("id");
            if(StringUtils.isEmpty(logId)){
                return ResponseModel.getModel("回退失败，请重新操作", "failed", null);
            }
            pm.put("accept_time",new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            accountLogDAO.adCheckLog(pm);
        } catch (Exception e) {
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            log.error("error:"+e.getMessage(), e);
            return ResponseModel.getModel("提交失败", "failed", null);
        }
        return ResponseModel.getModel("ok", "success", null);
    }

    /**
     * 回退日志信息
     * @param pm
     * @return
     */
    @Override
    public HashMap<String, Object> reply(ParameterMap pm) {
        try {
            String logId = pm.getString("id");
            if(StringUtils.isEmpty(logId)){
                return ResponseModel.getModel("回退失败，请重新操作", "failed", null);
            }
            pm.put("accept_time",DateUtil.getTime());
            accountLogDAO.adReplyLog(pm);
        } catch (Exception e) {
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            log.error("error:"+e.getMessage(), e);
            return ResponseModel.getModel("提交失败", "failed", null);
        }
        return ResponseModel.getModel("ok", "success", null);
    }
}
