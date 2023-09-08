package com.example.payrollsystem.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.payrollsystem.entity.Personneldata;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.payrollsystem.entity.Salary;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Kevin
 * @since 2023-06-07
 */
public interface IPersonneldataService extends IService<Personneldata> {

    Page<Personneldata> findPage(Page<Personneldata> page, String personnelDataNo, String staffNo);

    void removeByPersonnelDataNo(String personnelDataNo);

    void removeByPersonnelDataNos(List<String> personnelDataNo);

}
