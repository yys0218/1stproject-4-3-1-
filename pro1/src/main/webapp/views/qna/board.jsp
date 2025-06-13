<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="pro1.qna.QnaBoardDAO" %>
<%@ page import="pro1.qna.QnaBoardDTO" %>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>이거빼조-고쳐조</title>
	<link rel="stylesheet" href="/pro1/views/resources/style.css">
</head>
<body>
<%@include file="sidebar.jsp"  %>
<%
	//MemberDTO user = session.getAttribute("loginUser");
	//페이지 번호 부여
	String pageNo = request.getParameter("pageNo");
	if(pageNo==null){	pageNo="1";	}
	int currentPage = Integer.parseInt(pageNo);
	
	int pageSize = 3;//페이지에 보여질 글의 개수 지정
	int start = (currentPage-1)*pageSize+1; //(1-1)*10 +1 = 1 
	int end = currentPage*pageSize; //1*
	
	QnaBoardDAO dao = QnaBoardDAO.getInstance();
	ArrayList<QnaBoardDTO> board = dao.getBoard(start, end);

	//게시글이 없을 때 보여줄 항목 처리
	
%>	

	<div class="content">
		<table width="800" border="1">
		<tr>
			<td colspan="2"><h2>고쳐조 게시판</h2></td>
			<td width="100" align="right">
<% 				//로그인한 회원이라면 글작성 가능
				//if(user!=null){%>			
					<input type="button" onclick="window.location='uploadForm.jsp'" value="글작성"/>		
<%				//} %>			
			</td>
		</tr>
<%			for(QnaBoardDTO dto : board){	%>		
			<tr><td width="100" height="50" align="center">
<%					if(dto.getCategory()==1){%>
						문의 
<%					} else if(dto.getCategory()==2){ %>
						신고	
<% 					}%></td>	
				<td colspan="2">
					<a href="post.jsp?postNo=<%=dto.getPostNo()%>&pageNo=<%=currentPage%>"><font size="4"><%=dto.getTitle()%></font></a><br>
					<font size="2"><%=dto.getWriter()%> | <%=dto.getReg() %></font>
				</td>
			</tr>
<%	
	}
%>
			<tr><td align="center" colspan="3">			
<%
	int count = dao.boardCount(); //전체 게시글의 개수를 세는 메서드
	if(count>0){
		int pageBlock = 2; //한 번에 보여질 페이지 블럭 수 
		int pageCount = count/pageSize + (count%pageSize==0? 0 : 1); //총 페이지 블럭 수
		int startPage = (int)((currentPage-1)/pageBlock)*pageBlock+1; //시작 페이지 블럭 번호(현재 페이지가 1이면 1부터 시작)
		int endPage = startPage+pageBlock-1; //끝 페이지 블럭 번호(현재 페이지가 1이면 5로 끝)
		if(endPage>pageCount){endPage = pageCount;} //끝 블럭이 총 블럭보다 클 경우, 총 블럭 대입
		
		if(startPage>pageBlock){%> 
			<a href="board.jsp?pageNo=<%=startPage-pageBlock%>"> &lt;이전 </a> <% }
		for(int i=startPage; i<=endPage; i++){ %>
			<a href="board.jsp?pageNo=<%=i %>">&nbsp;<%=i %>&nbsp;</a>	
<%		}
		if(endPage<pageCount){%> 
			<a href="board.jsp?pageNo=<%=startPage+pageBlock%>">다음&gt;</a>
<%		}	
	}
%>
			</td></tr>
		</table>
	</div>
</body>
</html>