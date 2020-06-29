package com.yx.xinruitu.controller;

import com.yx.xinruitu.controller.base.BaseController;
import com.yx.xinruitu.entity.Const;
import com.yx.xinruitu.entity.User;
import com.yx.xinruitu.service.IAccountService;
import com.yx.xinruitu.service.IProjectService;
import com.yx.xinruitu.util.ParameterMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * ClassName: AccountController
 * Package: com.yx.xinruitu.controller
 * Decription:
 * <p>
 * Date: 2019/12/10 14:50
 * Author: lzfresh
 */
@Controller
@RequestMapping("/account")
public class AccountController extends BaseController {

    @Autowired
    private IAccountService iAccountService;

    @Autowired
    private IProjectService iProjectService;


    /**
     * 台账管理页面路径
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
        model.addAttribute("SearchPrjName",map.getString("prj_name"));
        model.addAttribute("SearchPrjName2nd",map.getString("prj_name_2nd"));
        model.addAttribute("SearchDrawingNo",map.getString("drawing_no"));
        model.addAttribute("SearchAcName",map.getString("ac_name"));
        model.addAttribute("SearchPartNo",map.getString("part_no"));
        model.addAttribute("PrjNameList",iProjectService.showPrjNameList());
        model.addAttribute("Prj2ndNameList",iProjectService.showPrj2ndNameList());
        model.addAttribute("DrawNoList",iAccountService.showDrawingNoList());
        model.addAttribute("AcNameList",iAccountService.showAcNameList());
        model.addAttribute("PartNoList",iAccountService.showPartNoList());
        model.addAttribute("AccList",iAccountService.getAccListByPage(map,pageSize,pageNo));
        return "page/account/accList";
    }

    /**
     * 添加台账
     * @return
     */
    @RequestMapping(value="/add",method=RequestMethod.POST)
    @ResponseBody
    public Object add(){
        ParameterMap pm = this.getParameterMap();
        pm.put("add_user",((User)this.getSession().getAttribute(Const.SESSION_USER)).getName());
        return iAccountService.add(pm);
    }

    /**
     * 根据台账ID查询台账信息
     * @return
     */
    @RequestMapping(value="/find",method=RequestMethod.GET)
    @ResponseBody
    public Object find(){
        return iAccountService.find(this.getParameterMap());
    }

    /**
     * 编辑台账信息
     * @return
     */
    @RequestMapping(value="/edit",method=RequestMethod.POST)
    @ResponseBody
    public Object edit(){
        return iAccountService.edit(this.getParameterMap());
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
     * 导入台账信息
     * @param file
     * @return
     */

    @RequestMapping(value="/uploadData",method=RequestMethod.POST)
    public String uploadData(MultipartFile file )  {
        HttpServletResponse response = this.getResponse();
        PrintWriter writer = null;
        ParameterMap pm = this.getParameterMap();
        String add_user = ((User)this.getSession().getAttribute(Const.SESSION_USER)).getName();
        pm.put("add_user",add_user);
        try {
            iAccountService.importAccountExcel(file,pm,response);
            writer = response.getWriter();
            writer.print("<script>alert('上传成功!')</script>");
            writer.print("<script>location.href='/account/list'</script>");
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            writer = response.getWriter();
            writer.print("<script>alert('上传失败!')</script>");
            writer.print("<script>location.href='/account/list'</script>");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "<script>location.href='/account/list'</script>";
    }

}
