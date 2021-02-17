package com.mvc.web.controller.admin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.jdbc.app.entity.Notice;
import com.mvc.web.service.NoticeService;

@MultipartConfig(
//		location="/upload",			//저장 경로
		fileSizeThreshold = 1024 * 1024, //
		maxFileSize = 1024 * 1024 * 50, // 최대 업로드 가능 용량 // 1024*1024>> 1메가
		maxRequestSize = 1024 * 1024 * 5 * 5// 전체 요청에 대한 파일 용량
)
@WebServlet("/admin/board/notice/reg")
public class regController extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
		String title = req.getParameter("title");
		String content = req.getParameter("content");
		String open = req.getParameter("open");
		boolean pub = Boolean.parseBoolean(open);

		// 여러 file을 업로드
		Collection<Part> parts = req.getParts(); // parts 는 배열형태?
		StringBuilder builder = new StringBuilder();

		for (Part p : parts) {
			System.out.println("getname: "+p.getName());
			if (!p.getName().equals("file")||p.getSize()==0) 
				continue;
			
			Part filePart = p;

			String fileName = filePart.getSubmittedFileName();

			builder.append(fileName);
			builder.append(",");

			InputStream fis = filePart.getInputStream();

			String realPath = req.getServletContext().getRealPath("/upload"); // 저장할 파일 경로 설정
			System.out.println("realPath: " + realPath);

			String filePath = realPath + File.separator + fileName; // 경로에 파일이름을 합치면 최종 저장 경로가 된다
			// File.separator >> 운영체제에 맞게 슬러쉬나 역슬러쉬로 바꿔준다
			System.out.println("filePath: " + filePath);

			File file = new File(realPath);
			if(!file.exists())
				file.mkdirs();
			
			FileOutputStream fos = new FileOutputStream(filePath); // FileOutputStream 파일을 컨트롤 할 때 필요한 것 정도?

			byte[] buf = new byte[1024]; // stream 기본단위가 1byte
			int size = 0;
			while ((size = fis.read(buf)) != -1) { // 1byte씩 읽다가 다 읽으면 즉, 가져올게 없으면 -1리턴 >>보내는 용량을 1키로바이트로 변경
				fos.write(buf, 0, size);
			}

			fos.close();
			fis.close();
		}
		
		builder.delete(builder.length()-1, builder.length());

		System.out.println("builder: "+builder.toString());
		
		int result = 0;
		Notice nt = new Notice();

		nt.setTitle(title);
		nt.setContent(content);
		nt.setWriterID("동동이");
		nt.setFiles(builder.toString()); // 파일이름 담기
		nt.setPub(pub);

		NoticeService ns = new NoticeService();
		result = ns.insertNotice(nt);

		res.sendRedirect("list");
//		PrintWriter out = res.getWriter();
//		out.printf("title: " +title);
//		out.printf("content: " +content);
//		out.printf("open: " +open);

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/view/admin/board/notice/reg.jsp").forward(req, res);
	}
}
