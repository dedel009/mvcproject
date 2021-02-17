package com.mvc.web.controller.admin;

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

@WebServlet("/admin/board/notice/list")
public class ListController extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {		
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
		String[] openIds = req.getParameterValues("open");
		String[] delIds = req.getParameterValues("del");
		String cmd = req.getParameter("cmd");
		System.out.println("cmd: "+cmd);
		
		switch(cmd) {
		case "일괄공개":
//			for(String s : openIds) {
//				System.out.println("open: "+s);
//			}
			break;
		case "일괄삭제":
//			for(String s : delIds) {
//				System.out.println("del: "+s);
//			}
			int [] ids = new int[delIds.length];
			for(int i=0; i<ids.length; i++) {
				ids[i] = Integer.parseInt(delIds[i]);
			}
			NoticeService ns = new NoticeService();
			int result = ns.deleteNoticeAll(ids);
			break;
		}
		
//		String c[]  = req.getParameterValues("c");
//		int ids[] = new int[c.length];
//		
//		for(int i=0; i<c.length; i++) {
//			ids[i] = Integer.parseInt(c[i]); 
//			System.out.println("선택된 값 : "+c[i]);
//		}
//		NoticeService ns = new NoticeService();
//		int result = ns.removeNoticeAll(ids);
//		System.out.println(result+"건이 삭제되었습니다.");
		res.sendRedirect("list");
	}
   @Override
   protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
      NoticeService ns = new NoticeService();
      
      //파라미터받기
      String page_ = req.getParameter("p");
      String field_ = req.getParameter("f");
      String query_ = req.getParameter("q");
      String id = req.getParameter("id");
      int result = 0;
      
//      System.out.println("field: "+field_);
//      System.out.println("query: "+query_);
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
//          System.out.println("id: "+id);
      }

//      System.out.println(result);
      
      List<Notice>list = ns.getNoticeList(page, field, query);
  
      int count = 0;
      count = ns.getNoticeCount(field, query);
      
      req.setAttribute(	"count", count);	//조회된 목록 개수 보내기
      req.setAttribute("noticelist", list);		//list 보내기
      req.getRequestDispatcher("/WEB-INF/view/admin/board/notice/list.jsp").forward(req, res);
      
   }
}