/**
 * 
 */
package libraryApp.data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import libraryApp.entity.Book;
import libraryApp.entity.BookLoan;
import libraryApp.entity.Borrower;
import libraryApp.entity.LibBranch;

public class BookLoanDAO extends BaseDAO<BookLoan> {
	private static final String SELECT_ALL = "select * from librarynew.tbl_book_loans";
	private static final String SELECT_ALL_SEARCH = "select * from tbl_book_loans where borrowerName like ?";
	private static final String SELECT_ONE = "select * from tbl_book_loans where branchId = ? and bookId = ?";
	private static final String SELECT_ID_BY_NAME = "select authorId from tbl_author where authorName = ?";
	private static final String INSERT = "insert into tbl_book_loans(bookId, branchId,cardNo,dateOut, dueDate,dateIn)values(?,?,?,CURDATE(), DATE_ADD(curdate(), INTERVAL 7 DAY), null)";
	private static final String DELETE = "delete from tbl_book_loans where bookId = ? and branchId = ?";
	private static final String UPDATE = "update tbl_book_loans set dateOut = ?, dueDate = ?, dateIn = ? where bookId = ? and branchId = ?";
	private static final String GET_COUNT = "select count(*) from tbl_book_loans";
	private static final String SELECT_All_From_Borrower ="SELECT title FROM tbl_book b"
			+ "join tbl_book_loans bl on b.bookId = bl.bookId"
			+ "join tbl_borrower bo on bo.cardNo = bl.cardNo"
			+ "where bl.branchId = ? and bo.cardNo = ? ";

	private static final String TABLE_NAME = "tbl_book_loans";
	private static final String KEY_COLUMN = null;
	private static final String UPDATE_RETURN = "update tbl_book_loans set dateOut = ?, dueDate = ?, dateIn = CURDATE() where bookId = ? and branchId = ?";

	public BookLoanDAO(Connection conn) {
		super(conn);

	}

	@Override
	public void create(BookLoan loan) throws SQLException {
		save(INSERT, new Object[]{loan.getBook().getBookId(),loan.getBranch().getBranchId(), 
				loan.getBorrower().getCardNo()});

	}

	@Override
	public void update(BookLoan loan) throws SQLException {
		save(UPDATE, new Object[]{loan.getCheckOutDate(),loan.getDueDate(),loan.getCheckInDate(), loan.getBook().getBookId(), loan.getBranch().getBranchId()});

	}
	public void updateReturn(BookLoan loan) throws SQLException {
		save(UPDATE_RETURN, new Object[]{loan.getCheckOutDate(),loan.getDueDate(), loan.getBook().getBookId(), loan.getBranch().getBranchId()});

	}

	@Override
	public void delete(BookLoan loan) throws SQLException {
		save(DELETE, new Object[]{loan.getBook().getBookId(), loan.getBranch().getBranchId()});

	}

	@Override
	public BookLoan read(BookLoan bl) throws SQLException{
		return read(SELECT_ONE, new Object[]{bl.getBranch().getBranchId(), bl.getBook().getBookId()});

	}

	public Integer getCount() throws SQLException{
		return getCount(GET_COUNT, new Object[]{});
	}

	@Override
	public List<BookLoan> readResult(ResultSet rs) throws SQLException {
		List<BookLoan> list = new ArrayList<>();
		BookDAO bookTemp = new BookDAO(conn);
		BranchDAO branchTemp = new BranchDAO(conn);
		BorrowerDAO borrowerTemp = new BorrowerDAO(conn);
		while(rs.next()){
			int branchId = rs.getInt("branchId");
			int bookId = rs.getInt("bookId");
			int cardId = rs.getInt("cardNo");
			Date dateOut = rs.getDate("dateOut");
			Date dateIn = rs.getDate("dateIn");
			Date dueDate = rs.getDate("dueDate");
			Book b = new Book();
			b.setBookId(bookId);
			LibBranch br = new LibBranch();
			br.setBranchId(branchId);
			Borrower bor = new Borrower();
			bor.setCardId(cardId);

			BookLoan bl = new BookLoan();
			Book bTemp = bookTemp.read(b);
			LibBranch brTemp = branchTemp.read(br);
			Borrower borTemp = borrowerTemp.read(bor);
			bl.setCheckOutDate(dateOut);
			bl.setCheckInDate(dateIn);
			bl.setDueDate(dueDate);
			bl.setBook(bTemp);
			bl.setBranch(brTemp);
			bl.setBorrower(borTemp);
			
			list.add(bl);
		}

		return list;
	}


	public List<BookLoan> getAllBooksFromBorrowerByBranch(Integer branchId,Integer cardNo) throws SQLException{
		return readAll(SELECT_All_From_Borrower, new Object[]{branchId, cardNo});	
	}

	@Override
	public String getTableName() {
		return TABLE_NAME;
	}


	@Override
	public String getKeyColumn() {
		return KEY_COLUMN;
	}
	public List<BookLoan> readAllLoan(String sql, Object[] vals) throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = makeStatement(sql, vals);
			rs = stmt.executeQuery();
			return  readResult(rs);
		} finally {
			if(rs!=null)rs.close();
			if(stmt!=null)stmt.close();
		}	

	}

	public List<BookLoan> getAllBookLoaned() throws SQLException {
		return readAllLoan(SELECT_ALL, new Object[]{});
	}
}
