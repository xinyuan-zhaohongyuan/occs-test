package com.knowology.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.knowology.constant.CodeStatus;
import com.knowology.dao.RoleMapper;
import com.knowology.dao.UserInfoMapper;
import com.knowology.dao.UserRoleMapper;
import com.knowology.model.Role;
import com.knowology.model.UserInfo;
import com.knowology.model.UserRole;
import com.knowology.request.UserInfoQuery;
import com.knowology.service.UserService;
import com.knowology.util.cipher.AESUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * <p></p>
 * @see com.knowology.service.UserService
 * @author : Conan
 * @date : 2019-07-22 11:36
 **/
@Transactional(rollbackFor = Exception.class)
@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private static final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    private static final String DEFAULT_PASSWORD = "sa123456";

    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private UserRoleMapper userRoleMapper;
    @Resource
    private RoleMapper roleMapper;

    @Override
    public PageInfo<UserInfo> list(UserInfoQuery userInfoRequestEntity) {
        PageHelper.startPage(userInfoRequestEntity.getPageNum(),userInfoRequestEntity.getPageSize());
        return new PageInfo<>(userInfoMapper.listUserInfo(userInfoRequestEntity));
    }

    @Override
    public List<Role> listAllRole() {
        Example example = new Example(Role.class);
        example.selectProperties("roleId", "roleName");
        return roleMapper.selectByExample(example);
    }

    @Override
    public void add(UserInfoQuery userInfoQuery) {
        //insert data into table `userinfo`
        UserInfo userInfo = new UserInfo();
        userInfo.setFullName(userInfoQuery.getFullName());
        userInfo.setUsername(userInfoQuery.getUsername());
        userInfo.setPassword(bCryptPasswordEncoder.encode(DEFAULT_PASSWORD));
        userInfo.setDeptId(userInfoQuery.getDeptId());
        userInfo.setCrateTime(new Date());
        userInfoMapper.insert(userInfo);
        //insert data into table `userrole`
        userInfoQuery.setUserId(userInfo.getUserId());
        insertListUserRole(userInfoQuery);
    }

    @PreAuthorize("hasAuthority('rightsManagement_update')")
    @Override
    public int update(UserInfoQuery userInfoQuery) {
        if(StringUtils.isNotBlank(userInfoQuery.getPassword())) {
            try {
                userInfoQuery.setPassword(AESUtil.desEncrypt(userInfoQuery.getPassword()));
            } catch (Exception e) {
                logger.error("decode string has error",e.getMessage());
                return CodeStatus.FUNCTION_ERROR;
            }
        }
        updateUserInfo(userInfoQuery);
        //删除并重新添加userrole映射表数据
        deleteUserRole(userInfoQuery.getUserId());
        insertListUserRole(userInfoQuery);
        return CodeStatus.FUNCTION_OK;
    }

    @Override
    public int updatePassword(UserInfoQuery userInfoQuery) {
        if(StringUtils.isNotBlank(userInfoQuery.getPassword())) {
            try {
                userInfoQuery.setPassword(userInfoQuery.getPassword());
            } catch (Exception e) {
                logger.error("decode string has error",e.getMessage());
                return CodeStatus.FUNCTION_ERROR;
            }
        }
        updateUserPassWord(userInfoQuery);
        //删除并重新添加userrole映射表数据
        return CodeStatus.FUNCTION_OK;
    }

    /**
     * 删除用户级联删除UserRole相关中间表数据
     * 管理员操作，不频繁且量不大，故不用外键级联了
     * @param userId
     */
    @Override
    public void delete(Integer userId) {
        Example userInfoExample = new Example(UserInfo.class);
        userInfoExample.createCriteria().andEqualTo("userId",userId);
        userInfoMapper.deleteByExample(userInfoExample);
        deleteUserRole(userId);
    }

    @Override
    public Integer selectCountByUsername(String username) {
        Example example = new Example(UserInfo.class);
        example.createCriteria().andEqualTo("username",username);
        return userInfoMapper.selectCountByExample(example);
    }

    /**
     * 更新userinfo
     * @param userInfoQuery
     */
    private void updateUserInfo(UserInfoQuery userInfoQuery) {
        Example example = new Example(UserInfo.class);
        example.createCriteria().andEqualTo("userId",userInfoQuery.getUserId());
        UserInfo userInfo = new UserInfo();
        userInfo.setFullName(userInfoQuery.getFullName());
        userInfo.setUsername(userInfoQuery.getUsername());
        if(StringUtils.isNotBlank(userInfoQuery.getPassword())){
            userInfo.setPassword(bCryptPasswordEncoder.encode(userInfoQuery.getPassword()));
        }
        userInfo.setDeptId(userInfoQuery.getDeptId());
        userInfo.setUpdateTime(new Date());
        userInfoMapper.updateByExampleSelective(userInfo,example);
    }

    /**
     * 更新userinfo
     * @param userInfoQuery
     */
    private void updateUserPassWord(UserInfoQuery userInfoQuery) {
        Example example = new Example(UserInfo.class);
        example.createCriteria().andEqualTo("username",userInfoQuery.getUsername());
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(userInfoQuery.getUsername());
        if(StringUtils.isNotBlank(userInfoQuery.getPassword())){
            userInfo.setPassword(bCryptPasswordEncoder.encode(userInfoQuery.getPassword()));
        }
        userInfo.setUpdateTime(new Date());
        userInfoMapper.updateByExampleSelective(userInfo,example);
    }

    /**
     * userrole映射表批量新增数据
     * @param userInfoQuery
     */
    private void insertListUserRole(UserInfoQuery userInfoQuery) {
        List<UserRole> list = new LinkedList<>();
        userInfoQuery.getRoleIds().forEach(roleId -> {
            UserRole userRole = new UserRole();
            userRole.setUserId(userInfoQuery.getUserId());
            userRole.setRoleId(roleId);
            list.add(userRole);
        });
        userRoleMapper.insertList(list);
    }

    /**
     * userrole映射表删除数据
     * @param userId
     */
    private void deleteUserRole(Integer userId) {
        Example userRoleExample = new Example(UserRole.class);
        userRoleExample.createCriteria().andEqualTo("userId",userId);
        userRoleMapper.deleteByExample(userRoleExample);
    }

    @Override
    public String getPicUrl(Long userId) {
        final String picUrl = userInfoMapper.getPicUrl(userId);
        return picUrl;
    }

    @Override
    public UserInfo getUserInfo(String username) {
        return userInfoMapper.getUserInfo(username);
    }

    @Override
    public int updatePicUrl(UserInfo userInfo) {
        Example example = new Example(UserInfo.class);
        example.createCriteria().andEqualTo("userId",userInfo.getUserId());
        userInfo.setUpdateTime(new Date());
        return userInfoMapper.updateByExampleSelective(userInfo,example);
    }
}
