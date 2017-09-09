package com.mworld.common.util;

public interface IntEnum <E extends Enum<E>> {
    String getDisplayName();
    Integer getIntValue();
}
