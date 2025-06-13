package pro1.ask;

import java.sql.*;
import java.util.ArrayList;

public class AskReplyDAO {
	private static AskReplyDAO instance = new AskReplyDAO();
	private AskReplyDAO() {};
	public static AskReplyDAO getInstance() { return instance; }
	
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private String sql;
	
	private Connection getConn() throws Exception {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		String url = "jdbc:oracle:thin:@localhost:1521:orcl";
		String user = "scott";
		String pw = "tiger";
		conn = DriverManager.getConnection(url,user,pw);
		return conn;
	}
	
	private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
		try { if( conn != null ) { conn.close(); } }catch(SQLException e) {e.printStackTrace();}
		try { if( pstmt != null ) { pstmt.close(); } }catch(SQLException e) {e.printStackTrace();}
		try { if( rs != null ) { rs.close(); } }catch(SQLException e) {e.printStackTrace();}
	}
	
	// 댓글 작성
	public int uploadReply(AskReplyDTO rto) {
	    int result = 0;
	    try {
	        conn = getConn();

	        // 댓글 등록
	        sql = "INSERT INTO ask_reply (commNo, content, postNo, reg, status, writer) " +
	              "VALUES (ask_reply_seq.NEXTVAL, ?, ?, SYSDATE, 0, ?)";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, rto.getContent());
	        pstmt.setInt(2, rto.getPostNo());
	        pstmt.setString(3, rto.getWriter());

	        result = pstmt.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        close(conn, pstmt, rs);
	    }
	    return result;
	}

	// 답변 목록
	public ArrayList<AskReplyDTO> commList(int postNo) {
	    ArrayList<AskReplyDTO> list = new ArrayList<>();
	    try {
	        conn = getConn();
	        sql = "SELECT * FROM ask_reply WHERE postNo = ? AND status = 0 ORDER BY replyNo ASC";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, postNo);
	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	        	AskReplyDTO rto = new AskReplyDTO();
	            rto.setReplyNo(rs.getInt("replyNo"));
	            rto.setContent(rs.getString("content"));
	            rto.setPostNo(rs.getInt("postNo"));
	            rto.setReg(rs.getTimestamp("reg"));
	            rto.setStatus(rs.getInt("status"));
	            rto.setWriter(rs.getString("writer"));
	            list.add(rto);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        close(conn, pstmt, rs);
	    }
	    return list;
	}

	// 답변 완전 삭제
	public int deleteComm(int replyNo) {
	    int result = 0;
	    try {
	        conn = getConn();
	        sql = "DELETE FROM ask_reply WHERE replyNo = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, replyNo);
	        result = pstmt.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        close(conn, pstmt, rs);
	    }
	    return result;
	}
}
