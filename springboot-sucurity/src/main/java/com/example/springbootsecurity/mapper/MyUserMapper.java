package com.example.springbootsecurity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springbootsecurity.entity.MyUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * <h3>springboot-study</h3>
 * <p></p>
 *
 * @author : ZhangYuJie
 * @date : 2022-05-15 16:59
 **/
@Mapper
public interface MyUserMapper extends BaseMapper<MyUser> {
}
