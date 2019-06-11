package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.RidCounts;
import cn.itcast.travel.domain.Route;

import java.util.List;
import java.util.Map;

public interface FavoriteDao {
    public Favorite findFavoriteByUidAndRid(int rid,int uid);
    public List<Route> findFavoriteByUid(int start,int pageSize,int uid);
    public int getCounts(int rid);

    public void add(int rid, int uid);

    public void delete(int rid, int uid);

    public List<RidCounts> getRidCounts();
}
