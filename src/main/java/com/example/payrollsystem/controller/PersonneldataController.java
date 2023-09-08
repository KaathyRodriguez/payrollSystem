package com.example.payrollsystem.controller;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.payrollsystem.entity.Salary;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.payrollsystem.common.Result;

import com.example.payrollsystem.service.IPersonneldataService;
import com.example.payrollsystem.entity.Personneldata;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Kevin
 * @since 2023-06-07
 */
@RestController
@RequestMapping("/personneldata")
public class PersonneldataController {

    @Resource
    private IPersonneldataService personneldataService;

    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody Personneldata personneldata) {
        try {
            personneldataService.saveOrUpdate(personneldata);
            return Result.success();
        }catch (Exception e)
        {
            return Result.error();
        }
    }

    @DeleteMapping("/{personnelDataNo}")
    public Result delete(@PathVariable String personnelDataNo) {
        personneldataService.removeByPersonnelDataNo(personnelDataNo);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<String> personnelDataNos) {
        personneldataService.removeByPersonnelDataNos(personnelDataNos);
        return Result.success();
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String personnelDataNo,
                           @RequestParam(defaultValue = "") String staffNo) {
        return Result.success(personneldataService.findPage(new Page<>(pageNum, pageSize), personnelDataNo,staffNo));
    }
    /**
     * excel 导入
     * @param file
     * @throws Exception
     */
    @PostMapping("/import")
    public Result imp(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        // 方式：忽略表头的中文，直接读取表的内容
        List<List<Object>> list = reader.read(1);
        List<Personneldata> personnelDatas = CollUtil.newArrayList();
        for (List<Object> row : list) {

            Personneldata personnelData = new Personneldata();
            personnelData.setPersonnelDataNo(row.get(0).toString());
            personnelData.setStaffNo(row.get(1).toString());
            personnelData.setBaseSalary(Float.valueOf(row.get(2).toString()));
            personnelData.setLivingAllowances(Float.valueOf(row.get(3).toString()));
            personnelData.setBookFee(Float.valueOf(row.get(4).toString()));
            personnelData.setCommutingFee(Float.valueOf(row.get(5).toString()));
            personnelData.setCleanFee(Float.valueOf(row.get(6).toString()));
            personnelData.setWorkHours(Float.valueOf(row.get(7).toString()));
            personnelData.setHourlyEarnings(Float.valueOf(row.get(8).toString()));
            personnelData.setPostAllowance(Float.valueOf(row.get(9).toString()));
            personnelData.setIndividualIncomeTax(Float.valueOf(row.get(10).toString()));
            personnelData.setHousingFund(Float.valueOf(row.get(11).toString()));
            personnelData.setPremium(Float.valueOf(row.get(12).toString()));


            personnelDatas.add(personnelData);
        }

        personneldataService.saveBatch(personnelDatas);
        return Result.success(true);
    }

    /**
     * 导出接口
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {
        // 从数据库查询出所有的数据
        List<Personneldata> list = personneldataService.list();
        // 在内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //自定义标题别名
        writer.addHeaderAlias("personnelDataNo", "人事数据编号");
        writer.addHeaderAlias("staffNo", "教职工编号");
        writer.addHeaderAlias("baseSalary", "基本工资");
        writer.addHeaderAlias("livingAllowances", "生活补贴");
        writer.addHeaderAlias("bookFee", "书报费");
        writer.addHeaderAlias("commutingFee", "交通费");
        writer.addHeaderAlias("cleanFee", "洗理费");
        writer.addHeaderAlias("workHours", "工作小时数");
        writer.addHeaderAlias("hourlyEarnings", "计时工资");
        writer.addHeaderAlias("postAllowance", "岗位津贴");
        writer.addHeaderAlias("individualIncomeTax", "个人所得税");
        writer.addHeaderAlias("housingFund", "住房公积金");
        writer.addHeaderAlias("premium", "保险");
        // 一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(list, true);

        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("工资明细", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();

    }
}

