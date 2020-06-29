package com.yx.xinruitu.controller;

import com.yx.xinruitu.controller.base.BaseController;
import com.yx.xinruitu.entity.Const;
import com.yx.xinruitu.entity.User;
import com.yx.xinruitu.service.*;
import com.yx.xinruitu.util.ParameterMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ClassName: AccountLog
 * Package: com.yx.xinruitu.controller
 * Decription:
 * <p>
 * Date: 2019/12/11 16:32
 * Author: lzfresh
 */
@Controller
@RequestMapping("/accountLog")
public class AccountLogController extends BaseController {

    @Autowired
    private WeChatService weChatService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IAccountLogService iAccountLogService;

    @Autowired
    private IAccountService iAccountService;

    @Autowired
    private IProjectService iProjectService;
    /**
     * 生产管理页面路径
     * @param model
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Object list(Model model){
        ParameterMap map = this.getParameterMap();
        if(map.containsKey("pageNo")){
            pageNo=Integer.valueOf(map.get("pageNo").toString());
        }
        model.addAttribute("SearchData",map.getString("add_time"));
        model.addAttribute("SearchDataL",map.getString("add_time_l"));
        model.addAttribute("SearchPrjName",map.getString("prj_name"));
        model.addAttribute("SearchPrjName2nd",map.getString("prj_name_2nd"));
        model.addAttribute("SearchPartNo",map.getString("part_no"));
        model.addAttribute("SearchUserName",map.getString("add_user"));
        model.addAttribute("SearchProcessType",map.getString("process_type"));
        model.addAttribute("SearchStatus",map.getString("status"));
        if(map.getString("status").equals("待审核")){
            map.put("status",0);
        }
        if(map.getString("status").equals("已审核")){
            map.put("status",1);
        }
        if(map.getString("add_time_l").isEmpty() && map.getString("add_time").isEmpty()){
            map.put("add_time_l",new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        }
        if(map.getString("status").isEmpty()){
            map.put("status",0);
        }
        map.put("logStatus","1and2");
        model.addAttribute("PrjNameList",iProjectService.showPrjNameList());
        model.addAttribute("Prj2ndNameList",iProjectService.showPrj2ndNameList());
        model.addAttribute("UserNameList",userService.showUserNameList());
        model.addAttribute("DrawNoList",iAccountService.showDrawingNoList());
        //model.addAttribute("AcNameList",iAccountService.showAcNameList());
        model.addAttribute("PartNoList",iAccountService.showPartNoList());
        model.addAttribute("AccLogList",iAccountLogService.getAccLogListByPage(map,pageNo));
        return "page/accountLog/accLogList";
    }

    /**
     * 添加生产日志
     * @return
     */
    @RequestMapping(value="/add",method=RequestMethod.POST)
    @ResponseBody
    public Object add(){
        ParameterMap pm = this.getParameterMap();
        pm.put("user_id",((User)this.getSession().getAttribute(Const.SESSION_USER)).getId());
        pm.put("add_user",((User)this.getSession().getAttribute(Const.SESSION_USER)).getName());
        return iAccountLogService.add(pm);
    }

    /**
     * 更新日志信息
     * @return
     */
    @RequestMapping(value="/edit",method=RequestMethod.POST)
    @ResponseBody
    public Object editLog(){
        ParameterMap pm = this.getParameterMap();
        pm.put("update_user",((User)this.getSession().getAttribute(Const.SESSION_USER)).getName());
        return iAccountLogService.edit(pm);
    }

    /**
     * 根据用户id查询该用户添加的最新日志
     * @return
     */
    @RequestMapping(value = "lastLogByUid", method = RequestMethod.GET)
    @ResponseBody
    public Object lastLogByUid(){
        String id = ((User) this.getSession().getAttribute(Const.SESSION_USER)).getId();
        return iAccountLogService.lastLogByUid(id);
    }

    /**
     * 管理员审核通过日志(批量)
     * @return
     */
    @RequestMapping(value = "/checkAccLog",method = RequestMethod.POST)
    @ResponseBody
    public Object checkAccLog(){
        ParameterMap pm = this.getParameterMap();
        pm.put("accept_user",((User)this.getSession().getAttribute(Const.SESSION_USER)).getName());
        return weChatService.checkAccLog(pm);
    }

    /**
     * 根据日志ID查看日志信息
     * @return
     */
    @RequestMapping(value="/find",method=RequestMethod.GET)
    @ResponseBody
    public Object find(){
        return iAccountLogService.find(this.getParameterMap());
    }

    /**
     * 回退日志信息
     * @return
     */
    @RequestMapping(value="/reply",method=RequestMethod.POST)
    @ResponseBody
    public Object edit(){
        ParameterMap pm = this.getParameterMap();
        pm.put("accept_user",((User)this.getSession().getAttribute(Const.SESSION_USER)).getName());
        return iAccountLogService.reply(pm);
    }

    /**
     * 审核通过日志信息
     * @return
     */
    @RequestMapping(value="/check",method=RequestMethod.POST)
    @ResponseBody
    public Object check(){
        ParameterMap pm = this.getParameterMap();
        pm.put("accept_user",((User)this.getSession().getAttribute(Const.SESSION_USER)).getName());
        return iAccountLogService.check(pm);
    }

}
