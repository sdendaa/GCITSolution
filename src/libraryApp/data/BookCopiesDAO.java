/**
 * 
 */
package libraryApp.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import libraryApp.entity.Book;
import libraryApp.entity.BookCopies;
import libraryApp.entity.LibBranch;

public class BookCopiesDAO extends BaseDAO<BookCopies> {
	private static final String SELECT_ALL = "select * from tbl_book_copies";
	private static final String SELECT_ONE = "select * from tbl_book_copies where bookId = ? and branchId = ?";
	private static final String SELECT_ID_BY_NAME = "select authorId from tbl_author where authorName = ?";
	private static final String INSERT = "insert into tbl_book_copies(bookId, branchId,noOfCopies)values(?,?,?)";
	private static final String DELETE = "delete from tbl_book_copies where bookId = ? and branchId = ?";
	private static final String UPDATE = "update tbl_book_copies set noOfCopies = noOfCopies + ? where bookId = ? and branchId = ?";
	private static final String GET_COUNT = "select count(*) from tbl_book_copies";
	
	private static final String TABLE_NAME = "tbl_book_copies";
	private static final String KEY_COLUMN = null;

	public BookCopiesDAO(Connection conn) {
		super(conn);
		
	}

	
	@Override
	public void create(BookCopies copy) throws SQLException {
				
		save(INSERT, new Object[]{copy.getBook().getBookId(), copy.getBranch().getBranchId()});
		
	}

	@Override
	public void update(BookCopies copy) throws SQLException{
		save(UPDATE, new Object[]{copy.getNoOfCopies(), copy.getBook().getBookId(), 
				copy.getBranch().getBranchId()});
		
	}

	@Override
	public void delete(BookCopies copy)throws SQLException {
		save(DELETE, new Object[]{copy.getBook().getBookId(), copy.getBranch().getBranchId()});
		
	}
	public Integer getCount() throws SQLException{
		return getCount(GET_COUNT, new Object[]{});
	}

	@Override
	public BookCopies read(BookCopies bc) throws SQLException {
		return null;
	}
	
	public BookCopies read(int pk1, int pk2) throws SQLException {
		return read(SELECT_ONE, new Object[]{pk1, pk2});
	}
	
	@Override
	public List<BookCopies> readAll(String sql, Object[] vals) throws SQLException {
		return readAll(SELECT_ALL, new Object[]{});
	}
	
	@Override
	public List<BookCopies> readResult(ResultSet rs) throws SQLException {
		List<BookCopies> list = new ArrayList<>();
		BookDAO bookTemp = new BookDAO(ConnectionUtils.getConnetion());
		BranchDAO branchTemp = new BranchDAO(ConnectionUtils.getConnetion());
		Book b = new Book();
		LibBranch br = new LibBranch();
		
		while(rs.next()){
			int bookId = rs.getInt("bookId");
			int branchId = rs.getInt("branchId");
			b.setBookId(bookId);
			br.setBranchId(branchId);
			BookCopies bc = new BookCopies();
			bc.setBook(bookTemp.read(b));
			bc.setBranch(branchTemp.read(br));
			bc.setNoOfCopies(rs.getInt("noOfCopies"));
			list.add(bc);
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

}
