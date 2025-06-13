package pro1.ask;

import java.sql.Timestamp;

public class AskBoardDTO {
	private int postNo;
	private String title;
	private String content;
	private String imgName;
	private String imgPath;
	private int views;
	private Timestamp reg;
	private int isEdited;
	private int isFixed;
	private int status;
	private String writer;
	private int isAnswered;
	
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public int getPostNo() {
		return postNo;
	}
	public void setPostNo(int postNo) {
		this.postNo = postNo;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getIsAnswered() {
		return isAnswered;
	}
	public void setIsAnswered(int isAnswered) {
		this.isAnswered = isAnswered;
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
	public int getViews() {
		return views;
	}
	public void setViews(int views) {
		this.views = views;
	}
	public Timestamp getReg() {
		return reg;
	}
	public void setReg(Timestamp reg) {
		this.reg = reg;
	}
	public int getIsEdited() {
		return isEdited;
	}
	public void setIsEdited(int isEdited) {
		this.isEdited = isEdited;
	}
	public int getIsFixed() {
		return isFixed;
	}
	public void setIsFixed(int isFixed) {
		this.isFixed = isFixed;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
