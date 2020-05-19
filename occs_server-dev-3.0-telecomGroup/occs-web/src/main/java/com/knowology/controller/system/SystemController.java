package com.knowology.controller.system;

import com.knowology.util.ResponseUtil;
import com.knowology.util.cipher.AESUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : Conan
 * @Description 登陆
 * @date : 2019-05-21 10:46
 **/
@RestController
public class SystemController {
    @SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(SystemController.class);

    @GetMapping("initKeyIv")
    public Object initKeyIv() {
        Map<String, String> map = new HashMap<>();
        map.put("key", AESUtil.KEY);
        map.put("iv",AESUtil.IV);
        return ResponseUtil.ok(map);
    }
}
