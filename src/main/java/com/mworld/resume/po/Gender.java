package com.mworld.resume.po;

import com.mworld.common.util.IntEnum;

public enum Gender implements IntEnum<Gender> {
    MALE("男", 1), FEMALE("女", 2);
    private String gender;
    private Integer value;

    private Gender(String gender, Integer value) {
        this.gender = gender;
        this.value = value;
    }

    public String getGender() {
        return gender;
    }

    public Integer getValue() {
        return value;
    }

    @Override
    public String getDisplayName() {
        return this.gender;
    }

    @Override
    public Integer getIntValue() {
        return this.value;
    }
}
