package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

import java.util.List;

public class UserServiceImpl implements UserService {
    private static UserDao dao = new UserDaoImpl();

    @Override
    public boolean registUser(User user) {
        boolean exist_flag = dao.findByUsername(user.getUsername());
        if(exist_flag){
            return false;
        }else {
            String activeCode = UuidUtil.getUuid();
            user.setCode(activeCode);
            String text = "<a href='http://localhost:8080/travel/user/activeUser?code="+activeCode+"'>点击激活【黑马旅游网】</a>";
            MailUtils.sendMail(user.getEmail(),text,"激活邮件【黑马旅游网】");
            return dao.registUser(user);
        }
    }

    @Override
    public boolean activeUser(String code) {
        boolean flag = dao.findByCode(code);
        if(flag){
            //激活成功
            dao.updateUser(code);
            return true;
        }else {
            //激活失败
            return false;
        }
    }

    @Override
    public User loginUser(User user) {
        return dao.findUserByUsernameAndPassword(user);
    }


}
