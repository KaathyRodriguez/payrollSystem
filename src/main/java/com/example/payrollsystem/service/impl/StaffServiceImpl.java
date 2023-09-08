package com.example.payrollsystem.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.payrollsystem.entity.Financialstatement;
import com.example.payrollsystem.entity.Staff;
import com.example.payrollsystem.mapper.FinancialstatementMapper;
import com.example.payrollsystem.mapper.StaffMapper;
import com.example.payrollsystem.service.IStaffService;
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
public class StaffServiceImpl extends ServiceImpl<StaffMapper, Staff> implements IStaffService {

    @Resource
    private StaffMapper staffMapper;

    @Override
    public Page<Staff> findPage(Page<Staff> page, String staffNo, String staffName, String staffPost) {
        return staffMapper.findPage(page, staffNo, staffName, staffPost);
    };
    @Override
    public void removeByStaffNo(String staffNo)
    {
        staffMapper.removeByStaffNo(staffNo);
    };

    @Override
    public void removeByStaffNos(List<String> staffNos)
    {
        staffMapper.removeByStaffNos(staffNos);
    };

}
