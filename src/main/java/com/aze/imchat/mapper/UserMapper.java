package com.aze.imchat.mapper;

import com.aze.imchat.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * user Mapper 接口
 * </p>
 *
 * @author aze
 * @since 2023-05-20
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
