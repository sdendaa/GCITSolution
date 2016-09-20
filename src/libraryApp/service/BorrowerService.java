/**
 * 
 */
package libraryApp.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import libraryApp.data.BookCopiesDAO;
import libraryApp.data.BookDAO;
import libraryApp.data.BookLoanDAO;
import libraryApp.data.BorrowerDAO;
import libraryApp.data.BranchDAO;
import libraryApp.data.ConnectionUtils;
import libraryApp.entity.Book;
import libraryApp.entity.BookCopies;
import libraryApp.entity.BookLoan;
import libraryApp.entity.Borrower;
import libraryApp.entity.LibBranch;
import libraryApp.exceptions.LibraryExceptions;


public class BorrowerService {
	
	public void checkOutBook(BookLoan bl)throws Exception{
		new ConnectionUtils();
		Connection myCon = ConnectionUtils.getConnetion();
		//Borrower bor = new Borrower();
		//LibBranch br = new LibBranch();
		//Book b = new Book();
		BookLoanDAO blDAO = new BookLoanDAO(myCon);
		//BookCopiesDAO bcDAO = new BookCopiesDAO(myCon);

		try {
			
//			if(bl == null||bor.getCardNo() == 0){
//				throw new Exception("Please! Enter the valid card number");
//			}
//			
//			if(br == null || br.getBranchId() == 0){
//				throw new LibraryExceptions("branch Id cannot be zero or match any branch");
//			}
//		
//			if(b == null || b.getBookId() == 0){
//				throw new LibraryExceptions("book Id cannot be zero or match any book");
//			}
			

			//if(br.getNoCopies().get(b) > 0){
				
				
//				BookLoan myLoan = new BookLoan();
//				myLoan.setBook(b);
//				myLoan.setBorrower(bor);
//				myLoan.setBranch(br);


				blDAO.create(bl);
//
//				BookCopies bc = new BookCopies();
//				bc.setNoOfCopies(bc.getNoOfCopies() - 1);
				myCon.commit();

		} catch (Exception e) {
			e.printStackTrace();
			myCon.rollback();
		}finally{
			myCon.close();
		}

	}
	
	public LibBranch getLibraryBranchById(int branchId) throws SQLException{
		new ConnectionUtils();
		Connection myCon = ConnectionUtils.getConnetion();
		BranchDAO brDAO = new BranchDAO(myCon);
		LibBranch br = new LibBranch();
		LibBranch myBr = null;
		try {
			br.setBranchId(branchId);
			myBr = brDAO.read(br);
			myCon.commit();
		} catch (Exception e) {
			e.printStackTrace();
			myCon.rollback();
			
		}finally{
			myCon.close();
		}
		return myBr;
	}
	
	public boolean returnBook(Book b, LibBranch br, Borrower bo)throws Exception{
		new ConnectionUtils();
		Connection myCon = ConnectionUtils.getConnetion();
		BookCopiesDAO bcDAO = new BookCopiesDAO(myCon);
		BookLoanDAO blDAO = new BookLoanDAO(myCon);
	
		try {
			BookLoan loan = new BookLoan();
			loan.setBook(b);
			loan.setBranch(br);
			loan.setBorrower(bo);
			blDAO.delete(loan);
			
			BookCopies bc = new BookCopies();
			bc.setBook(b);
			bc.setBranch(br);
			
			int OrigNoOfCopies = 0;
			int newNoOfCopies;
			if(bcDAO.read(bc) == null){
				newNoOfCopies = OrigNoOfCopies +1;
				bc.setNoOfCopies(newNoOfCopies);
				bcDAO.create(bc);
			}else{
				newNoOfCopies = bcDAO.read(bc).getNoOfCopies();
				bcDAO.update(bc);
			}
			
			myCon.commit();
		} catch (Exception e) {
			myCon.rollback();
			throw e;
		}finally{
			myCon.close();
		}
		return true;
	}

	public Borrower getBorrowerById(Integer borrowerId) throws SQLException {
		new ConnectionUtils();
		Connection myCon = ConnectionUtils.getConnetion();
		BorrowerDAO borDAO = new BorrowerDAO(myCon);
//		Borrower bor = new Borrower();
//		bor.setCardId(borrowerId);
		return borDAO.getBorrowerById(borrowerId);
		
	}
	
	public List<Book> getAllBorrowedBooks(Integer branchId, Integer cardNo) throws SQLException{
		new ConnectionUtils();
		Connection myCon = ConnectionUtils.getConnetion();
		BookDAO bDAO = new BookDAO(myCon);
		List<Book> booksList = bDAO.getAllBooksBorrowedByBranch(branchId, cardNo);
		return booksList;
	}
}
