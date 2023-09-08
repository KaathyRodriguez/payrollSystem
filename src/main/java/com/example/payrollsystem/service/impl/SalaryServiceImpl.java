package com.example.payrollsystem.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.payrollsystem.entity.Salary;
import com.example.payrollsystem.entity.Staff;
import com.example.payrollsystem.mapper.SalaryMapper;
import com.example.payrollsystem.mapper.StaffMapper;
import com.example.payrollsystem.service.ISalaryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Kevin
 * @since 2023-06-07
 */
@Service
public class SalaryServiceImpl extends ServiceImpl<SalaryMapper, Salary> implements ISalaryService {

    @Resource
    private SalaryMapper salaryMapper;

    @Override
    public Page<Salary> findPage(Page<Salary> page, String salaryNo, String staffNo, String staffPost) {
        return salaryMapper.findPage(page, salaryNo, staffNo, staffPost);
    };
    @Override
    public void removeBySalaryNo(String salaryNo)
    {
        salaryMapper.removeBySalaryNo(salaryNo);
    };

    @Override
    public void removeBySalaryNos(List<String> salaryNos)
    {
        salaryMapper.removeBySalaryNos(salaryNos);
    };

}
