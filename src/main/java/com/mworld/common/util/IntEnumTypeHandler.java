package com.mworld.common.util;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IntEnumTypeHandler<E extends Enum<E> & IntEnum<E>> extends BaseTypeHandler<IntEnum> {
    private Class<IntEnum> type;

    public IntEnumTypeHandler(Class<IntEnum> type) {
        if (type == null)
            throw new IllegalArgumentException("Type argument cannot be null");
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
        ps.setInt(i, parameter.getIntValue());
    }

    @Override
    public IntEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return convert(rs.getInt(columnName));
    }

    @Override
    public IntEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return convert(columnIndex);
    }

    @Override
    public IntEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return convert(cs.getInt(columnIndex));
    }
}

