package com.study.system.pojo;

import lombok.Data;

@Data
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

}
