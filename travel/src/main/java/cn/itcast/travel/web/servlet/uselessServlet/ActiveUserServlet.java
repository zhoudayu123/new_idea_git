package cn.itcast.travel.web.servlet.uselessServlet;

import cn.itcast.travel.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/activeUserServlet")
public class ActiveUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        if(code !=null){
            boolean flag = new UserServiceImpl().activeUser(code);
            if(flag){
                //注册成功
                request.getRequestDispatcher("active_ok.html").forward(request,response);
            }else {
                //注册失败
                request.getRequestDispatcher("active_ng.html").forward(request,response);

            }
        }


    }
}
