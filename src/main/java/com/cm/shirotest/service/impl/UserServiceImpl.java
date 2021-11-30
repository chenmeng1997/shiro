package com.cm.shirotest.service.impl;

import com.cm.shirotest.entity.User;
import com.cm.shirotest.dao.UserMapper;
import com.cm.shirotest.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author 陈萌
 * @since 2021-11-30
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
