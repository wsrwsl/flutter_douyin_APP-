package com.myblog.mapper;

import com.myblog.entity.Video;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Vector;

@Repository
public interface VideoMapper {
    public  void updateVideo(String userName,String videoUrl,String brand,String type,double price,int age);
    public  void deleteVideo(String videoUrl);
    Vector<Video> requestVideos(String userName,String selectBrand,String selectType,int selectPrice,int selectAge);
    Vector<Video> requestTotalVideos(String selectBrand,String selectType,int selectPrice,int selectAge);
}
