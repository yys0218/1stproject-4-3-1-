package pro1.ask;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import pro1.ask.AskBoardDTO;

public class AskBoardDAO {
	private static AskBoardDAO instance = new AskBoardDAO();
	private AskBoardDAO() {};
	public static AskBoardDAO getInstance() { return instance; }
	
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

	
	// 게시글 등록
	public int uploadPost(AskBoardDTO ato) {
		int result = 0;
		
		int postNo	= ato.getPostNo();
		int no		= 0;
		
		try {
			conn = getConn();
			sql = "SELECT max(postNo) FROM ask_board ";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				no = rs.getInt(1)+1;
			}else {
				no = 1;
			}
			sql = "INSERT INTO ask_board(postNo, title, content, imgName, imgPath, views, reg, isEdited, isFixed, status, writer, isAnswered)"
					+"VALUES(ask_board_seq.NEXTVAL, ?, ?, ?, 'D:/oner/upload', 0, sysdate, 0, 0, 0, ?, 0)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, ato.getTitle());
			pstmt.setString(2, ato.getContent());
			pstmt.setString(3, ato.getImgName());
			pstmt.setString(4, ato.getWriter());
			result = pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(conn, pstmt, rs);
		}
		return result;
	}
	
	// 글 개수
	public int postCount() {
		int result = 0;		
		try {
			conn = getConn();
			sql = " select count(*) from ask_board ";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if( rs.next() ) {
				result = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt, rs);
		}
		return result;
	}
	
	// 글목록
	public ArrayList<AskBoardDTO> postList(int start, int end) {
		ArrayList<AskBoardDTO> list = new ArrayList<AskBoardDTO>();
		try {
			conn = getConn();
			sql = " select * from "
				+ " ( select b.*, rownum r from "
				+ " ( select * from ask_board order by postNo desc, postNo asc ) b ) "
				+ " where r >= ? and r <= ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			rs = pstmt.executeQuery();
			while( rs.next() ) {
				AskBoardDTO ato = new AskBoardDTO();
				ato.setPostNo(rs.getInt("postNo"));
				ato.setTitle(rs.getString("title"));
				ato.setViews(rs.getInt("views"));
				ato.setReg(rs.getTimestamp("reg"));
				ato.setWriter(rs.getString("writer"));
				ato.setIsAnswered(rs.getInt("isAnswered"));
				list.add(ato);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			close(conn, pstmt, rs);
		}
		return list;
	}
	
	// 글 내용(+조회수)
	 public AskBoardDTO postContent(int postNo) {
		 AskBoardDTO ato = new AskBoardDTO();
		try {
			conn=getConn();
			sql=" update ask_board set views=views+1 where postNo=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, postNo);
			pstmt.executeUpdate();
			
			sql = " select * from ask_board where postNo=? and status=0 ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, postNo);
			rs = pstmt.executeQuery();
			if( rs.next() ) {
				ato.setPostNo( rs.getInt("postNo") );
				ato.setTitle( rs.getString("title") );
				ato.setContent( rs.getString("content") );
				ato.setViews(rs.getInt("views"));
				ato.setReg( rs.getTimestamp("reg") );
				ato.setWriter(rs.getString("writer"));
			}			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt, rs);
		} 
		return ato;
	}
	
	 // 게시글 수정
	 public int updatePost(AskBoardDTO ato) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			conn = getConn(); // DAO 내에 구현된 커넥션 메서드
			
			String sql;
			if (ato.getImgName() != null && !ato.getImgName().isEmpty()) {
				sql = "UPDATE ask_board SET title=?, content=?, imgName=?, isEdited=1 WHERE postNo=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, ato.getTitle());
				pstmt.setString(2, ato.getContent());
				pstmt.setString(3, ato.getImgName());
				pstmt.setInt(4, ato.getPostNo());
			} else {
				sql = "UPDATE ask_board SET title=?, content=?, isEdited=1 WHERE postNo=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, ato.getTitle());
				pstmt.setString(2, ato.getContent());
				pstmt.setInt(4, ato.getPostNo());
			}
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt, null); // DAO 내 자원반환 메서드
		}
		return result;
	}
	 
	// 게시글 삭제
	public void deletePost(int postNo) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConn(); // 이 메서드는 DAO에 이미 있을 겁니다
			String sql = "DELETE FROM ask_board WHERE postNo = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, postNo);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();  // 에러 로그는 꼭 남기세요
		} finally {
			close(conn, pstmt, null); // 자원 정리 (DAO에 있는 close 메서드)
		}
	}
	
	// 게시글 정렬
	public ArrayList<AskBoardDTO> postListSorted(int start, int end, String sort) {
	    ArrayList<AskBoardDTO> list = new ArrayList<>();
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    String orderBy = "reg DESC"; // 기본 정렬

	    if ("views".equals(sort)) {
	        orderBy = "views DESC";
	    }

	    try {
	        conn = getConn();
	        String sql = "SELECT * FROM (SELECT ROWNUM rnum, A.* FROM ("
	                   + "SELECT * FROM ask_board ORDER BY " + orderBy
	                   + ") A) WHERE rnum BETWEEN ? AND ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, start);
	        pstmt.setInt(2, end);
	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	        	AskBoardDTO ato = new AskBoardDTO();
	            ato.setPostNo(rs.getInt("postNo"));
	            ato.setTitle(rs.getString("title"));
	            ato.setWriter(rs.getString("writer"));
	            ato.setReg(rs.getTimestamp("reg"));
	            ato.setViews(rs.getInt("views"));
	            ato.setIsAnswered(rs.getInt("isAnswered"));
	            list.add(ato);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        close(conn, pstmt, rs);
	    }
	    return list;
	}
	
	// 게시글 목록 검색 + 정렬
	public ArrayList<AskBoardDTO> searchPostsSorted(String keyword, String sort, int startRow, int endRow) {
	    ArrayList<AskBoardDTO> list = new ArrayList<>();
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
	        conn = getConn();

	        String orderBy = "postNo DESC";
	        if ("views".equals(sort)) {
	            orderBy = "views DESC";
	        }

	        StringBuilder sql = new StringBuilder();
	        sql.append("SELECT * FROM ( ");
	        sql.append("  SELECT ROWNUM rnum, A.* FROM ( ");
	        sql.append("    SELECT postNo, title, content, writer, reg, views");
	        sql.append("    FROM ask_board ");
	        sql.append("    WHERE (title LIKE ? OR content LIKE ?) ");

	        sql.append("    ORDER BY ").append(orderBy);
	        sql.append("  ) A WHERE ROWNUM <= ? ");
	        sql.append(") WHERE rnum >= ?");

	        pstmt = conn.prepareStatement(sql.toString());
	        pstmt.setString(1, "%" + keyword + "%");
	        pstmt.setString(2, "%" + keyword + "%");

	        int paramIndex = 3;
	        
	        pstmt.setInt(paramIndex++, endRow);
	        pstmt.setInt(paramIndex, startRow);

	        rs = pstmt.executeQuery();
	        while (rs.next()) {
	        	AskBoardDTO ato = new AskBoardDTO();
	            ato.setPostNo(rs.getInt("postNo"));
	            ato.setTitle(rs.getString("title"));
	            ato.setContent(rs.getString("content"));
	            ato.setWriter(rs.getString("writer"));
	            ato.setReg(rs.getTimestamp("reg"));
	            ato.setViews(rs.getInt("views"));
	            ato.setIsAnswered(rs.getInt("isAnswered"));
	            list.add(ato);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        close(conn, pstmt, rs);
	    }

	    return list;
	}

	// 게시글 개수 검색
	public int searchPostCount(String keyword) {
	    int count = 0;
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
	        conn = getConn();

	        StringBuilder sql = new StringBuilder();
	        sql.append("SELECT COUNT(*) FROM ask_board ");
	        sql.append("WHERE (title LIKE ? OR content LIKE ?) ");


	        pstmt = conn.prepareStatement(sql.toString());
	        pstmt.setString(1, "%" + keyword + "%");
	        pstmt.setString(2, "%" + keyword + "%");

	        rs = pstmt.executeQuery();
	        if (rs.next()) {
	            count = rs.getInt(1);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        close(conn, pstmt, rs);
	    }

	    return count;
	}

	public ArrayList<AskBoardDTO> getTop3PostsByViews() {
	    ArrayList<AskBoardDTO> list = new ArrayList<>();
	    String sql;

	    try {
	        conn = getConn();

	        // 전체 게시글 중 조회수 top 3
	        sql = "SELECT * FROM (SELECT * FROM ask_board ORDER BY views DESC) WHERE ROWNUM <= 3";
	        pstmt = conn.prepareStatement(sql);

	        rs = pstmt.executeQuery();
	        while (rs.next()) {
	        	AskBoardDTO ato = new AskBoardDTO();
	            ato.setPostNo(rs.getInt("postNo"));
	            ato.setTitle(rs.getString("title"));
	            ato.setWriter(rs.getString("writer"));
	            ato.setReg(rs.getTimestamp("reg"));
	            ato.setViews(rs.getInt("views"));
	            ato.setIsAnswered(rs.getInt("isAnswered"));
	            list.add(ato);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        close(conn, pstmt, rs);
	    }

	    return list;
	}
}
