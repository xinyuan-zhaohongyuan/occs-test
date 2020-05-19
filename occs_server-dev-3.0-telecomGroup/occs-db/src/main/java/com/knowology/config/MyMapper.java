package com.knowology.config;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @description: BaseMapper，用于拓展接口
 * @author: Conan
 * @create: 2019-01-08 15:21
 **/
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
