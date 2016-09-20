/**
 * 
 */
package libraryApp.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import libraryApp.entity.Borrower;

public class BorrowerDAO extends BaseDAO<Borrower> {
	private static final String SELECT_ALL = "select * from tbl_borrower";
	private static final String SELECT_ALL_SEARCH = "select * from tbl_borrower where name like ?";
	private static final String SELECT_ONE = "select * from tbl_borrower where cardNo = ?";
	private static final String SELECT_ID_BY_NAME = "select authorId from tbl_author where authorName = ?";
	private static final String INSERT = "insert into tbl_borrower(name, address, phone)values(?,?,?)";
	private static final String DELETE = "delete from tbl_borrower where cardNo = ?";
	private static final String UPDATE = "update tbl_borrower set name = ?,address = ?, phone = ? where cardNo = ?";
	private static final String GET_COUNT = "select count(*) from tbl_borrower";
	
	private static final String TABLE_NAME = "tbl_borrower";
	private static final String KEY_COLUMN = "cardNo";
	public BorrowerDAO(Connection conn) {
		super(conn);
		
	}

	@Override
	public void create(Borrower bro) throws SQLException {
		save(INSERT, new Object[]{ bro.getBorrowerName(), bro.getBorrowerAddress(), bro.getBorrowerPhone()});
		
	}

	@Override
	public void update(Borrower bro) throws SQLException{
		save(UPDATE, new Object[]{bro.getBorrowerName(),bro.getBorrowerAddress(), bro.getBorrowerPhone(), bro.getCardNo()});
		
	}

	
	@Override
	public void delete(Borrower bro)throws SQLException {
		save(DELETE, new Object[]{bro.getCardNo()});
		
	}

	@Override
	public Borrower read(Borrower bor) throws SQLException {
		 return read(SELECT_ONE, new Object[]{bor.getCardNo()});
	}
	public Borrower getBorrowerById(Integer cardNo) throws SQLException {
		return read(SELECT_ONE, new Object[]{cardNo});
	}
	
	public List<Borrower> readAllBranchDAO(int pageNo, int pageSize, String searchString) throws SQLException {
		setPageNo(pageNo);
		setPageSize(pageSize);
		
		if(searchString != null && !"".equals(searchString)){
			List<Object> valueList = new ArrayList<Object>();
			searchString = "%"+searchString+"%";
			valueList.add(searchString);
			return readAll(SELECT_ALL_SEARCH, new Object[]{searchString});
		}else{
			return readAll(SELECT_ALL, new Object[]{});
		}
	}
	
	@Override
	public List<Borrower> readResult(ResultSet rs) throws SQLException {
		List<Borrower> list = new ArrayList<>();
		while(rs.next()){
			Borrower bo = new Borrower();
			bo.setCardId(Integer.parseInt(rs.getString("cardNo")));
			bo.setBorrowerName(rs.getString("name"));
			bo.setBorrowerAddress(rs.getString("address"));
			bo.setBorrowerPhone(rs.getString("phone"));
			
			list.add(bo);
		}
		
		return list;
	}

	@Override
	public String getTableName() {
		
		return TABLE_NAME;
	}

	@Override
	public String getKeyColumn() {
		
		return KEY_COLUMN;
	}

	public Integer getCount() throws SQLException{
		return getCount(GET_COUNT, new Object[]{});
	}

}
