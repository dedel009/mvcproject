package com.mvc.web.controller.notice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jdbc.app.entity.Notice;
import com.mvc.web.service.NoticeService;

@WebServlet("/notice/list")
public class ListController extends HttpServlet{
   
   @Override
   protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
      NoticeService ns = new NoticeService();
      
      //파라미터받기
      String page_ = req.getParameter("p");
      String field_ = req.getParameter("f");
      String query_ = req.getParameter("q");
      String id = req.getParameter("id");
      int result = 0;
      System.out.println("field: "+field_);
      System.out.println("query: "+query_);

      int page =1;
      String field = "title";
      String query = "";
      
      if(page_ !=null && !page_.equals("")) {
    	  page = Integer.parseInt(page_);
      }
      
      if(field_ !=null && !field_.equals("")) {
    	  field = field_;
   
      }
      if(query_ !=null && !query_.equals("")) {
    	  query = query_;
      }
      if(id!=null&&!id.equals("")) {
    	  id = req.getParameter("id");
    	  result = ns.removeList(id);
          System.out.println("id: "+id);
      }

      System.out.println("result: "+result);
      
      List<Notice>list = ns.getPubNoticeList(page, field, query);
  
      int count = 0;
      count = ns.getPubNoticeCount(field, query);
      
      req.setAttribute(	"count", count);	//조회된 목록 개수 보내기
      req.setAttribute("noticelist", list);		//list 보내기
      req.getRequestDispatcher("/WEB-INF/view/notice/list.jsp").forward(req, res);
      
   }
}