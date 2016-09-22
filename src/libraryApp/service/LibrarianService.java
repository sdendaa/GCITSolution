/**
 * 
 */
package libraryApp.service;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import libraryApp.data.BookCopiesDAO;
import libraryApp.data.BookDAO;
import libraryApp.data.BranchDAO;
import libraryApp.data.ConnectionUtils;
import libraryApp.entity.Book;
import libraryApp.entity.LibBranch;
import libraryApp.exceptions.LibraryExceptions;

public class LibrarianService {
	private static LibrarianService instance = null;

	public static LibrarianService getIntance(){
		//double locked synchronized check
		if(instance == null){
			synchronized (LibrarianService.class) {
				if(instance == null){
					instance = new LibrarianService();
				}
			}
			
		}
		return instance;
	}

	public void updateLibraryDetail(LibBranch br) throws Exception{
		new ConnectionUtils();
		Connection con = ConnectionUtils.getConnetion();
		BranchDAO brD = new BranchDAO(con);
		
		try {
			if(br == null || br.getBranchName() == null ){
				throw new LibraryExceptions("branch cannot be zero or blank");
			}else if(br.getBranchName().length() == 0||br.getBranchName().length()> 45
					||br.getBranchAddress().length()> 45){
				throw new LibraryExceptions("branch name or address cannot be more than 45 chars");
			}else if(brD.read(br) == null){
				throw new LibraryExceptions("This branch doesn't exist");	
			}
			brD.update(br);
			con.commit();
		} catch (Exception e) {
			e.printStackTrace();
			con.rollback();
		}finally{
			con.close();
		}

	}

//	public void AddBookCopies(BookCopies bc) throws Exception{
//		new ConnectionUtils();
//		Connection con = ConnectionUtils.getConnetion();
//		BookDAO bD = new BookDAO(con);
//		BookCopiesDAO bcD = new BookCopiesDAO(con);
//		BranchDAO brD = new BranchDAO(con);
//		LibBranch br = new LibBranch();
//		Book b = new Book();
//		
//		br.setBranchId(bc.getBranch().getBranchId());
//		b.setBookId(bc.getBook().getBookId());
//		try {
//			LibBranch newBr = brD.read(br);
//			Book newB = bD.read(b);
//			if(newBr==null || newB==null){
//				throw new LibraryExceptions("Book or library branch cannot be null or blank.");
//			}else if(bc.getBranch().getBranchId() == 0 || bc.getBook().getBookId() == 0){
//				throw new LibraryExceptions("branch Id or bookId cannot be zero or match any branch");
//			}
//			if(getNoOfCopyByBranch(bc.getBranch().getBranchId(), bc.getBook().getBookId()) == null){
//				bcD.create(bc);
//				con.commit();
//			}else{
//				bcD.update(bc);
//				con.commit();
//			}	
//		} catch (Exception e) {
//			e.printStackTrace();
//			con.rollback();
//		}finally{
//			con.close();
//		}
//
//	}
	public List<Book> getAllBooksFromBranch(Integer branchId, Integer pageNo,String searchString) throws SQLException{
		new ConnectionUtils();
		Connection con = ConnectionUtils.getConnetion();
		List<Book> bookList = new ArrayList<>();
		BookDAO bD = new BookDAO(con);
	try {
		bookList = bD.getAllBooksByBranch(branchId, pageNo, 10, searchString);
		con.commit();
	} catch (Exception e) {
		e.printStackTrace();
		con.rollback();
	}finally{
		con.close();
	}
		return bookList;
	}
	public Integer getNoOfCopyByBranch(Integer branchId, Integer bookId) throws SQLException{
		new ConnectionUtils();
		Connection con = ConnectionUtils.getConnetion();
		BookCopiesDAO bcD = new BookCopiesDAO(con);
		return bcD.getNoCopyByBranch(branchId, bookId);
	}
}