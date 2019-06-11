package cn.itcast.travel.web.servlet.uselessServlet;

import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.impl.UserServiceImpl;
import cn.itcast.travel.util.MailUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> map = request.getParameterMap();
        User user = new User();
        response.setContentType("application/json;charset=utf-8");
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        User loginUser = null;
        try {
            loginUser = new UserServiceImpl().loginUser(user);
        }catch (Exception e){
        }

        ResultInfo info = new ResultInfo();
        if(loginUser==null){
            info.setFlag(false);
            info.setErrorMsg("用户名或密码错误！");
        }

        if(loginUser!=null && !"Y".equals(loginUser.getStatus())){
            //未激活状态
            info.setFlag(false);
            info.setErrorMsg("账号尚未激活！");
        }

        if(loginUser!=null && "Y".equals(loginUser.getStatus())){
            //激活状态
            request.getSession().setAttribute("user",loginUser);
            info.setFlag(true);
        }

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);
        response.getWriter().write(json);
//        mapper.writeValue(response.getWriter(),info);

    }
}
