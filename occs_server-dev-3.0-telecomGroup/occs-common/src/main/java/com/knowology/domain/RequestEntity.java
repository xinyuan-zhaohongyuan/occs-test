package com.knowology.domain;

import lombok.Data;

import java.util.Date;

/**
 * @description: 请求参数
 * @author: Conan
 * @create: 2019-03-07 16:14
 **/
@Data
public class RequestEntity {
	Date startTime;
	Date endTime;
	int startPage;
	int pageSize;
}
