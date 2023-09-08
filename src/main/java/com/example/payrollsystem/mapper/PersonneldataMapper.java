package com.example.payrollsystem.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.payrollsystem.entity.Personneldata;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.payrollsystem.entity.Salary;
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
public interface PersonneldataMapper extends BaseMapper<Personneldata> {

    Page<Personneldata> findPage(Page<Personneldata> page, @Param("personnelDataNo") String personnelDataNo, @Param("staffNo") String staffNo);
    void removeByPersonnelDataNo(@Param("personnelDataNo") String personnelDataNo);

    void removeByPersonnelDataNos(@Param("personnelDataNos") List<String> personnelDataNos);

}
