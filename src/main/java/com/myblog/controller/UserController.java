package com.myblog.controller;


import com.myblog.entity.User;
import com.myblog.service.UserService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Controller
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
   private UserService userService;

    //status:0代表成功注册,100代表注册失败
    //message:"

    Map<String,Object> map = new HashMap<>();

    @GetMapping("/regist")
    public String regist(String name ,String pass){
        map.clear();
        if(name.equals("") || pass.equals("")){
            map.put("status","100");
            map.put("message","用户名或密码为空");
            return CommonMethod.mapToJsonString(map);
        }
        User user=new User();
        System.out.println("用户注册"+user.getName()+user.getPass());
        user.setName(name);
        user.setPass(pass);
        User isExist = userService.findName(name);
        if(isExist!=null){
            map.put("status",100);
            map.put("message","用户名已存在");
        }else{
            userService.regist(user);
            map.put("status",0);
            map.put("message","success");
        }
        return CommonMethod.mapToJsonString(map);
    }

    @GetMapping("/login")
    public String  login(String name,String pass){
        map.clear();
        System.out.println("用户登录："+name+pass);
        User user =  userService.login(name,pass);
        if(user!=null&& !name.equals("") && !pass.equals(""))
        {
            map.put("status",0);
            map.put("message","success");
        }else{
            map.put("status",100);
            map.put("message","用户名或密码错误");
        }
        return CommonMethod.mapToJsonString(map);
    }

    @GetMapping("/requestUserPower")
    public String  requestUserPower(String userName){
        map.clear();
        User user = userService.findName(userName);
        if(user==null){
            map.put("status",100);
            map.put("message","用户名不存在");
        }else{
            map.put("status",0);
            map.put("meeage","success");
            map.put("data",CommonMethod.modelToMap(user));
        }
        return CommonMethod.mapToJsonString(map);
    }

    @GetMapping("/updateUserLove")
    public String  updateUserLove(String userName,String brand,String type,double price,int age){
        map.clear();
        if(!brand.equals("--")){
            hindleLove(userName,1,brand);
        }
        if(!type.equals("--")){
            hindleLove(userName,2,type);
        }
        if(price>0.01){
            String priceStr=Double.toString(price);
            hindleLove(userName,3,priceStr);
        }
        if(age<100){
            String ageStr=Integer.toString(age);
            hindleLove(userName,4,ageStr);
        }
        map.put("status",0);
        map.put("meeage","success");
        return CommonMethod.mapToJsonString(map);
    }

    void hindleLove(String userName,int type,String typeDetail){
        String isExist=userService.findUserLove(userName,type,typeDetail);
        if(isExist==null){
            userService.insertUserLove(userName,type,typeDetail);
        }else{
            userService.updateUserLove(userName,type,typeDetail);
        }
    }

}
