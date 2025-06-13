<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="pro1.qna.QnaBoardDAO" %>
<%@ page import="pro1.qna.QnaBoardDTO" %>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>이거빼조-고쳐조</title>
	<%--<link rel="stylesheet" href="/pro1/views/resources/style.css">--%>
</head>
<body>
<%@include file="sidebar.jsp"%>
<%	
	int postNo=Integer.parseInt(request.getParameter("postNo"));
	QnaBoardDAO dao = QnaBoardDAO.getInstance();
	QnaBoardDTO dto = dao.getPost(postNo);
	/*
	MemberDTO user = (QnaBoardDTO)session.getAttribute("loginUser"); session이 있다면(로그인이 된 상태라면) 유저정보 통째로 꺼내
	String writer = user.getWriter();//user가 존재한다면 writer값 가져오기
	if(user==null){ //로그인없이 페이지 접근했을 경우 로그인페이지로 돌리기 */	
		
	//session, 비밀번호 대조 후 일치하는 사람만 확인 가능
	//int ppw = user.getWriter();
	dto.getPpw();	
 %>
 
	<div align="center" class="content">
		<table width="800" border="1">
			<tr><td colspan="2">
				<font size="8"><%=dto.getTitle() %></font>
<%		if(dto.getCategory()==1){ //문의글 카테고리 표시 %>
			<span>문의글</span>			
<%		} else if(dto.getCategory()==2){ //신고글 카테고리 표시 %>
			<span>신고글</span>
<%		}	%>
			</td>		
				<td width="100" align="right">
<%					//if(user!=null){ //로그인 상태이고, 작성자 본인이 맞다면 %>
					<input type=button value="수정" onclick="window.location='updateForm.jsp?category=<%=dto.getCategory()%>&postNo=<%=dto.getPostNo()%>'">
					<input type=button value="삭제" onclick="window.location='deletePro.jsp?postNo=<%=dto.getPostNo()%>'">	
				</td>
<%	//}	%>
			</tr>
			<tr><td colspan="3"><hr width="800"></td></tr>
			<tr>
				<td width="120"><%=dto.getWriter() %></td>
				<td><%=dto.getReg() %></td>
				<td width="100" align="right">조회수 <%=dto.getViews() %></td>
			</tr>
			<tr>
				<td colspan="3"><%=dto.getContent()%></td>
			</tr>
		</table>
	</div>
	<div class="content">
		<hr width="800" align="center">
		<table width="800" align="center">
			<tr><td>답글 < % 댓글 수 % ></td></tr>
		</table>
		<hr width="800" align="center">
<% 		
		//reply처리(댓글양식) 
		//if()
%>		
		<form action="commPro.jsp" method="post">
			<textarea rows="5" cols="30" name="content">댓글</textarea>
			<input type="submit" value="작성"/>
		</form>
	</div>
</body>
</html>