package cn.itcast.travel.service;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.domain.User;

import java.util.List;


public interface UserService {
    public boolean registUser(User user);
    public boolean activeUser(String code);
    public User loginUser(User user);

}
