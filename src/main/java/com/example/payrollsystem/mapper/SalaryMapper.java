package com.example.payrollsystem.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.payrollsystem.entity.Salary;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.payrollsystem.entity.Staff;
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
public interface SalaryMapper extends BaseMapper<Salary> {


    Page<Salary> findPage(Page<Salary> page, @Param("salaryNo") String salaryNo, @Param("staffNo") String staffNo, @Param("staffPost") String staffPost);
    void removeBySalaryNo(@Param("salaryNo") String salaryNo);

    void removeBySalaryNos(@Param("salaryNos") List<String> salaryNos);

}
