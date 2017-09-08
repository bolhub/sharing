package com.mworld.util;

import com.mworld.common.exception.NotLoginException;
import com.mworld.resume.exception.EnumArgException;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IntEnumTypeHandler<E extends Enum<E> & IntEnum<E>> extends BaseTypeHandler<IntEnum> {
    private Class<IntEnum> type;

    public IntEnumTypeHandler(Class<IntEnum> type) throws EnumArgException {
        if (type == null)
            throw new EnumArgException("Type argument cannot be null");
        this.type = type;
    }

    private IntEnum convert(int status){
        IntEnum[] objs = type.getEnumConstants();
        for (IntEnum em : objs){
            if (em.getIntValue() == status){
                return em;
            }
        }
        return null;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, IntEnum parameter, JdbcType jdbcType) throws SQLException {

    }

    @Override
    public IntEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return null;
    }

    @Override
    public IntEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return null;
    }

    @Override
    public IntEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return null;
    }
}

