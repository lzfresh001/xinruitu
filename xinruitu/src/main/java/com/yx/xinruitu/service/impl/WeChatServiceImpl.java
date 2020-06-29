package com.yx.xinruitu.service.impl;

import com.yx.xinruitu.dao.AccountLogDAO;
import com.yx.xinruitu.dao.UserDao;
import com.yx.xinruitu.entity.Const;
import com.yx.xinruitu.entity.PaginationList;
import com.yx.xinruitu.entity.ResponseModel;
import com.yx.xinruitu.entity.User;
import com.yx.xinruitu.entity.msg.strategy.*;
import com.yx.xinruitu.service.WeChatService;
import com.yx.xinruitu.util.*;
import com.yx.xinruitu.util.wechat.WeChatConstant;
import com.yx.xinruitu.util.wechat.XmlUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WeChatServiceImpl implements WeChatService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private AccountLogDAO accountLogDAO;

    private Logger log = Logger.getLogger(this.getClass());

    public String processRequest(HttpServletRequest request) {
        // 默认返回的文本消息内容
        try {
            String openId = null;
            // 调用parseXml方法解析请求消息
            Map<String,String> requestMap = XmlUtil.xmlToMap(request);
            // 工厂类生产策略
            MsgStrategy msgStrategy =null;

            String type = requestMap.get(WeChatConstant.MSG_TYPE);
            switch (type) {
                case WeChatConstant.REQ_MESSAGE_TYPE_TEXT:
                     new TextMsgStrategy();
                case WeChatConstant.REQ_MESSAGE_TYPE_IMAGE:
                    new ImageMsgStrategy();
                case WeChatConstant.REQ_MESSAGE_TYPE_VOICE:
                     new VoiceMsgStrategy();
                    break;
                case WeChatConstant.REQ_MESSAGE_TYPE_VIDEO:
                     new VideoMsgStrategy();
                case WeChatConstant.REQ_MESSAGE_TYPE_LOCATION:
                     new LocationMsgStrategy();
                case WeChatConstant.REQ_MESSAGE_TYPE_LINK:
                     new LinkMsgStrategy();
                // 事件推送
                case WeChatConstant.REQ_MESSAGE_TYPE_EVENT:
                    // 事件类型
                    String eventType = requestMap.get(WeChatConstant.EVENT);
                    switch (eventType) {
                        case WeChatConstant.EVENT_TYPE_SUBSCRIBE: //关注事件
                            /*System.out.println("===================OpenId-SUBSCRIBE========================");
                            System.out.println(requestMap.get(WeChatConstant.FROM_USER_NAME));
                            System.out.println("===================OpenId-SUBSCRIBE========================");
                            User usr = userDao.checkOpenId(requestMap.get(WeChatConstant.FROM_USER_NAME));
                            if(null != usr){
                                System.out.println(requestMap.get(WeChatConstant.FROM_USER_NAME) + "已存在");
                            }else{
                                ParameterMap pm = new ParameterMap();
                                pm.put("usertype",2);
                                String code=FileEveryDaySerialNumber.getValue4();
                                pm.put("username",code);
                                String password = "000000";
                                pm.put("password", SHA.encryptSHA(password));
                                pm.put("openid",requestMap.get(WeChatConstant.FROM_USER_NAME));
                                pm.put("add_time", DateUtil.getTime());
                                userDao.subscribeRegister(pm);
                                System.out.println(requestMap.get(WeChatConstant.FROM_USER_NAME) + "已注册");
                            }*/

                        case WeChatConstant.EVENT_TYPE_VIEW:
                        case WeChatConstant.EVENT_TYPE_UNSUBSCRIBE:
                           new UnsubscribeMsgStrategy();
                        case WeChatConstant.EVENT_TYPE_SCAN:
                             new ScanMsgStrategy();
                        case WeChatConstant.EVENT_TYPE_LOCATION:
                             new ReportLocationMsgStrategy();
                        case WeChatConstant.EVENT_TYPE_CLICK:
                            new ClickMsgStrategy();
                    }
                    break;
            }

            // 将策略交给执行策略的上下文
            //MsgStrategyContext context = new MsgStrategyContext(msgStrategy);
            //return context.execute(requestMap);
            return openId;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 微信端用户注册
     * @param pm
     * @return
     */
    @Override
    @Transactional
    public HashMap<String, Object> register(ParameterMap pm) {
        try {
            User checkUser = userDao.checkUser(pm);
            if(null != checkUser){
                return ResponseModel.getModel("用户已存在", "falied", null);
            }
            pm.put("usertype",2);
            String code=FileEveryDaySerialNumber.getValue4();
            pm.put("username",code);
            String password = "000000";
            pm.put("password", SHA.encryptSHA(password));
            pm.put("add_time", DateUtil.getTime());
            userDao.empRegister(pm);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            log.error("error:"+e.getMessage(), e);
            return ResponseModel.getModel("提交失败", "failed", null);
        }
        return ResponseModel.getModel("ok", "success", pm);
    }

    /**
     * 微信端用户登陆
     * @param pm
     * @return
     */
    @Override
    public HashMap<String, Object> login(ParameterMap pm, HttpSession session) {
        User user = null;
        try {
            String password = pm.getString("password");
            pm.put("password", SHA.encryptSHA(password));
            user = userDao.empLogin(pm);
            if(null == user){
                return ResponseModel.getModel("账号或密码错误", "failed", null);
            }
            if(user.getStatus() == -1){
                return ResponseModel.getModel("账号异常", "failed", null);
            }
            session.setMaxInactiveInterval(86400);
            session.setAttribute(Const.WX_SESSION_USER, user);
        } catch (Exception e) {
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            log.error("error:"+e.getMessage(), e);
            return ResponseModel.getModel("提交失败", "failed", null);
        }
        return ResponseModel.getModel("ok", "success", user);
    }

    /**
     * 微信端登陆成功跳向员工日志列表
     * @param pm
     * @param currIndex
     * @return
     */
    @Override
    public HashMap<String, Object> getEmpLog(ParameterMap pm,int currIndex) {
        try {
            int pageSize = 100;
            pm.put("currIndex",(currIndex-1)*pageSize);
            pm.put("pageSize",pageSize);
            List<ParameterMap> list = accountLogDAO.getEmpLogByUid(pm);
            Integer count = accountLogDAO.getEmpLogCountByUid(pm);
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
     * 管理员微信端登陆成功跳向管理员日志列表
     * @param pm
     * @param currIndex
     * @return
     */
    @Override
    public HashMap<String, Object> getAdminLog(ParameterMap pm, int currIndex) {
        try {
            int pageSize = 100;
            pm.put("currIndex",(currIndex-1)*pageSize);
            pm.put("pageSize",pageSize);
            List<ParameterMap> list = accountLogDAO.getAdminLog(pm);
            Integer count = accountLogDAO.getAdminLogCount(pm);
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
     * 根据真实姓名和电话查询账号
     * @param pm
     * @return
     */
    @Override
    public HashMap<String, Object> checkNo(ParameterMap pm) {
        try {
            ParameterMap map = userDao.checkNo(pm);
            return ResponseModel.getModel("ok", "success",map);
        } catch (Exception e) {
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            log.error("error:"+e.getMessage(), e);
            return ResponseModel.getModel("提交失败", "failed", null);
        }
    }

    /**
     * 员工修改密码
     * @param pm
     * @return
     */
    @Override
    public HashMap<String, Object> updatePwd(ParameterMap pm) {

        try {
            String passWord = pm.getString("passWord");
            pm.put("passWord", SHA.encryptSHA(passWord));
            Integer row = userDao.updatePwd(pm);
            if(row < 1){
                return ResponseModel.getModel("修改失败，请核实姓名和电话", "failed", null);
            }
        } catch (Exception e) {
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            log.error("error:"+e.getMessage(), e);
            return ResponseModel.getModel("提交失败", "failed", null);
        }
        return ResponseModel.getModel("ok", "success", null);
    }

    /**
     * 管理员审核通过日志
     * @param pm
     * @return
     */
    @Override
    public HashMap<String, Object> adCheckLog(ParameterMap pm) {
        try {
            String logId = pm.getString("id");
            if(StringUtils.isEmpty(logId)){
                return ResponseModel.getModel("通过失败，请重新操作", "failed", null);
            }
            pm.put("accept_time",DateUtil.getTime());
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
     * 管理员审核回退日志
     * @param pm
     * @return
     */
    @Override
    public HashMap<String, Object> adReplyLog(ParameterMap pm) {
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

    /**
     * 管理员审核通过日志(批量)
     * @param pm
     * @return
     */
    @Override
    public HashMap<String, Object> checkAccLog(ParameterMap pm) {
        try {
            pm.put("accept_time",new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            accountLogDAO.checkAccLog(pm);
        } catch (Exception e) {
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            log.error("error:"+e.getMessage(), e);
            return ResponseModel.getModel("提交失败", "failed", null);
        }
        return ResponseModel.getModel("ok", "success", null);
    }


}
