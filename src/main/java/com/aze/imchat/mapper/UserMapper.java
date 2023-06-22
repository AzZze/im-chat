package com.aze.imchat.mapper;

import com.aze.imchat.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

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
