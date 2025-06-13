<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="pro1.qna.QnaBoardDAO" %>
<% request.setCharacterEncoding("UTF-8");%>
<jsp:useBean class="pro1.qna.QnaBoardDTO" id="dto"/>
<jsp:setProperty name="dto" property="*"/>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%

	QnaBoardDAO dao = QnaBoardDAO.getInstance();
	//int result = dao.update메서드
%>	
</body>
</html>