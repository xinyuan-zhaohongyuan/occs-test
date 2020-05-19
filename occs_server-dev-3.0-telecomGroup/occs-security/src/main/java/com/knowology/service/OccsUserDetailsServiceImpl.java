package com.knowology.service;

import com.knowology.dao.MenuMapper;
import com.knowology.dao.UserInfoMapper;
import com.knowology.domain.OccsUserDetails;
import com.knowology.model.Menu;
import com.knowology.model.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>Implement UserDetailsService</p>
 *
 * @author : Conan
 * @date : 2019-07-17 16:18
 **/
@Service
public class OccsUserDetailsServiceImpl implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(OccsUserDetailsServiceImpl.class);
    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo = selectUserInfo(username);
        if (userInfo == null) {
            throw new UsernameNotFoundException("Username is not found");
        }
        List<Menu> authorities = menuMapper.selectMenusByUsername(username);
        if (null == authorities || authorities.size() == 0) {
            //权限为空,即用户未授予任何权限
            logger.error("User's authority is empty,it shouldn't have happened , please check this user's authority");
            return new OccsUserDetails(userInfo.getUsername(), userInfo.getPassword(), userInfo.getFullName(),userInfo.getUserId());
        }
        return new OccsUserDetails(userInfo.getUsername(), userInfo.getPassword(),
                AuthorityUtils.commaSeparatedStringToAuthorityList(String.join(",", authorities.stream().map(Menu::getOperation).collect(Collectors.toList()))),
                userInfo.getFullName(),userInfo.getUserId());
    }

    private UserInfo selectUserInfo(String username) {
        Example example = new Example(UserInfo.class);
        example.createCriteria().andEqualTo("username", username);
        List<UserInfo> userInfos = userInfoMapper.selectByExample(example);
        return userInfos == null || userInfos.isEmpty() ? null : userInfos.get(0);
    }

}
