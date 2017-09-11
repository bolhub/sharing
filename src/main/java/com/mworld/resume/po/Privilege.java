package com.mworld.resume.po;

import com.mworld.common.util.IntEnum;

public enum Privilege implements IntEnum<Privilege> {
    PERSONAL("私有", 1), COMMONABLE("公开", 2);

    private String privilegeName;
    private Integer value;
    Privilege(String privilegeName, Integer value) {
        this.privilegeName = privilegeName;
        this.value = value;
    }

    public String getPrivilegeName() {
        return privilegeName;
    }

    public Integer getValue() {
        return value;
    }

    @Override
    public String getDisplayName() {
        return this.privilegeName;
    }

    @Override
    public Integer getIntValue() {
        return this.value;
    }
}
