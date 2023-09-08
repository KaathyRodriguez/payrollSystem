package com.example.payrollsystem.controller;


import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.payrollsystem.common.Result;
import com.example.payrollsystem.entity.Financialstatement;
import com.example.payrollsystem.entity.Salary;
import com.example.payrollsystem.service.IFinancialstatementService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Kevin
 * @since 2023-06-02
 */
@RestController
@RequestMapping("/financialstatement")
public class FinancialstatementController {

    @Resource
    private IFinancialstatementService financialstatementService;

    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody Financialstatement financialstatement) throws Exception{
        try {
            financialstatementService.saveOrUpdate(financialstatement);
            return Result.success();
        }catch (Exception e)
        {
            return Result.error();
        }
    }

    @DeleteMapping("/{statementNo}")
    public Result delete(@PathVariable String statementNo) {
        financialstatementService.removeByStatementNo(statementNo);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<String> statementNos) {
        financialstatementService.removeByStatementNos(statementNos);
        return Result.success();
    }



    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String statementNo,
                           @RequestParam(defaultValue = "") String statementDate,
                           @RequestParam(defaultValue = "") String auditor) {
        return Result.success(financialstatementService.findPage(new Page<>(pageNum, pageSize), statementNo,statementDate,auditor));
    }
    /**
     * 导出接口
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {
        // 从数据库查询出所有的数据
        List<Financialstatement> list = financialstatementService.list();
        // 通过工具类创建writer 写出到磁盘路径
//        ExcelWriter writer = ExcelUtil.getWriter(filesUploadPath + "/用户信息.xlsx");
        // 在内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //自定义标题别名
        writer.addHeaderAlias("statementNo", "财务编号");
        writer.addHeaderAlias("statementDate", "财务日期");
        writer.addHeaderAlias("monthIncome", "月收入");
        writer.addHeaderAlias("monthExpend", "月支出");
        writer.addHeaderAlias("monthProfit", "月利润");
        writer.addHeaderAlias("auditor", "审计人");
        writer.addHeaderAlias("note", "备注");
        // 一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(list, true);

        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("财务报表", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();

    }
}

