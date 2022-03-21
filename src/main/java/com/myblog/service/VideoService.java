package com.myblog.service;

import com.myblog.entity.Video;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Vector;

@Service
public interface VideoService {
    void updateVideo(String userName,String videoUrl,String brand,String type,double price,int age);
    public  void deleteVideo(String videoUrl);
    List<Video> requestVideos(String userName,String selectBrand,String selectType,int selectPrice,int selectAge);
    List<Video>  requestTotalVideos(String selectBrand,String selectType,int selectPrice,int selectAge);
}
