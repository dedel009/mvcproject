package com.jdbc.app.entity;

import java.util.Date;

public class NoticeComment {
	private int id;
	private int mid;
	private String comment;
	private Date regDate;
	private String writerID;
	
	//생성자
	public NoticeComment() {
		
	}
	public NoticeComment(int id, int mid, String comment, Date regDate, String writerID) {
		this.id = id;
		this.mid = mid;
		this.comment = comment;
		this.regDate = regDate;
		this.writerID = writerID;
	}
	//getter setter
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMid() {
		return mid;
	}
	public void setMid(int mid) {
		this.mid = mid;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	public String getWriterID() {
		return writerID;
	}
	public void setWriterID(String writerID) {
		this.writerID = writerID;
	}
	
	
}
