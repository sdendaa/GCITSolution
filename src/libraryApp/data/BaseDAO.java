/**
 * 
 */
package libraryApp.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.mysql.jdbc.Statement;

import libraryApp.entity.BaseEntity;


@SuppressWarnings("hiding")
public abstract class BaseDAO<BaseEntity> {
	protected Connection conn = null;
	private Integer pageNo;
	private Integer pageSize;
	public BaseDAO(Connection conn){
		this.conn = conn;
	}

	public abstract void create(BaseEntity entity) throws SQLException;
	public abstract void update(BaseEntity entity) throws SQLException;
	public abstract void delete(BaseEntity entity) throws SQLException;
	public abstract BaseEntity read(BaseEntity entity) throws SQLException;
	public abstract String getTableName();
	public abstract String getKeyColumn();
	public abstract List<BaseEntity> readResult(ResultSet rs) throws SQLException;


	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	protected void save(String sql,Object[] vals) throws SQLException {
		PreparedStatement stmt = makeStatement(sql, vals);
		stmt.executeUpdate();
	}

	protected Integer saveAndGetId(String sql,Object[] vals) throws SQLException {
		PreparedStatement stmt= conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		ResultSet rs=null;
		int lastInsertId=-1;
		int idx = 1;
		for(Object o : vals) {
			stmt.setObject(idx, o);
			idx++;
		}
		stmt.executeUpdate();
		rs=stmt.getGeneratedKeys();

		if (rs.next()) {
			lastInsertId = rs.getInt(1);
		} 
		return lastInsertId;
	}

	public BaseEntity read(String sql, Object[] vals) throws SQLException{
		PreparedStatement stmt = makeStatement(sql, vals);
		ResultSet rs = stmt.executeQuery();
		List<BaseEntity> belist=readResult(rs);
		if(belist.isEmpty()){
			return null;
		}else{
			return  belist.get(0);
		}

	}

	public List<BaseEntity> readAll(String sql, Object[] vals) throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			pageNo = getPageNo();
			pageSize = getPageSize();
			if(pageNo != null){
				if(pageNo >0){
					int index = (pageNo-1) * pageSize;
					sql+=" LIMIT "+index+" , "+pageSize;
				}
			}
			stmt = makeStatement(sql, vals);
			rs = stmt.executeQuery();
			return  readResult(rs);
		} finally {
			if(rs!=null)rs.close();
			if(stmt!=null)stmt.close();
		}	

	}
	public Integer getCount(String query, Object[] values) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			pstmt = makeStatement(query, values);
			rs = pstmt.executeQuery();
			while(rs.next()){
				return rs.getInt(1);
			}
		}finally {
			if(rs != null) rs.close();
			if(pstmt != null) pstmt.close();
		}
		return -1;
	}

	public PreparedStatement makeStatement(String sql, Object[] vals) throws SQLException{
		PreparedStatement stmt = conn.prepareStatement(sql);
		int indx = 1;
		for(Object o: vals){
			stmt.setObject(indx, o);
			indx++;
		}
		return stmt;

	}

}
