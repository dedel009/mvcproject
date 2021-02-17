package com.mvc.web.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jdbc.app.entity.Notice;
import com.jdbc.app.entity.NoticeComment;

public class NoticeService { // DAO
	private String url = "jdbc:mysql://13.124.135.97:3306/jdbc?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	private String root = "cinema_pm";
	private String pw = "cinema1234";
	private String driver = "com.mysql.jdbc.Driver";
	
	
	public int deleteNoticeAll(int[] ids) {
		int result = 0;
		String params = "";
		
		for(int i=0; i<ids.length;i++) {
			params += ids[i];
			if(i<ids.length-1) {		//ex) 1,3,5,6
				params +=",";
			}
		}
		String sql="delete from notice where id in("+params+")";
		try {
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url, root, pw);
			Statement st = con.createStatement();
			result = st.executeUpdate(sql);
			

		} catch (Exception e) {
			e.printStackTrace();
		}		
		
		return result;
	}
	
	public int removeNoticeAll(int[] ids){		//list 체크해서 삭제
		String sql = "delete from notice where id=?"; 
		int result = 0;
		try {
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url, root, pw);
			for(int i=0; i<ids.length; i++) {
				int id = ids[i];
				PreparedStatement psmt = con.prepareStatement(sql);
				psmt.setInt(1, id);
				result = psmt.executeUpdate();
				result++;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public int insertNotice(Notice nt) {		//글쓰기
		int result = 0;
		String sql = "insert into notice (title, writer_id, content, files, pubflag)  "
						+ " values(?, ?, ?, ?, ?) ";
		try {
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url, root, pw);
			PreparedStatement psmt = con.prepareStatement(sql);
			psmt.setString(1, nt.getTitle());
			psmt.setString(2, nt.getWriterID());
			psmt.setString(3, nt.getContent());
			psmt.setString(4, nt.getFiles());		
			String pub = "N";
			System.out.println("files: "+nt.getFiles());
			if(nt.getPub()) {
				pub = "Y";
			}
			psmt.setString(5, pub);
			result = psmt.executeUpdate();
			

		} catch (Exception e) {
			e.printStackTrace();
		}		
		return result;
	}	
		
	public int updateNotcie(Notice nt) {	//글수정
		return 0;
	}
	public int deleteNotcie(int id){	//디테일에 가서 글을 보고 삭제
		return 0;
		
	}
	public List<Notice> getNoticeNewList(){		//목록확인하기
		return null;
	}
	public int removeList(String id) {		//삭제
		int result = 0;
		String sql = "delete from notice where id =?";
		try {
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url, root, pw);
			PreparedStatement psmt = con.prepareStatement(sql);
			psmt.setInt(1, Integer.parseInt(id));
			result = psmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}	
	
	public List<Notice> getNoticeList() { // 글 조회
		return getNoticeList(1, "title", "");
	}
	
	public List<Notice> getNoticeList(int page) { // 글 조회
		return getNoticeList(page, "title", "");
	}

	public List<Notice> getNoticeList(int page, String field, String query) { // 글 조회
		int start = 1 + (page - 1) * 10; // 1 11 21 31 .....
		int end = page * 10; // 10 20 30 .....

		String sql = "Select *" 
						+ "from (Select @rownum:=@rownum+1 as num , n.*" 
						+ "        from(select *"
						+ "               From notice"
						+ "			   where "+field+" like ?" 				//%검색어%
						+ "		      order by regdate desc)n"
						+ "        Where (@rownum:=0)=0) num "
						+ "Where num.num between ? and ? ";

		List<Notice> list = new ArrayList<Notice>();
		try {
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url, root, pw);
			PreparedStatement psmt = con.prepareStatement(sql);
			psmt.setString(1, "%"+query+"%");
			psmt.setInt(2, start);
			psmt.setInt(3, end);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String writerid = rs.getString("writer_id");
				Date regDate = rs.getDate("regdate");
				String content = rs.getString("content");
				int hit = rs.getInt("hit");
				String files = rs.getString("files");
				String pubflag = rs.getString("pubflag");
				boolean pub = false;
				
				if(pubflag.equals("Y")) {
					pub = true;
				}
				
				Notice nt = new Notice(id, title, writerid, content, regDate, hit, files, pub);
				list.add(nt);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<Notice> getPubNoticeList(int page, String field, String query) {
		int start = 1 + (page - 1) * 10; // 1 11 21 31 .....
		int end = page * 10; // 10 20 30 .....
		
		String sql = "Select * "
				   		+ "from (Select @rownum:=@rownum+1 as num, n.* "
				   		+ "	       from(select * "
				   		+ "				  From notice "
				   		+ "				 where pubflag = 'Y' "
				   		+ "				   and	"+field+" like ? "
				   		+ "			     order by regdate desc)n, "
				   		+ "			   (select @rownum:=0)v)num "
				   		+ " Where num.id between ? and ? ";

		List<Notice> list = new ArrayList<Notice>();
		try {
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url, root, pw);
			PreparedStatement psmt = con.prepareStatement(sql);
			psmt.setString(1, "%"+query+"%");
			psmt.setInt(2, start);
			psmt.setInt(3, end);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String writerid = rs.getString("writer_id");
				Date regDate = rs.getDate("regdate");
				String content = rs.getString("content");
				int hit = rs.getInt("hit");
				String files = rs.getString("files");
				String pubflag = rs.getString("pubflag");
				boolean pub = false;
				
				if(pubflag.equals("Y")) {
					pub = true;
				}
				
				Notice nt = new Notice(id, title, writerid, content, regDate, hit, files, pub);
				list.add(nt);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public Notice getNoticeDetail(int id) {
		Notice nt = null;
		String sql = "Select * from notice where id=?"; 

		try {
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url, root, pw);
			PreparedStatement psmt = con.prepareStatement(sql);
			psmt.setInt(1, id);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				id = rs.getInt("id");
				String title = rs.getString("title");
				String writerid = rs.getString("writer_id");
				Date regDate = rs.getTimestamp("regdate");
				String content = rs.getString("content");
				int hit = rs.getInt("hit");
				String files = rs.getString("files");
				String pubflag = rs.getString("pubflag");
				boolean pub = false;
				
				if(pubflag.equals("Y")) {
					pub = true;
				}

				nt = new Notice(id, title, writerid, content, regDate, hit, files, pub);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nt;
	}
	
	public int getNoticeCount() {
		return getNoticeCount("title", "");
	}

	public int getNoticeCount(String field, String query) {
		int count = 0;
		String sql = "Select count(num.id) as count" 
				+ " from (Select @rownum:=@rownum+1 as num , n.*" 
				+ "        from(select *"
				+ "               From notice"
				+ "			   where "+field+" like ?" 				//%검색어%
				+ "		      order by regdate desc)n"
				+ "        Where (@rownum:=0)=0) num";
		
		try {
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url, root, pw);
			PreparedStatement psmt = con.prepareStatement(sql);
			psmt.setString(1, "%"+query+"%");
			ResultSet rs = psmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt("count");
//				System.out.println("count: "+count);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}

		return count;
	}
	
	public int getPubNoticeCount(String field, String query) {
		int count = 0;
		String sql = "Select count(num.id) as count" 
				+ " from (Select @rownum:=@rownum+1 as num , n.*" 
				+ "        from(select *"
				+ "               From notice"
				+ "			   where pubflag = 'Y' "
				+ "                 and "+field+" like ?" 				//%검색어%
				+ "		      order by regdate desc)n"
				+ "        Where (@rownum:=0)=0) num";
		
		try {
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url, root, pw);
			PreparedStatement psmt = con.prepareStatement(sql);
			psmt.setString(1, "%"+query+"%");
			ResultSet rs = psmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt("count");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("count: "+count);
		return count;
	}

	public void countNoticeHit(int id) {
		String sql = "update notice set hit=(hit+1) where id =?";
		
		try {
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url, root, pw);
			PreparedStatement psmt = con.prepareStatement(sql);
			psmt.setInt(1, id);
			psmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public int insertNoticeComment(NoticeComment nc) {
		int result = 0;
		String sql = "insert into comment(mid, comment, writer_id) values(?, ?, ?)";

		try {
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url, root, pw);
			PreparedStatement psmt = con.prepareStatement(sql);
			psmt.setInt(1, nc.getMid());
			psmt.setString(2, nc.getComment());
			psmt.setString(3, nc.getWriterID());
			result = psmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<NoticeComment> getCommentList(int id) {
		List<NoticeComment> clist = new ArrayList<NoticeComment>();
		String sql = "select * from comment where mid =? order by regdate desc";
		try {
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url, root, pw);
			PreparedStatement psmt = con.prepareStatement(sql);
			psmt.setInt(1, id);
			ResultSet rs = psmt.executeQuery();
			while(rs.next()) {
				int id_db = rs.getInt("id");
				int mid = rs.getInt("mid");
				String writerID = rs.getString("writer_id");
				Date regdate = rs.getTimestamp("regdate");
				String comment = rs.getString("comment");
				
				NoticeComment nc = new NoticeComment();
				nc.setId(id_db);
				nc.setMid(mid);
				nc.setComment(comment);
				nc.setRegDate(regdate);
				nc.setWriterID(writerID);
				
				clist.add(nc);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return clist;
	}

	public int getNoticeCommentCount(int id) {
		int count = 0;
		String sql = "select count(id)  as count from comment where mid = ?";
		try {
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url, root, pw);
			PreparedStatement psmt = con.prepareStatement(sql);
			psmt.setInt(1, id);
			ResultSet rs = psmt.executeQuery();
			while(rs.next()) {
				count = rs.getInt("count");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
}
