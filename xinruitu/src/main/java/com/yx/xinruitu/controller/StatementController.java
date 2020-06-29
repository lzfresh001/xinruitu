package com.yx.xinruitu.controller;

import com.yx.xinruitu.controller.base.BaseController;
import com.yx.xinruitu.service.IStatementService;
import com.yx.xinruitu.util.ParameterMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @program: xinruitu
 * @description
 * @author: WangYouBin
 * @create: 2019-12-17 09:04
 **/
@Controller
@RequestMapping("/statement")
public class StatementController extends BaseController {


    @Autowired
    private IStatementService statementService;

    /** 按要求获取拼接板信息 */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String getStatementList(Model model) {
        ParameterMap map = this.getParameterMap();
        /** 添加起始日期 */
        model.addAttribute("start_date", map.getString("start_date"));
        /** 添加截止日期 */
        model.addAttribute("end_date", map.getString("end_date"));
        model.addAttribute("StatementList", statementService.findStatementList(map));
        return "page/statement/statement";
    }


    /** 钻孔Excel数据导出 */
    @RequestMapping("/export")
    public ResponseEntity<FileSystemResource> exportExcel() {
        HttpServletResponse response = this.getResponse();
        PrintWriter writer = null;
        ParameterMap map = this.getParameterMap();
        String starTime = map.getString("start_date");
        if ( null == starTime || "".equals(starTime) ) {
            try {
                writer = response.getWriter();
                writer.print("<script>alert('请选择统计时间!')</script>");
                writer.print("<script>location.href='/statement/list'</script>");
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return statementService.exportExcel(map, new File("D:\\a.xlsx"),response);
    }


    /** 按要求获取杆件信息 */
    @RequestMapping(value = "/pole", method = RequestMethod.GET)
    public String getPoleList(Model model) {
        ParameterMap map = this.getParameterMap();
        /** 添加起始日期 */
        model.addAttribute("start_date", map.getString("start_date"));
        /** 添加截止日期 */
        model.addAttribute("end_date", map.getString("end_date"));
        model.addAttribute("PoleList", statementService.findPoleList(map));
        return "page/statement/pole";
    }


    /** 按要求获取磁力钻信息 */
    @RequestMapping(value = "/magneticDrill", method = RequestMethod.GET)
    public String getMagneticDrillList(Model model) {
        ParameterMap map = this.getParameterMap();
        /** 添加起始日期 */
        model.addAttribute("start_date", map.getString("start_date"));
        /** 添加截止日期 */
        model.addAttribute("end_date", map.getString("end_date"));
        model.addAttribute("MagneticDrillList", statementService.findMagneticDrillList(map));
        return "page/statement/magneticDrill";
    }

    /** 按要求获取刨边计量信息 */
    @RequestMapping(value = "/chamfering", method = RequestMethod.GET)
    public String getChamferingList(Model model) {
        ParameterMap map = this.getParameterMap();
        /** 添加起始日期 */
        model.addAttribute("start_date", map.getString("start_date"));
        /** 添加截止日期 */
        model.addAttribute("end_date", map.getString("end_date"));
        model.addAttribute("ChamferingList", statementService.findChamferingList(map));
        return "page/statement/chamfering";
    }


    /** 刨边Excel数据导出 */
    @RequestMapping("/chamferingExportExcel")
    public ResponseEntity<FileSystemResource> chamferingExportExcel() {
        ParameterMap map = this.getParameterMap();
        HttpServletResponse response = this.getResponse();
        PrintWriter writer = null ;
        String starTime = map.getString("start_date");
        if ( null == starTime || "".equals(starTime) ) {
            try {
                writer = response.getWriter();
                writer.print("<script>alert('请选择统计时间!')</script>");
                writer.print("<script>location.href='/statement/chamfering'</script>");
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return statementService.chamferingExportExcel(map, new File("D:\\a.xlsx"),response);
    }

    /** 按要求获取焰切计量数据 */
    @RequestMapping(value = "/radiagraph", method = RequestMethod.GET)
    public String getRadiagraphList(Model model) {
        ParameterMap map = this.getParameterMap();
        /** 添加起始日期 */
        model.addAttribute("start_date", map.getString("start_date"));
        /** 添加截止日期 */
        model.addAttribute("end_date", map.getString("end_date"));
        model.addAttribute("RadiagraphList", statementService.findRadiagraphList(map));
        return "page/statement/radiagraph";
    }


    /** 焰切Excel数据导出 */
    @RequestMapping("/radiagraphExportExcel")
    public ResponseEntity<FileSystemResource> radiagraphExportExcel() {
        ParameterMap map = this.getParameterMap();
        HttpServletResponse response = this.getResponse();
        PrintWriter writer = null ;
        String starTime = map.getString("start_date");
        if ( null == starTime || "".equals(starTime) ) {
            try {
                writer = response.getWriter();
                writer.print("<script>alert('请选择统计时间!')</script>");
                writer.print("<script>location.href='/statement/radiagraph'</script>");
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return statementService.radiagraphExportExcel(map, new File("D:\\a.xlsx"),response);
    }




    /** 预留其他位置一 */
    @RequestMapping(value = "/one", method = RequestMethod.GET)
    public String getStatementOne(Model model) {
        ParameterMap map = this.getParameterMap();
        /** 添加起始日期 */
        model.addAttribute("start_date", map.getString("start_date"));
        /** 添加截止日期 */
        model.addAttribute("end_date", map.getString("end_date"));
        model.addAttribute("StatementOneList", statementService.findStatementOne(map));
        return "page/statement/elseOne";
    }

    /** 预留其他位置二 */
    @RequestMapping(value = "/two", method = RequestMethod.GET)
    public String getStatement (Model model) {
        ParameterMap map = this.getParameterMap();
        /** 添加起始日期 */
        model.addAttribute("start_date", map.getString("start_date"));
        /** 添加截止日期 */
        model.addAttribute("end_date", map.getString("end_date"));
        model.addAttribute("StatementTwoList", statementService.findStatementTwo(map));
        return "page/statement/elseTwo";
    }


    /** 预留其他位置三 */
    @RequestMapping(value = "/three", method = RequestMethod.GET)
    public String getStatementThree(Model model) {
        ParameterMap map = this.getParameterMap();
        /** 添加起始日期 */
        model.addAttribute("start_date", map.getString("start_date"));
        /** 添加截止日期 */
        model.addAttribute("end_date", map.getString("end_date"));
        model.addAttribute("StatementThreeList", statementService.findStatementThree(map));
        return "page/statement/elseThree";
    }


    /** 预留其他位置四 */
    @RequestMapping(value = "/four", method = RequestMethod.GET)
    public String getStatementFour(Model model) {
        ParameterMap map = this.getParameterMap();
        /** 添加起始日期 */
        model.addAttribute("start_date", map.getString("start_date"));
        /** 添加截止日期 */
        model.addAttribute("end_date", map.getString("end_date"));
        model.addAttribute("StatementFourList", statementService.findStatementFour(map));
        return "page/statement/elseFour";
    }


    /** 预留其他位置五 */
    @RequestMapping(value = "/five", method = RequestMethod.GET)
    public String getStatementFive(Model model) {
        ParameterMap map = this.getParameterMap();
        /** 添加起始日期 */
        model.addAttribute("start_date", map.getString("start_date"));
        /** 添加截止日期 */
        model.addAttribute("end_date", map.getString("end_date"));
        model.addAttribute("StatementFiveList", statementService.findStatementFive(map));
        return "page/statement/elseFive";
    }
}