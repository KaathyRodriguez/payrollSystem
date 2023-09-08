package com.example.payrollsystem.controller;


import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.payrollsystem.common.Result;

import com.example.payrollsystem.service.ISalaryService;
import com.example.payrollsystem.entity.Salary;

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
@RequestMapping("/salary")
public class SalaryController {

    @Resource
    private ISalaryService salaryService;

    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody Salary salary) {
        try {
            salaryService.saveOrUpdate(salary);
            return Result.success();
        }catch (Exception e)
        {
            return Result.error();
        }
    }

    @DeleteMapping("/{salaryNo}")
    public Result delete(@PathVariable String salaryNo) {
        salaryService.removeBySalaryNo(salaryNo);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<String> salaryNos) {
        salaryService.removeBySalaryNos(salaryNos);
        return Result.success();
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String salaryNo,
                           @RequestParam(defaultValue = "") String staffNo,
                           @RequestParam(defaultValue = "") String staffPost) {
        return Result.success(salaryService.findPage(new Page<>(pageNum, pageSize), salaryNo,staffNo,staffPost));
    }
    /**
     * 导出接口
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {
        // 从数据库查询出所有的数据
        List<Salary> list = salaryService.list();
        // 通过工具类创建writer 写出到磁盘路径
//        ExcelWriter writer = ExcelUtil.getWriter(filesUploadPath + "/用户信息.xlsx");
        // 在内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //自定义标题别名
        writer.addHeaderAlias("salaryNo", "工资单号");
        writer.addHeaderAlias("staffNo", "教职工编号");
        writer.addHeaderAlias("staffName", "教职工姓名");
        writer.addHeaderAlias("staffPost", "岗位");
        writer.addHeaderAlias("staffTitle", "职称");
        writer.addHeaderAlias("baseSalary", "基本工资");
        writer.addHeaderAlias("income", "绩效工资");
        writer.addHeaderAlias("expend", "扣除工资");
        writer.addHeaderAlias("netPayroll", "实发工资");
        // 一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(list, true);

        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("工资信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();

    }
}

