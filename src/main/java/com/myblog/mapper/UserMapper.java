package com.myblog.mapper;


import com.myblog.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {


    public void addUser(User user);

    public User findUserByNameAndPwd(String name,String pass);

    public User findUserByName(String name);

    public String findUserLove(String userName,int type,String typeDetail);

    public void updateUserLove(String userName,int type,String typeDetail);

    public void insertUserLove(String userName,int type,String typeDetail);

    public List<String> findUserMostLove(String userName, int type);
}
