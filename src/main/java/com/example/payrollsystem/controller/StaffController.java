package com.example.payrollsystem.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.payrollsystem.common.Result;

import com.example.payrollsystem.service.IStaffService;
import com.example.payrollsystem.entity.Staff;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Kevin
 * @since 2023-06-07
 */
@RestController
@RequestMapping("/staff")
public class StaffController {

    @Resource
    private IStaffService staffService;

    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody Staff staff) {
        try {
            staffService.saveOrUpdate(staff);
            return Result.success();
        }catch (Exception e)
        {
            return Result.error();
        }
    }

    @DeleteMapping("/{staffNo}")
    public Result delete(@PathVariable String staffNo) {
        staffService.removeByStaffNo(staffNo);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<String> staffNos) {
        staffService.removeByStaffNos(staffNos);
        return Result.success();
    }


    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String staffNo,
                           @RequestParam(defaultValue = "") String staffName,
                           @RequestParam(defaultValue = "") String staffPost){
        return Result.success(staffService.findPage(new Page<>(pageNum, pageSize), staffNo,staffName,staffPost));
    }

}

