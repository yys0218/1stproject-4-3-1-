package pro1.qna;

import java.sql.Timestamp;

public class QnaBoardDTO {
	//private 변수 설정
	private int postNo;
	private String title;
	private String content;
	private String writer;
	private int ppw;
	private Timestamp reg;
	private int views;
	private String imgName;
	private String imgPath;
	private int category;
	private int status;
	private int isAnswered;

	//get()set()
	public int getPostNo() {
		return postNo;
	}
	public void setPostNo(int postNo) {
		this.postNo = postNo;
	}
	public String getTitle() {
		return title;
	}
	public int getPpw() {
		return ppw;
	}
	public void setPpw(int ppw) {
		this.ppw = ppw;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getWriter() {
		return writer;
	}

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Timestamp getReg() {
		return reg;
	}
	public void setReg(Timestamp reg) {
		this.reg = reg;
	}
	public int getViews() {
		return views;
	}
	public void setViews(int views) {
		this.views = views;
	}
	public String getImgName() {
		return imgName;
	}
	public void setImgName(String imgName) {
		this.imgName = imgName;
	}
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getIsAnswered() {
		return isAnswered;
	}
	public void setIsAnswered(int isAnswered) {
		this.isAnswered = isAnswered;
	}
	
	
	
	
	
}
