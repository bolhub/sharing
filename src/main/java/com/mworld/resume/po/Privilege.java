package com.mworld.resume.po;

import com.mworld.common.util.IntEnum;
import com.mworld.common.util.IntEnumTypeHandler;

public enum Privilege implements IntEnum<Privilege> {
    PERSONAL("私有", 1), COMMONABLE("公开", 2);

    private String privilegeName;
    private Integer value;

    private Privilege(String privilegeName, Integer value) {
        this.privilegeName = privilegeName;
        this.value = value;
    }

    public String getPrivilegeName() {
        return privilegeName;
    }

    public void setPrivilegeName(String privilegeName) {
        this.privilegeName = privilegeName;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
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
