package com.knowology.config;

import com.knowology.filter.JwtAuthenticationFilter;
import com.knowology.filter.OccsUsernamePasswordAuthenticationFilter;
import com.knowology.handler.SysAuthenticationFailureHandler;
import com.knowology.handler.SysAuthenticationSuccessHandler;
import com.knowology.handler.SysLogoutHandler;
import com.knowology.service.OccsUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * security config
 * @author Conan
 * @date 2019-07-17
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class OccsSecurityConfig extends WebSecurityConfigurerAdapter {

    private final OccsUserDetailsServiceImpl userDetailsService;
    private final SecurityProperties securityProperties;

    @Autowired
    public OccsSecurityConfig(OccsUserDetailsServiceImpl userDetailsService, SecurityProperties securityProperties) {
        this.userDetailsService = userDetailsService;
        this.securityProperties = securityProperties;
    }

    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .and()
//                .formLogin()
//                .loginProcessingUrl("/login")
//                .successHandler(successHandler())
//                .failureHandler(failureHandler())
//                .and()
                .logout()
                .logoutUrl("/logout")
                .addLogoutHandler(logoutHandler())
                .and()
                .authorizeRequests()
                .antMatchers("/websocket/*").permitAll()
                .antMatchers(HttpMethod.OPTIONS,"/**").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers(HttpMethod.GET,"/verify/*").permitAll()
                .antMatchers(HttpMethod.GET,"/initKeyIv").permitAll()
                .antMatchers(HttpMethod.POST,"/passive/download").permitAll()
                .antMatchers(HttpMethod.POST,"/blacknum/download").permitAll()
                .antMatchers(HttpMethod.GET,"/user/getPic/*").permitAll()
                .antMatchers(HttpMethod.GET,"/job/audio/*").permitAll()
                .antMatchers(HttpMethod.GET,"/黑名单号码示例.xlsx").permitAll()
                .antMatchers(HttpMethod.GET,"/号码组示例.xlsx").permitAll()
                .antMatchers(HttpMethod.GET,"/voice/audio/*").permitAll()
                .antMatchers("/swagger*//**",
                        "/v2/api-docs",
                        "/webjars*//**").permitAll()
//                .antMatchers(HttpMethod.GET,"/test/*").permitAll()
                .anyRequest().authenticated()
                .and()
                //excel下载，解决x-frame-options deny禁止iframe调用问题
                .headers().frameOptions().disable()
                .and()
                .cors()
                .and()
                .userDetailsService(userDetailsService)
                .addFilterAt(occsUsernamePasswordAuthenticationFilter(),UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationFilter(), OccsUsernamePasswordAuthenticationFilter.class);
//		http.csrf().disable().authorizeRequests().anyRequest().permitAll();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private OccsUsernamePasswordAuthenticationFilter occsUsernamePasswordAuthenticationFilter() throws Exception{
        OccsUsernamePasswordAuthenticationFilter filter = new OccsUsernamePasswordAuthenticationFilter();
        filter.setAuthenticationManager(authenticationManagerBean());
        filter.setAuthenticationSuccessHandler(successHandler());
        filter.setAuthenticationFailureHandler(failureHandler());
        return filter;
    }

    private JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(userDetailsService,securityProperties);
    }

    private SysAuthenticationSuccessHandler successHandler() {
        return new SysAuthenticationSuccessHandler();
    }

    private SysAuthenticationFailureHandler failureHandler() {
        return new SysAuthenticationFailureHandler();
    }

    private SysLogoutHandler logoutHandler() {
        SysLogoutHandler logoutHandler = new SysLogoutHandler();
        return logoutHandler;
    }
}
