package com.yx.xinruitu.controller;

import com.yx.xinruitu.controller.base.BaseController;
import com.yx.xinruitu.entity.AccountLog;
import com.yx.xinruitu.entity.Const;
import com.yx.xinruitu.entity.User;
import com.yx.xinruitu.service.*;
import com.yx.xinruitu.util.ParameterMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@Controller
@RequestMapping("/wx")
public class WxywController extends BaseController{
    @Autowired
    private IUserService userService;

    @Autowired
    private WeChatService weChatService;

    @Autowired
    private IAccountService iAccountService;

    @Autowired
    private IProjectService iProjectService;

    @Autowired
    private IAccountLogService iAccountLogService;

    /**
     * 查找用户
     * @return
     */
    @RequestMapping(value="/bind",method= RequestMethod.GET)
    public Object bind(Model model){
        HashMap<String, Object> result=userService.findbyusername(this.getParameterMap());
        model.addAttribute("user",result);
        return "page/user/bind";
    }

    /**
     * 微信端用户注册
     * @return
     */
    @PostMapping(value = "/register")
    @ResponseBody
    public Object register(HttpSession session){
        ParameterMap pm = this.getParameterMap();
        return weChatService.register(pm);
    }

    /**
     * 微信端用户登陆
     * @return
     */
    @GetMapping(value = "/login")
    @ResponseBody
    public Object login(){
        ParameterMap pm = this.getParameterMap();
        HttpSession session = this.getSession();
        return weChatService.login(pm,session);
    }

    /**
     * 员工微信端登陆成功跳向员工日志列表
     * @param model
     * @return
     */
    @RequestMapping(value = "/toEmpList")
    public String toEmpList(Model model, HttpSession session){
        User user = (User) this.getSession().getAttribute(Const.WX_SESSION_USER);
        if(null == user){
            return "redirect:/static/wxpage/wxLogin.html";
        }
        ParameterMap map = this.getParameterMap();
        if(map.containsKey("pageNo")){
            pageNo=Integer.valueOf(map.get("pageNo").toString());
        }
        map.put("user_id",user.getId());
        session.setAttribute("emSearchDate",map.getString("add_time"));
        session.setAttribute("emSearchPrjName",map.getString("prj_name"));
        session.setAttribute("emSearchPrjName2nd",map.getString("prj_name_2nd"));
        session.setAttribute("emSearchProcessType",map.getString("process_type"));
        session.setAttribute("emSearchStatus",map.getString("status"));
        if(map.getString("status").equals("待审核")){
            map.put("status","0");
        }
        if(map.getString("status").equals("已审核")){
            map.put("status","1");
        }
        if(map.getString("add_time").isEmpty()){
            map.put("add_time",new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        }
//        if(map.getString("searchType").equals("M") && !map.getString("add_time").isEmpty()){
//            map.put("add_time",map.getString("add_time").substring(0,7));
//        }
//        if(map.getString("searchType").equals("M") && map.getString("add_time").isEmpty()){
//            map.put("add_time","");
//        }
        model.addAttribute("WxEmp",user);
        model.addAttribute("EmpLogList", weChatService.getEmpLog(map,pageNo));
        model.addAttribute("WxPrjNameList",iProjectService.showPrjNameList());
        model.addAttribute("WxPrj2ndNameList",iProjectService.showPrj2ndNameList());
        model.addAttribute("WxDrawNoList",iAccountService.showDrawingNoList());
        model.addAttribute("WxAcNameList",iAccountService.showAcNameList());
        model.addAttribute("WxPartNoList",iAccountService.showPartNoList());
        return "page/wxpage/empList";
    }

    /**
     * 管理员微信端登陆成功跳向管理员日志列表
     * @param model
     * @return
     */
    @RequestMapping(value = "/toAdminList")
    public String toAdminList(Model model, HttpSession session){
        User user = (User) this.getSession().getAttribute(Const.WX_SESSION_USER);
        if(null == user){
            return "redirect:/static/wxpage/wxLogin.html";
        }
        ParameterMap map = this.getParameterMap();
        if(map.containsKey("pageNo")){
            pageNo=Integer.valueOf(map.get("pageNo").toString());
        }
//        map.put("user_id",user.getId());
        session.setAttribute("SearchDate",map.getString("add_time"));
        session.setAttribute("SearchPrjName",map.getString("prj_name"));
        session.setAttribute("SearchPrjName2nd",map.getString("prj_name_2nd"));
        session.setAttribute("SearchUserName",map.getString("add_user"));
        session.setAttribute("SearchProcessType",map.getString("process_type"));
        session.setAttribute("SearchStatus",map.getString("status"));
        if(map.getString("status").equals("待审核")){
            map.put("status","0");
        }
        if(map.getString("status").equals("已审核")){
            map.put("status","1");
        }
        if(map.getString("status").isEmpty()){
            map.put("status","0");
        }
//        if(map.getString("add_time").isEmpty()){
//            map.put("add_time",new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
//        }
//        if(map.getString("searchType").equals("M") && !map.getString("add_time").isEmpty()){
//            map.put("add_time",map.getString("add_time").substring(0,7));
//        }
//        if(map.getString("searchType").equals("M") && map.getString("add_time").isEmpty()){
//            map.put("add_time","");
//        }
        model.addAttribute("AdWxEmp",user);
        model.addAttribute("AdEmpLogList", weChatService.getAdminLog(map,pageNo));
        model.addAttribute("AdWxPrjNameList",iProjectService.showPrjNameList());
        model.addAttribute("AdWxPrj2ndNameList",iProjectService.showPrj2ndNameList());
        model.addAttribute("AdUserNameList",userService.showUserNameList());
        model.addAttribute("AdWxDrawNoList",iAccountService.showDrawingNoList());
        model.addAttribute("AdWxAcNameList",iAccountService.showAcNameList());
        model.addAttribute("AdWxPartNoList",iAccountService.showPartNoList());
        return "page/wxpage/adminList";
    }

    /**
     * 根据图号、名称、件号获取长、宽、厚
     * @return
     */
    @RequestMapping(value="/getTHWValue",method=RequestMethod.GET)
    @ResponseBody
    public Object getTHWValue(){
        return iAccountService.getTHWValue(this.getParameterMap());
    }

    /**
     * 微信添加生产日志
     * @return
     */
    @RequestMapping(value="/addLog",method=RequestMethod.POST)
    @ResponseBody
    public Object addLog(){
        ParameterMap pm = this.getParameterMap();
        pm.put("user_id",((User)this.getSession().getAttribute(Const.WX_SESSION_USER)).getId());
        pm.put("add_user",((User)this.getSession().getAttribute(Const.WX_SESSION_USER)).getName());
        return iAccountLogService.add(pm);
    }

    /**
     * 根据日志ID查看日志信息
     * @return
     */
    @RequestMapping(value="/findLog",method=RequestMethod.GET)
    @ResponseBody
    public Object findLog(){
        return iAccountLogService.find(this.getParameterMap());
    }

    /**
     * 根据用户id查询该用户添加的最新日志
     * @return
     */
    @RequestMapping(value = "lastLogByUid", method = RequestMethod.GET)
    @ResponseBody
    public Object lastLogByUid(){
        String id = ((User) this.getSession().getAttribute(Const.WX_SESSION_USER)).getId();
        return iAccountLogService.lastLogByUid(id);
    }

    /**
     * 微信更新日志信息
     * @return
     */
    @RequestMapping(value="/editLog",method=RequestMethod.POST)
    @ResponseBody
    public Object editLog(){
        ParameterMap pm = this.getParameterMap();
        pm.put("update_user",((User)this.getSession().getAttribute(Const.WX_SESSION_USER)).getName());
        return iAccountLogService.edit(pm);
    }

    /**
     * 根据真实姓名和电话查询账号
     * @return
     */
    @RequestMapping(value="/checkNo",method=RequestMethod.GET)
    @ResponseBody
    public Object checkNo(){
        return weChatService.checkNo(this.getParameterMap());
    }

    /**
     * 员工修改密码
     * @return
     */
    @RequestMapping(value="/updatePwd",method=RequestMethod.POST)
    @ResponseBody
    public Object updatePwd(){
        return weChatService.updatePwd(this.getParameterMap());
    }

    /**
     * 管理员审核通过日志
     * @return
     */
    @RequestMapping(value = "/adCheckLog",method = RequestMethod.POST)
    @ResponseBody
    public Object adCheckLog(){
        ParameterMap pm = this.getParameterMap();
        pm.put("accept_user",((User)this.getSession().getAttribute(Const.WX_SESSION_USER)).getName());
        return weChatService.adCheckLog(pm);
    }

    /**
     * 管理员审核回退日志
     * @return
     */
    @RequestMapping(value = "/adReplyLog",method = RequestMethod.POST)
    @ResponseBody
    public Object adReplyLog(){
        ParameterMap pm = this.getParameterMap();
        pm.put("accept_user",((User)this.getSession().getAttribute(Const.WX_SESSION_USER)).getName());
        return weChatService.adReplyLog(pm);
    }

    /**
     * 管理员审核通过日志(批量)
     * @return
     */
    @RequestMapping(value = "/checkAccLog",method = RequestMethod.POST)
    @ResponseBody
    public Object checkAccLog(){
        ParameterMap pm = this.getParameterMap();
        pm.put("accept_user",((User)this.getSession().getAttribute(Const.WX_SESSION_USER)).getName());
        return weChatService.checkAccLog(pm);
    }


    /**
     * 跳转添加页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/toEmpAddPage")
    public String toEmpAddPage(Model model){
        User user = (User) this.getSession().getAttribute(Const.WX_SESSION_USER);
        if(null == user){
            return "redirect:/static/wxpage/wxLogin.html";
        }
        //ParameterMap map = this.getParameterMap();
        AccountLog pm = new AccountLog();
        model.addAttribute("LastLog",pm);
        model.addAttribute("WxPrjNameList",iProjectService.showPrjNameList());
        model.addAttribute("WxPrj2ndNameList",iProjectService.showPrj2ndNameList());
        model.addAttribute("WxDrawNoList",iAccountService.showDrawingNoList());
        model.addAttribute("WxAcNameList",iAccountService.showAcNameList());
        model.addAttribute("WxPartNoList",iAccountService.showPartNoList());
        return "page/wxpage/wxEmpAdd";
    }

    /**
     * 跳转继续添加页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/toEmpReAddPage")
    public String toEmpReAddPage(Model model){
        User user = (User) this.getSession().getAttribute(Const.WX_SESSION_USER);
        if(null == user){
            return "redirect:/static/wxpage/wxLogin.html";
        }
        //ParameterMap map = this.getParameterMap();
        HashMap<String, Object> stringObjectHashMap = iAccountLogService.lastLogByUid(user.getId());
        ParameterMap pm = (ParameterMap) stringObjectHashMap.get("data");
        AccountLog accountLog = new AccountLog();
        if(null != pm){
            model.addAttribute("LastLog",pm);
        }else{
            model.addAttribute("LastLog",accountLog);
        }
        model.addAttribute("WxPrjNameList",iProjectService.showPrjNameList());
        model.addAttribute("WxPrj2ndNameList",iProjectService.showPrj2ndNameList());
        model.addAttribute("WxDrawNoList",iAccountService.showDrawingNoList());
        model.addAttribute("WxAcNameList",iAccountService.showAcNameList());
        model.addAttribute("WxPartNoList",iAccountService.showPartNoList());
        return "page/wxpage/wxEmpAdd";
    }

    /**
     * 跳转员工列表页面
     * @return
     */
    @RequestMapping(value = "/goBackEmpList")
    public String goBackEmpList(){
        return "redirect:/wx/toEmpList";
    }

    /**
     * 管理员跳转添加页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/toAdminAddPage")
    public String toAdminAddPage(Model model){
        User user = (User) this.getSession().getAttribute(Const.WX_SESSION_USER);
        if(null == user){
            return "redirect:/static/wxpage/wxLogin.html";
        }
        //ParameterMap map = this.getParameterMap();
        AccountLog pm = new AccountLog();
        model.addAttribute("LastLog",pm);
        model.addAttribute("WxPrjNameList",iProjectService.showPrjNameList());
        model.addAttribute("WxPrj2ndNameList",iProjectService.showPrj2ndNameList());
        model.addAttribute("WxDrawNoList",iAccountService.showDrawingNoList());
        model.addAttribute("WxAcNameList",iAccountService.showAcNameList());
        model.addAttribute("WxPartNoList",iAccountService.showPartNoList());
        return "page/wxpage/wxAdminAdd";
    }

    /**
     * 管理员跳转继续添加页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/toAdminReAddPage")
    public String toAdminReAddPage(Model model){
        User user = (User) this.getSession().getAttribute(Const.WX_SESSION_USER);
        if(null == user){
            return "redirect:/static/wxpage/wxLogin.html";
        }
        HashMap<String, Object> stringObjectHashMap = iAccountLogService.lastLogByUid(user.getId());
        ParameterMap pm = (ParameterMap) stringObjectHashMap.get("data");
        AccountLog accountLog = new AccountLog();
        if(null != pm){
            model.addAttribute("LastLog",pm);
        }else{
            model.addAttribute("LastLog",accountLog);
        }
        //ParameterMap map = this.getParameterMap();
        model.addAttribute("WxPrjNameList",iProjectService.showPrjNameList());
        model.addAttribute("WxPrj2ndNameList",iProjectService.showPrj2ndNameList());
        model.addAttribute("WxDrawNoList",iAccountService.showDrawingNoList());
        model.addAttribute("WxAcNameList",iAccountService.showAcNameList());
        model.addAttribute("WxPartNoList",iAccountService.showPartNoList());
        return "page/wxpage/wxAdminAdd";
    }

    /**
     * 跳转管理员列表页面
     * @return
     */
    @RequestMapping(value = "/goBackAdminList")
    public String goBackAdminList(){
        return "redirect:/wx/toAdminList";
    }


    /**
     * 员工跳向修改页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/toEmpUpdatePage")
    public String toEmpUpdatePage(Model model){
        User user = (User) this.getSession().getAttribute(Const.WX_SESSION_USER);
        if(null == user){
            return "redirect:/static/wxpage/wxLogin.html";
        }
        //ParameterMap map = this.getParameterMap();
        HashMap<String, Object> stringObjectHashMap = iAccountLogService.find(this.getParameterMap());
        ParameterMap pm = (ParameterMap) stringObjectHashMap.get("data");
        model.addAttribute("ReEditLog",pm);
        model.addAttribute("WxPrjNameList",iProjectService.showPrjNameList());
        model.addAttribute("WxPrj2ndNameList",iProjectService.showPrj2ndNameList());
        model.addAttribute("WxDrawNoList",iAccountService.showDrawingNoList());
        model.addAttribute("WxAcNameList",iAccountService.showAcNameList());
        model.addAttribute("WxPartNoList",iAccountService.showPartNoList());
        return "page/wxpage/wxEmpUpdate";
    }

    /**
     * 管理员跳向修改页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/toAdminUpdatePage")
    public String toAdminUpdatePage(Model model){
        User user = (User) this.getSession().getAttribute(Const.WX_SESSION_USER);
        if(null == user){
            return "redirect:/static/wxpage/wxLogin.html";
        }
        //ParameterMap map = this.getParameterMap();
        HashMap<String, Object> stringObjectHashMap = iAccountLogService.find(this.getParameterMap());
        ParameterMap pm = (ParameterMap) stringObjectHashMap.get("data");
        model.addAttribute("ReEditLog",pm);
        model.addAttribute("WxPrjNameList",iProjectService.showPrjNameList());
        model.addAttribute("WxPrj2ndNameList",iProjectService.showPrj2ndNameList());
        model.addAttribute("WxDrawNoList",iAccountService.showDrawingNoList());
        model.addAttribute("WxAcNameList",iAccountService.showAcNameList());
        model.addAttribute("WxPartNoList",iAccountService.showPartNoList());
        return "page/wxpage/wxAdminUpdate";
    }

    /**
     * 员工跳向查询页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/toEmpSearchPage")
    public String toEmpSearchPage(Model model){
        User user = (User) this.getSession().getAttribute(Const.WX_SESSION_USER);
        if(null == user){
            return "redirect:/static/wxpage/wxLogin.html";
        }
        model.addAttribute("WxPrjNameList",iProjectService.showPrjNameList());
        model.addAttribute("WxPrj2ndNameList",iProjectService.showPrj2ndNameList());
        return "page/wxpage/wxEmpSearch";
    }

    /**
     * 管理员跳向查询页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/toAdminSearchPage")
    public String toAdminSearchPage(Model model){
        User user = (User) this.getSession().getAttribute(Const.WX_SESSION_USER);
        if(null == user){
            return "redirect:/static/wxpage/wxLogin.html";
        }
        model.addAttribute("WxPrjNameList",iProjectService.showPrjNameList());
        model.addAttribute("WxPrj2ndNameList",iProjectService.showPrj2ndNameList());
        model.addAttribute("UserNameList",userService.showUserNameList());
        return "page/wxpage/wxAdminSearch";
    }

    /**
     * 注册成功跳转登录页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/registerToLogin")
    public String registerToLogin(Model model){
        ParameterMap parameterMap = this.getParameterMap();

        model.addAttribute("RegisterUserName",parameterMap.getString("username"));
        return "page/wxpage/empInfo";
    }



}