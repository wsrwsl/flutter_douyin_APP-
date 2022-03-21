package com.myblog.service;


import com.myblog.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    void regist(User user);
    User login(String name,String pass);
    User findName(String name);
    public String findUserLove(String userName,int type,String typeDetail);
    public void updateUserLove(String userName,int type,String typeDetail);
    public void insertUserLove(String userName,int type,String typeDetail);
    public List<String> findUserMostLove(String userName, int type);
}
