package com.example.payrollsystem.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.payrollsystem.entity.Financialstatement;
import com.example.payrollsystem.entity.Staff;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Kevin
 * @since 2023-06-07
 */
public interface StaffMapper extends BaseMapper<Staff> {


    Page<Staff> findPage(Page<Staff> page, @Param("staffNo") String staffNo, @Param("staffName") String staffName, @Param("staffPost") String staffPost);
    void removeByStaffNo(@Param("staffNo") String staffNo);

    void removeByStaffNos(@Param("staffNos") List<String> staffNos);

}
