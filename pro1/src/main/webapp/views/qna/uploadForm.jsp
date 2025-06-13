<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="pro1.qna.QnaBoardDTO" %>    
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<title>이거빼조-고쳐조</title>
	<%-- jQuery 라이브러리 로드 (bootstrap css/js파일은 제외한 lite버전 사용 예정)--%>
	<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
	
	<%-- summernote css/js 파일 로드--%>
	<link href="https://cdn.jsdelivr.net/npm/summernote@0.9.0/dist/summernote-lite.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/summernote@0.9.0/dist/summernote-lite.min.js"></script>

	<%-- summernote 내 한글 사용위한 파일 로드--%>
	<script src=" https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/lang/summernote-ko-KR.min.js"></script>
	<%--안되면 이 링크로 해보자 
	<script src="https://cdn.jsdelivr.net/npm/summernote@0.9.0/lang/summernote-ko-KR.min.js"></script>--%>
	
	<%-- 스타일 시트 연결 --%>
	<link rel="stylesheet" href="/pro1/views/resources/style.css">
</head>
<body>
<%
	/*MemberDTO user = (QnaBoardDTO)session.getAttribute("loginUser"); //session이 있다면(로그인이 된 상태라면) 유저정보 통째로 꺼내기
	String writer = user.getWriter();//user가 존재한다면 writer값 가져오기
	if(user==null){ //로그인없이 페이지 접근했을 경우 로그인페이지로 돌리기 */	
%>
		<%--  <script>
			alert("로그인 후 작성이 가능합니다.");
			window.location="/pro1/views/member/loginForm.jsp";
		</script>--%>
<%	
//	}
%>
<%@include file="sidebar.jsp"  %>
	<div align="center" class="content">
		<h2 align="center">글 작성</h2>
		<hr width="400" align="center">
		<form method="post" action="uploadPro.jsp">
			<table width="400">
				<tr><td width="200">
					작성자&nbsp;&nbsp;&nbsp;Writer<%-- 세션에서 writer출력--%>
					<input type="hidden" name="writer" value=" writer표현문"/>
				</td>
				<td align="right" width="200">구분 &nbsp;
					<select name="category">
						<option value="1">문의</option>
						<option value="2">신고</option>
					</select>&nbsp;&nbsp;&nbsp;&nbsp;
				</td></tr>
				<tr><td colspan="2">제목 &nbsp;&nbsp;&nbsp;&nbsp;
					<input type="text" name="title" size="40" maxlength="100" required/></td>
				</tr>
			</table>
			<br>
			<textarea name="content" id="summernote"></textarea>
			<br>
			<!--<input type="hidden" name="imgPath" id="imgPath"/>-->
			<table width="800">
				<tr>
					<td><input type="button" id="resetbtn" value="다시 작성"/></td>
					<td align="right">글 비밀번호를 입력해주세요&nbsp;&nbsp;
						<input type="password" name="ppw" maxlength="4" placeholder="숫자 4자" required/>
					<input type="submit" value="업로드"/></td>
				</tr>
			</table>
		</form>
	</div>
	
	<script> 
	 $(document).ready(function() {
		$('#summernote').summernote({
			width: 800,               // 에디터 넓이
			height: 450,                 // 에디터 높이
			minHeight: 450,             // 최소 높이
			maxHeight: 420,             // 최대 높이
			focus: true,                  // 에디터 로딩후 자동으로 입력창에 커서(focut)가 가게 함
			lang: "ko-KR",               // 한글 설정
			toolbar: [
				// 글꼴 설정
				['fontname', ['fontname']],
				// 글자 크기 설정
				['fontsize', ['fontsize']],
				// 굵기, 기울임꼴, 밑줄,취소 선, 서식지우기
				['style', ['bold', 'italic', 'underline','strikethrough', 'clear']],
				// 글자색
				['color', ['forecolor','color']],
				//글정렬
				['para', ['paragraph']],
				// 그림첨부, 링크만들기, 동영상첨부
				['insert', ['picture', 'video', 'link']],
				// 표만들기
				['table', ['table']],
				// 줄간격
				['height', ['height']],
				// 코드보기, 확대해서보기, 도움말
				['view', ['codeview', 'undo', 'redo']]
			  ],
			callbacks: {
				onInit: function(){ 
			    	$('#resetbtn').on('click', function(){ //에디터 글 내용 reset시키기
			    		$('#summernote').summernote('reset');
			        });//on()메서드 종료
			        $('#summernote').summernote('formatPara'); //기본 좌측정렬로 만들어주는 함수
			        $('#summernote').summernote('justifyLeft');
			    },
			    onImageUpload: function(files) {//에디터에서 이미지가 업로드될 때마다 호출, 이 함수가 서버로 이미지를 전송->서버에서 반환된 url을 에디터에 다시 설정함
			    	for (let i = 0; i < files.length; i++) {
			    		uploadImage(files[i]);//아래에서 정의됨
			    	}
			    }
			},
			dialogsInBody: true,
			disableDragAndDrop: true // base64 막기
		}); //summernote함수 설정 끝
	 });//document.ready 끝
			
	function uploadImage(file) {
		 const formData = new FormData(); //formData 객체 생성
		 formData.append("file", file); //formData에 파일명 "file"인 file 객체? 추가
		 $.ajax({
			 url: "imageUpload.jsp", // 실제 이미지 저장 경로(처리 경로)를 담은 파일
			 type: "POST",
			 data: formData,
			 contentType: false, //multipart/form-data로 처리되는 것과 같음
			 processData: false, //formData 전송 설정: 파일전송시 자동으로 쿼리스트링 형식으로 전송되지 않도록 막는 처리
			 success: function(url) {
				console.log(url); //썸머노트가 사진처리 성공 시 콘솔에 그 url 출력
			 	$('#summernote').summernote('insertImage', url);//url이 summernote에 삽입됨
			 },
			error: function(err) {
				console.error("업로드 실패", err);
				alert("이미지 업로드에 실패했습니다.");
			}
		 });//$.ajax 종료
	}//uploadImage 종료

	/* summernote유효성검사 나중에나중에
	function uploadCheck(){
		let check = document.;
		if( !check.title.value ){ 
			alert("제목을 입력해주세요.");
			return false;
		}
	}*/	
</script>
</body>
</html>