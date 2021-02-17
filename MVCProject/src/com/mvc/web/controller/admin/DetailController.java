package com.mvc.web.controller.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jdbc.app.entity.Notice;
import com.mvc.web.service.NoticeService;

@WebServlet("/admin/board/notice/detail")
public class DetailController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		int id = Integer.parseInt(req.getParameter("id"));

//		// 파라미터받기
//		String page_ = req.getParameter("p");
//		String field_ = req.getParameter("f");
//		String query_ = req.getParameter("q");
//
//		System.out.println("field: " + field_);
//		System.out.println("query: " + query_);
//		int page = 1;
//		String field = "title";
//		String query = "";
//
//		if (page_ != null && !page_.equals("")) {
//			page = Integer.parseInt(page_);
//		}
//
//		if (field_ != null && !field_.equals("")) {
//			field = field_;
//		}
//		if (query_ != null && !query_.equals("")) {
//			query = query_;
//		}

		NoticeService ns = new NoticeService();

		Notice nt = ns.getNoticeDetail(id);
		
//		req.setAttribute("p", page);
//		req.setAttribute("f", field);
//		req.setAttribute("q", query);
		req.setAttribute("nt", nt);
		req.setAttribute("id", id);
		req.getRequestDispatcher("/WEB-INF/view/admin/board/notice/detail.jsp").forward(req, res);
	}
}
