package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());


    @Override
    public List<Route> findAll(int start, int pageSize, int cid,String rname) {
        String sql = "select * from tab_route where 1 = 1";
        StringBuilder sb = new StringBuilder(sql);
        ArrayList<Object> params = new ArrayList<>();
        if(cid!=0){
            sb.append(" and cid= ?");
            params.add(cid);
        }
        if(rname!=null && rname.length()>0){
            sb.append(" and rname like ?");
            params.add("%"+rname+"%");
        }
        sb.append(" limit ?,?");
        params.add(start);
        params.add(pageSize);
        sql = new String(sb);
        List<Route> list = template.query(sql, new BeanPropertyRowMapper<Route>(Route.class), params.toArray());
        return list;
    }

    @Override
    public int getCounts(int cid,String rname) {
        String sql = "select count(cid) from tab_route where 1=1";
        StringBuilder sb = new StringBuilder(sql);
        ArrayList<Object> params = new ArrayList<>();
        if(cid!=0){
            sb.append(" and cid= ?");
            params.add(cid);
        }
        if(rname!=null && rname.length()>0){
            sb.append(" and rname like ?");
            params.add("%"+rname+"%");
        }
        sql = new String(sb);
        Integer count = template.queryForObject(sql, Integer.class,params.toArray());
        return count;
    }

    @Override
    public int getCountsByUid(int uid) {
        String sql = "select count(*) from tab_favorite where uid = ?";
        return template.queryForObject(sql, Integer.class,uid);
    }

    @Override
    public Route findOne(int rid) {
        String sql = "select * from tab_route where rid = ?";
        return template.queryForObject(sql,new BeanPropertyRowMapper<Route>(Route.class),rid);
    }

    @Override
    public List<Route> findAllRoute() {
        String sql = "select * from tab_route ";
        return template.query(sql,new BeanPropertyRowMapper<Route>(Route.class));
    }

    @Override
    public void delCount(int rid) {
        String sql = "update tab_route set count = count-1 where rid = ?";
        template.update(sql,rid);
    }

    @Override
    public void addCount(int rid) {
        String sql = "update tab_route set count = count+1 where rid = ?";
        template.update(sql,rid);
    }
}
