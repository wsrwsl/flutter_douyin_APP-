package com.myblog.serviceimpl;


import com.myblog.entity.User;
import com.myblog.mapper.UserMapper;
import com.myblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void regist(User user) {
        userMapper.addUser(user);

    }

    @Override
    public User login(String name, String pass) {
          return  userMapper.findUserByNameAndPwd(name,pass);
    }

    @Override
    public User findName(String name){
       return  userMapper.findUserByName(name);

    }
    @Override
    public String findUserLove(String userName,int type,String typeDetail){
        return userMapper.findUserLove(userName,type,typeDetail);
    }
    @Override
    public void updateUserLove(String userName,int type,String typeDetail){
         userMapper.updateUserLove(userName,type,typeDetail);
    }
    @Override
    public void insertUserLove(String userName,int type,String typeDetail){
        userMapper.insertUserLove(userName,type,typeDetail);
    }

    @Override
    public List<String> findUserMostLove(String userName, int type){
        return userMapper.findUserMostLove(userName,type);
    }
}
