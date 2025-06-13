<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="pro1.qna.QnaBoardDAO" %>
<% request.setCharacterEncoding("UTF-8"); %>
<jsp:useBean class="pro1.qna.QnaBoardDTO" id="dto"/>
<jsp:setProperty name="dto" property="*"/>

<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<title>qna_uploadPro</title>
</head>
<body>
<%
	QnaBoardDAO dao = QnaBoardDAO.getInstance();
	//dao.getMr(request); multipart로 받아보려고 했던 시도..

	int result= dao.uploadPost(dto);
	if(result==1){
%>
<script>
	alert("게시글이 업로드되었습니다.");
	window.location="post.jsp";
</script>
<%
	}
%>	
</body>
</html>