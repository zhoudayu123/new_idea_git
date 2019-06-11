package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

import java.util.List;

public interface RouteService {
    public PageBean findAll(int currentPage, int pageSize, int cid,String rname);
    public Route findOne(int rid);

}
