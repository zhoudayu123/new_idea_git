package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.RidCounts;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class FavoriteDaoImpl implements FavoriteDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public Favorite findFavoriteByUidAndRid(int rid, int uid) {
        String sql = "select * from tab_favorite where rid = ? and uid = ?";
        try {
            return template.queryForObject(sql,new BeanPropertyRowMapper<Favorite>(Favorite.class),rid,uid);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public List<Route> findFavoriteByUid(int start,int pageSize,int uid) {
        String sql = "SELECT * FROM tab_route t1 ,tab_favorite t2 WHERE t1.`rid` = t2.`rid` AND t2.`uid`=? limit ?,?;";
        try {
            return template.query(sql,new BeanPropertyRowMapper<Route>(Route.class),uid,start,pageSize);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public int getCounts(int rid) {
        String sql = "select count(*) from tab_favorite where rid = ?";
        return template.queryForObject(sql,Integer.class,rid);
    }

    @Override
    public void add(int rid, int uid) {
        String sql = "insert tab_favorite values(?,?,?)";
        template.update(sql,rid,new Date(),uid);

    }

    @Override
    public void delete(int rid, int uid) {
        String sql = "delete from tab_favorite where rid = ? and uid = ?";
        template.update(sql,rid,uid);
    }

    @Override
    public List<RidCounts> getRidCounts() {
        String sql = "select rid,count(*) counts from tab_favorite group by rid";
        return template.query(sql,new BeanPropertyRowMapper<RidCounts>(RidCounts.class));
    }
}
