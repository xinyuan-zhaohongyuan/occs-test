package com.knowology.dao;

import com.knowology.config.MyMapper;
import com.knowology.model.UserInfo;
import com.knowology.request.UserInfoQuery;
import org.apache.ibatis.annotations.Param;

import javax.websocket.server.PathParam;
import java.util.List;

public interface UserInfoMapper extends MyMapper<UserInfo> {
    /**
     * UserInfo列表
     * @param userInfoRequestEntity
     * @return List<UserInfo>
     */
    List<UserInfo> listUserInfo(UserInfoQuery userInfoRequestEntity);

    /**
     * @param username
     * @return
     */
    UserInfo selectUserInfo(@PathParam("username") String username);

    /**
     * 加载头像地址
     * @param userId
     * @return
     */
    String getPicUrl(@Param("userId") Long userId);

    /**
     * 根据用户名加载用户信息
     * @param username
     * @return
     */
    UserInfo getUserInfo(@Param("username") String username);
}