package com.knowology.dao;

import com.knowology.config.MyMapper;
import com.knowology.model.ExampleData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExampleDataMapper extends MyMapper<ExampleData> {
    List<ExampleData> search(@Param("passiveNum") String passiveNum, @Param("startTime") String startTime);
//    List<ReportWholePO> listWholeReport(ReportQueryEntity entity);
//    List<ReportSpecificPO> listSpecificReport(ReportQueryEntity entity);
//    List<ReportWordCloudPO> listWordCloudReport(ReportQueryEntity entity);
//    ReportIndexPO listIndexReport(ReportQueryEntity entity);
//    String selectPercentOfAll(ReportQueryEntity entity);
//    List<ReportAreaPO> listArea();

    void insertExampleData(ExampleData data);
}