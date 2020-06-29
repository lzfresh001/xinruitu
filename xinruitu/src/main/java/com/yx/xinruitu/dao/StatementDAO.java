package com.yx.xinruitu.dao;

import com.yx.xinruitu.entity.AccountLog;
import com.yx.xinruitu.util.ParameterMap;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: xinruitu
 * @description
 * @author: WangYouBin
 * @create: 2019-12-17 08:54
 **/
@Repository
@Mapper
public interface StatementDAO {

    List<ParameterMap> getStatementListByPage(ParameterMap map);


    Integer getStatementListCount(ParameterMap pm);

    List<AccountLog> findStatementList(ParameterMap map);


    List<AccountLog> findChamferingList(ParameterMap map);

    List<AccountLog> findRadiagraphList(ParameterMap map);

    List<AccountLog> findFive(ParameterMap map);

    List<AccountLog> findFour(ParameterMap map);

    List<AccountLog> findThree(ParameterMap map);

    List<AccountLog> findTwo(ParameterMap map);

    List<AccountLog> findOne(ParameterMap map);
}