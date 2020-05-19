package com.knowology.controller.system;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.knowology.util.ResponseUtil;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 验证码功能
 * @author xullent
 */
@Controller
@RequestMapping("verify")
public class ChaController {

    private static final Logger logger = LoggerFactory.getLogger(ChaController.class);
    @Autowired
    DefaultKaptcha defaultKaptcha;

    /**
     * 生成验证码
     * @param httpServletRequest
     * @param httpServletResponse
     * @throws Exception
     */
    @ApiOperation(value="获取验证码", notes="获取验证码")
    @RequestMapping("/getCode")
    public void defaultKaptcha(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception{
        byte[] captchaChallengeAsJpeg = null;
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        try {
            //生产验证码字符串并保存到session中
            String createText = defaultKaptcha.createText();
            httpServletRequest.getSession().setAttribute("vrifyCode", createText);
            //使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
            BufferedImage challenge = defaultKaptcha.createImage(createText);
            ImageIO.write(challenge, "jpg", jpegOutputStream);
        } catch (IllegalArgumentException e) {
            httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        //定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
        captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
        httpServletResponse.setHeader("Cache-Control", "no-store");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0);
        httpServletResponse.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream =
                httpServletResponse.getOutputStream();
        responseOutputStream.write(captchaChallengeAsJpeg);
        responseOutputStream.flush();
        responseOutputStream.close();
    }

    /**
     * 验证码验证
     * @param httpServletRequest
     * @param httpServletResponse
     * @param code
     * @return
     */
    @RequestMapping("/checkCode")
    @ResponseBody
    public Object imgvrifyControllerDefaultKaptcha(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String code){
        Map<String,Object> map = new HashMap<>();
        String captchaId = (String) httpServletRequest.getSession().getAttribute("vrifyCode");
        logger.info("captchaId : " + captchaId);
       if(code == null || "".equals(code) || captchaId == null ){
           return false;
       }
        if(!captchaId.trim().toUpperCase().equals(code.trim().toUpperCase())) {
            map.put("result",false);
        } else {
            map.put("result",true);
        }
        return ResponseUtil.ok(map);
    }
}
