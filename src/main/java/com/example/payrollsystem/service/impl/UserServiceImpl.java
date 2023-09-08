package com.example.payrollsystem.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.payrollsystem.common.Constants;
import com.example.payrollsystem.common.RoleEnum;
import com.example.payrollsystem.controller.dto.UserDTO;
import com.example.payrollsystem.controller.dto.UserPasswordDTO;
import com.example.payrollsystem.entity.User;
import com.example.payrollsystem.exception.ServiceException;
import com.example.payrollsystem.mapper.UserMapper;
import com.example.payrollsystem.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.payrollsystem.utils.TokenUtils;
import org.apache.xmlbeans.impl.xb.ltgfmt.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

import static cn.hutool.system.SystemUtil.getUserInfo;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Kevin
 * @since 2023-05-30
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private static final Log LOG = Log.get();
    @Resource
    private UserMapper userMapper;

    @Override
    public UserDTO login(UserDTO userDTO) {

        User one = getUserInfo(userDTO);
        if (one != null) {
            BeanUtil.copyProperties(one, userDTO, true);
            //设置token
            String token = TokenUtils.getToken(one.getId().toString(), one.getPassword());
            userDTO.setPassword(SecureUtil.md5(userDTO.getPassword()));
            userDTO.setToken(token);
            return userDTO;

        } else {
            throw new ServiceException(Constants.CODE_600, "用户名或密码错误");
        }
    }

    private User getUserInfo(UserDTO userDTO) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userDTO.getUsername());
        queryWrapper.eq("password", userDTO.getPassword());
        User one;
        try {
            one = getOne(queryWrapper); // 从数据库查询用户信息
        } catch (Exception e) {
            LOG.error(e);
            throw new ServiceException(Constants.CODE_500, "系统错误");
        }
        return one;
    }

    @Override
    public  User getById(String id)
    {
        return userMapper.getById(id);
    };


    @Override
    public Page<User> findPage(Page<User> page, String id, String username, String role) {
        return userMapper.findPage(page, id, username, role);
    };
    @Override
    public void removeById(String id)
    {
        userMapper.removeById(id);
    };

    @Override
    public void removeByIds(List<String> ids)
    {
        userMapper.removeByIds(ids);
    };

}
