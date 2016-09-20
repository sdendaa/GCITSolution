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
import libraryApp.entity.Publisher;



public class BookDAO extends BaseDAO<Book> {
	private static final String SELECT_ALL = "select * from tbl_book";
	private static final String SELECT_ALL_SEARCH = "select * from tbl_book where title like ?";
	private static final String SELECT_ONE = "select * from tbl_book where bookId = ?";
	private static final String SELECT_ID_BY_NAME = "select authorId from tbl_author where authorName = ?";
	private static final String INSERT = "insert into tbl_book (title, pubId) values (?,?)";
	private static final String DELETE = "delete from tbl_book where bookId = ?";
	private static final String UPDATE = "update tbl_book set title = ? where bookId = ?";
	private static final String GET_COUNT = "select count(*) from tbl_book";
	private static final String SELECT_ALL_BRANCH = "SELECT * FROM librarynew.tbl_book b"
			+ " join tbl_book_copies bl on b.bookId = bl.bookId"
			+ " join tbl_library_branch br on bl.branchId = bl.branchId"
			+ " where br.branchId = ?";
	private static final String SELECT_ALL_SEARCH_BY_BRANCH ="SELECT title FROM tbl_book b"
			+ "join tbl_book_loans bl on b.bookId = bl.bookId"
			+ "join tbl_library_branch br on bl.branchId = bl.branchId"
			+ "where br.branchId = ? and title like ?";
	
	private static final String TABLE_NAME = "tbl_book";
	private static final String KEY_COLUMN = "bookId";
	private static final String SELECT_BORROWED_BOOK = "SELECT * FROM tbl_book b"
			+ " join tbl_book_loans bl on b.bookId = bl.bookId"
			+ " join tbl_library_branch br on bl.branchId = bl.branchId"
			+ " where br.branchId = ? and bl.cardNo = ?";


	public BookDAO(Connection conn) {
		super(conn);
		
	}
	
	@Override
	public void create(Book book) throws SQLException {
		save(INSERT, new Object[]{book.getBookTitle(),  book.getPublisher().getPublisherId()});
		
	}

	@Override
	public void update(Book book) throws SQLException{
		save(UPDATE, new Object[]{book.getBookTitle(), book.getBookId()});
		 
	}

	@Override
	public void delete(Book book) throws SQLException{
		save(DELETE, new Object[]{book.getBookId()});
		
	}

	@Override
	public Book read(Book b) throws SQLException {
		return read(SELECT_ONE, new Object[]{b.getBookId()});
	}
	
	public List<Book> getAllBooksDAO(Integer pageNo, Integer pageSize, String searchString) throws SQLException {
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
	
	public Integer getCount() throws SQLException{
		return getCount(GET_COUNT, new Object[]{});
	}
	
	@Override
	public List<Book> readResult(ResultSet rs) throws SQLException {
		List<Book> list = new ArrayList<>();
		PublisherDAO publTemp = new PublisherDAO(conn);
		
		while(rs.next()){
			Book bo = new Book();
			int publId = rs.getInt("pubId");
			Publisher pu = new Publisher();
			pu.setPublisherId(publId);
			
			bo.setBookId(rs.getInt("bookId"));
			bo.setBookTitle(rs.getString("title"));
			bo.setPublisher(publTemp.read(pu));
			list.add(bo);
		}
		
		return list;
	}

	public Integer getCountByBranch(Integer branchId) throws SQLException{
		return getCount(SELECT_ALL_BRANCH, new Object[]{branchId});
	}

	@Override
	public String getTableName() {
		return TABLE_NAME;
	}

	@Override
	public String getKeyColumn() {
		
		return KEY_COLUMN;
	}

	public List<Book> getAllBooksByBranch(Integer branchId, Integer pageNo,Integer pageSize, String searchString) throws SQLException {
		setPageNo(pageNo);
		setPageSize(pageSize);
		if(searchString !=null && !"".equals(searchString)){
			List<Object> valueList = new ArrayList<Object>();
			searchString = "%"+searchString+"%";
			valueList.add(searchString);
			return readAll(SELECT_ALL_SEARCH_BY_BRANCH, new Object[]{branchId, searchString});
		}else{
			return readAll(SELECT_ALL_BRANCH, new Object[]{branchId});
		}
	}

	public List<Book> getAllBooksBorrowedByBranch(Integer branchId, Integer cardNo) throws SQLException {
		return readAll(SELECT_BORROWED_BOOK, new Object[]{branchId, cardNo});
	}
	
}
