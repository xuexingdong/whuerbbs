package cn.whuerbbs.backend.mapper.typehandler;

import cn.whuerbbs.backend.enumeration.*;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 通用枚举转换器
 * referer: https://blog.51cto.com/7266799/2171917
 *
 * @param <E>
 */

@MappedJdbcTypes(JdbcType.INTEGER)
@MappedTypes({AttitudeTarget.class, AttitudeStatus.class, Gender.class, NotificationType.class})
public class IntegerEnumHandler<E extends BaseEnum> extends BaseTypeHandler<E> {
    private Map<Integer, E> enumMap = new HashMap<>();

    public IntegerEnumHandler(Class<E> type) {
        if (type == null)
            throw new IllegalArgumentException("Type argument cannot be null");
        var enums = type.getEnumConstants();
        if (enums == null)
            throw new IllegalArgumentException(type.getSimpleName() + " does not represent an enum type.");
        for (var e : enums) {
            enumMap.put(e.value(), e);
        }
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.value());
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return get(rs.getInt(columnName));
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return get(rs.getInt(columnIndex));
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return get(cs.getInt(columnIndex));
    }

    private E get(Integer v) {
        if (v == null) {
            return null;
        }
        return this.enumMap.get(v);

    }
}
