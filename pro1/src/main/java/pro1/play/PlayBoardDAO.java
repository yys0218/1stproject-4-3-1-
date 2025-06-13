package pro1.play;

import java.sql.*; 
import java.util.ArrayList;
import java.util.List;
import pro1.play.PlayBoardDTO;

public class PlayBoardDAO {
	private static PlayBoardDAO instance = new PlayBoardDAO();
	private PlayBoardDAO() {};
	public static PlayBoardDAO getInstance() { return instance; }
	
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
	public int uploadPost(PlayBoardDTO bto) {
		int result = 0;
		
		int postNo	= bto.getPostNo();
		int no		= 0;
		
		try {
			conn = getConn();
			sql = "SELECT max(postNo) FROM play_board ";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				no = rs.getInt(1)+1;
			}else {
				no = 1;
			}
			sql = "INSERT INTO play_board(postNo, title, content, category, imgName, imgPath, views, reg, isEdited, isFixed, status, writer)"
					+"VALUES(play_board_seq.NEXTVAL, ?, ?, ?, ?, 'D:/oner/upload', 0, sysdate, 0, 0, 0, ?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, bto.getTitle());
			pstmt.setString(2, bto.getContent());
			pstmt.setInt(3, bto.getCategory());
			pstmt.setString(4, bto.getImgName());
			pstmt.setString(5, bto.getWriter());
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
			sql = " select count(*) from play_board ";
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
	public ArrayList<PlayBoardDTO> postList(int start, int end) {
		ArrayList<PlayBoardDTO> list = new ArrayList<PlayBoardDTO>();
		try {
			conn = getConn();
			sql = " select * from "
				+ " ( select b.*, rownum r from "
				+ " ( select * from play_board order by postNo desc, postNo asc ) b ) "
				+ " where r >= ? and r <= ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			rs = pstmt.executeQuery();
			while( rs.next() ) {
				PlayBoardDTO bto = new PlayBoardDTO();
				bto.setPostNo(rs.getInt("postNo"));
				bto.setCategory(rs.getInt("category"));
				bto.setTitle(rs.getString("title"));
				bto.setViews(rs.getInt("views"));
				bto.setReg(rs.getTimestamp("reg"));
				bto.setWriter(rs.getString("writer"));
				list.add(bto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			close(conn, pstmt, rs);
		}
		return list;
	}	
	
	// 카테고리 1 글목록
	public ArrayList<PlayBoardDTO> postList1(int start, int end) {
		ArrayList<PlayBoardDTO> list = new ArrayList<PlayBoardDTO>();
		try {
			conn = getConn();
			sql = " select * from "
				+ " ( select b.*, rownum r from "
				+ " ( select * from play_board order by postNo desc, postNo asc ) b ) "
				+ " where r >= ? and r <= ? and category=1 ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			rs = pstmt.executeQuery();
			while( rs.next() ) {
				PlayBoardDTO bto = new PlayBoardDTO();
				bto.setPostNo(rs.getInt("postNo"));
				bto.setCategory(rs.getInt("category"));
				bto.setTitle(rs.getString("title"));
				bto.setViews(rs.getInt("views"));
				bto.setReg(rs.getTimestamp("reg"));
				bto.setWriter(rs.getString("writer"));
				list.add(bto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			close(conn, pstmt, rs);
		}
		return list;
	}	
	
	// 카테고리 2 글목록
	public ArrayList<PlayBoardDTO> postList2(int start, int end) {
		ArrayList<PlayBoardDTO> list = new ArrayList<PlayBoardDTO>();
		try {
			conn = getConn();
			sql = " select * from "
				+ " ( select b.*, rownum r from "
				+ " ( select * from play_board order by postNo desc, postNo asc ) b ) "
				+ " where r >= ? and r <= ? and category=2 ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			rs = pstmt.executeQuery();
			while( rs.next() ) {
				PlayBoardDTO bto = new PlayBoardDTO();
				bto.setPostNo(rs.getInt("postNo"));
				bto.setCategory(rs.getInt("category"));
				bto.setTitle(rs.getString("title"));
				bto.setViews(rs.getInt("views"));
				bto.setReg(rs.getTimestamp("reg"));
				bto.setWriter(rs.getString("writer"));
				list.add(bto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			close(conn, pstmt, rs);
		}
		return list;
	}	
	
	// 카테고리 3 글목록
	public ArrayList<PlayBoardDTO> postList3(int start, int end) {
		ArrayList<PlayBoardDTO> list = new ArrayList<PlayBoardDTO>();
		try {
			conn = getConn();
			sql = " select * from "
				+ " ( select b.*, rownum r from "
				+ " ( select * from play_board order by postNo desc, postNo asc ) b ) "
				+ " where r >= ? and r <= ? and category=3 ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			rs = pstmt.executeQuery();
			while( rs.next() ) {
				PlayBoardDTO bto = new PlayBoardDTO();
				bto.setPostNo(rs.getInt("postNo"));
				bto.setCategory(rs.getInt("category"));
				bto.setTitle(rs.getString("title"));
				bto.setViews(rs.getInt("views"));
				bto.setReg(rs.getTimestamp("reg"));
				bto.setWriter(rs.getString("writer"));
				list.add(bto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			close(conn, pstmt, rs);
		}
		return list;
	}	
	
	// 글 내용(+조회수)
	 public PlayBoardDTO postContent(int postNo) {
		 PlayBoardDTO bto = new PlayBoardDTO();
		try {
			conn=getConn();
			sql=" update play_board set views=views+1 where postNo=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, postNo);
			pstmt.executeUpdate();
			
			sql = " select * from play_board where postNo=? and status=0 ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, postNo);
			rs = pstmt.executeQuery();
			if( rs.next() ) {
				bto.setCategory(rs.getInt("category"));
				bto.setPostNo( rs.getInt("postNo") );
				bto.setTitle( rs.getString("title") );
				bto.setContent( rs.getString("content") );
				bto.setViews(rs.getInt("views"));
				bto.setReg( rs.getTimestamp("reg") );
				bto.setWriter(rs.getString("writer"));
			}			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt, rs);
		} 
		return bto;
	}
	
	 // 게시글 수정
	 public int updatePost(PlayBoardDTO bto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			conn = getConn(); // DAO 내에 구현된 커넥션 메서드
			
			String sql;
			if (bto.getImgName() != null && !bto.getImgName().isEmpty()) {
				sql = "UPDATE play_board SET title=?, content=?, category=?, imgName=?, isEdited=1 WHERE postNo=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, bto.getTitle());
				pstmt.setString(2, bto.getContent());
				pstmt.setInt(3, bto.getCategory());
				pstmt.setString(4, bto.getImgName());
				pstmt.setInt(5, bto.getPostNo());
			} else {
				sql = "UPDATE play_board SET title=?, content=?, category=?, isEdited=1 WHERE postNo=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, bto.getTitle());
				pstmt.setString(2, bto.getContent());
				pstmt.setInt(3, bto.getCategory());
				pstmt.setInt(4, bto.getPostNo());
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
			String sql = "DELETE FROM play_board WHERE postNo = ?";
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
	public ArrayList<PlayBoardDTO> postListSorted(int start, int end, String sort) {
	    ArrayList<PlayBoardDTO> list = new ArrayList<>();
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
	                   + "SELECT * FROM play_board ORDER BY " + orderBy
	                   + ") A) WHERE rnum BETWEEN ? AND ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, start);
	        pstmt.setInt(2, end);
	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	            PlayBoardDTO bto = new PlayBoardDTO();
	            bto.setPostNo(rs.getInt("postNo"));
	            bto.setTitle(rs.getString("title"));
	            bto.setWriter(rs.getString("writer"));
	            bto.setReg(rs.getTimestamp("reg"));
	            bto.setViews(rs.getInt("views"));
	            bto.setCategory(rs.getInt("category"));
	            list.add(bto);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        close(conn, pstmt, rs);
	    }
	    return list;
	}
	
	// 카테고리 + 정렬
	public ArrayList<PlayBoardDTO> postListByCategorySorted(int start, int end, int category, String sort) {
	    ArrayList<PlayBoardDTO> list = new ArrayList<>();
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    String orderBy = "reg DESC"; // 기본: 최신순
	    if ("views".equals(sort)) {
	        orderBy = "views DESC";
	    }

	    try {
	        conn = getConn();
	        String sql = "SELECT * FROM ("
	                   + "  SELECT ROWNUM rnum, A.* FROM ("
	                   + "    SELECT * FROM play_board WHERE category=? ORDER BY " + orderBy
	                   + "  ) A"
	                   + ") WHERE rnum BETWEEN ? AND ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, category);  // 카테고리 조건
	        pstmt.setInt(2, start);
	        pstmt.setInt(3, end);
	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	            PlayBoardDTO bto = new PlayBoardDTO();
	            bto.setPostNo(rs.getInt("postNo"));
	            bto.setCategory(rs.getInt("category"));
	            bto.setTitle(rs.getString("title"));
	            bto.setWriter(rs.getString("writer"));
	            bto.setReg(rs.getTimestamp("reg"));
	            bto.setViews(rs.getInt("views"));
	            list.add(bto);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        close(conn, pstmt, rs);
	    }

	    return list;
	}
	
	// 카테고리별 글개수
	public int postCountByCategory(int category) {
	    int count = 0;
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
	        conn = getConn();
	        String sql = "SELECT COUNT(*) FROM play_board WHERE category=?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, category);
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
	
	// 게시글 목록 검색 + 정렬
	public ArrayList<PlayBoardDTO> searchPostsSorted(String keyword, String sort, int startRow, int endRow, int category) {
	    ArrayList<PlayBoardDTO> list = new ArrayList<>();
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
	        sql.append("    SELECT postNo, title, content, writer, reg, views, category ");
	        sql.append("    FROM play_board ");
	        sql.append("    WHERE (title LIKE ? OR content LIKE ?) ");

	        if (category != -1) {
	            sql.append(" AND category = ? ");
	        }

	        sql.append("    ORDER BY ").append(orderBy);
	        sql.append("  ) A WHERE ROWNUM <= ? ");
	        sql.append(") WHERE rnum >= ?");

	        pstmt = conn.prepareStatement(sql.toString());
	        pstmt.setString(1, "%" + keyword + "%");
	        pstmt.setString(2, "%" + keyword + "%");

	        int paramIndex = 3;
	        if (category != -1) {
	            pstmt.setInt(paramIndex++, category);
	        }
	        pstmt.setInt(paramIndex++, endRow);
	        pstmt.setInt(paramIndex, startRow);

	        rs = pstmt.executeQuery();
	        while (rs.next()) {
	            PlayBoardDTO bto = new PlayBoardDTO();
	            bto.setPostNo(rs.getInt("postNo"));
	            bto.setTitle(rs.getString("title"));
	            bto.setContent(rs.getString("content"));
	            bto.setWriter(rs.getString("writer"));
	            bto.setReg(rs.getTimestamp("reg"));
	            bto.setViews(rs.getInt("views"));
	            bto.setCategory(rs.getInt("category"));
	            list.add(bto);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        close(conn, pstmt, rs);
	    }

	    return list;
	}

	// 게시글 개수 검색
	public int searchPostCount(String keyword, int category) {
	    int count = 0;
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
	        conn = getConn();

	        StringBuilder sql = new StringBuilder();
	        sql.append("SELECT COUNT(*) FROM play_board ");
	        sql.append("WHERE (title LIKE ? OR content LIKE ?) ");

	        if (category != -1) {
	            sql.append("AND category = ?");
	        }

	        pstmt = conn.prepareStatement(sql.toString());
	        pstmt.setString(1, "%" + keyword + "%");
	        pstmt.setString(2, "%" + keyword + "%");

	        if (category != -1) {
	            pstmt.setInt(3, category);
	        }

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

	public ArrayList<PlayBoardDTO> getTop3PostsByViews(int category) {
	    ArrayList<PlayBoardDTO> list = new ArrayList<>();
	    String sql;

	    try {
	        conn = getConn();

	        if (category == -1) {
	            // 전체 게시글 중 조회수 top 3
	            sql = "SELECT * FROM (SELECT * FROM play_board ORDER BY views DESC) WHERE ROWNUM <= 3";
	            pstmt = conn.prepareStatement(sql);
	        } else {
	            // 해당 카테고리 게시글 중 조회수 top 3
	            sql = "SELECT * FROM (SELECT * FROM play_board WHERE category = ? ORDER BY views DESC) WHERE ROWNUM <= 3";
	            pstmt = conn.prepareStatement(sql);
	            pstmt.setInt(1, category);
	        }

	        rs = pstmt.executeQuery();
	        while (rs.next()) {
	            PlayBoardDTO dto = new PlayBoardDTO();
	            dto.setPostNo(rs.getInt("postNo"));
	            dto.setTitle(rs.getString("title"));
	            dto.setWriter(rs.getString("writer"));
	            dto.setReg(rs.getTimestamp("reg"));
	            dto.setViews(rs.getInt("views"));
	            dto.setCategory(rs.getInt("category"));
	            list.add(dto);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        close(conn, pstmt, rs);
	    }

	    return list;
	}
}
