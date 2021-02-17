package com.mvc.web.controller.notice;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jdbc.app.entity.Notice;
import com.jdbc.app.entity.NoticeComment;
import com.mvc.web.service.NoticeService;

@WebServlet("/notice/detail")
public class DetailController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		System.out.println("id: "+ id);
		NoticeService ns = new NoticeService();
		ns.countNoticeHit(id);
		Notice nt = ns.getNoticeDetail(id);
		int comCount = 0;
		comCount = ns.getNoticeCommentCount(id);
		
		if(comCount != 0) {
			List<NoticeComment> clist = ns.getCommentList(id);
			req.setAttribute("clist", clist);
		}

		req.setAttribute("comCount", comCount);
		req.setAttribute("nt", nt);		
		req.setAttribute("id", id);
		req.getRequestDispatcher("/WEB-INF/view/notice/detail.jsp").forward(req, res);
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
		int result = 0;
		String pID = req.getParameter("pageID");
		int pageID = Integer.parseInt(pID);
		String writer_id = req.getParameter("writer_id");
		String comment = req.getParameter("comment");
		System.out.println("comment: "+comment);
		System.out.println("pageID: "+pID);
		System.out.println("writer_id: "+writer_id);
		
		NoticeService ns = new NoticeService();
		NoticeComment nc = new NoticeComment();
		
		nc.setMid(pageID);
		nc.setComment(comment);
		nc.setWriterID(writer_id);
		result = ns.insertNoticeComment(nc);
		
		res.sendRedirect("detail?id="+pageID);
	}
}
