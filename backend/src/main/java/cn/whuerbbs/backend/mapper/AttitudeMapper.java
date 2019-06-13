package cn.whuerbbs.backend.mapper;

import cn.whuerbbs.backend.model.Attitude;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface AttitudeMapper {
    boolean insert(Attitude attitude);

    long count(Attitude attitude);

    int delete(Attitude attitude);

    Optional<Attitude> select(Attitude attitude);
}
