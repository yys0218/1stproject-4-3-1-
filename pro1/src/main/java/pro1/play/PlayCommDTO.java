package pro1.play;

import java.sql.Timestamp;

public class PlayCommDTO {
	private int commNo;
	private String content;
	private int postNo;
	private Timestamp reg;
	private int ref;
	private int isEdited;
	private int par;
	private int status;
	private String writer;
	
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public int getCommNo() { 
		return commNo;
	}
	public void setCommNo(int commNo) {
		this.commNo = commNo;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getPostNo() {
		return postNo;
	}
	public void setPostNo(int postNo) {
		this.postNo = postNo;
	}
	public Timestamp getReg() {
		return reg;
	}
	public void setReg(Timestamp reg) {
		this.reg = reg;
	}
	public int getRef() {
		return ref;
	}
	public void setRef(int ref) {
		this.ref = ref;
	}
	public int getIsEdited() {
		return isEdited;
	}
	public void setIsEdited(int isEdited) {
		this.isEdited = isEdited;
	}
	public int getPar() {
		return par;
	}
	public void setPar(int par) {
		this.par = par;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
