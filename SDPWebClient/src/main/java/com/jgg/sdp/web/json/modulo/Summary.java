package com.jgg.sdp.web.json.modulo;

public class Summary {

	private String  fileName;
	private String  memberName;
	private String  author;
	private String  uid;
	private Integer versions;
	private Long    tms;
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public Integer getVersions() {
		return versions;
	}
	public void setVersions(Integer versions) {
		this.versions = versions;
	}
	public Long getTms() {
		return tms;
	}
	public void setTms(Long tms) {
		this.tms = tms;
	}
	
	
}
