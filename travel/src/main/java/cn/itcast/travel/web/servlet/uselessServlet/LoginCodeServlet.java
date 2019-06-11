package cn.itcast.travel.web.servlet.uselessServlet;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/loginCodeServlet")
public class LoginCodeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String checkcode_server = (String)session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
        String check = request.getParameter("check");
        ObjectMapper mapper = new ObjectMapper();
        if(checkcode_server!=null && checkcode_server.equalsIgnoreCase(check)){
            //登陆验证码正确
            mapper.writeValue(response.getWriter(),true);
        }else {
            //登陆验证码错误
            mapper.writeValue(response.getWriter(),false);
        }

    }
}
