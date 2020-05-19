package com.knowology.dto;

import lombok.Data;

/**
 * <p>节点标签</p>
 *
 * @author : Conan
 * @date : 2019-08-14 18:29
 **/
@Data
public class NodeTag {
    /**
     * 节点名
     */
    private String node;

    /**
     * 标签
     */
    private String tag;
}
