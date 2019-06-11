package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.RouteImgDaoImpl;
import cn.itcast.travel.dao.impl.SellerDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {
    private RouteDao dao = new RouteDaoImpl();
    private SellerDao sellerDao = new SellerDaoImpl();
    private RouteImgDao routeImgDao = new RouteImgDaoImpl();
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();

    public PageBean findAll(int currentPage,int pageSize,int cid,String rname){
        PageBean<Route> pageBean = new PageBean<>();
        int totalCounts = dao.getCounts(cid,rname);
        int totalPages = totalCounts % pageSize == 0 ? totalCounts/pageSize : (totalCounts/pageSize+1);
        int start = (currentPage-1)*pageSize;

        List<Route> list = dao.findAll(start, pageSize, cid,rname);
        pageBean.setCurrentPage(currentPage);
        pageBean.setList(list);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalCounts(totalCounts);
        pageBean.setTotalPages(totalPages);

        return pageBean;
    }

    public Route findOne(int rid){
        Route route = dao.findOne(rid);
        int counts = favoriteDao.getCounts(rid);
        Seller seller = sellerDao.findSeller(route.getSid());
        List<RouteImg> routeImgList = routeImgDao.findImgs(rid);

        route.setCount(counts);
        route.setSeller(seller);
        route.setRouteImgList(routeImgList);
        return route;
    }




}
