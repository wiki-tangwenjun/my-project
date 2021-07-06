package com.study.pojo;

public class Menu {
    private String id;

    private String name;

    private Long folderOrMenu;

    private String menuHref;

    private String menuRoute;

    private Long menuLevel;

    private Long status;

    private String remark;

    private String parentId;

    private String reserve1;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getFolderOrMenu() {
        return folderOrMenu;
    }

    public void setFolderOrMenu(Long folderOrMenu) {
        this.folderOrMenu = folderOrMenu;
    }

    public String getMenuHref() {
        return menuHref;
    }

    public void setMenuHref(String menuHref) {
        this.menuHref = menuHref == null ? null : menuHref.trim();
    }

    public String getMenuRoute() {
        return menuRoute;
    }

    public void setMenuRoute(String menuRoute) {
        this.menuRoute = menuRoute == null ? null : menuRoute.trim();
    }

    public Long getMenuLevel() {
        return menuLevel;
    }

    public void setMenuLevel(Long menuLevel) {
        this.menuLevel = menuLevel;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    public String getReserve1() {
        return reserve1;
    }

    public void setReserve1(String reserve1) {
        this.reserve1 = reserve1 == null ? null : reserve1.trim();
    }
}