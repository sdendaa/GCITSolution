/**
 * 
 */
package libraryApp.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import libraryApp.entity.LibBranch;


public class BranchDAO extends BaseDAO<LibBranch> {
	private static final String SELECT_ALL = "select * from tbl_library_branch";
	private static final String SELECT_ALL_SEARCH = "select * from tbl_library_branch where branchName like ?";
	private static final String SELECT_ONE = "select * from tbl_library_branch where branchId = ?";
	private static final String SELECT_ID_BY_NAME = "select authorId from tbl_author where authorName = ?";
	private static final String INSERT = "insert into tbl_library_branch(branchName, branchAddress)values(?,?)";
	private static final String DELETE = "delete from tbl_library_branch where branchId = ?";
	private static final String UPDATE = "update tbl_library_branch set branchName = ?, branchAddress = ? where branchId = ?";
	private static final String GET_COUNT = "select count(*) from tbl_library_branch";
	
	private static final String TABLE_NAME = "tbl_library_branch";
	private static final String KEY_COLUMN = "branchId";
	private static final String SELECT_ALL_RETURN = "SELECT * FROM tbl_library_branch lb"
			+ " join tbl_book_loans bl on bl.branchId = lb.branchId"
			+ " join tbl_borrower b on b.cardNo = bl.cardNo"
			+ " where b.cardNo = ? and bl.dateIn = null";
	
	public BranchDAO(Connection conn) {
		super(conn);
		
	}

	
	@Override
	public void create(LibBranch branch) throws SQLException {
		save(INSERT, new Object[]{branch.getBranchName(), branch.getBranchAddress()});
		
	}

	
	@Override
	public void update(LibBranch branch)throws SQLException  {
		save(UPDATE, new Object[]{branch.getBranchName(),branch.getBranchAddress(), branch.getBranchId()});
		
	}

	
	@Override
	public void delete(LibBranch branch) throws SQLException {
		save(DELETE, new Object[]{branch.getBranchId()});
		
	}

	@Override
	public LibBranch read(LibBranch br) throws SQLException {
		return read(SELECT_ONE, new Object[]{br.getBranchId()});
	}

	
	public List<LibBranch> readAllBranchDAO(Integer pageNo, Integer pageSize, String searchString) throws SQLException {
		setPageNo(pageNo);
		setPageSize(pageSize);
		
		if(searchString !=null && !"".equals(searchString)){
			List<Object> valueList = new ArrayList<Object>();
			searchString = "%"+searchString+"%";
			valueList.add(searchString);
			return readAll(SELECT_ALL_SEARCH, new Object[]{searchString});
		}else{
			return readAll(SELECT_ALL, new Object[]{});
		}
	}
	public List<LibBranch> readAllBranchToReturnDAO(int cardNo) throws SQLException {
			return readAll(SELECT_ALL_RETURN, new Object[]{cardNo});
		
	}
	@Override
	public List<LibBranch> readResult(ResultSet rs) throws SQLException {
		List<LibBranch> list = new ArrayList<>();
		while(rs.next()){
			LibBranch br = new LibBranch();
			br.setBranchId(rs.getInt("branchId"));
			br.setBranchName(rs.getString("branchName"));
			br.setBranchAddress(rs.getString("branchAddress"));
			
			list.add(br);
		}
		
		return list;
	}
	
	public Integer getCount() throws SQLException{
		return getCount(GET_COUNT, new Object[]{});
	}


	@Override
	public String getTableName() {
		
		return TABLE_NAME;
	}


	@Override
	public String getKeyColumn() {
		
		return KEY_COLUMN;
	}

}
