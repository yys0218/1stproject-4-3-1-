<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="java.io.*, javax.servlet.*, javax.servlet.http.*" %>
<%@ page import="com.oreilly.servlet.MultipartRequest" %>
<%@ page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy" %>
<%@ page import="pro1.qna.QnaBoardDAO" %>

<%
  request.setCharacterEncoding("UTF-8");

  // 저장 경로 설정 (실제 디렉토리 경로)
  String imgPath = "/views/resources/qnaImg"; //웹이 접근하는 경로, 서버 내 이 경로에 폴더가 생성됨
  String uploadPath = application.getRealPath(imgPath); //실제 사진이 올라가는 서버 내 경로

  // 저장 디렉토리 생성 >> 여기서 지정한 저장경로에 사진이 저장됨
  File uploadDir = new File(uploadPath); //저장경로를 눈에 보이는 폴더로 지정
  if (!uploadDir.exists()) {
    uploadDir.mkdirs();
  }

  //MultipartRequest 객체 생성
  MultipartRequest multi = new MultipartRequest(request, uploadPath, 10*1024*1024, "UTF-8", new DefaultFileRenamePolicy());

  // 브라우저에서 접근할 수 있는 URL 경로 반환
  String imgName = multi.getFilesystemName("file"); //"file"이 input의 name?..
  String fileUrl = request.getContextPath() + imgPath + "/" + imgName;
  
  response.setContentType("text/plain; charset=UTF-8");
  response.getWriter().write(fileUrl);
  
  
%>
