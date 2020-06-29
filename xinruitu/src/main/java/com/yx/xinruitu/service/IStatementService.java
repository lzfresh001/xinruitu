package com.yx.xinruitu.service;

import com.yx.xinruitu.util.ParameterMap;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.HashMap;

/**
 * @program: xinruitu
 * @description
 * @author: WangYouBin
 * @create: 2019-12-17 08:58
 **/
public interface IStatementService {


    ResponseEntity<FileSystemResource> exportExcel(ParameterMap map, File file, HttpServletResponse response);

    HashMap<String,Object> findStatementList(ParameterMap map);

    HashMap<String,Object> findPoleList(ParameterMap map);

    HashMap<String,Object> findMagneticDrillList(ParameterMap map);

    HashMap<String,Object> findChamferingList(ParameterMap map);

    ResponseEntity<FileSystemResource> chamferingExportExcel(ParameterMap map, File file, HttpServletResponse response);

    HashMap<String,Object> findRadiagraphList(ParameterMap map);

    ResponseEntity<FileSystemResource> radiagraphExportExcel(ParameterMap map, File file, HttpServletResponse response);

    /**
     * 预留其他位置一
     * @param map
     * @return
     */
    HashMap<String,Object> findStatementOne(ParameterMap map);

    /**
     * 预留其他位置二
     * @param map
     * @return
     */
    HashMap<String,Object> findStatementTwo(ParameterMap map);

    /**
     * 预留其他位置三
     * @param map
     * @return
     */
    HashMap<String,Object> findStatementThree(ParameterMap map);

    /**
     * 预留其他位置四
     * @param map
     * @return
     */
    HashMap<String,Object> findStatementFour(ParameterMap map);

    /**
     * 预留其他位置五
     * @param map
     * @return
     */
    HashMap<String,Object> findStatementFive(ParameterMap map);
}
