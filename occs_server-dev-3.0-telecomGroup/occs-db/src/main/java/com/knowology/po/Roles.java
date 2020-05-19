package com.knowology.po;

import lombok.Data;

/**
 * 增加一个实体类,给前端返回角色名和ID
 * @author xullent
 */
@Data
public class Roles {

    private String roleName;

    private Integer roleId;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}
