package com.yx.xinruitu.service.impl;

import com.yx.xinruitu.dao.AccountDAO;
import com.yx.xinruitu.dao.ProjectDAO;
import com.yx.xinruitu.entity.Account;
import com.yx.xinruitu.entity.PaginationList;
import com.yx.xinruitu.entity.ResponseModel;
import com.yx.xinruitu.service.IAccountService;
import com.yx.xinruitu.util.DateUtil;
import com.yx.xinruitu.util.FileEveryDaySerialNumber;
import com.yx.xinruitu.util.ParameterMap;
import com.yx.xinruitu.util.Tools;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * ClassName: AccountService
 * Package: com.yx.xinruitu.service.impl
 * Decription:
 * <p>
 * Date: 2019/12/10 14:57
 * Author: lzfresh
 */
@Service
public class AccountService implements IAccountService {

    @Autowired
    private AccountDAO accountDAO;

    @Autowired
    private ProjectDAO projectDAO;

    private Logger log = Logger.getLogger(this.getClass());


    /**
     * 按要求获取台账信息列表
     *
     * @param pm
     * @param pageSize
     * @param currIndex
     * @return
     */
    @Override
    public HashMap<String, Object> getAccListByPage(ParameterMap pm, int pageSize, int currIndex) {
        try {
            pm.put("drawing_no", pm.getString("drawing_no").toUpperCase());
            pm.put("currIndex", (currIndex - 1) * pageSize);
            pm.put("pageSize", pageSize);
            List<ParameterMap> list = accountDAO.getAccListByPage(pm);
            Integer count = accountDAO.getAccListCount(pm);
            PaginationList page = new PaginationList();
            page.setList(list);
            page.setDraw(currIndex);
            page.setPageSize(pageSize);
            page.setRecordsTotal(count != null ? count : 0);
            page.setTotalpage(page.getRecordsTotal() % pageSize > 0 ? (page.getRecordsTotal() / pageSize) + 1 : (page.getRecordsTotal() / pageSize));
            return ResponseModel.getModel("ok", "success", page);
        } catch (Exception e) {
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            log.error("get ProList error", e);
            return ResponseModel.getModel("error", "falied", null);
        }
    }

    /**
     * 添加台账
     *
     * @param pm
     * @return
     */
    @Override
    @Transactional
    public HashMap<String, Object> add(ParameterMap pm) {
        try {
            pm.put("drawing_no", pm.getString("drawing_no").toUpperCase());
            pm.put("part_no", pm.getString("part_no").toUpperCase());
            pm.put("add_time", DateUtil.getTime());
            ParameterMap parameterMap = accountDAO.checkAccount(pm);
            if (parameterMap != null) {
                return ResponseModel.getModel("台账图号名称已存在", "falied", null);
            }
            accountDAO.saveAccount(pm);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            log.error("error:" + e.getMessage(), e);
            return ResponseModel.getModel("提交失败", "failed", null);
        }
        return ResponseModel.getModel("ok", "success", null);
    }

    /**
     * 根据台账ID查询台账信息
     *
     * @param pm
     * @return
     */
    @Override
    public HashMap<String, Object> find(ParameterMap pm) {
        try {
            String ac_id = pm.getString("ac_id");
            if (Tools.isEmpty(ac_id)) {
                return ResponseModel.getModel("你请求的是冒牌接口", "falied", null);
            }
            ParameterMap map = accountDAO.findAccount(ac_id);
            return ResponseModel.getModel("ok", "success", map);
        } catch (Exception e) {
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            log.error("error:" + e.getMessage(), e);
            return ResponseModel.getModel("提交失败", "failed", null);
        }
    }

    /**
     * 编辑台账信息
     *
     * @param pm
     * @return
     */
    @Override
    public HashMap<String, Object> edit(ParameterMap pm) {
        try {
            pm.put("drawing_no", pm.getString("drawing_no").toUpperCase());
            pm.put("part_no", pm.getString("part_no").toUpperCase());
            accountDAO.editAccount(pm);
        } catch (Exception e) {
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            log.error("error:" + e.getMessage(), e);
            return ResponseModel.getModel("提交失败", "failed", null);
        }
        return ResponseModel.getModel("ok", "success", null);
    }

    /**
     * 获取所有台账的图号
     *
     * @return
     */
    @Override
    public List<Account> showDrawingNoList() {
        return accountDAO.showDrawingNoList();
    }

    /**
     * 获取所有台账的名称
     *
     * @return
     */
    @Override
    public List<Account> showAcNameList() {
        return accountDAO.showAcNameList();
    }

    /**
     * 获取所有台账的件号
     *
     * @return
     */
    @Override
    public List<Account> showPartNoList() {
        return accountDAO.showPartNoList();
    }

    /**
     * 根据图号、名称、件号获取长、宽、厚
     *
     * @param pm
     * @return
     */
    @Override
    public HashMap<String, Object> getTHWValue(ParameterMap pm) {
        try {
            pm.put("drawing_no", pm.getString("drawing_no").toUpperCase());
            pm.put("part_no", pm.getString("part_no").toUpperCase());
            ParameterMap map = (ParameterMap) accountDAO.getTHWValue(pm);
            return ResponseModel.getModel("ok", "success", map);
        } catch (Exception e) {
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            log.error("error:" + e.getMessage(), e);
            return ResponseModel.getModel("提交失败", "failed", null);
        }
    }

    /**
     * Excel数据导入;
     *
     * @param file
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void importAccountExcel(MultipartFile file, ParameterMap parameterMap , HttpServletResponse response) throws Exception {

        //获取添加人新信息;
        String add_user = parameterMap.get("add_user").toString();
        List<List<Object>> lists = read2007Excel2(file , response);
        /** 项目名称 */
        String prj_name = null;
        /** 项目二级名称 */
        String prj_name_2nd = null;

        /** 图号坐标 */
        int dwraingNo = 0;
        /** 件号坐标 */
        int partNo = 0;
        /** 长度坐标 */
        int width = 0;
        /** 宽度坐标 */
        int height = 0;
        /** 厚度坐标 */
        int thickness = 0;
        /** 数量坐标 */
        int count = 0;
        for (int i = 0; i < lists.size(); i++) {
            if (lists.get(i).get(1) == "") {
                String str = lists.get(i).get(0).toString();

                //分割项目项目名称与二级名称;
                String[] s = str.split("\\s+");
                /** 赋值 */
                if (s.length > 2) {
                    prj_name = s[0];
                    prj_name_2nd = s[1];
                    //生成项目编号;
                    String prj_code = FileEveryDaySerialNumber.getValue3();
                    parameterMap.put("prj_code", prj_code);
                    parameterMap.put("prj_name", prj_name);
                    parameterMap.put("prj_name_2nd", prj_name_2nd);
                    parameterMap.put("add_user", add_user);
                    parameterMap.put("add_time", DateUtil.getTime());

                    projectDAO.saveProject(parameterMap);
                    continue;
                }
            }

            if (i == 1) {
                //获取一行中单元格的数量,遍历获取相关单元格的索引.
                for (int j = 0; j < lists.get(i).size(); j++) {
                    switch (lists.get(i).get(j).toString()) {
                        case "图号":
                            dwraingNo = j;
                            break;
                        case "件号":
                            partNo = j;
                            break;
                        case "长":
                            width = j;
                            break;
                        case "宽":
                            height = j;
                            break;
                        case "厚":
                            thickness = j;
                            break;
                        case "数量":
                            count = j;
                            break;
                    }
                }
                continue;
            }

            //获取所有合并单元格
            if (i >= 2 && !"厚".equals(lists.get(i).get(thickness)) && !"".equals(lists.get(i).get(thickness)) &&
                    !"长".equals(lists.get(i).get(width)) && !"".equals(lists.get(i).get(width))) {

                /** 添加项目id 信息 */
                parameterMap.put("prj_id", parameterMap.get("prj_id"));
                /** 添加图号信息 */
                parameterMap.put("drawing_no", lists.get(i).get(dwraingNo));
                /** 添加件号信息 */
                parameterMap.put("part_no", lists.get(i).get(partNo));
                /** 添加厚度信息 */
                parameterMap.put("thickness", Integer.valueOf(String.valueOf(lists.get(i).get(thickness))));
                /** 添加宽度信息 */
                parameterMap.put("height", Integer.valueOf(String.valueOf(lists.get(i).get(height))));
                /** 添加长度信息 */
                parameterMap.put("width", Integer.valueOf(String.valueOf(lists.get(i).get(width))));
                /** 添加数量信息 */
                parameterMap.put("count", Integer.valueOf(String.valueOf(lists.get(i).get(count))));
                /** 添加添加人信息 */
                parameterMap.put("add_user", parameterMap.get("add_user"));
                /** 添加添加时间信息 */
                parameterMap.put("add_time", DateUtil.getTime());
                /** 添加项目名称信息 */
                parameterMap.put("prj_name", prj_name);
                /** 添加项目二级名称信息 */
                parameterMap.put("prj_name_2nd", prj_name_2nd);
                accountDAO.saveAccount(parameterMap);
                
            }
        }
    }


    /**
     * 读取Office excel
     */
    private static List<List<Object>> read2007Excel2(MultipartFile is , HttpServletResponse response) throws IOException {
        List<List<Object>> list = new LinkedList<List<Object>>();
        PrintWriter writer = null ;
        if (is.isEmpty()){
            writer = response.getWriter();
            writer.print("<script>alert('请选择上传文件!')</script>");
            writer.print("<script>window.history.go(-1);</script>");
            writer.close();
        }
        //获取文件后缀名;
        String name = is.getOriginalFilename();
        //判断所上传文件是否为空;
        Workbook xwb = null;
        if (name.endsWith("xls")) {
            xwb = new HSSFWorkbook(is.getInputStream());
        } else if (name.endsWith("xlsx")) {
            xwb = new XSSFWorkbook(is.getInputStream());
        } else {
            writer = response.getWriter();
            writer.print("<script>alert('不支持该文件格式!')</script>");
            writer.print("<script>window.history.go(-1);</script>");
            writer.close();
        }

        for (int x = 0; x < xwb.getNumberOfSheets(); x++) {
            // 循环读取工作簿内容
            Sheet sheet = xwb.getSheetAt(x);
            Object value = null;
            Row row = null;
            Cell cell = null;
            int counter = 0;
            for (int i = sheet.getFirstRowNum(); counter < sheet.getPhysicalNumberOfRows(); i++) {
                row = sheet.getRow(i);
                if (row == null) {
                    continue;
                } else {
                    counter++;
                }
                List<Object> linked = new LinkedList<>();

                for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
                    cell = row.getCell(j);
                    if (cell == null) {
                        continue;
                    }
                    // 格式化 number String// 字符
                    DecimalFormat df = new DecimalFormat("0");
                    // 格式化日期字符串
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    switch (cell.getCellType()) {
                        case XSSFCell.CELL_TYPE_STRING:
                            value = cell.getStringCellValue();
                            break;
                        case XSSFCell.CELL_TYPE_NUMERIC:
                            if ("@".equals(cell.getCellStyle().getDataFormatString())) {
                                value = df.format(cell.getNumericCellValue());
                            } else if ("General".equals(cell.getCellStyle()
                                    .getDataFormatString())) {
                                value = df.format(cell.getNumericCellValue());
                            } else {
                                value = sdf.format(HSSFDateUtil.getJavaDate(cell
                                        .getNumericCellValue()));
                            }
                            break;
                        case XSSFCell.CELL_TYPE_BOOLEAN:
                            value = cell.getBooleanCellValue();
                            break;
                        case XSSFCell.CELL_TYPE_BLANK:
                            value = "";
                            break;
                        default:
                            value = cell.toString();
                    }
                    linked.add(value);
                }
                list.add(linked);
            }
        }

        return list;
    }
}