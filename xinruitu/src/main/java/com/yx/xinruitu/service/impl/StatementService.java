package com.yx.xinruitu.service.impl;

import com.yx.xinruitu.dao.StatementDAO;
import com.yx.xinruitu.entity.AccountLog;
import com.yx.xinruitu.entity.PaginationList;
import com.yx.xinruitu.entity.ResponseModel;
import com.yx.xinruitu.service.IStatementService;
import com.yx.xinruitu.util.ExportCellStyle;
import com.yx.xinruitu.util.ParameterMap;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


/**
 * @program: xinruitu
 * @description
 * @author: WangYouBin
 * @create: 2019-12-17 08:59
 **/
@Service
public class StatementService implements IStatementService {

    @Autowired
    private StatementDAO statementDAO;

    private Logger log = Logger.getLogger(this.getClass());

    /**
     * 获取符合要求的钻孔数据
     *
     * @param map
     * @return
     */
    @Override
    public HashMap<String, Object> findStatementList(ParameterMap map) {
        try {
            String start_time = map.getString("start_date");

            if (null == start_time || start_time == "") {
                return ResponseModel.getModel("ok", "success", new PaginationList());
            }
            List<AccountLog> list = statementDAO.findStatementList(map);

            for (AccountLog accountLog : list) {

                //添加计量孔数数据
                accountLog.setMeasure_hole(accountLog.getSingle_hole() * accountLog.getCount() * accountLog.getCoefficient());
                //添加实动孔数;
                accountLog.setActual_hole(accountLog.getSingle_hole() * accountLog.getCount());
            }
            PaginationList page = new PaginationList();
            page.setList(list);
            return ResponseModel.getModel("ok", "success", page);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("get ProList error", e);
            return ResponseModel.getModel("error", "falied", null);
        }
    }

    /**
     * 按要求获取杆件信息
     */
    @Override
    public HashMap<String, Object> findPoleList(ParameterMap map) {
        try {
            String start_date = map.getString("start_date");
            if (null == start_date || start_date == "") {
                return ResponseModel.getModel("ok", "success", new PaginationList());
            }
            List<AccountLog> list = statementDAO.findStatementList(map);
            //遍历集合;
            for (AccountLog accountLog : list) {
                //添加计量孔数数据
                accountLog.setMeasure_hole(accountLog.getSingle_hole() * accountLog.getCount() * accountLog.getCoefficient());
                //添加实动孔数;
                accountLog.setActual_hole(accountLog.getSingle_hole() * accountLog.getCount());
            }
            PaginationList page = new PaginationList();
            page.setList(list);
            return ResponseModel.getModel("ok", "success", page);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("get ProList error", e);
            return ResponseModel.getModel("error", "falied", null);
        }
    }

    /**
     * 按要求获取磁力钻钻孔计量数据
     */
    @Override
    public HashMap<String, Object> findMagneticDrillList(ParameterMap map) {
        try {
            String start_date = map.getString("start_date");
            if (null == start_date || start_date == "") {
                return ResponseModel.getModel("ok", "success", new PaginationList());
            }
            List<AccountLog> list = statementDAO.findStatementList(map);
            //遍历集合;
            for (AccountLog accountLog : list) {
                //添加计量孔数数据
                accountLog.setMeasure_hole(accountLog.getSingle_hole() * accountLog.getCount() * accountLog.getCoefficient());
                //添加实动孔数;
                accountLog.setActual_hole(accountLog.getSingle_hole() * accountLog.getCount());
            }
            PaginationList page = new PaginationList();
            page.setList(list);
            return ResponseModel.getModel("ok", "success", page);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("get ProList error", e);
            return ResponseModel.getModel("error", "falied", null);
        }
    }

    /**
     * 按要求获取刨边数据集合;
     *
     * @param map
     * @return
     */
    @Override
    public HashMap<String, Object> findChamferingList(ParameterMap map) {
        try {
            String start_date = map.getString("start_date");
            if (null == start_date || start_date == "") {
                return ResponseModel.getModel("ok", "success", new PaginationList());
            }
            List<AccountLog> list = statementDAO.findChamferingList(map);
            //遍历集合;
            for (AccountLog accountLog : list) {
                //添加重量数据(单重 * 数量);
                accountLog.setWeight(accountLog.getSingle_weight() * accountLog.getCount());
            }
            PaginationList page = new PaginationList();
            page.setList(list);
            return ResponseModel.getModel("ok", "success", page);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("get ProList error", e);
            return ResponseModel.getModel("error", "falied", null);
        }
    }


    /**
     * * 刨边计量数据导出
     * *
     * * @param map
     * * @param file
     * * @return
     */
    @Override
    public ResponseEntity<FileSystemResource> chamferingExportExcel(ParameterMap map, File file ,HttpServletResponse response) {
        if (file == null) {
            return null;
        }
        //设置下载相关的信息;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", "attachment; filename=" + System.currentTimeMillis() + ".xlsx");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("Last-Modified", new Date().toString());
        headers.add("ETag", String.valueOf(System.currentTimeMillis()));

        String path = "D:\\a.xlsx";


        //创建工作簿
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFDataFormat dataFormat = workbook.createDataFormat();
        XSSFCellStyle xssfCellStyle = workbook.createCellStyle();
        XSSFCellStyle CellStyle = ExportCellStyle.setRowCellCenter(xssfCellStyle);
        //从工作簿里面创建sheet
        Sheet sheet = workbook.createSheet("Sheet");
        //设置sheet
        //设置所有列的列宽
        sheet.setDefaultColumnWidth(10);
        //合并
        CellRangeAddress region0 = new CellRangeAddress(0, 0, 0, 5);
        sheet.addMergedRegion(region0);


        List<AccountLog> list = statementDAO.findChamferingList(map);
        PrintWriter writer = null ;
        CellRangeAddress address = null ;
        if (list.size() == 0){
            try {
                writer = response.getWriter();
                writer.print("<script>alert('查询数据为空!无法导出!')</script>");
                writer.print("<script>window.history.go(-1);</script>");
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
            for (AccountLog accountLog : list) {

                //的sheet上创建第一行
                int rownum = 0;
                Row row1 = sheet.createRow(rownum);
                //在row01上创建单元格
                Cell cell1 = row1.createCell(0);


                /**
                 * 生成项目名称;
                 * 获取年份/月份;
                 * 拼接相关计量数据;
                 */
                String start_date = map.getString("start_date");
                String end_date = map.getString("end_date");
                cell1.setCellValue(start_date + "～" + end_date + "   " + accountLog.getProcess_type() + "计量统计");
                cell1.setCellStyle(CellStyle);

                //第二行
                rownum++;
                Row row2 = sheet.createRow(rownum);
                String[] titles = {"图号","件号", "数量", "单重", "重量/KG", "备注"};


                for (int i = 0; i < titles.length; i++) {
                    Cell cell = row2.createCell(i);
                    cell.setCellValue(titles[i]);
                    cell.setCellStyle(CellStyle);
                }
                //第三行
                rownum++;

                //项目名称;
                String prj_name_1 = "";
                //二级名称;
                String prj_name_2nd_1 = "";

                //项目名称;
                String prj_name_2 = "";
                //二级名称;
                String prj_name_2nd_2 = "";

                double sum = 0;

                for (int i = 0; i < list.size(); i++) {
                    accountLog = list.get(i);
                    if (i < list.size() - 1) {
                        if (i == 0) {
                            prj_name_2 = list.get(i + 1).getPrj_name();
                            prj_name_2nd_2 = list.get(i + 1).getPrj_name_2nd();
                        } else {
                            prj_name_1 = list.get(i - 1).getPrj_name();
                            prj_name_2nd_1 = list.get(i - 1).getPrj_name_2nd();
                            prj_name_2 = list.get(i + 1).getPrj_name();
                            prj_name_2nd_2 = list.get(i + 1).getPrj_name_2nd();
                        }

                    }else{
                        if ( i == 0){
                            prj_name_1 = "";
                            prj_name_2nd_1 = "";
                            prj_name_2 = "";
                            prj_name_2nd_2 = "";
                        } else {
                            prj_name_1 = list.get(i - 1).getPrj_name();
                            prj_name_2nd_1 = list.get(i - 1).getPrj_name_2nd();
                            prj_name_2 = "";
                            prj_name_2nd_2 = "";
                        }
                    }
                    if (!prj_name_1.equals(accountLog.getPrj_name()) || !prj_name_2nd_1.equals(accountLog.getPrj_name_2nd())) {
                        Row row7 = sheet.createRow(rownum);
                        address = new CellRangeAddress(rownum, rownum, 0, 5);
                        sheet.addMergedRegionUnsafe(address);
                        //在row01上创建单元格
                        Cell cell7 = row7.createCell(0);
                        //向cell_row01写东西
                        cell7.setCellValue(accountLog.getPrj_name() + "   " + accountLog.getPrj_name_2nd());
                        RegionUtil.setBorderRight(BorderStyle.THIN,address,sheet);
                        rownum++;
                        sum = 0;
                    }

                    Row row = sheet.createRow(rownum);

                    /*** 创建图号单元格; */
                    Cell drawingCell = row.createCell(0);
                    drawingCell.setCellValue(accountLog.getDrawing_no());
                    drawingCell.setCellStyle(CellStyle);

                    /** 创建件号单元格 */
                    Cell idCell = row.createCell(1);
                    idCell.setCellValue(accountLog.getPart_no());
                    idCell.setCellStyle(CellStyle);

                    /*** 创建数量单元格; */
                    Cell nameCell = row.createCell(2);
                    nameCell.setCellValue(accountLog.getSum());
                    nameCell.setCellStyle(CellStyle);

                    /** 创建单重cell */
                    Cell SingleWeightCell = row.createCell(3);
                    SingleWeightCell.setCellValue(accountLog.getSingle_weight());
                    SingleWeightCell.setCellStyle(CellStyle);

                    /*** 创建重量单元格; */
                    Cell weightCell = row.createCell(4);
                    weightCell.setCellValue(accountLog.getSingle_weight() * accountLog.getCount());
                    weightCell.setCellStyle(CellStyle);

                    /** * 创建备注单元格; */
                    Cell remarkCell = row.createCell(5);
                    remarkCell.setCellValue("");
                    remarkCell.setCellStyle(CellStyle);
                    rownum++;

                    sum += accountLog.getSingle_weight() * accountLog.getCount();
                    if (!prj_name_2.equals(accountLog.getPrj_name()) || !prj_name_2nd_2.equals(accountLog.getPrj_name_2nd())) {
                        //添加备注小计行;
                        Row row5 = sheet.createRow(rownum);
                        Cell cell = row5.createCell(5);
                        for (int x = 0; x <= 4; x++) {
                            Cell cell2 = row5.createCell(x);
                            cell2.setCellStyle(CellStyle);
                        }
                        cell.setCellValue("合计");
                        cell.setCellStyle(CellStyle);

                        //添加备注小计数字行;
                        rownum++;
                        Row row4 = sheet.createRow(rownum);
                        Cell cell3 = row4.createCell(5);
                        for (int x = 0; x <= 4; x++) {
                            Cell cell2 = row4.createCell(x);
                            cell2.setCellValue("");
                            cell2.setCellStyle(CellStyle);
                        }
                        cell3.setCellValue(sum);
                        cell3.setCellStyle(CellStyle);
                        rownum++;
                    }
                }

            }

        //导出数据
        try {
            workbook.write(new FileOutputStream(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().headers(headers).contentLength(file.length()).contentType(MediaType.parseMediaType("application/octet-stream")).body(new FileSystemResource(file));
    }

    /**
     * 按要求获取焰切数据集合;
     *
     * @param map
     * @return
     */
    @Override
    public HashMap<String, Object> findRadiagraphList(ParameterMap map) {
        try {
            String start_date = map.getString("start_date");
            if (null == start_date || start_date == "") {
                return ResponseModel.getModel("ok", "success", new PaginationList());
            }
            List<AccountLog> list = statementDAO.findRadiagraphList(map);
            /**
             * 遍历集合;
             * 添加数据;
             * 	单件长度=（长+宽）*2
             * 	实际长度=单件长度*数量
             * 	计量长度=实际长度*系数
             */
            for (AccountLog accountLog : list) {
                /** 添加单件长度数据(（长+宽）*2); */
                accountLog.setSingle_length((accountLog.getWidth() + accountLog.getHeight()) * 2);
                /** 添加实际长度数据(实际长度=单件长度*数量); */
                accountLog.setActual_length((accountLog.getWidth() + accountLog.getHeight()) * 2 * accountLog.getCount());
                /** 添加计量长度数据(计量长度=实际长度*系数); */
                accountLog.setMeasure_length((accountLog.getWidth() + accountLog.getHeight()) * 2 * accountLog.getCount() * accountLog.getCoefficient());
            }
            PaginationList page = new PaginationList();
            page.setList(list);
            return ResponseModel.getModel("ok", "success", page);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("get ProList error", e);
            return ResponseModel.getModel("error", "falied", null);
        }
    }


    /**
     * 焰切数据导出
     *
     * @param map
     * @param file
     * @return
     */
    @Override
    public ResponseEntity<FileSystemResource> radiagraphExportExcel(ParameterMap map, File file ,HttpServletResponse response) {
        if (file == null) {
            return null;
        }
        //设置下载相关的信息;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", "attachment; filename=" + System.currentTimeMillis() + ".xlsx");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("Last-Modified", new Date().toString());
        headers.add("ETag", String.valueOf(System.currentTimeMillis()));

        String path = "D:\\a.xlsx";


        //创建工作簿
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFDataFormat dataFormat = workbook.createDataFormat();
        XSSFCellStyle tableStyleCenter = workbook.createCellStyle();
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        XSSFCellStyle xssfCellStyle = ExportCellStyle.setRowCellCenter(cellStyle);

        //从工作簿里面创建sheet
        Sheet sheet = workbook.createSheet("Sheet");
        //设置sheet
        //设置所有列的列宽
        sheet.setDefaultColumnWidth(10);

        //合并
        CellRangeAddress region1 = new CellRangeAddress(0, 0, 0, 10);
        sheet.addMergedRegion(region1);
        CellRangeAddress region = null;
        List<AccountLog> list = statementDAO.findStatementList(map);
        PrintWriter writer = null ;

        if (list.size() == 0){
            try {
                writer = response.getWriter();
                writer.print("<script>alert('查询数据为空!无法导出!')</script>");
                writer.print("<script>window.history.go(-1);</script>");
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
            for (AccountLog accountLog : list) {
                //的sheet上创建第一行
                int rownum = 0;
                Row row1 = sheet.createRow(rownum);
                //在row01上创建单元格
                Cell cell1 = row1.createCell(0);
                /**
                 * 生成项目名称;
                 * 获取年份/月份;
                 * 拼接相关计量数据;
                 */
                String start_date = map.getString("start_date");
                String end_date = map.getString("end_date");
                cell1.setCellValue(start_date + "～" + end_date + "   " + accountLog.getProcess_type() + "计量统计");
                cell1.setCellStyle(xssfCellStyle);


                //第二行
                rownum++;
                Row row2 = sheet.createRow(rownum);
                String[] titles = {"图号", "件号", "长", "宽", "厚", "数量", "单件长度", "实际长度", "系数", "计量长度", "备注"};

                for (int i = 0; i < titles.length; i++) {
                    Cell cell = row2.createCell(i);
                    cell.setCellValue(titles[i]);
                    cell.setCellStyle(xssfCellStyle);
                }

                //第三行
                rownum++;

                //项目名称;
                String prj_name_1 = "";
                //二级名称;
                String prj_name_2nd_1 = "";

                //项目名称;
                String prj_name_2 = "";
                //二级名称;
                String prj_name_2nd_2 = "";

                double sum = 0;


                for (int i = 0; i < list.size(); i++) {
                    accountLog = list.get(i);
                    if (i < list.size() - 1) {
                        if (i == 0) {
                            prj_name_2 = list.get(i + 1).getPrj_name();
                            prj_name_2nd_2 = list.get(i + 1).getPrj_name_2nd();
                        } else {
                            prj_name_1 = list.get(i - 1).getPrj_name();
                            prj_name_2nd_1 = list.get(i - 1).getPrj_name_2nd();
                            prj_name_2 = list.get(i + 1).getPrj_name();
                            prj_name_2nd_2 = list.get(i + 1).getPrj_name_2nd();
                        }

                    } else {
                        if ( i == 0){
                            prj_name_1 = "";
                            prj_name_2nd_1 = "";
                            prj_name_2 = "";
                            prj_name_2nd_2 = "";
                        } else {
                            prj_name_1 = list.get(i - 1).getPrj_name();
                            prj_name_2nd_1 = list.get(i - 1).getPrj_name_2nd();
                            prj_name_2 = "";
                            prj_name_2nd_2 = "";
                        }
                    }
                    if (!prj_name_1.equals(accountLog.getPrj_name()) || !prj_name_2nd_1.equals(accountLog.getPrj_name_2nd())) {

                        Row row7 = sheet.createRow(rownum);
                        region = new CellRangeAddress(rownum, rownum, 0, 10);
                        sheet.addMergedRegionUnsafe(region);
                        //在row01上创建单元格
                        Cell cell7 = row7.createCell(0);
                        //向cell_row01写东西
                        cell7.setCellValue(accountLog.getPrj_name() + "   " + accountLog.getPrj_name_2nd());
                        RegionUtil.setBorderRight(BorderStyle.THIN, region, sheet);
                        rownum++;
                        sum = 0;
                    }

                    Row row = sheet.createRow(rownum);

                    /**
                     * 创建图号单元格
                     */
                    Cell drawingNoCell = row.createCell(0);
                    drawingNoCell.setCellValue(accountLog.getDrawing_no());
                    drawingNoCell.setCellStyle(xssfCellStyle);

                    /**
                     * 创建件号单元格;
                     */
                    Cell partNoCell = row.createCell(1);
                    partNoCell.setCellValue(accountLog.getPart_no());
                    partNoCell.setCellStyle(xssfCellStyle);

                    /**
                     * 创建长度单元格;
                     */
                    Cell lenghtCell = row.createCell(2);
                    lenghtCell.setCellValue(accountLog.getWidth());
                    lenghtCell.setCellStyle(xssfCellStyle);


                    /**
                     * 创建宽度单元格;
                     */
                    Cell widthCell = row.createCell(3);
                    widthCell.setCellValue(accountLog.getHeight());
                    widthCell.setCellStyle(xssfCellStyle);

                    /**
                     * 创建厚度单元格;
                     */
                    Cell thicknessCell = row.createCell(4);
                    thicknessCell.setCellValue(accountLog.getThickness());
                    thicknessCell.setCellStyle(xssfCellStyle);

                    /**
                     * 创建数量单元格;
                     */
                    Cell countCell = row.createCell(5);
                    countCell.setCellValue(accountLog.getSum());
                    countCell.setCellStyle(xssfCellStyle);
                    /**
                     * 创建单件长度单元格;
                     */
                    Cell singleLengthCell = row.createCell(6);
                    singleLengthCell.setCellValue((accountLog.getWidth() + accountLog.getHeight()) * 2);
                    singleLengthCell.setCellStyle(xssfCellStyle);
                    /**
                     * 创建实际长度单元格;
                     */
                    Cell actualLengthCell = row.createCell(7);
                    actualLengthCell.setCellValue((accountLog.getWidth() + accountLog.getHeight()) * 2 * accountLog.getCount());
                    actualLengthCell.setCellStyle(xssfCellStyle);
                    /**
                     * 创建系数单元格;
                     */
                    Cell coefficientCell = row.createCell(8);
                    coefficientCell.setCellValue(accountLog.getCoefficient());
                    coefficientCell.setCellStyle(xssfCellStyle);
                    /**
                     * 创建计量长度单元格;
                     */
                    Cell measureHoleCell = row.createCell(9);
                    //设置该行样式为整数的数值
                    tableStyleCenter.setDataFormat(dataFormat.getFormat("0"));
                    measureHoleCell.setCellValue((accountLog.getWidth() + accountLog.getHeight()) * 2 * accountLog.getCount() * accountLog.getCoefficient());
                    measureHoleCell.setCellStyle(xssfCellStyle);
                    /**
                     * 创建备注单元格;
                     */
                    Cell remarkCell = row.createCell(10);
                    remarkCell.setCellValue("");
                    remarkCell.setCellStyle(xssfCellStyle);
                    rownum++;
                    sum += (accountLog.getWidth() + accountLog.getHeight()) * 2 * accountLog.getCount() * accountLog.getCoefficient();
                    if (!prj_name_2.equals(accountLog.getPrj_name()) || !prj_name_2nd_2.equals(accountLog.getPrj_name_2nd())) {
                        //添加备注小计行;
                        Row row5 = sheet.createRow(rownum);
                        for (int x = 0; x <= 9; x++) {
                            Cell cell2 = row5.createCell(x);
                            cell2.setCellStyle(xssfCellStyle);
                        }
                        Cell cell = row5.createCell(10);
                        cell.setCellValue("合计");
                        cell.setCellStyle(xssfCellStyle);

                        //添加备注小计数字行;
                        rownum++;
                        Row row4 = sheet.createRow(rownum);
                        for (int x = 0; x <= 9; x++) {
                            Cell cell2 = row4.createCell(x);
                            cell2.setCellStyle(xssfCellStyle);
                        }
                        Cell cell3 = row4.createCell(10);
                        cell3.setCellStyle(xssfCellStyle);
                        //设置该行样式为整数的数值
                        tableStyleCenter.setDataFormat(dataFormat.getFormat("0"));
                        cell3.setCellValue(sum);
                        cell3.setCellStyle(xssfCellStyle);
                        rownum++;
                    }
                }


                }

        //导出数据
        try {
            workbook.write(new FileOutputStream(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().headers(headers).contentLength(file.length()).contentType(MediaType.parseMediaType("application/octet-stream")).body(new FileSystemResource(file));
    }

    /**
     * 获取符合要求的钻孔数据 (预留其他位置一)
     *
     * @param map
     * @return
     */
    @Override
    public HashMap<String, Object> findStatementOne(ParameterMap map) {
        try {
            String start_time = map.getString("start_date");
            System.out.println(start_time);
            if (null == start_time || start_time == "") {
                return ResponseModel.getModel("ok", "success", new PaginationList());
            }
            List<AccountLog> list = statementDAO.findOne(map);

            for (AccountLog accountLog : list) {

                //添加计量孔数数据
                accountLog.setMeasure_hole(accountLog.getSingle_hole() * accountLog.getCount() * accountLog.getCoefficient());
                //添加实动孔数;
                accountLog.setActual_hole(accountLog.getSingle_hole() * accountLog.getCount());
            }
            PaginationList page = new PaginationList();
            page.setList(list);
            return ResponseModel.getModel("ok", "success", page);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("get ProList error", e);
            return ResponseModel.getModel("error", "falied", null);
        }
    }

    /**
     * 获取符合要求的钻孔数据 (预留其他位置二)
     *
     * @param map
     * @return
     */
    @Override
    public HashMap<String, Object> findStatementTwo(ParameterMap map) {
        try {
            String start_time = map.getString("start_date");
            System.out.println(start_time);
            if (null == start_time || start_time == "") {
                return ResponseModel.getModel("ok", "success", new PaginationList());
            }
            List<AccountLog> list = statementDAO.findTwo(map);

            for (AccountLog accountLog : list) {

                //添加计量孔数数据
                accountLog.setMeasure_hole(accountLog.getSingle_hole() * accountLog.getCount() * accountLog.getCoefficient());
                //添加实动孔数;
                accountLog.setActual_hole(accountLog.getSingle_hole() * accountLog.getCount());
            }
            PaginationList page = new PaginationList();
            page.setList(list);
            return ResponseModel.getModel("ok", "success", page);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("get ProList error", e);
            return ResponseModel.getModel("error", "falied", null);
        }
    }

    /**
     * 获取符合要求的钻孔数据 (预留其他位置三)
     *
     * @param map
     * @return
     */
    @Override
    public HashMap<String, Object> findStatementThree(ParameterMap map) {
        try {
            String start_time = map.getString("start_date");
            System.out.println(start_time);
            if (null == start_time || start_time == "") {
                return ResponseModel.getModel("ok", "success", new PaginationList());
            }
            List<AccountLog> list = statementDAO.findThree(map);

            for (AccountLog accountLog : list) {

                //添加计量孔数数据
                accountLog.setMeasure_hole(accountLog.getSingle_hole() * accountLog.getCount() * accountLog.getCoefficient());
                //添加实动孔数;
                accountLog.setActual_hole(accountLog.getSingle_hole() * accountLog.getCount());
            }
            PaginationList page = new PaginationList();
            page.setList(list);
            return ResponseModel.getModel("ok", "success", page);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("get ProList error", e);
            return ResponseModel.getModel("error", "falied", null);
        }
    }


    /**
     * our
     * 获取符合要求的钻孔数据 (预留其他位置四)
     *
     * @param map
     * @return
     */
    @Override
    public HashMap<String, Object> findStatementFour(ParameterMap map) {
        try {
            String start_time = map.getString("start_date");

            if (null == start_time || start_time == "") {
                return ResponseModel.getModel("ok", "success", new PaginationList());
            }
            List<AccountLog> list = statementDAO.findFour(map);

            for (AccountLog accountLog : list) {

                //添加计量孔数数据
                accountLog.setMeasure_hole(accountLog.getSingle_hole() * accountLog.getCount() * accountLog.getCoefficient());
                //添加实动孔数;
                accountLog.setActual_hole(accountLog.getSingle_hole() * accountLog.getCount());
            }
            PaginationList page = new PaginationList();
            page.setList(list);
            return ResponseModel.getModel("ok", "success", page);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("get ProList error", e);
            return ResponseModel.getModel("error", "falied", null);
        }
    }

    /**
     * 获取符合要求的钻孔数据 (预留其他位置五)
     *
     * @param map
     * @return
     */
    @Override
    public HashMap<String, Object> findStatementFive(ParameterMap map) {
        try {
            String start_time = map.getString("start_date");
            System.out.println(start_time);
            if (null == start_time || start_time == "") {
                return ResponseModel.getModel("ok", "success", new PaginationList());
            }
            List<AccountLog> list = statementDAO.findFive(map);

            for (AccountLog accountLog : list) {

                //添加计量孔数数据
                accountLog.setMeasure_hole(accountLog.getSingle_hole() * accountLog.getCount() * accountLog.getCoefficient());
                //添加实动孔数;
                accountLog.setActual_hole(accountLog.getSingle_hole() * accountLog.getCount());
            }
            PaginationList page = new PaginationList();
            page.setList(list);
            return ResponseModel.getModel("ok", "success", page);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("get ProList error", e);
            return ResponseModel.getModel("error", "falied", null);
        }
    }


    /***
     * 钻孔计量数据导出
     * @param map
     * @param file
     * @return
     */
    @Override
    public ResponseEntity<FileSystemResource> exportExcel(ParameterMap map, File file , HttpServletResponse response) {
        if (file == null) {
            return null;
        }
        //设置下载相关参数;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", "attachment; filename=" + System.currentTimeMillis() + ".xlsx");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("Last-Modified", new Date().toString());
        headers.add("ETag", String.valueOf(System.currentTimeMillis()));

        String path = "D:\\a.xlsx";

        //创建工作簿
        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFDataFormat dataFormat = workbook.createDataFormat();
        XSSFCellStyle xssfCellStyle = workbook.createCellStyle();
        XSSFCellStyle cellStyle = ExportCellStyle.setRowCellCenter(xssfCellStyle);
        //从工作簿里面创建sheet
        Sheet sheet = workbook.createSheet("Sheet");
        //设置sheet
        //设置所有列的列宽
        sheet.setDefaultColumnWidth(10);

        CellRangeAddress region0 = new CellRangeAddress(0, 0, 0, 7);
        sheet.addMergedRegion(region0);
        PrintWriter writer = null ;
        CellRangeAddress address = null ;
        List<AccountLog> list = statementDAO.findStatementList(map);
        if (list.size() == 0){
            try {
                writer = response.getWriter();
                writer.print("<script>alert('查询数据为空!无法导出!')</script>");
                writer.print("<script>window.history.go(-1);</script>");
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
            for (AccountLog accountLog : list) {
                //的sheet上创建第一行
                int rownum = 0;
                Row row1 = sheet.createRow(rownum);
                //在row01上创建单元格
                Cell cell1 = row1.createCell(0);

                /**
                 * 生成项目名称;
                 * 获取年份/月份;
                 * 拼接相关计量数据;
                 */
                String start_date = map.getString("start_date");
                String end_date = map.getString("end_date");
                cell1.setCellValue(start_date + "～" + end_date + "   " + accountLog.getProcess_type() + "计量统计");
                cell1.setCellStyle(cellStyle);
                //设置标题样式

                //第二行
                rownum++;
                Row row2 = sheet.createRow(rownum);
                String[] titles = {"图号","件号", "数量", "单块孔数", "实动孔数", "系数", "计量孔数", "备注"};


                for (int i = 0; i < titles.length; i++) {
                    Cell cell = row2.createCell(i);
                    cell.setCellValue(titles[i]);
                    cell.setCellStyle(cellStyle);
                }

                //前一个项目名称;
                String prj_name_1 = "";
                //前一个项目的二级名称;
                String prj_name_2nd_1 = "";

                //后一个项目名称;
                String prj_name_2 = "";
                //后一个项目的二级名称;
                String prj_name_2nd_2 = "";

                Integer sum = 0;

                //第三行
                rownum++;
                for (int i = 0; i < list.size(); i++) {
                    accountLog = list.get(i);
                    if (i < list.size() - 1) {
                        if (i == 0) {
                            prj_name_2 = list.get(i + 1).getPrj_name();
                            prj_name_2nd_2 = list.get(i + 1).getPrj_name_2nd();
                        }else{
                            prj_name_1 = list.get(i - 1).getPrj_name();
                            prj_name_2nd_1 = list.get(i - 1).getPrj_name_2nd();
                            prj_name_2 = list.get(i + 1).getPrj_name();
                            prj_name_2nd_2 = list.get(i + 1).getPrj_name_2nd();
                        }

                    } else {
                        if ( i == 0){
                            prj_name_1 = "";
                            prj_name_2nd_1 = "";
                            prj_name_2 = "";
                            prj_name_2nd_2 = "";
                        } else {
                            prj_name_1 = list.get(i - 1).getPrj_name();
                            prj_name_2nd_1 = list.get(i - 1).getPrj_name_2nd();
                            prj_name_2 = "";
                            prj_name_2nd_2 = "";
                        }
                    }
                    if (!prj_name_1.equals(accountLog.getPrj_name()) || !prj_name_2nd_1.equals(accountLog.getPrj_name_2nd())) {
                        Row row7 = sheet.createRow(rownum);
                        address = new CellRangeAddress(rownum, rownum, 0, 7);
                        sheet.addMergedRegionUnsafe(address);
                        //在row01上创建单元格
                        Cell cell7 = row7.createCell(0);
                        //向cell_row01写东西
                        cell7.setCellValue(accountLog.getPrj_name() + "   " + accountLog.getPrj_name_2nd());
                        RegionUtil.setBorderRight(BorderStyle.THIN,address,sheet);
                        rownum++;
                        sum = 0;
                    }

                    Row row = sheet.createRow(rownum);


                    //创建图号cell
                    Cell drawingCell = row.createCell(1);
                    drawingCell.setCellValue(accountLog.getDrawing_no());
                    drawingCell.setCellStyle(cellStyle);

                    //创建件号cell
                    Cell idCell = row.createCell(0);
                    idCell.setCellValue(accountLog.getPart_no());
                    idCell.setCellStyle(cellStyle);

                    //创建数量cell
                    Cell nameCell = row.createCell(2);
                    nameCell.setCellValue(accountLog.getSum());
                    nameCell.setCellStyle(cellStyle);

                    //创建单块孔数cell
                    Cell singleHoleCell = row.createCell(3);
                    singleHoleCell.setCellValue(accountLog.getSingle_hole());
                    singleHoleCell.setCellStyle(cellStyle);

                    //创建实动孔数cell
                    Cell actualHoleCell = row.createCell(4);
                    actualHoleCell.setCellValue(accountLog.getSingle_hole() * accountLog.getCount());
                    actualHoleCell.setCellStyle(cellStyle);

                    //创建系数cell
                    Cell coefficientCell = row.createCell(5);
                    coefficientCell.setCellValue(accountLog.getCoefficient());
                    coefficientCell.setCellStyle(cellStyle);


                    //创建计量孔数cell
                    Cell measureHoleCell = row.createCell(6);
                    measureHoleCell.setCellValue(accountLog.getSingle_hole() * accountLog.getCount() * accountLog.getCoefficient());
                    measureHoleCell.setCellStyle(cellStyle);

                    //创建备注cell
                    Cell remarkCell = row.createCell(7);
                    remarkCell.setCellValue("");
                    remarkCell.setCellStyle(cellStyle);
                    rownum++;
                    sum += accountLog.getSingle_hole() * accountLog.getCount() * accountLog.getCoefficient();
                    if (!prj_name_2.equals(accountLog.getPrj_name()) || !prj_name_2nd_2.equals(accountLog.getPrj_name_2nd())) {
                        //添加备注小计行;
                        Row row5 = sheet.createRow(rownum);
                        Cell cell = row5.createCell(7);

                        for (int x = 0; x <= 6; x++) {
                            Cell cell2 = row5.createCell(x);
                            cell2.setCellStyle(cellStyle);
                        }

                        cell.setCellValue("合计");
                        cell.setCellStyle(cellStyle);

                        //添加备注小计数字行;
                        rownum++;
                        Row row4 = sheet.createRow(rownum);
                        Cell cell3 = row4.createCell(7);
                        for (int x = 0; x <= 6; x++) {
                            Cell cell2 = row4.createCell(x);
                            cell2.setCellValue("");
                            cell2.setCellStyle(cellStyle);
                        }
                        cell3.setCellValue(sum);
                        cell3.setCellStyle(cellStyle);
                        rownum++;
                    }
                }
            }
            //导出数据
            try {
                workbook.write(new FileOutputStream(path));
            } catch (IOException e) {
                e.printStackTrace();
            }

        return ResponseEntity.ok().headers(headers).contentLength(file.length()).contentType(MediaType.parseMediaType("application/octet-stream")).body(new FileSystemResource(file));

    }
}