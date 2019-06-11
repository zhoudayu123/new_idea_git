package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;



public interface FavoriteService {
    public boolean isFavorite(int rid,int uid);

    public void change(int rid, int uid);

    public PageBean<Route> findMyFavorite(int currentPage,int pageSize,int uid);

    public PageBean init(int currentPage,int pageSize,String rname,double lowPrice,double highPrice);
}
