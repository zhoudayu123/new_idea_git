package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.RidCounts;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.util.JedisUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.*;

public class FavoriteServiceImpl implements FavoriteService {
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();
    private RouteDao dao = new RouteDaoImpl();

    @Override
    public boolean isFavorite(int rid, int uid) {
        Favorite favorite = favoriteDao.findFavoriteByUidAndRid(rid, uid);
        return favorite!=null;
    }

    @Override
    public void change(int rid, int uid) {
        Jedis jedis = JedisUtil.getJedis();
        Route route = dao.findOne(rid);
        String routeStr = null;
        try {
            routeStr = new ObjectMapper().writeValueAsString(route);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if (favoriteDao.findFavoriteByUidAndRid(rid, uid)!=null){
            System.out.println("1:"+jedis.zrank("routeSet",routeStr));
           favoriteDao.delete(rid,uid);
           dao.delCount(rid);
            //redis中rid对应的score减1
            Double score = jedis.zincrby("routeSet", -1, routeStr);

        }else {
            favoriteDao.add(rid,uid);
            dao.addCount(rid);
            System.out.println("2:"+jedis.zrank("routeSet",routeStr));
            //判断redis中 hash rid:route 有没有rid的键，没有，
            if(jedis.zrank("routeSet",routeStr)!=null){
                //有， rid对应的score加1
                jedis.zincrby("routeSet", 1, routeStr);

            }else {
                //没有，添加rid:route,   score,rid
                jedis.zadd("routeSet",1,routeStr);

            }
       }
       jedis.close();

    }

    @Override
    public PageBean<Route> findMyFavorite(int currentPage,int pageSize,int uid) {
        PageBean<Route> pageBean = new PageBean<>();
        int totalCounts = dao.getCountsByUid(uid);
        int totalPages = totalCounts % pageSize == 0 ? totalCounts/pageSize : (totalCounts/pageSize+1);
        int start = (currentPage-1)*pageSize;
        List<Route> list = favoriteDao.findFavoriteByUid(start,pageSize,uid);

        pageBean.setCurrentPage(currentPage);
        pageBean.setList(list);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalCounts(totalCounts);
        pageBean.setTotalPages(totalPages);

        return pageBean;
    }

    @Override
    public PageBean init(int currentPage,int pageSize,String rname,double lowPrice,double highPrice) {
        Jedis jedis = JedisUtil.getJedis();
        PageBean<Route> pb = new PageBean<>();
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<String> temp_list = new ArrayList<>();
        ArrayList<Route> list = new ArrayList<>();

        int start = (currentPage-1)*pageSize;
        int end = currentPage*pageSize-1;
        Boolean setFlag = jedis.exists("routeSet");
        if(!setFlag){
            List<Route> routeList = dao.findAllRoute();
            for (Route route : routeList) {
                int counts = favoriteDao.getCounts(route.getRid());
                try {
                    jedis.zadd("routeSet",counts,mapper.writeValueAsString(route));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        }

        Set<String> routeSet = jedis.zrevrange("routeSet", 0, -1);

        if(rname!="" && rname!=null && rname.length()>0){
            for (String route : routeSet) {
                Route route1 = null ;
                try {
                    route1 = mapper.readValue(route, Route.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(route.contains(rname) && route1.getPrice()>=lowPrice && route1.getPrice()<=highPrice){
                    temp_list.add(route);
                }
            }
        }else {
            for (String route : routeSet) {
                Route route1 = null ;
                try {
                    route1 = mapper.readValue(route, Route.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(route1!=null && route1.getPrice()>=lowPrice && route1.getPrice()<=highPrice){
                    temp_list.add(route);
                }
            }
        }

        for (int i = start; i <=end ; i++) {
            try {
                if(i<temp_list.size()){
                    list.add(mapper.readValue(temp_list.get(i),Route.class));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        int totalCounts = temp_list.size();
        int totalPages = totalCounts % pageSize==0 ? totalCounts/pageSize : totalCounts/pageSize+1;

        pb.setTotalPages(totalPages);
        pb.setTotalCounts(totalCounts);
        pb.setPageSize(pageSize);
        pb.setList(list);
        pb.setCurrentPage(currentPage);

        jedis.close();
        return pb;
    }
}
