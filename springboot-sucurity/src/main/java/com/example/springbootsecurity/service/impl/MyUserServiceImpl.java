package com.example.springbootsecurity.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springbootsecurity.entity.MyUser;
import com.example.springbootsecurity.mapper.MyUserMapper;
import com.example.springbootsecurity.service.IMyUserService;
import org.springframework.stereotype.Service;

/**
 * <h3>springboot-study</h3>
 * <p></p>
 *
 * @author : ZhangYuJie
 * @date : 2022-05-15 17:00
 **/
@Service
public class MyUserServiceImpl extends ServiceImpl<MyUserMapper, MyUser> implements IMyUserService {
}
