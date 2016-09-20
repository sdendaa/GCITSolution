/**
 * 
 */
package libraryApp.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import libraryApp.data.AuthorDAO;
import libraryApp.data.BookCopiesDAO;
import libraryApp.data.BookDAO;
import libraryApp.data.BookLoanDAO;
import libraryApp.data.BorrowerDAO;
import libraryApp.data.BranchDAO;
import libraryApp.data.ConnectionUtils;
import libraryApp.data.GenreDAO;
import libraryApp.data.PublisherDAO;
import libraryApp.entity.Author;
import libraryApp.entity.Book;
import libraryApp.entity.BookCopies;
import libraryApp.entity.BookLoan;
import libraryApp.entity.Borrower;
import libraryApp.entity.Genre;
import libraryApp.entity.LibBranch;
import libraryApp.entity.Publisher;
import libraryApp.exceptions.LibraryExceptions;

public class AdminstratorService {

	//eagar singleton
	//private static AdminstratorService instance = new AdminstratorService();
	//lazy singleton
	private static AdminstratorService instance = null;

	public static AdminstratorService getIntance(){
		//double locked synchronized check
		if(instance == null){
			synchronized (AdminstratorService.class) {
				if(instance == null){
					instance = new AdminstratorService();
				}
			}

		}
		return instance;
	}
	//Book service start
	public void addBook(Book b) throws Exception{
		new ConnectionUtils();
		Connection myCon = ConnectionUtils.getConnetion();
		BookDAO bDAO = new BookDAO(myCon);

		try {
			if(b == null ||b.getBookTitle() == null|| b.getBookTitle().trim().length()==0){
				throw new LibraryExceptions("Book cannot be null or blank");
			}else if(b.getBookTitle().trim().length() > 45){
				throw new LibraryExceptions("Book title cannot more than 45 chars");
			}
			bDAO.create(b);
			myCon.commit();
		} catch (Exception e) {
			myCon.rollback();
			throw e;
		}finally{
			myCon.close();
		}
	}
	public void updateBook(Book b)throws Exception{
		new ConnectionUtils();
		Connection myCon = ConnectionUtils.getConnetion();
		try {
			if(b != null){
				if(new BookDAO(myCon).read(b) == null){
					myCon.rollback();
					throw new LibraryExceptions("The book doesn't exist");
				}
				if(b.getBookTitle() == null || b.getBookTitle().length() < 0 || b.getBookTitle().length() > 45){
					myCon.rollback();
					throw new Exception("Book title cannot be empty and needs to be less than 45 characters!");
				}
			}

			BookDAO bDAO = new BookDAO(myCon);
			bDAO.update(b);
			myCon.commit();
		} catch (Exception e) {
			myCon.rollback();
			throw e;
		}finally{
			myCon.close();
		}

	}

	public void deleteBook(Book b)throws Exception{
		new ConnectionUtils();
		Connection myCon = ConnectionUtils.getConnetion();
		BookDAO bDAO = new BookDAO(myCon);
		if(b.getBookId()==0){
			throw new LibraryExceptions("This Book doesn't exist in the system to do delete.");
		}
		try {
			bDAO.delete(b);
			myCon.commit();
		} catch (Exception e) {
			myCon.rollback();
			throw e;
		}finally{
			myCon.close();
		}
	}

	private BookCopies getBookCopiesByBranchAndId(Integer branchId, Integer bookId) throws SQLException {
		Connection co = ConnectionUtils.getConnetion();
		BookCopiesDAO bD = new BookCopiesDAO(co);
		LibBranch br = new LibBranch();
		br.setBranchId(branchId);
		Book b = new Book();
		b.setBookId(bookId);
		BookCopies bc = new BookCopies(); 
		bc.setBranch(br);
		bc.setBook(b);
		BookCopies myBook = new BookCopies();
		try {
			
			myBook = bD.read(branchId, bookId);
			co.commit();
		} catch (Exception e) {
			e.printStackTrace();
			co.rollback();
		}finally{
			co.close();
		}

		return myBook;
	}
	//Author service start here
	public void addAuthor(Author author) throws Exception{
		// bizz logic
		new ConnectionUtils();
		Connection myCon = ConnectionUtils.getConnetion();
		AuthorDAO bDAO = new AuthorDAO(myCon);
		try {
			if(author != null){
				if( author.getAuthorName() == null|| author.getAuthorName().length() <= 0
						|| author.getAuthorName().length() > 45){

					throw new LibraryExceptions("Author name cannot be null or more than 45 characters");
				}
				bDAO.create(author);
				myCon.commit();
			}else{
				throw new LibraryExceptions("Author cannot be null or blank");
			}
		} catch (Exception e) {
			myCon.rollback();
			throw e;
		}finally{
			myCon.close();
		}

	}

	public void deleteAuthor(Author a)throws Exception{
		new ConnectionUtils();
		Connection myCon = ConnectionUtils.getConnetion();
		AuthorDAO bDAO = new AuthorDAO(myCon);
		if(a == null ||a.getAuthorName() == null|| a.getAuthorId()==0){
			throw new LibraryExceptions("This Author doesn't exist in the system to do delete.");
		}

		try {
			bDAO.delete(a);
			myCon.commit();
		} catch (Exception e) {
			myCon.rollback();
			throw e;
		}finally{
			myCon.close();
		}
	}

	public void updateAuthor(Author a)throws Exception{
		new ConnectionUtils();
		Connection myCon = ConnectionUtils.getConnetion();
		AuthorDAO bDAO = new AuthorDAO(myCon);
		if(a == null ||a.getAuthorId() == 0 || a.getAuthorName() == null){
			throw new LibraryExceptions("This Author doesn't exist in the system to do update.");
		}

		try {
			bDAO.update(a);
			myCon.commit();
		} catch (Exception e) {
			myCon.rollback();
			throw e;
		}finally{
			myCon.close();
		}

	}
	//Publisher service start here
	public void addPublisher(Publisher p)throws Exception{
		new ConnectionUtils();
		Connection myCon = ConnectionUtils.getConnetion();
		PublisherDAO bDAO = new PublisherDAO(myCon);
		try {
			if(p != null){
				if(p.getPublisherName() == null|| p.getPublisherName().trim().length()== 0){
					throw new LibraryExceptions("publisher name cannot be null or blank");
				}else if(p.getPublisherName().trim().length() > 45){
					throw new LibraryExceptions("publisher name cannot more than 45 chars");
				}
				bDAO.create(p);
				myCon.commit();

			}else{
				throw new LibraryExceptions("publisher cannot be null or blank");	
			}

		} catch (Exception e) {
			myCon.rollback();
			throw e;
		}finally{
			myCon.close();
		}

	}

	public void updatePublisher(Publisher pub)throws Exception{
		new ConnectionUtils();
		Connection myCon = ConnectionUtils.getConnetion();
		PublisherDAO bDAO = new PublisherDAO(myCon);
		if(pub == null ||pub.getPublisherId() == 0 || pub.getPublisherName() == null){
			throw new LibraryExceptions("This Publisher doesn't exist in the system to do update.");
		}

		try {
			bDAO.update(pub);
			myCon.commit();
		} catch (Exception e) {
			myCon.rollback();
			throw e;
		}finally{
			myCon.close();
		}


	}

	public void deletePublisher(Publisher p)throws Exception{
		new ConnectionUtils();
		Connection myCon = ConnectionUtils.getConnetion();
		PublisherDAO bDAO = new PublisherDAO(myCon);
		if(p == null ||p.getPublisherName() == null|| p.getPublisherId()==0){
			throw new LibraryExceptions("This Publisher doesn't exist in the system to do delete.");
		}

		try {
			bDAO.delete(p);
			myCon.commit();
		} catch (Exception e) {
			myCon.rollback();
			throw e;
		}finally{
			myCon.close();
		}

	}
	//Borrower service start here
	public void AddBorrower(Borrower bo)throws Exception{
		new ConnectionUtils();
		Connection myCon = ConnectionUtils.getConnetion();
		BorrowerDAO bDAO = new BorrowerDAO(myCon);
		try {
			if(bo != null){
				if( bo.getBorrowerName() == null|| bo.getBorrowerName().length() <= 0
						|| bo.getBorrowerName().length() > 45){
					throw new LibraryExceptions("Borrower name cannot be null or more than 45 characters");
				}
				bDAO.create(bo);
				myCon.commit();
			}else{
				throw new LibraryExceptions("Borrower cannot be null or blank");
			}
		} catch (Exception e) {
			myCon.rollback();
			throw e;
		}finally{
			myCon.close();
		}

	}

	public void updateBorrower(Borrower bo)throws Exception{
		new ConnectionUtils();
		Connection myCon = ConnectionUtils.getConnetion();
		BorrowerDAO bDAO = new BorrowerDAO(myCon);
		if(bo == null ||bo.getCardNo() == 0){
			throw new LibraryExceptions("borrower or card No cannot be null or blank. ");
		}

		try {
			bDAO.update(bo);
			myCon.commit();
		} catch (Exception e) {
			myCon.rollback();
			throw e;
		}finally{
			myCon.close();
		}

	}

	public void deleteBorrower(Borrower bo)throws Exception{
		new ConnectionUtils();
		Connection myCon = ConnectionUtils.getConnetion();
		BorrowerDAO bDAO = new BorrowerDAO(myCon);
		if(bo == null ||bo.getCardNo() == 0){
			throw new LibraryExceptions("This Borrower doesn't exist in the system to do delete.");
		}

		try {
			bDAO.delete(bo);
			myCon.commit();
		} catch (Exception e) {
			myCon.rollback();
			throw e;
		}finally{
			myCon.close();
		}
	}
	//Branch service start here
	public void AddBranch( LibBranch br)throws Exception{
		new ConnectionUtils();
		Connection myCon = ConnectionUtils.getConnetion();
		BranchDAO bDAO = new BranchDAO(myCon);
		try {
			if(br != null){
				if(br.getBranchName() == null|| br.getBranchName().trim().length()==0){
					throw new LibraryExceptions("Branch name cannot be null or blank");
				}else if(br.getBranchName().trim().length() > 45){
					throw new LibraryExceptions("Branch name cannot more than 45 chars");
				}
				bDAO.create(br);
				myCon.commit();
			}else{
				throw new LibraryExceptions("Branch cannot be null or blank");
			}
		} catch (Exception e) {
			myCon.rollback();
			throw e;
		}finally{
			myCon.close();
		}

	}

	public void updateBranch(LibBranch br)throws Exception{
		new ConnectionUtils();
		Connection myCon = ConnectionUtils.getConnetion();
		BranchDAO bDAO = new BranchDAO(myCon);
		if(br == null ||br.getBranchId() == 0){
			throw new LibraryExceptions("This branch doesn't exist in the system to do update.");
		}

		try {
			bDAO.update(br);
			myCon.commit();
		} catch (Exception e) {
			myCon.rollback();
			throw e;
		}finally{
			myCon.close();
		}
	}

	public void deleteBranch(LibBranch br)throws Exception{
		new ConnectionUtils();
		Connection myCon = ConnectionUtils.getConnetion();
		BranchDAO bDAO = new BranchDAO(myCon);
		if(br == null ||br.getBranchName() == null|| br.getBranchId()==0){
			throw new LibraryExceptions("This Branch doesn't exist in the system to do delete.");
		}

		try {
			bDAO.delete(br);
			myCon.commit();
		} catch (Exception e) {
			myCon.rollback();
			throw e;
		}finally{
			myCon.close();
		}

	}

	//Over ride due date service start here
	public void overRideDueDate(BookLoan lb)throws Exception{
		new ConnectionUtils();
		Connection myCon = ConnectionUtils.getConnetion();
		BookLoanDAO bDAO = new BookLoanDAO(myCon);

		if(lb == null || lb.getBorrower().getCardNo() == 0 ||lb.getBook().getBookId() == 0||
				lb.getBranch().getBranchId() ==0){
			throw new LibraryExceptions("This cardId, branchId or BookId is not right to do update.");
		}
		try {
			bDAO.update(lb);
			myCon.commit();
		} catch (Exception e) {
			myCon.rollback();
			throw e;
		}finally{
			myCon.close();
		}

	}
	public int getPublisherCount() throws SQLException{
		new ConnectionUtils();
		Connection myCon = ConnectionUtils.getConnetion();
		int count = 0;
		try {
			PublisherDAO pDAO = new PublisherDAO(myCon);
			count = pDAO.getCount();
			myCon.commit();
		} catch (Exception e) {
			e.printStackTrace();
			myCon.rollback();
		}finally{
			myCon.close();
		}

		return count;
	}

	public int getAuthorsCount() throws SQLException{
		new ConnectionUtils();
		Connection myCon = ConnectionUtils.getConnetion();
		int count = 0;
		try {
			AuthorDAO bDAO = new AuthorDAO(myCon);
			count = bDAO.getCount();
			myCon.commit();
		} catch (Exception e) {
			e.printStackTrace();
			myCon.rollback();
		}finally{
			myCon.close();
		}

		return count;
	}
	public int getBooksCount() throws SQLException{
		new ConnectionUtils();
		Connection myCon = ConnectionUtils.getConnetion();
		int count = 0;
		try {
			BookDAO bDAO = new BookDAO(myCon);
			count = bDAO.getCount();
			myCon.commit();
		} catch (Exception e) {
			e.printStackTrace();
			myCon.rollback();
		}finally{
			myCon.close();
		}

		return count;
	}
	public int getBookLoanedCount() throws SQLException{
		new ConnectionUtils();
		Connection myCon = ConnectionUtils.getConnetion();
		int count = 0;
		try {
			BookLoanDAO blDAO = new BookLoanDAO(myCon);
			count = blDAO.getCount();
			myCon.commit();
		} catch (Exception e) {
			e.printStackTrace();
			myCon.rollback();
		}finally{
			myCon.close();
		}

		return count;
	}
	public int getBooksCountByBranch(int branchId) throws SQLException{
		new ConnectionUtils();
		Connection myCon = ConnectionUtils.getConnetion();
		int count = 0;
		try {
			BookDAO bDAO = new BookDAO(myCon);
			count = bDAO.getCountByBranch(branchId);
			myCon.commit();
		} catch (Exception e) {
			e.printStackTrace();
			myCon.rollback();
		}finally{
			myCon.close();
		}

		return count;
	}
	public int getBorrowerCount() throws SQLException{
		new ConnectionUtils();
		Connection myCon = ConnectionUtils.getConnetion();
		int count = 0;
		try {
			BorrowerDAO bDAO = new BorrowerDAO(myCon);
			count = bDAO.getCount();
			myCon.commit();
		} catch (Exception e) {
			e.printStackTrace();
			myCon.rollback();
		}finally{
			myCon.close();
		}

		return count;
	}
	public int getBranchsCount() throws SQLException{
		new ConnectionUtils();
		Connection myCon = ConnectionUtils.getConnetion();
		int count = 0;
		try {
			BranchDAO bDAO = new BranchDAO(myCon);
			count = bDAO.getCount();
			myCon.commit();
		} catch (Exception e) {
			e.printStackTrace();
			myCon.rollback();
		}finally{
			myCon.close();
		}

		return count;
	}
	// to get all authors
	public List<Author> getAllAuthors(Integer pageNo, String searchString) throws Exception {
		new ConnectionUtils();
		Connection myCon = ConnectionUtils.getConnetion();
		AuthorDAO aDAO = new AuthorDAO(myCon);
		List<Author> authorsList = null;
		try {
			authorsList = aDAO.readAllAuthors(pageNo, 10, searchString );
			myCon.commit();
		} catch (Exception e) {
			e.printStackTrace();
			myCon.rollback();
		}finally{
			myCon.close();
		}

		return authorsList;

	}
	// to get all publishers
	public List<Publisher> getAllPublishers(int pageNo, String searchString) throws Exception {
		Connection co = ConnectionUtils.getConnetion();
		List<Publisher> pubList =null;
		try{

			PublisherDAO p = new PublisherDAO(co);
			pubList = p.readAllPublisher(pageNo, 10, searchString);
			co.commit();
		}
		catch(Exception e){
			co.rollback();
		}finally{
			co.close();
		}
		return pubList;
	}
	public List<BookLoan> getAllBookLoaned() throws Exception {
		Connection co = ConnectionUtils.getConnetion();
		List<BookLoan> loanList = new ArrayList<BookLoan>();
		try{

			BookLoanDAO bl = new BookLoanDAO(co);
			loanList = bl.getAllBookLoaned();
			co.commit();
		}
		catch(Exception e){
			e.printStackTrace();
			co.rollback();
		}finally{
			co.close();
		}
		
		return loanList;
	}
	
	public List<Genre> getAllGenre() throws Exception{
		Connection co = ConnectionUtils.getConnetion();
		List<Genre> genresList =null;
		try{

			GenreDAO b = new GenreDAO(co);
			genresList = b.getAllGenre();
			co.commit();

		}
		catch(Exception e){
			co.rollback();
		}finally{
			co.close();
		}
		return genresList;
	}
	public List<Book> getAllBooks(Integer pageNo, String searchString) throws SQLException{
		Connection co = ConnectionUtils.getConnetion();
		List<Book> booksList =null;
		try{

			BookDAO b = new BookDAO(co);
			booksList = b.getAllBooksDAO(pageNo, 10, searchString);
			co.commit();

		}
		catch(Exception e){
			co.rollback();
		}finally{
			co.close();
		}
		return booksList;
	}
	public List<LibBranch> getAllBranchs(Integer pageNo, String searchString) throws SQLException{
		Connection co = ConnectionUtils.getConnetion();
		BranchDAO br = new BranchDAO(co);
		List<LibBranch> branchList = new ArrayList<>();
		try {
			branchList = br.readAllBranchDAO(pageNo, 10, searchString);
			co.commit();
		} catch (Exception e) {
			co.rollback();
		}finally {
			co.close();
		}
		return branchList;
	}
	
	public List<LibBranch> getAllBranchsToReturn(int cardNo) throws SQLException{
		Connection co = ConnectionUtils.getConnetion();
		BranchDAO br = new BranchDAO(co);
		List<LibBranch> branchList = new ArrayList<>();
		try {
			branchList = br.readAllBranchToReturnDAO(cardNo);
			co.commit();
		} catch (Exception e) {
			co.rollback();
		}finally {
			co.close();
		}
		return branchList;
	}

	public Author getAuthorById(Integer authorId) throws SQLException{
		Connection co = ConnectionUtils.getConnetion();
		AuthorDAO bD = new AuthorDAO(co);
		Author a = new Author(); 
		Author myAuthor = null;
		try {
			a.setAuthorId(authorId);
			myAuthor = bD.read(a);
			co.commit();
		} catch (Exception e) {
			e.printStackTrace();
			co.rollback();
		}finally{
			co.close();
		}

		return myAuthor;
	}
	public Book getBookById(int bookId) throws SQLException{
		Connection co = ConnectionUtils.getConnetion();
		BookDAO bD = new BookDAO(co);
		Book b = new Book(); 
		Book myBook = null;
		try {
			b.setBookId(bookId);
			myBook = bD.read(b);
			co.commit();
		} catch (Exception e) {
			e.printStackTrace();
			co.rollback();
		}finally{
			co.close();
		}

		return myBook;
	}
	
	public BookLoan getBookLoanById(int branchId, int bookId) throws SQLException{
		Connection co = ConnectionUtils.getConnetion();
		BookLoanDAO bD = new BookLoanDAO(co);
		BookLoan bl = new BookLoan();
		Book b = new Book();
		LibBranch br = new LibBranch();
		BookLoan myLoan = new BookLoan();
		try {
			br.setBranchId(branchId);
			b.setBookId(bookId);
			bl.setBranch(br);
			bl.setBook(b);
			myLoan = bD.read(bl);
			co.commit();
		} catch (Exception e) {
			e.printStackTrace();
			co.rollback();
		}finally{
			co.close();
		}

		return myLoan;
	}
	
	public Borrower getBorrowerById(int borrowerId) throws SQLException{
		Connection co = ConnectionUtils.getConnetion();
		BorrowerDAO bD = new BorrowerDAO(co);
		Borrower bo = new Borrower(); 
		Borrower myBorr = new Borrower();
		try {
			bo.setCardId(borrowerId);;
			myBorr = bD.read(bo);
			co.commit();
		} catch (Exception e) {
			e.printStackTrace();
			co.rollback();
		}finally{
			co.close();
		}

		return myBorr;
	}
	
	public LibBranch getBranchById(int branchId) throws SQLException{
		Connection co = ConnectionUtils.getConnetion();
		BranchDAO bD = new BranchDAO(co);
		LibBranch bo = new LibBranch(); 
		LibBranch myBorr = new LibBranch();
		try {
			bo.setBranchId(branchId);;
			myBorr = bD.read(bo);
			co.commit();
		} catch (Exception e) {
			e.printStackTrace();
			co.rollback();
		}finally{
			co.close();
		}

		return myBorr;
	}
	
	public Publisher getPublisherById(int publisherId) throws SQLException{
		Connection co = ConnectionUtils.getConnetion();
		PublisherDAO bD = new PublisherDAO(co);
		Publisher pu = new Publisher(); 
		Publisher myPub = null;
		try {
			pu.setPublisherId(publisherId);
			myPub = bD.read(pu);
			co.commit();
		} catch (Exception e) {
			e.printStackTrace();
			co.rollback();
		}finally{
			co.close();
		}

		return myPub;
	}

	public List<Borrower> getAllBorrowers(int pageNo, String searchString) throws SQLException {
		Connection co = ConnectionUtils.getConnetion();
		BorrowerDAO boTemp = new BorrowerDAO(co);
		List<Borrower> borrowerList = new ArrayList<>();
		try {
			borrowerList = boTemp.readAllBranchDAO(pageNo, 10, searchString);
			co.commit();
		} catch (Exception e) {
			co.rollback();
		}finally {
			co.close();
		}
		return borrowerList;
	}
	public Integer getAuthorsCountByName(String searchString) throws SQLException {
		AuthorDAO authDao = new AuthorDAO(ConnectionUtils.getConnetion());
		return authDao.getCountByName(searchString);
	}
	public List<Book> getAllBooksByBranch(Integer branchId,Integer pageNo, String searchString)
			throws SQLException {
		Connection co = ConnectionUtils.getConnetion();
		BookDAO boTemp = new BookDAO(co);
		List<Book> bookListBr = new ArrayList<>();
		try {
			bookListBr = boTemp.getAllBooksByBranch(branchId, pageNo, 10, searchString);
			co.commit();
		} catch (Exception e) {
			co.rollback();
		}finally {
			co.close();
		}
		return bookListBr;
	}
	public void udateBookLoan(BookLoan bl) throws Exception {
		new ConnectionUtils();
		Connection myCon = ConnectionUtils.getConnetion();
		try {
			if(bl != null){
				if(new BookLoanDAO(myCon).read(bl) == null){
					myCon.rollback();
					throw new LibraryExceptions("The bookloan doesn't exist");
				}
//				if(bl.getBook().getBookTitle() == null || bl.getBook().getBookTitle().length() < 0
//						|| bl.getBook().getBookTitle().length() > 45){
//					myCon.rollback();
//					throw new Exception("Book title cannot be empty and needs to be less than 45 characters!");
//				}
			}

			BookLoanDAO bDAO = new BookLoanDAO(myCon);
			bDAO.update(bl);
			myCon.commit();
		} catch (Exception e) {
			myCon.rollback();
			throw e;
		}finally{
			myCon.close();
		}
	}
}
