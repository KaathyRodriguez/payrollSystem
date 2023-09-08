package com.example.payrollsystem.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.payrollsystem.entity.Financialstatement;
import com.example.payrollsystem.entity.Staff;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Kevin
 * @since 2023-06-07
 */
public interface IStaffService extends IService<Staff> {

    Page<Staff> findPage(Page<Staff> page, String staffNo, String staffName, String staffPost);

    void removeByStaffNo(String staffNo);

    void removeByStaffNos(List<String> staffNos);

}
