package libraryApp.cleint;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import libraryApp.entity.Author;
import libraryApp.entity.Book;
import libraryApp.entity.BookCopies;
import libraryApp.entity.BookLoan;
import libraryApp.entity.Borrower;
import libraryApp.entity.LibBranch;
import libraryApp.entity.Publisher;
import libraryApp.service.AdminstratorService;
import libraryApp.service.LibrarianService;

/**
 * Servlet implementation class AdministratorServlet
 */
@WebServlet(name="AdministratorServlet", 
urlPatterns={"/addAuthor", "/addBranch", "/updateBook", "/searchAuthors","/pageAuthors","/pageBooks","/editBook",
		"/pageBranchs","/pageBorrowers","/searchBranchs","/editBorrower","/deleteBook","/deleteBranch",
		"/searchBorrowers","/editAuthor","/editPublisher","/editBranch","/deleteAuthor","/deleteBorrower",
		"/showAllPublishers","/showAllBorrowers","/deletePublisher","/editBookLoan",
		"/searchBooks", "/searchPublishers", "/pagePublishers","/addNoCopiesAdm",
		"/updateAuthor", "/addPublisher","/addBorrower", "/showAllBooks","/showAllBranchs", "/addBook"})
public class AdministratorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdministratorServlet() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String function = request.getServletPath();
		String forwardPage = "/index.jsp";
		Boolean ajax = false;
		try {
			switch (function) {
			case "/pageAuthors": {
				AdminstratorService service = new AdminstratorService();
				Integer totalCount = 0;
				Integer pageNo= Integer.parseInt(request.getParameter("pageNo"));
				StringBuffer str = new StringBuffer();
				String searchString = request.getParameter("searchString");
				if(searchString==null || searchString.length()<0){
					searchString = null;		
				}
				totalCount = service.getAuthorsCountByName(searchString);
				Integer pageSize = 10;
				Integer pageCount = 0;
				if (totalCount % pageSize > 0) {
					pageCount = totalCount / pageSize + 1;
				} else {
					pageCount = totalCount / pageSize;
				}
				str.append("<ul class='pagination'>");
				for (int i = 1; i <= pageCount; i++) {
					str.append("<li><a href='javascript:search("+i+")'>"+i+"</a></li>");
				}
				str.append("</ul>");
				response.getWriter().append(str);
				ajax = true;

				break;
			}
			case "/pageBooks": {
				List<Book> books = new ArrayList<Book>();
				Integer pageNo= Integer.parseInt(request.getParameter("pageNo"));
				books = AdminstratorService.getIntance().getAllBooks(pageNo, null);
				request.setAttribute("books", books);
				forwardPage = "/showAllBooks.jsp";
				break;
			}
			case "/pageBranchs": {
				List<LibBranch> branchs = new ArrayList<LibBranch>();
				Integer pageNo= Integer.parseInt(request.getParameter("pageNo"));
				branchs = AdminstratorService.getIntance().getAllBranchs(pageNo, null);
				request.setAttribute("branchs", branchs);
				forwardPage = "/showAllBranchs.jsp";
				break;
			}
			case "/pageBorrowers": {
				List<Borrower> borrowers = new ArrayList<Borrower>();
				Integer pageNo= Integer.parseInt(request.getParameter("pageNo"));
				borrowers = AdminstratorService.getIntance().getAllBorrowers(pageNo, null);
				request.setAttribute("borrowers", borrowers);
				forwardPage = "/showAllBorrowers.jsp";
				break;
			}
			case "/pagePublishers": {
				List<Publisher> pubList = new ArrayList<Publisher>();
				Integer pageNo= Integer.parseInt(request.getParameter("pageNo"));
				pubList = AdminstratorService.getIntance().getAllPublishers(pageNo, null);
				request.setAttribute("pubList", pubList);
				forwardPage = "/showAllPublishers.jsp";
				break;
			}
			case "/searchAuthors": {
				searchAuthors(request, response);
				ajax = true;
				break;
			}

			default:
				doPost(request,response);
				break;
			}
		} catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("result", "Operation Failed! " + e.getMessage());
		}

		if(!ajax){
			RequestDispatcher rd = getServletContext().getRequestDispatcher(forwardPage);
			rd.forward(request, response);
		}
		//		
		//		RequestDispatcher rd = getServletContext().getRequestDispatcher(forwardPage);
		//		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String function = request.getServletPath();
		String forwardPage = "/index.jsp";

		try {
			switch (function) {
			case "/addAuthor": {
				addAuthor(request);
				request.setAttribute("result", "Author Added Succesfully!");
				forwardPage = "/showAllAuthors.jsp";
				break;
			}		
			case "/addNoCopiesAdm": {
				addNoCopiesAdm(request);
				forwardPage = "/showAllBookByBranch.jsp";
				break;
			}

			case "/addPublisher": {
				addPublisher(request);
				forwardPage = "/showAllPublishers.jsp";
				break;
			}
			case "/deletePublisher": {
				deletePublisher(request);
				forwardPage = "/showAllPublishers.jsp";
				request.setAttribute("result", "Author deleted Succesfully!");
				break;
			}
			case "/deleteBorrower": {
				deleteBorrower(request);
				forwardPage = "/showAllBorrowers.jsp";
				request.setAttribute("result", "Author deleted Succesfully!");
				break;
			}

			case "/deleteBranch": {
				deleteBranch(request);
				forwardPage = "/showAllBranchs.jsp";
				request.setAttribute("result", "Author deleted Succesfully!");
				break;
			}
			case "/deleteBook": {
				deleteBook(request);
				forwardPage = "/showAllBooks.jsp";
				request.setAttribute("result", "Author deleted Succesfully!");
				break;
			}

			case "/deleteAuthor": {
				deleteAuthor(request);
				forwardPage = "/showAllAuthors.jsp";
				request.setAttribute("result", "Author deleted Succesfully!");
				break;
			}

			case "/addBook": {
				addBook(request);
				forwardPage = "/addNoCopiesAdm.jsp";
				request.setAttribute("result", "Book Added Succesfully!");
				break;

			}
			case "/addBorrower":{
				addBorrower(request);
				forwardPage = "/showAllBorrowers.jsp";
				request.setAttribute("result", "Borrower Added Succesfully!");
				break;
			}
			case "/updateBook": {
				updateBook(request);
				request.setAttribute("result", "Book updated Succesfully!");
				break;
			}
			case "/editBookLoan": {
				editBookLoan(request);
				forwardPage = "/showAllBookLoaned.jsp";
				break;
			}
			case "/editBranch": {
				editBranch(request);
				forwardPage = "/showAllBranchs.jsp";
				break;
			}

			case "/editBorrower": {
				editBorrower(request);
				forwardPage = "/showAllBorrowers.jsp";
				break;
			}

			case "/editBook": {
				editBook(request);
				forwardPage = "/showAllBooks.jsp";
				break;
			}

			case "/editPublisher": {
				editPublisher(request);
				forwardPage = "/showAllPublishers.jsp";
				break;
			}
			case "/editAuthor": {
				editAuthors(request);
				forwardPage = "/showAllAuthors.jsp";
				break;
			}
			case "/searchBooks": {
				searchBooks(request);
				forwardPage = "/showAllBooks.jsp";
				break;
			}
			case "/searchBranchs": {
				searchBranchs(request);
				forwardPage = "/showAllBranchs.jsp";
				break;
			}
			case "/searchBorrowers": {
				searchBorrowers(request);
				forwardPage = "/showAllBorrowers.jsp";
				break;
			}
			case "/searchPublishers": {
				searchPublishers(request);
				forwardPage = "/showAllPublishers.jsp";
				break;
			}
			case "/updateAuthor": {
				updateAuthor(request);
				request.setAttribute("result", "Book updated Succesfully!");
				break;
			}
			case "/addBranch": {
				addBranch(request);
				forwardPage = "/showAllBranchs.jsp";
				request.setAttribute("result", "Branch Added Succesfully!");
				break;
			}

			case "/showAllBooks": {
				showAllBooks(request);
				forwardPage = "/showAllBooks.jsp";
				break;
			}
			case "/showAllBranchs": {
				showAllBranchs(request);
				forwardPage = "/showAllBranchs.jsp";
				break;
			}

			case "/showAllPublishers": {
				showAllBranchs(request);
				forwardPage = "/showAllPublishers.jsp";
				break;
			}
			case "/showAllBorrowers": {
				showAllBranchs(request);
				forwardPage = "/showAllBorrowers.jsp";
				break;
			}

			default:
				break;
			}
		} catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("result", "Operation Failed! " + e.getMessage());
		}
		RequestDispatcher rd = getServletContext().getRequestDispatcher(forwardPage);

		rd.forward(request, response);
	}
	private void addNoCopiesAdm(HttpServletRequest request) throws Exception {
		String branchId = request.getParameter("branchId");
		String bookId = request.getParameter("bookId");
		String noCopy = request.getParameter("noOfCopies");
		
		LibBranch br = new LibBranch();
		br.setBranchId(Integer.parseInt(branchId));
		Book b = new Book();
		b.setBookId(Integer.parseInt(bookId));
		b.setBranch(br);
		
		BookCopies bc = new BookCopies();
		bc.setNoOfCopies(Integer.parseInt(noCopy));
		bc.setBook(b);
		bc.setBranch(br);
	
		AdminstratorService.getIntance().addNoCopies(bc);
	}

	private void editBookLoan(HttpServletRequest request) throws Exception {
		String dateOut = request.getParameter("dateOut");
		String dueDate = request.getParameter("dueDate");
//		String dateIn = request.getParameter("dateIn");
		Integer bookId = Integer.parseInt(request.getParameter("bookId"));
		Integer branchId = Integer.parseInt(request.getParameter("branchId"));

		BookLoan bl = new BookLoan();
		Book b = new Book();
		b.setBookId(bookId);
		LibBranch br = new LibBranch();
		br.setBranchId(branchId);
		bl.setBook(b);
		bl.setBranch(br);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date parsedDateOut = formatter.parse(dateOut);
		java.util.Date parsedDueDate = formatter.parse(dueDate);
//		java.util.Date parsedDateIn = formatter.parse(dateIn);
		Date dateO = new Date(parsedDateOut.getTime());
		Date dDate = new Date(parsedDueDate.getTime());
//		Date dateI = new Date(parsedDateIn.getTime());
		
		bl.setCheckOutDate(dateO);
		bl.setDueDate(dDate);
//		bl.setCheckInDate(dateI);

		AdminstratorService.getIntance().udateBookLoan(bl);
	}

	private void deletePublisher(HttpServletRequest request) throws Exception {
		String pubId = request.getParameter("publisherId");
		String pubName = request.getParameter("publisherName");
		String pubAddress = request.getParameter("publisherAddress");
		String pubPhone = request.getParameter("publisherPhone");
		Publisher p = new Publisher();
		p.setPublisherId(Integer.parseInt(pubId));
		p.setPublisherName(pubName);
		p.setPublisherAddress(pubAddress);
		p.setPublisherPhone(pubPhone);
		AdminstratorService.getIntance().deletePublisher(p);

	}

	private void deleteBorrower(HttpServletRequest request) throws Exception {
		String cardNo = request.getParameter("cardNo");
		String borrowerName = request.getParameter("name");
		String borrowerAddress = request.getParameter("address");
		String borrowerPhone = request.getParameter("phone");
		Borrower bo = new Borrower();
		bo.setCardId(Integer.parseInt(cardNo));
		bo.setBorrowerName(borrowerName);
		bo.setBorrowerAddress(borrowerAddress);
		bo.setBorrowerPhone(borrowerPhone);
		AdminstratorService.getIntance().deleteBorrower(bo);

	}

	private void deleteBranch(HttpServletRequest request) throws Exception {
		String branchName = request.getParameter("branchName");
		String branchId = request.getParameter("branchId");
		String branchAddress = request.getParameter("branchAddress");
		LibBranch br = new LibBranch();
		br.setBranchAddress(branchAddress);
		br.setBranchId(Integer.parseInt(branchId));
		br.setBranchName(branchName);
		AdminstratorService.getIntance().deleteBranch(br);

	}

	private void deleteBook(HttpServletRequest request) throws Exception {
		String bookTitle = request.getParameter("title");
		String bookId = request.getParameter("bookId");
		Book b=new Book();
		b.setTitle(bookTitle);
		b.setBookId(Integer.parseInt(bookId));
		AdminstratorService.getIntance().deleteBook(b);

	}

	private void deleteAuthor(HttpServletRequest request) throws Exception {
		String authorName = request.getParameter("authorName");
		String authorId = request.getParameter("authorId");
		Author auth=new Author();
		auth.setAuthorName(authorName);
		auth.setAuthorId(Integer.parseInt(authorId));
		AdminstratorService.getIntance().deleteAuthor(auth);

	}

	public void editBranch(HttpServletRequest request) throws Exception {
		String branchId = request.getParameter("branchId");
		String branchName = request.getParameter("branchName");
		String branchAddress = request.getParameter("branchAddress");
		LibBranch br = new LibBranch();
		br.setBranchId(Integer.parseInt(branchId));
		br.setBranchName(branchName);
		br.setBranchAddress(branchAddress);
		AdminstratorService.getIntance().updateBranch(br);

	}

	private void editBorrower(HttpServletRequest request) throws Exception {
		String cardId = request.getParameter("cardNo");
		String name = request.getParameter("name");
		String address = request.getParameter("address");
		String phone = request.getParameter("phone");
		Borrower bo = new Borrower();
		bo.setCardId(Integer.parseInt(cardId));
		bo.setBorrowerName(name);
		bo.setBorrowerAddress(address);
		bo.setBorrowerPhone(phone);
		AdminstratorService.getIntance().updateBorrower(bo);

	}

	private void editBook(HttpServletRequest request) throws Exception {
		String bookId = request.getParameter("bookId");
		String bookTitle = request.getParameter("title");
		Book  b = new Book();
		b.setBookId(Integer.parseInt(bookId));
		b.setTitle(bookTitle);
		AdminstratorService.getIntance().updateBook(b);

	}

	private void editPublisher(HttpServletRequest request) throws Exception {

		String publisherName = request.getParameter("publisherName");
		String publisherAddress = request.getParameter("publisherAddress");
		String publisherPhone = request.getParameter("publisherPhone");
		String publisherId = request.getParameter("publisherId");
		Publisher pub = new Publisher();
		pub.setPublisherId(Integer.parseInt(publisherId));
		pub.setPublisherName(publisherName);
		pub.setPublisherAddress(publisherAddress);
		pub.setPublisherPhone(publisherPhone);
		AdminstratorService.getIntance().updatePublisher(pub);	

	}

	private void editAuthors(HttpServletRequest request) throws Exception {
		String authorName = request.getParameter("authorName");
		String authorId = request.getParameter("authorId");
		Author auth=new Author();
		auth.setAuthorName(authorName);
		auth.setAuthorId(Integer.parseInt(authorId));
		AdminstratorService.getIntance().updateAuthor(auth);

	}

	private void searchPublishers(HttpServletRequest request) throws Exception {
		String searchString = request.getParameter("searchString");
		request.setAttribute("pubList", AdminstratorService.getIntance().getAllPublishers(1, searchString));

	}

	private void searchBorrowers(HttpServletRequest request)throws Exception {
		String searchString = request.getParameter("searchString");
		request.setAttribute("borrowers", AdminstratorService.getIntance().getAllBorrowers(1, searchString));

	}

	public void searchBranchs(HttpServletRequest request) throws Exception{
		String searchString = request.getParameter("searchString");
		request.setAttribute("branchs", AdminstratorService.getIntance().getAllBranchs(1, searchString));

	}

	private void searchBooks(HttpServletRequest request) throws Exception {
		String searchString = request.getParameter("searchString");
		request.setAttribute("books", AdminstratorService.getIntance().getAllBooks(1, searchString));

	}

	public void showAllBranchs(HttpServletRequest request) throws SQLException {
		List<LibBranch> branchs = new ArrayList<>();
		int pageNo = Integer.parseInt(request.getParameter("pageNo"));
		branchs = AdminstratorService.getIntance().getAllBranchs(pageNo, null);
		if(branchs != null){
			request.setAttribute("branchs", branchs);
		}

	}
	private void showAllBorrowers(HttpServletRequest request) throws SQLException {
		List<Borrower> borrowers = new ArrayList<>();
		int pageNo = Integer.parseInt(request.getParameter("pageNo"));
		borrowers = AdminstratorService.getIntance().getAllBorrowers(pageNo, null);
		if(borrowers != null){
			request.setAttribute("borrowers", borrowers);
		}

	}

	private void searchAuthors(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String searchString = request.getParameter("searchString");
		Integer pageNo = Integer.parseInt(request.getParameter("pageNo"));
		AdminstratorService admService = new AdminstratorService();
		if(searchString==null || searchString.length()<0){
			searchString = null;		
		}
		List<Author> authors = new ArrayList<Author>();
		authors = admService.getAllAuthors(pageNo, searchString);
		request.setAttribute("authors", authors);

		StringBuffer str = new StringBuffer();
		str.append("<tr><th>#</th><th>Author Name</th><th>Edit</th><th>Delete</th></tr>");
		for(Author a: authors){
			str.append("<tr><td>"+authors.indexOf(a) + 1+"</td>");
			str.append("<td>"+a.getAuthorName()+"</td>");
			str.append("<td><button class='btn btn-sm btn-success' data-toggle='modal' data-target='#myModal1' href='editAuthor.jsp?authorId="+a.getAuthorId()+"'>Edit</button>");
			str.append("<td><button class='btn btn-sm btn-danger'>Delete</button></tr>");
		}
		response.getWriter().append(str);		
	}

	private void updateAuthor(HttpServletRequest request) throws Exception {
		String authorName = request.getParameter("authorName");
		Author a = new Author();
		a.setAuthorName(authorName);
		AdminstratorService.getIntance().updateAuthor(a);

	}

	private void addBranch(HttpServletRequest request) throws Exception {

		String branchName = request.getParameter("branchName");
		String branchAddress= request.getParameter("branchAddress");
		LibBranch br = new LibBranch();
		br.setBranchName(branchName);
		br.setBranchAddress(branchAddress);
		AdminstratorService.getIntance().AddBranch(br);

	}

	private void addBorrower(HttpServletRequest request) throws Exception {

		String borrName = request.getParameter("name");
		String borrAddress= request.getParameter("address");
		String borrPhone = request.getParameter("phone");

		Borrower b = new Borrower();
		b.setBorrowerName(borrName);
		b.setBorrowerAddress(borrAddress);
		b.setBorrowerPhone(borrPhone);

		AdminstratorService.getIntance().AddBorrower(b);

	}

	private void updateBook(HttpServletRequest request) throws Exception {
		String title = request.getParameter("bookTitle");
		Book b = new Book();
		Author a = new Author();
		b.setTitle(title);
		AdminstratorService.getIntance().updateAuthor(a);
		AdminstratorService.getIntance().updateBook(b);

	}

	private void addPublisher(HttpServletRequest request) throws Exception {
		String publisherName = request.getParameter("publisherName");
		String publisherAddress= request.getParameter("publisherAddress");
		String publisherPhone = request.getParameter("publisherPhone");

		Publisher p = new Publisher();
		p.setPublisherName(publisherName);
		p.setPublisherAddress(publisherAddress);
		p.setPublisherPhone(publisherPhone);

		AdminstratorService.getIntance().addPublisher(p);
	}

	private void addBook(HttpServletRequest request) throws Exception {

		String title = request.getParameter("bookTitle");
		String authorId = request.getParameter("authorId");
		String pubId = request.getParameter("pubId");
		String branchId = request.getParameter("branchId");
		Book b = new Book();
		Author a = new Author();
		Publisher p = new Publisher();
		LibBranch br = new LibBranch();
		BookCopies bc = new BookCopies();

		b.setTitle(title);
		a.setAuthorId(Integer.parseInt(authorId));
		p.setPublisherId(Integer.parseInt(pubId));
		br.setBranchId(Integer.parseInt(branchId));
		bc.setNoOfCopies(1);
		bc.setBook(b);
		bc.setBranch(br);

		b.setAuthor(a);;
		b.setPublisher(p);
		b.setBranch(br);
		Integer bookId=AdminstratorService.getIntance().addAndGetBookId(b);
		if(bookId!=0){
			b.setBookId(bookId);
		}
		request.setAttribute("book", b);
	}

	private void addAuthor(HttpServletRequest request) throws Exception {
		String authorName = request.getParameter("authorName");
		Author a = new Author();
		a.setAuthorName(authorName);

		AdminstratorService.getIntance().addAuthor(a);

	}
	private void showAllAuthors(HttpServletRequest request) throws Exception {
		List<Author> authorList = new ArrayList<Author>();
		Integer pageNo= Integer.parseInt(request.getParameter("pageNo"));
		authorList = AdminstratorService.getIntance().getAllAuthors(pageNo, null);
		if(authorList != null){
			request.setAttribute("authorList", authorList);
		}

	}
	private void showAllPublishers(HttpServletRequest request) throws Exception {
		Integer pageNo= Integer.parseInt(request.getParameter("pageNo"));
		List<Publisher> pubList = new ArrayList<>();
		pubList = AdminstratorService.getIntance().getAllPublishers(pageNo, null);
		if(pubList != null){
			request.setAttribute("pubList", pubList);
		}
	}

	private void showAllBooks(HttpServletRequest request) throws Exception {
		Integer pageNo= Integer.parseInt(request.getParameter("pageNo"));
		List<Book> books = new ArrayList<>();
		books = AdminstratorService.getIntance().getAllBooks(pageNo, null);
		if(books != null){
			request.setAttribute("books", books);
		}
	}
	private void showAllBookLoaned(HttpServletRequest request) throws Exception {
		//Integer pageNo= Integer.parseInt(request.getParameter("pageNo"));
		List<BookLoan> loans = new ArrayList<>();
		loans = AdminstratorService.getIntance().getAllBookLoaned();
		if(loans != null){
			request.setAttribute("loans", loans);
		}
	}

}
