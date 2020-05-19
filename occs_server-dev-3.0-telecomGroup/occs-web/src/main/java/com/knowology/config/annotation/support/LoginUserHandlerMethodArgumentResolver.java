package com.knowology.config.annotation.support;

import com.knowology.config.annotation.FullName;
import com.knowology.config.annotation.LoginUser;
import com.knowology.config.annotation.UserId;
import com.knowology.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Optional;

/**
 * @author : Conan
 * @Description 处理请求参数, 取出user信息
 * @date : 2019-05-24 14:50
 **/
public class LoginUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(LoginUserHandlerMethodArgumentResolver.class);

    /**
     * 支持的参数条件
     * @param methodParameter
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return (methodParameter.getParameterType().isAssignableFrom(String.class)
                && methodParameter.hasParameterAnnotation(LoginUser.class))
                ||
                (methodParameter.getParameterType().isAssignableFrom(String.class)
                && methodParameter.hasParameterAnnotation(FullName.class))
                ||
                (methodParameter.getParameterType().isAssignableFrom(String.class)
                        && methodParameter.hasParameterAnnotation(UserId.class));

    }

    /**
     * 解析请求参数
     * @param methodParameter
     * @param modelAndViewContainer
     * @param nativeWebRequest
     * @param webDataBinderFactory
     * @return
     * @throws Exception
     */
    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        if (methodParameter.hasParameterAnnotation(LoginUser.class)) {
            return Optional.ofNullable(nativeWebRequest.getHeader(JwtUtil.AUTHORIZATION))
                    .map(JwtUtil::getUsername).orElse(methodParameter.getParameterAnnotation(LoginUser.class).defaultValue());
        }
        if (methodParameter.hasParameterAnnotation(FullName.class)) {
            return Optional.ofNullable(nativeWebRequest.getHeader(JwtUtil.AUTHORIZATION))
                    .map(JwtUtil::getFullName).orElse(null);
        }
        if (methodParameter.hasParameterAnnotation(UserId.class)) {
            return Optional.ofNullable(nativeWebRequest.getHeader(JwtUtil.AUTHORIZATION))
                    .map(JwtUtil::getUserId).orElse(null);
        }
        return null;
    }
}
