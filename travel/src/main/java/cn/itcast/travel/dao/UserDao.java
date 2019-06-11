package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.domain.User;

import java.util.List;

public interface UserDao {
    public boolean findByUsername(String username);
    public boolean findByCode(String code);
    public boolean registUser(User user);
    public void updateUser(String code);
    public User findUserByUsernameAndPassword(User user);


}
