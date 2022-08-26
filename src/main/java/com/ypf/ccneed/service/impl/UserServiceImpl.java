package com.ypf.ccneed.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ypf.ccneed.entity.User;
import com.ypf.ccneed.mapper.UserMapper;
import com.ypf.ccneed.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ypf
 * @since 2022-07-27
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
