package com.mvc.web.controller.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/admin/board/notice/index")
public class indexController extends HttpServlet{
   
   @Override
   protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

      req.getRequestDispatcher("/WEB-INF/view/admin/board/notice/index.jsp").forward(req, res);
      
   }
}