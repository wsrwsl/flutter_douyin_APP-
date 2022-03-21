package com.myblog.controller;

import com.myblog.entity.Video;
import com.myblog.service.UserService;
import com.myblog.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.abs;

@Controller
@RestController
@RequestMapping("/video")
public class VideoController {
    Map<String,Object> map = new HashMap<>();
    Map<String,Object> data = new HashMap<>();
    @Autowired
    private VideoService videoService;
    @Autowired
    private UserService userService;

    @GetMapping("/updateVideo")
    public String  updateVideo(String userName,String videoUrl,String brand,String type,String price,int age){
        map.clear();
        double priceDouble=0.0;
        if(price.equals("--")){
            priceDouble=0.0;
        }else{
            priceDouble=Double.parseDouble(price);
        }
        if(videoUrl==null||videoUrl.isEmpty()){
            map.put("status",100);
            map.put("message","未上传视频");
        }
        videoService.updateVideo(userName,videoUrl,brand,type,priceDouble,age);
        map.put("status",0);
        map.put("message","success");
        return CommonMethod.mapToJsonString(map);
    }

    @GetMapping("/deleteVideo")
    public String  deleteVideo(String videoUrl){
        map.clear();
        if(videoUrl==null||videoUrl.isEmpty()){
            map.put("status",100);
            map.put("message","视频为空");
        }else{
            videoService.deleteVideo(videoUrl);
            map.put("status",0);
            map.put("message","success");
        }
        return CommonMethod.mapToJsonString(map);
    }

    double calNum(Video video,String loveBrand,String loveType,double lovePrice,int lovaAge){
        double sum=0;
        if(video.getBrand()!=null&& video.getBrand().contains(loveBrand)){
            sum+=1;
        }
        if(video.getType()!=null&& video.getType().contains(loveType)){
            sum+=1;
        }
        if(abs(video.getPrice()-lovePrice)<0.5){
            sum+=2.2;
        }else{
            sum+=(1.0/abs(video.getPrice()-lovePrice));
        }
        if(abs(video.getAge()-lovaAge)<=2){
            sum+=1;
        }
        return sum;
    }

    @GetMapping("/requestVideos")
    public String  requestVideos(String userName,String selectBrand,String selectType,int selectPrice,int selectAge){
        map.clear();
        data.clear();

        //对客户端传来的数据做处理
        if((selectBrand==null)||(selectBrand.equals("不限"))||(selectBrand.equals("--"))){
            selectBrand="%";
        }else{
            selectBrand="%"+selectBrand+"%";
        }

        if(selectType==null|| selectType.equals("不限") || selectType.equals("--")){
            selectType="%";
        }else{
            selectType="%"+selectType+"%";
        }

        if(selectPrice==0){
            selectPrice=1000000000;
        }
        else if(selectPrice==1) {
            selectPrice = 5;
        }else if(selectPrice==2){
            selectPrice=10;
        }else if(selectPrice==3){
            selectPrice=15;
        }else if(selectPrice==4){
            selectPrice=25;
        }else if(selectPrice==5){
            selectPrice=50;
        }else{
            selectPrice=1000000000;
        }

        if(selectAge==0){
            selectAge=100;
        }

        List<Video> listVideoModel;

        if(userName==null||userName.isEmpty()){
            listVideoModel=videoService.requestTotalVideos(selectBrand,selectType,selectPrice,selectAge);
        }else{
            listVideoModel=videoService.requestVideos(userName,selectBrand,selectType,selectPrice,selectAge);
        }


        //通过喜爱排序
        String loveBrand="";
        List<String> brand=userService.findUserMostLove(userName,1);
        if(brand!=null&& !brand.isEmpty()){
            loveBrand+=brand.get(0);
        }

        String loveType="";
        List<String>type=userService.findUserMostLove(userName,2);
        if(type!=null&& !type.isEmpty()){
            loveType+=type.get(0);
        }

        double lovePrice=8;
        List<String>price=userService.findUserMostLove(userName,3);
        if(price!=null&& !price.isEmpty()){
            lovePrice=Double.parseDouble(price.get(0));
        }

        int loveAge=100;
        List<String>age=userService.findUserMostLove(userName,4);
        if(age!=null&& !age.isEmpty()){
            loveAge=Integer.parseInt(price.get(0));
        }
        for(int i=0;i<listVideoModel.size();i++){
            double start=calNum(listVideoModel.get(i),loveBrand,loveType,lovePrice,loveAge);
            for(int j=i+1;j<listVideoModel.size();j++){
                double to=calNum(listVideoModel.get(j),loveBrand,loveType,lovePrice,loveAge);
                if(to>start){
                    Video temp=listVideoModel.get(i);
                    listVideoModel.set(i,listVideoModel.get(j));
                    listVideoModel.set(j,temp);
                    start=to;
                }
            }
        }

        //转换成map传给客户端
        List<Map<String,Object>> listVideo=new ArrayList<Map<String,Object>>();
        for(int i=0;i<listVideoModel.size();i++){
            if(listVideoModel.get(i).getPrice()==1000000000){
                listVideoModel.get(i).setPrice(0);
            }
            if(listVideoModel.get(i).getAge()==100){
                listVideoModel.get(i).setAge(0);
            }
            listVideo.add(CommonMethod.modelToMap(listVideoModel.get(i)));
        }
        data.put("videosList",listVideo);
        map.put("status",0);
        map.put("message","success");
        map.put("data",data);
        return CommonMethod.mapToJsonString(map);
    }
}
