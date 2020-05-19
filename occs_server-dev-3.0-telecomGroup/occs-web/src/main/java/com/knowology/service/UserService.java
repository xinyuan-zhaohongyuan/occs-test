package com.knowology.service;

import com.github.pagehelper.PageInfo;
import com.knowology.model.Role;
import com.knowology.model.UserInfo;
import com.knowology.request.UserInfoQuery;

import java.util.List;


public interface UserService {
    /**
     * 用户列表
     * @return PageInfo<UserInfo>
     */
    PageInfo<UserInfo> list(UserInfoQuery userInfoRequestEntity);

    /**
     * 加载所有角色
     * @return
     */
    List<Role> listAllRole();

    void add(UserInfoQuery userInfoRequestEntity);

    int update(UserInfoQuery userInfoRequestEntity);
    int updatePassword(UserInfoQuery userInfoQuery);

    /**
     * delete by user id
     * @param userId
     */
    void delete(Integer userId);

    /**
     * The username as condition,to count userinfo
     * for judge if username is existed or no
     * @param username
     * @return
     */
    Integer selectCountByUsername(String username);

    /**
     * 获取头像地址
     * @param id
     * @return
     */
     String getPicUrl(Long userId);

    /**
     * 根据用户名加载用户信息
     * @param username
     * @return
     */
    UserInfo getUserInfo(String username);

    /**
     * 更新头像
     * @param userInfo
     * @return
     */
    int updatePicUrl(UserInfo userInfo);
}
