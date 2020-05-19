package com.knowology.filter;

import com.alibaba.fastjson.JSON;
import com.knowology.config.SecurityProperties;
import com.knowology.util.JwtUtil;
import com.knowology.util.ResponseUtil;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author : Conan
 * @Description JWT过滤器
 * @date : 2019-05-20 14:51
 **/

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private PathMatcher pathMatcher = new AntPathMatcher();
    private UserDetailsService userDetailsService;
    private SecurityProperties securityProperties;

    public JwtAuthenticationFilter(UserDetailsService userDetailsService, SecurityProperties securityProperties) {
        this.userDetailsService = userDetailsService;
        this.securityProperties = securityProperties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        final String token = httpServletRequest.getHeader(JwtUtil.AUTHORIZATION);
        logger.info("获取请求头token:" + token);
        if (StringUtils.isNotBlank(token)) {
            try {
                final boolean isValid = JwtUtil.validateToken(token);
                if (!isValid) {
                    logger.error("Token is invalid");
                    loginFailed(httpServletResponse);
                    return;
                }

                String username = JwtUtil.getUsername(token);
                if (StringUtils.isNotBlank(username)) {
                    //检查token是否异地登陆
                    String tokenInBucket = JwtUtil.getTokenFromBucket(username);
                    if (StringUtils.isNotBlank(tokenInBucket)
                            && !token.equals(tokenInBucket)) {
                        loginFailed(httpServletResponse);
                        return;
                    }
                    if (null == SecurityContextHolder.getContext().getAuthentication()) {
                        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
                logger.error("Token is invalid:", e.getMessage());
                loginFailed(httpServletResponse);
                return;
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return securityProperties.getIgnoreTokenUrls().stream().anyMatch(url -> pathMatcher.match(url, request.getServletPath()));
    }

    private void loginFailed(HttpServletResponse httpServletResponse) {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.setStatus(403);
        try {
            PrintWriter pw = httpServletResponse.getWriter();
            pw.write(JSON.toJSONString(ResponseUtil.failAuthorization("登陆失败")));
            pw.flush();
            pw.close();
        } catch (IOException e) {
            logger.error("登陆失败:", e.getMessage());
        }

    }

}
