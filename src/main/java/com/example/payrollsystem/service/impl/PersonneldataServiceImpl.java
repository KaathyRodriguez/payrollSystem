package com.example.payrollsystem.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.payrollsystem.entity.Personneldata;
import com.example.payrollsystem.entity.Salary;
import com.example.payrollsystem.mapper.PersonneldataMapper;
import com.example.payrollsystem.mapper.SalaryMapper;
import com.example.payrollsystem.service.IPersonneldataService;
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
public class PersonneldataServiceImpl extends ServiceImpl<PersonneldataMapper, Personneldata> implements IPersonneldataService {

    @Resource
    private PersonneldataMapper personneldataMapper;

    @Override
    public Page<Personneldata> findPage(Page<Personneldata> page, String personnelDataNo, String staffNo) {
        return personneldataMapper.findPage(page, personnelDataNo, staffNo);
    };
    @Override
    public void removeByPersonnelDataNo(String personnelDataNo)
    {
        personneldataMapper.removeByPersonnelDataNo(personnelDataNo);
    };

    @Override
    public void removeByPersonnelDataNos(List<String> personnelDataNos)
    {
        personneldataMapper.removeByPersonnelDataNos(personnelDataNos);
    };
}
