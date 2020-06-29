package com.yx.xinruitu.controller;

import com.yx.xinruitu.controller.base.BaseController;
import com.yx.xinruitu.entity.Const;
import com.yx.xinruitu.entity.User;
import com.yx.xinruitu.service.IProjectService;
import com.yx.xinruitu.util.ParameterMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 项目管理Controller
 */
@Controller
@RequestMapping("/project")
public class ProjectController extends BaseController {

    @Autowired
    private IProjectService iProjectService;


    /**
     * 项目管理页面路径
     * @param model
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Object list(Model model){
        ParameterMap map = this.getParameterMap();
        if(map.containsKey("pageNo")){
            pageNo=Integer.valueOf(map.get("pageNo").toString());
        }
        model.addAttribute("PrjName",map.getString("prj_name"));
        model.addAttribute("ProList",iProjectService.getProListByPage(map,pageSize,pageNo));
        return "page/project/proList";
    }

    /**
     * 生成项目编号路径
     * @return
     */
    @RequestMapping(value="/createPrjCode",method=RequestMethod.GET)
    @ResponseBody
    public Object createPrjCode(){
        return iProjectService.createPrjCode();
    }

    /**
     * 添加项目
     * @return
     */
    @RequestMapping(value="/add",method=RequestMethod.POST)
    @ResponseBody
    public Object add(){
        ParameterMap pm = this.getParameterMap();
        pm.put("add_user",((User)this.getSession().getAttribute(Const.SESSION_USER)).getName());
        return iProjectService.add(pm);
    }

    /**
     * 根据项目ID查询项目信息
     * @return
     */
    @RequestMapping(value="/find",method=RequestMethod.GET)
    @ResponseBody
    public Object find(){
        return iProjectService.find(this.getParameterMap());
    }

    /**
     * 编辑项目信息
     * @return
     */
    @RequestMapping(value="/edit",method=RequestMethod.POST)
    @ResponseBody
    public Object edit(){
        return iProjectService.edit(this.getParameterMap());
    }

    /**
     * 获取所有项目的名称和ID
     * @return
     */
    @RequestMapping(value="/getPrjName",method=RequestMethod.GET)
    @ResponseBody
    public Object getPrjName(){
        return iProjectService.getPrjName();
    }

    /**
     * 根据项目名称和二级名称获取项目ID
     * @return
     */
    @RequestMapping(value="/getPrjIdValue",method=RequestMethod.GET)
    @ResponseBody
    public Object getPrjIdValue(){
        return iProjectService.getPrjIdValue(this.getParameterMap());
    }
}
