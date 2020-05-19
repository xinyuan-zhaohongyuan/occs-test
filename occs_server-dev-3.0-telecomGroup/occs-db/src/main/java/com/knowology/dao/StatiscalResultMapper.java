package com.knowology.dao;

import com.knowology.po.OverAll;
import com.knowology.po.Reason;
import com.knowology.po.StatiscalResult;
import com.knowology.po.UnStatiscalResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author : Conan
 * @Description 自定义的反馈结果分析mapper
 * @date : 2019-04-28 17:29
 **/

public interface StatiscalResultMapper {

    /**
     * 加载省份列表
     * @return
     */
    List<String> listProvinces();

    /**
     * 满意度统计
     * @param province
     * @return
     */
    OverAll listOverAll(@Param("province") String province);

    /**
     * 整体满意度排名数据 NEW
     * @param province
     * @return
     */
    List<StatiscalResult> listOverAllRank(@Param("province") String province);
    /**
     * 不满意原因统计
     */
    UnStatiscalResult unStatiscalResult();

    /**
     * 统计节点具体不满意原因
     * @param column
     * @return
     */
    List<Reason> unStatiscalList(@Param("column") String column);

}
