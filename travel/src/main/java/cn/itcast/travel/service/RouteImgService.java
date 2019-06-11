package cn.itcast.travel.service;

import cn.itcast.travel.domain.RouteImg;

import java.util.List;

public interface RouteImgService {
    public List<RouteImg> findImgs(int rid);
}
