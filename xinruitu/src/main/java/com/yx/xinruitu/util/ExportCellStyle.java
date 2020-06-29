package com.yx.xinruitu.util;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

/**
 * @program: xinruitu
 * @description
 * @author: WangYouBin
 * @create: 2019-12-18 11:23
 **/
public class ExportCellStyle {


    /**
     * 创建水平和垂直居中的方法
     * @param
     * @return
     */
    public static XSSFCellStyle setRowCellCenter(XSSFCellStyle cellStyle) {

        //水平居中
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        //垂直居中
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        //下边框
        cellStyle.setBorderBottom(BorderStyle.THIN);
        //上边框
        cellStyle.setBorderTop(BorderStyle.THIN);
        //左边框;
        cellStyle.setBorderLeft(BorderStyle.THIN);
        //右边框;
        cellStyle.setBorderRight(BorderStyle.THIN);

        return cellStyle;
    }


}
