package com.knowology.controller.system;

import com.github.pagehelper.PageInfo;
import com.knowology.config.annotation.LoginUser;
import com.knowology.model.Role;
import com.knowology.model.UserInfo;
import com.knowology.request.UserInfoQuery;
import com.knowology.service.DeptService;
import com.knowology.service.UserService;
import com.knowology.util.ResponseUtil;
import com.knowology.util.cipher.AESUtil;
import com.knowology.valid.AddCheck;
import com.knowology.valid.UpdateCheck;
import com.knowology.vo.TreeDept;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>用户管理</p>
 *
 * @author : Conan
 * @date : 2019-07-22 10:44
 **/
@RestController
@RequestMapping("user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private UserService userService;
    private DeptService deptService;
    @Value("${occs.pic.directory}")
    private String picDirectory;

    private UserController(UserService userService,DeptService deptService) {
        this.userService = userService;
        this.deptService = deptService;
    }

    /**
     * 用户列表及查询
     * @return
     */
    @ApiOperation(value="获取用户列表", notes="获取用户列表")
    @PostMapping("list")
    public Object list(@RequestBody UserInfoQuery userInfoRequestEntity) {
        PageInfo<UserInfo> list = userService.list(userInfoRequestEntity);
        Map<String,Object> map = new HashMap<>(4);
        map.put("total", list.getTotal());
        map.put("items", list.getList());
        return ResponseUtil.ok(map);
    }

    @PostMapping("listAllRole")
    public Object listAllRole() {
        List<Role> list = userService.listAllRole();
        return ResponseUtil.ok(list);
    }

    @PostMapping("listAllDepts")
    public Object listAllDepts() {
        List<TreeDept> treeDept = deptService.deptTree();
        return ResponseUtil.ok(treeDept);
    }

//    @PreAuthorize("hasAuthority('rightsManagement_add')")
    @PostMapping("add")
    public Object add(@Validated(AddCheck.class) @RequestBody UserInfoQuery userInfoRequestEntity) {
        int num = userService.selectCountByUsername(userInfoRequestEntity.getUsername());
        if (num > 0) {
            return ResponseUtil.fail("username has existed");
        }
        userService.add(userInfoRequestEntity);
        return ResponseUtil.ok();
    }

//    @PreAuthorize("hasAuthority('rightsManagement_update')")
    @PostMapping("update")
    public Object update(@Validated(UpdateCheck.class) @RequestBody UserInfoQuery userInfoRequestEntity) {
        int code = userService.update(userInfoRequestEntity);
        if (0 == code) {
            return ResponseUtil.ok();
        }
        return ResponseUtil.fail();
    }

    @PostMapping("delete")
    public Object delete(Integer userId) {
        userService.delete(userId);
        return ResponseUtil.ok();
    }
    /**
     * 点击个人设置,加载当前用户信息
     * @param username 登陆用户名，即工号
     * @return
     */
    @PostMapping("getUser")
    public Object getUser(@LoginUser String username) {
        if (StringUtils.isBlank(username)) {
            return ResponseUtil.fail("用户不存在");
        }
        final UserInfo UserInfo = userService.getUserInfo(username);
        return UserInfo;
    }

    /**
     * 获取头像
     * @param id
     * @return
     */
    @GetMapping("getPic/{id}")
    public Object getPic(@PathVariable("id") Long id, HttpServletResponse response) {
        if (id == null) {
            return ResponseUtil.fail("头像加载失败");
        }
        final String picUrl = userService.getPicUrl(id);
        if(picUrl == null || "".equals(picUrl)){
            return ResponseUtil.fail("头像加载失败");
        }
        File imgFile = new File(picUrl);
        FileInputStream fin = null;
        OutputStream output=null;
        try {
            output = response.getOutputStream();
            fin = new FileInputStream(imgFile);
            byte[] arr = new byte[1024*10];
            int n;
            while((n=fin.read(arr))!= -1){
                output.write(arr, 0, n);
            }
            output.flush();
            output.close();
        } catch (IOException e) {
            logger.error("load pic error, ID is:"+id, e.getMessage());
        }
        return ResponseUtil.ok();
    }
    /**
     * 上传头像
     * @param id
     * @param file
     * @param response
     * @return
     */
    @SuppressWarnings("unused")
	@PostMapping("uploadPic")
    public Object uploadPic(Integer id, MultipartFile file, HttpServletResponse response) {
        String picUrl = null;
        if (null == file || null == id) {
            return ResponseUtil.fail("上传失败");
        }
        try {
            final String originalFilename = file.getOriginalFilename();
            final InputStream inputStream = file.getInputStream();
            File picFile = new File(picDirectory);
            if (!picFile.exists()) {
                picFile.mkdirs();
            }
            picUrl = picDirectory + File.separator +originalFilename;
            File picture = new File(picUrl);
            file.transferTo(picture);
        }catch (IOException e) {
            logger.error("uploadPic failed:",e.getMessage());
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(id);
        userInfo.setAvatar(picUrl);
        userService.updatePicUrl(userInfo);
        return ResponseUtil.ok();
    }

    /**
     * 个人设置修改代码
     * @param newPassword
     * @return
     */
    @PostMapping("updatePassword")
    public Object updatePassword(@LoginUser String username, String newPassword) {
        if (StringUtils.isBlank(newPassword)) {
            return ResponseUtil.fail("密码不能为空");
        }
        UserInfoQuery userInfoQuery = new UserInfoQuery();
        userInfoQuery.setUsername(username);
        try {
            newPassword = AESUtil.desEncrypt(newPassword);
        } catch (Exception e) {
            logger.error("password修改失败",e.getMessage());
            return ResponseUtil.fail();
        }
        userInfoQuery.setPassword(newPassword);
        int code = userService.updatePassword(userInfoQuery);
        if (0 == code) {
            return ResponseUtil.ok();
        }
        return ResponseUtil.fail();
    }
}
