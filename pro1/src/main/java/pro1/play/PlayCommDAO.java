package pro1.play;

import java.sql.*;
import java.util.ArrayList;

public class PlayCommDAO {
	private static PlayCommDAO instance = new PlayCommDAO();
	private PlayCommDAO() {};
	public static PlayCommDAO getInstance() { return instance; }
	
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
	public int uploadComm(PlayCommDTO cto) {
	    int result = 0;
	    try {
	        conn = getConn();

	        // 댓글 ref 계산
	        sql = "SELECT NVL(MAX(ref), 0) + 1 FROM play_comm";
	        pstmt = conn.prepareStatement(sql);
	        rs = pstmt.executeQuery();
	        int newRef = 1;
	        if (rs.next()) {
	            newRef = rs.getInt(1);
	        }
	        rs.close();
	        pstmt.close();

	        // 댓글 등록
	        sql = "INSERT INTO play_comm (commNo, content, postNo, reg, ref, isEdited, par, status, writer) " +
	              "VALUES (play_comm_seq.NEXTVAL, ?, ?, SYSDATE, ?, 0, 0, 0, ?)";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, cto.getContent());
	        pstmt.setInt(2, cto.getPostNo());
	        pstmt.setInt(3, newRef);
	        pstmt.setString(4, cto.getWriter());

	        result = pstmt.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        close(conn, pstmt, rs);
	    }
	    return result;
	}

	// 대댓글 작성
	public int uploadCocomm(PlayCommDTO cto) {
	    int result = 0;
	    try {
	        conn = getConn();

	        sql = "SELECT NVL(MAX(par), 0) + 1 FROM play_comm WHERE ref = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, cto.getRef());
	        rs = pstmt.executeQuery();
	        int newPar = 1;
	        if (rs.next()) {
	            newPar = rs.getInt(1);
	        }
	        rs.close();
	        pstmt.close();

	        sql = "INSERT INTO play_comm (commNo, content, postNo, reg, ref, isEdited, par, status, writer) " +
	              "VALUES (play_comm_seq.NEXTVAL, ?, ?, SYSDATE, ?, 0, ?, 0, ?)";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, cto.getContent());
	        pstmt.setInt(2, cto.getPostNo());
	        pstmt.setInt(3, cto.getRef());
	        pstmt.setInt(4, newPar);
	        pstmt.setString(5, cto.getWriter());

	        result = pstmt.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        close(conn, pstmt, rs);
	    }
	    return result;
	}

	// 댓글 목록(대댓글포함)
	public ArrayList<PlayCommDTO> commList(int postNo) {
	    ArrayList<PlayCommDTO> list = new ArrayList<>();
	    try {
	        conn = getConn();
	        sql = "SELECT * FROM play_comm WHERE postNo = ? AND status = 0 ORDER BY ref ASC, par ASC";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, postNo);
	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	            PlayCommDTO cto = new PlayCommDTO();
	            cto.setCommNo(rs.getInt("commNo"));
	            cto.setContent(rs.getString("content"));
	            cto.setPostNo(rs.getInt("postNo"));
	            cto.setReg(rs.getTimestamp("reg"));
	            cto.setRef(rs.getInt("ref"));
	            cto.setIsEdited(rs.getInt("isEdited"));
	            cto.setPar(rs.getInt("par"));
	            cto.setStatus(rs.getInt("status"));
	            cto.setWriter(rs.getString("writer"));
	            list.add(cto);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        close(conn, pstmt, rs);
	    }
	    return list;
	}

	// 댓글(또는 대댓글) 완전 삭제
	public int deleteComm(int commNo) {
	    int result = 0;
	    try {
	        conn = getConn();
	        sql = "DELETE FROM play_comm WHERE commNo = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, commNo);
	        result = pstmt.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        close(conn, pstmt, rs);
	    }
	    return result;
	}
}
