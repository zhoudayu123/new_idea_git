package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.FavoriteServiceImpl;
import cn.itcast.travel.service.impl.RouteServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    private RouteService routeService = new RouteServiceImpl();
    private FavoriteService favoriteService = new FavoriteServiceImpl();

    public void findPage(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr =  request.getParameter("pageSize");
        String cidStr = request.getParameter("cid");
        String rname = request.getParameter("rname");
//        rname = new String(rname.getBytes("iso8859-1"),"utf-8");


//        String decode = URLDecoder.decode(rname, "iso-8859-1");
//        rname = URLEncoder.encode(decode);

        int currentPage = 0;
        if(currentPageStr!=null && currentPageStr.length()>0){
            currentPage = Integer.valueOf(currentPageStr);
        }else {
            currentPage = 1;
        }

        int cid = 0;
        if(!"null".equals(cidStr) && cidStr!=null && cidStr.length()>0){
            cid = Integer.valueOf(cidStr);
        }

        int pageSize = 0;
        if(pageSizeStr!=null && pageSizeStr.length()>0){
            pageSize = Integer.valueOf(pageSizeStr);
        }else {
            pageSize = 5;
        }

        PageBean pb = routeService.findAll(currentPage, pageSize, cid,rname);
        writeValue(pb,response);
    }

    public void findOne(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String ridStr = request.getParameter("rid");
        int rid = Integer.parseInt(ridStr);
        Route route = routeService.findOne(rid);
        writeValue(route,response);
    }

    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String rid = request.getParameter("rid");
        User user = (User)request.getSession().getAttribute("user");
        int uid = 0;
        if(user!=null){
            uid = user.getUid();
        }
        boolean flag = favoriteService.isFavorite(Integer.parseInt(rid),uid);
        writeValue(flag,response);
    }

    public void changeFavorite(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String rid = request.getParameter("rid");
        User user = (User) request.getSession().getAttribute("user");
        int uid;
        if (user != null) {
            uid = user.getUid();
        } else {
            return;
        }
        favoriteService.change(Integer.parseInt(rid), uid);
        boolean flag = favoriteService.isFavorite(Integer.parseInt(rid), uid);
        writeValue(flag, response);
    }

    public void findFavorite(HttpServletRequest request, HttpServletResponse response) throws IOException {
            String currentPageStr = request.getParameter("currentPage");
            String pageSizeStr = request.getParameter("pageSize");
            String uidStr = request.getParameter("uid");


            int currentPage = 0;
            if (!"null".equals(currentPage) && currentPageStr != null && currentPageStr.length() > 0) {
                currentPage = Integer.valueOf(currentPageStr);
            } else {
                currentPage = 1;
            }

            int uid = 0;
            if (!"null".equals(uidStr) && uidStr != null && uidStr.length() > 0) {
                uid = Integer.valueOf(uidStr);
            }

            int pageSize = 0;
            if (pageSizeStr != null && pageSizeStr.length() > 0) {
                pageSize = Integer.valueOf(pageSizeStr);
            } else {
                pageSize = 8;
            }

            PageBean pb = favoriteService.findMyFavorite(currentPage, pageSize, uid);
            writeValue(pb, response);
    }

    public void favoriteRange(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        String rname = request.getParameter("rname");
        String lowPriceStr = request.getParameter("lowPrice");
        rname = new String(rname.getBytes("iso-8859-1"),"utf-8");
        String highPriceStr = request.getParameter("highPrice");

        int currentPage = 0;
        if (!"null".equals(currentPage) && currentPageStr != null && currentPageStr.length() > 0) {
            currentPage = Integer.valueOf(currentPageStr);
        } else {
            currentPage = 1;
        }

        int pageSize = 0;
        if (pageSizeStr != null && pageSizeStr.length() > 0) {
            pageSize = Integer.valueOf(pageSizeStr);
        } else {
            pageSize = 8;
        }

        double lowPrice = 0;
        if(!"null".equals(lowPriceStr) && lowPriceStr!=null && lowPriceStr.length()>0){
            lowPrice = Double.parseDouble(lowPriceStr);
        }else {
            lowPrice = 0;
        }

        double highPrice = 0;
        if(!"null".equals(highPriceStr) && highPriceStr!=null && highPriceStr.length()>0){
            highPrice = Double.parseDouble(highPriceStr);
        }else {
            highPrice = 99999999;
        }

        PageBean pb = favoriteService.init(currentPage, pageSize, rname, lowPrice, highPrice);
        writeValue(pb,response);
    }

}
