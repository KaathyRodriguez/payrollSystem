package com.example.payrollsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.payrollsystem.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Kevin
 * @since 2023-05-30
 */
public interface UserMapper extends BaseMapper<User> {

    Page<User> findPage(Page<User> page, @Param("id") String id, @Param("username") String username, @Param("role") String role);
    void removeById(@Param("id") String id);

    void removeByIds(@Param("ids") List<String> ids);

    @Select("SELECT * FROM User WHERE id = #{id}")
    User getById(String id);
}
