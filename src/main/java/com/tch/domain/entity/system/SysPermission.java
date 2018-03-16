package com.tch.domain.entity.system;

/**
 * Created by shz on 2017/8/10.
 */

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 权限实体类;
 *
 */
@Entity
public class SysPermission implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;// 主键.
    private String name;// 名称.
    private String resourceType;// 资源类型，[menu|button]
    private String url;// 资源路径.
    private String permission; // 权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:view
    private Long parentId; // 父编号
    private String icon;//图标
    private String parentIds; // 父编号列表
    private Boolean available = Boolean.FALSE;
    private String remark;

//  @ManyToMany(fetch = FetchType.LAZY)
//  @JoinTable(name = "SysRolePermission", joinColumns = { @JoinColumn(name = "permissionId") }, inverseJoinColumns = {
//          @JoinColumn(name = "roleId") })
//  private List<SysRole> roles;



    public String getName() {
        return name;
    }



    public Long getId() {
        return id;
    }



    public void setId(Long id) {
        this.id = id;
    }



    public void setName(String name) {
        this.name = name;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }





    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }


//  public List<SysRole> getRoles() {
//      return roles;
//  }
//
//  public void setRoles(List<SysRole> roles) {
//      this.roles = roles;
//  }   

    public String getRemark() {
        return remark;
    }



    public void setRemark(String remark) {
        this.remark = remark;
    }



    public String getIcon() {
        return icon;
    }



    public void setIcon(String icon) {
        this.icon = icon;
    }



    @Override
    public String toString() {
        return "SysPermission [id=" + id + ", name=" + name + ", resourceType=" + resourceType + ", url=" + url
                + ", permission=" + permission + ", parentId=" + parentId + ", icon=" + icon + ", parentIds="
                + parentIds + ", available=" + available + ", remark=" + remark + "]";
    }




}