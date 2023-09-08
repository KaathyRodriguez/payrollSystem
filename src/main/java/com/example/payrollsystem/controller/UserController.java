package com.example.payrollsystem.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.payrollsystem.common.Constants;
import com.example.payrollsystem.common.Result;
import com.example.payrollsystem.controller.dto.UserDTO;
import com.example.payrollsystem.entity.User;
import com.example.payrollsystem.service.IUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Kevin
 * @since 2023-05-30
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody UserDTO userDTO) {
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            return Result.error(Constants.CODE_400, "参数错误");
        }
        UserDTO dto = userService.login(userDTO);
        return Result.success(dto);
    }


    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody User user) {
        try {
            userService.saveOrUpdate(user);
            return Result.success();
        }catch (Exception e)
        {
            return Result.error();
        }
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id) {
        userService.removeById(id);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<String> ids) {
        userService.removeByIds(ids);
        return Result.success();
    }

//    @GetMapping
//    public Result findAll() {
//        return Result.success(userService.list());
//    }


    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String id,
                           @RequestParam(defaultValue = "") String username,
                           @RequestParam(defaultValue = "") String role) {

        return Result.success(userService.findPage(new Page<>(pageNum, pageSize), id,username,role));
    }

}

