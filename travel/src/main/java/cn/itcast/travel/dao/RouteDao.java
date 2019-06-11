package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;

import java.util.List;

public interface RouteDao {
    public List<Route> findAll(int start, int pageSize, int cid,String rname);
    public int getCounts(int cid,String rname);
    public int getCountsByUid(int uid);
    public Route findOne(int rid);
    public List<Route> findAllRoute();
    public void delCount(int rid);
    public void addCount(int rid);

}
