package com.example.payrollsystem.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.payrollsystem.entity.Salary;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.payrollsystem.entity.Staff;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Kevin
 * @since 2023-06-07
 */
public interface ISalaryService extends IService<Salary> {

    Page<Salary> findPage(Page<Salary> page, String salaryNo, String staffNo, String staffPost);

    void removeBySalaryNo(String salaryNo);

    void removeBySalaryNos(List<String> salaryNos);

}
