package com.fzy.weblog.service;

import com.fzy.weblog.common.domain.dos.UserDO;
import com.fzy.weblog.common.domain.dos.UserRoleDO;
import com.fzy.weblog.common.domain.mapper.UserMapper;
import com.fzy.weblog.common.domain.mapper.UserRoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 从数据库中查询
        UserDO userDo = userMapper.findByUsername(username);
        if (Objects.isNull(userDo)){
throw new UsernameNotFoundException("用户不存在");
        }
        // 用户角色
        List<UserRoleDO> roleDOS= userRoleMapper.selectByUsername(username);
        String[] roleArr = null;
        // 转数组
        List<String> roles = roleDOS.stream().map(p -> p.getRole()).collect(Collectors.toList());
        roleArr = roles.toArray(new String[roles.size()]);
        // ...

        // 暂时先写死，密码为 quanxiaoha, 这里填写的密文，数据库中也是存储此种格式
        // authorities 用于指定角色，这里写死为 ADMIN 管理员
        return User.withUsername(userDo.getUsername()).password(userDo.getPassword())
                .authorities(roleArr).build();

    }
}
