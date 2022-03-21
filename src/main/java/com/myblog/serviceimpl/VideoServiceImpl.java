package com.myblog.serviceimpl;

import com.myblog.entity.Video;
import com.myblog.mapper.VideoMapper;
import com.myblog.service.UserService;
import com.myblog.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Vector;

@Service
public class VideoServiceImpl  implements VideoService {
    @Autowired
    private VideoMapper videoMapper;
    @Override
    public void updateVideo(String userName, String videoUrl,String brand,String type,double price,int age){
        videoMapper.updateVideo(userName,videoUrl,brand,type,price,age);
    }
    @Override
    public  void deleteVideo(String videoUrl){
        videoMapper.deleteVideo(videoUrl);
    }
    @Override
    public List<Video> requestVideos(String userName,String selectBrand,String selectType,int selectPrice,int selectAge){

        return videoMapper.requestVideos(userName,selectBrand,selectType,selectPrice,selectAge);
    }
    @Override
    public  List<Video>  requestTotalVideos(String selectBrand,String selectType,int selectPrice,int selectAge){
        return videoMapper.requestTotalVideos(selectBrand,selectType,selectPrice,selectAge);
    }

}
