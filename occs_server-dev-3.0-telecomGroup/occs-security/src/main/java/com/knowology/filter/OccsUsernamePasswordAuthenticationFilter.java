package com.knowology.filter;

import com.knowology.util.cipher.AESUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>自定义UsernamePasswordAuthenticationFilter，目的是对前端加密传输的密码进行解密</p>
 *
 * @author : Conan
 * @date : 2019-07-26 16:15
 * @see org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
 **/

public class OccsUsernamePasswordAuthenticationFilter extends
        UsernamePasswordAuthenticationFilter {
    private static final Logger logger = LoggerFactory.getLogger(OccsUsernamePasswordAuthenticationFilter.class);

    public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "username";
    public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";

    private boolean postOnly = true;

    // ~ Constructors
    // ===================================================================================================

    public OccsUsernamePasswordAuthenticationFilter() {
        new AntPathRequestMatcher("/login", "POST");
//        super();
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }

        String username = obtainUsername(request);
        //获取密码密文
        String password = obtainPassword(request);
        try {
            password = AESUtil.desEncrypt(password);
        } catch (Exception e) {
            logger.error("decode password has error",e.getMessage());
            throw new BadCredentialsException("Please check your password");
        }

        if (username == null) {
            username = "";
        }

        if (password == null) {
            password = "";
        }

        username = username.trim();

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                username, password);

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);
        System.out.println("========>"+authRequest.toString());
        return this.getAuthenticationManager().authenticate(authRequest);
    }


}
