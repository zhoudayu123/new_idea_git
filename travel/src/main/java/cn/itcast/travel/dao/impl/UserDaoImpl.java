package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class UserDaoImpl implements UserDao {
    private  JdbcTemplate template  = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public boolean findByUsername(String username) {
        String sql = "select count(uid) from tab_user where username = ?";
        Integer count = template.queryForObject(sql, Integer.class, username);
        if(count==0){
            return false;
        }else {
            return true;
        }
    }

    @Override
    public boolean findByCode(String code) {
        String sql = "select count(uid) from tab_user where code = ?";
        Integer count = template.queryForObject(sql, Integer.class, code);
        if(count==0){
            return false;
        }else {
            return true;
        }
    }

    @Override
    public boolean registUser(User user) {
        String sql = "insert tab_user values(null,?,?,?,?,?,?,?,'N',?)";
        int count = template.update(sql, user.getUsername(), user.getPassword(), user.getName(),
                user.getBirthday(), user.getSex(), user.getTelephone(), user.getEmail(),user.getCode());
        if(count==0){
            return false;
        }else {
            return true;
        }
    }

    @Override
    public void updateUser(String code) {
        String sql = "update tab_user set status = 'Y' where code = ?";
        template.update(sql,code);
    }

    @Override
    public User findUserByUsernameAndPassword(User user) {
        String sql = "select * from tab_user where username = ? and password = ?";
        User loginUser = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), user.getUsername(), user.getPassword());
        return loginUser;
    }



}
