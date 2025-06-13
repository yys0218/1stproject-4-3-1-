package pro1.qna;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;//mr생성용(request의 타입)

import com.oreilly.servlet.MultipartRequest; //mr생성용 
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy; //dp생성용

public class QnaBoardDAO { //싱글톤 패턴으로 사용예정: QnaBoardDAO.getInstance();

	//자신의 인스턴스를 담을 private static instance 변수 선언, 생성자 접근제한하기, 메서드 생성
	private static QnaBoardDAO instance = new QnaBoardDAO();
	private QnaBoardDAO() {};
	public static QnaBoardDAO getInstance() {	return instance;	} //클래스명으로 호출할 수 있게 static 메서드 여야함!!
	
	//사용할 변수 미리 선언 private으로
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private String sql;
	
	//DB연결 메서드 정의
	private Connection getConn() throws Exception{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		String url="jdbc:oracle:thin:@localhost:1521:orcl";
		String user="scott";
		String pw="tiger";
		conn = DriverManager.getConnection(url,user,pw);
		return conn;
	}
	
	//DB연결 해제
	private void close( Connection conn, PreparedStatement pstmt, ResultSet rs) {
		try { if(conn!=null) {conn.close();}} catch (SQLException e) {	e.printStackTrace();	}
		try { if(pstmt!=null) {pstmt.close();}} catch (SQLException e) {	e.printStackTrace();	}
		try { if(rs!=null) {rs.close();}} catch (SQLException e) {	e.printStackTrace();	}
	}
			
	
	//multipartRequest 생성하는 메서드: 파일을 workspace내 폴더에 저장함
	public MultipartRequest getMr(HttpServletRequest request) {
		String path="D:/views/resources/qnaImg";
		int max=1024*1024*100; //100메가 업로드 가능
		String enc="UTF-8";
		DefaultFileRenamePolicy dp = new DefaultFileRenamePolicy();
		
		MultipartRequest mr=null;
		try {
			mr = new MultipartRequest(request,path,max,enc,dp);
		} catch (IOException e) {	e.printStackTrace();	}
		return mr;
	}
	
	
	// 글 작성
		public int uploadPost(QnaBoardDTO dto) {
			int result=0;		
			try {
				conn=getConn();
				sql = " insert into qna_board (postno, category, title, content, ppw, writer ) values( qna_board_seq.nextval, ?, ?, ?, ?, ?)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, dto.getCategory());
				pstmt.setString(2, dto.getTitle());
				pstmt.setString(3, dto.getContent());
				pstmt.setInt(4, dto.getPpw());
				pstmt.setString(5, dto.getWriter());
				result = pstmt.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				close(conn, pstmt,rs);
			}		
			return result;
		}
	
		
		//글 내용 불러오기 + readCount 올리는 쿼리문
		public QnaBoardDTO getPost(int postNo) {
			QnaBoardDTO dto = new QnaBoardDTO();
			try {
				conn = getConn();			
				sql = "update qna_board set views=views+1 where postno=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, postNo);
				pstmt.executeUpdate();
				
				sql="select * from qna_board where postno=?"; //어떤 글번호의 레코드 조회
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, postNo);
				rs=pstmt.executeQuery();
				if(rs.next()) { //dto에 rs의 결과값 넣기
					dto.setPostNo(rs.getInt("postno"));
					dto.setTitle(rs.getString("title"));
					dto.setContent(rs.getString("content"));
					dto.setWriter(rs.getString("writer"));
					dto.setPpw(rs.getInt("ppw"));
					dto.setReg(rs.getTimestamp("reg"));
					dto.setViews(rs.getInt("views"));
					//dto.setImgName(rs.getString("imgName"));
					//dto.setImgPath(rs.getString("imgPath"));
					dto.setCategory(rs.getInt("category"));
					dto.setStatus(rs.getInt("status"));
					dto.setIsAnswered(rs.getInt("isanswered"));
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				close(conn,pstmt,rs);
			}
			return dto;
		}
	
	
		//글목록 불러오기
		public ArrayList<QnaBoardDTO> getBoard(int start, int end) {
			ArrayList<QnaBoardDTO> board = new ArrayList<QnaBoardDTO>();
			
			try {
				conn=getConn();
				sql="select * from (select b.*, rownum r from (select * from qna_board order by postNo desc) b) where r>=? and r<=?";
				pstmt=conn.prepareStatement(sql);
				pstmt.setInt(1, start);
				pstmt.setInt(2, end);
				rs = pstmt.executeQuery();
				while(rs.next()) {
					QnaBoardDTO dto = new QnaBoardDTO();
					dto.setPostNo(rs.getInt("postNo"));
					dto.setTitle(rs.getString("title"));
					dto.setWriter(rs.getString("writer"));
					dto.setReg(rs.getTimestamp("reg"));
					dto.setCategory(rs.getInt("category"));
					dto.setStatus(rs.getInt("status"));
					dto.setIsAnswered(rs.getInt("isAnswered"));
					board.add(dto);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				close(conn,pstmt,rs);
			}
			
			return board;
		}
	
		
		//전체 게시글 개수 세기
		public int boardCount() {
			int count = 0;
			try {
				conn=getConn();
				sql="select count(*) from qna_board";
				pstmt=conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				if(rs.next()) {
					count=rs.getInt(1);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				close(conn, pstmt, rs);
			}
			return count;
		}
		
		
		//글내용 업데이트하는 메서드
		
		
}
