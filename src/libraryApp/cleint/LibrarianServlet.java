package libraryApp.cleint;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory.Default;

import libraryApp.data.BookDAO;
import libraryApp.data.BranchDAO;
import libraryApp.data.ConnectionUtils;
import libraryApp.entity.Book;
import libraryApp.entity.BookCopies;
import libraryApp.entity.LibBranch;
import libraryApp.service.AdminstratorService;
import libraryApp.service.LibrarianService;

/**
 * Servlet implementation class LibrarianServlet
 */
@WebServlet(name = "LibrarianServlet",
urlPatterns={"/addNoCopies","/updateBranchDetail","/editBranchLibr","/pageBranchsLib",
		"/selectBranch", "/selectBook", "/searchBranchsLib", "/showAllBooksByBranch"})
public class LibrarianServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LibrarianServlet() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String function = request.getServletPath();
		String forwardPage = "/index.jsp";
		AdministratorServlet ads = new AdministratorServlet();
		try {
			switch (function) {
			case "/pageBranchsLib": {
				List<LibBranch> branchs = new ArrayList<LibBranch>();
				Integer pageNo= Integer.parseInt(request.getParameter("pageNo"));
				branchs = AdminstratorService.getIntance().getAllBranchs(pageNo, null);
				request.setAttribute("branchs", branchs);
				forwardPage = "/showLibrarianBranchs.jsp";
				break;
			}
			case "/searchBranchsLib": {
				searchBranchsLibr(request);
				forwardPage = "/showLibrarianBranchs.jsp";
				break;
			}

			default:
				break;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("result", "Operation failes " +e.getMessage());
		}
		
		RequestDispatcher rd = getServletContext().getRequestDispatcher(forwardPage);
		rd.forward(request, response);
	}

	private void searchBranchsLibr(HttpServletRequest request) throws SQLException {
		String searchString = request.getParameter("searchString");
		request.setAttribute("branchs", AdminstratorService.getIntance().getAllBranchs(1, searchString));
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String function = request.getServletPath();
		String forwardPage = "/showLibrarianBranchs.jsp";
		AdministratorServlet ads = new AdministratorServlet();
		try {
			switch(function){
				case "/addNoCopies":{
					addNoCopies(request);
					request.setAttribute("result", "Book number added Succesfully!");
					break;
				}
				case "/updateBranchDetail":{
					updateBranchDetail(request);
					request.setAttribute("result", "Branch deatail changed Succesfully!");
					break;
				}
				case "/selectBranch":{
					selectBranch(request);
					forwardPage = "/selectBranch.jsp";
					break;
				}
				case "/editBranchLibr": {
					editBranchLibr(request);
					forwardPage = "/showLibrarianBranchs.jsp";
					break;
				}
				case "/showLibrarianBranchs": {
					ads.showAllBranchs(request);
					forwardPage = "/showLibrarianBranchs.jsp";
					break;
				}
				case "/showAllBooksByBranch": {
					showAllBooksByBranch(request);
					forwardPage = "/showLibrarianBranchs.jsp";
					break;
				}
				case "/selectBook":{
					selectBook(request);
					request.setAttribute("result", "Book returned Succesfully!");
					forwardPage = "/selectBook.jsp";
					break;
				}
				default:
					break;
			}

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("result", "Operation failed!" + e.getMessage());
		}
		RequestDispatcher rd = getServletContext().getRequestDispatcher(forwardPage);
		rd.forward(request, response);
	}

	private void editBranchLibr(HttpServletRequest request) throws Exception {
		String branchId = request.getParameter("branchId");
		String branchName = request.getParameter("branchName");
		String branchAddress = request.getParameter("branchAddress");
		LibBranch br = new LibBranch();
		br.setBranchId(Integer.parseInt(branchId));
		br.setBranchName(branchName);
		br.setBranchAddress(branchAddress);
		AdminstratorService.getIntance().updateBranch(br);
		
	}

	private void selectBook(HttpServletRequest request) {
		String bookId = request.getParameter("bookId");
		request.setAttribute("bookId", bookId);
		
	}

	private void selectBranch(HttpServletRequest request) {
		String brId = request.getParameter("branchId");
		request.setAttribute("branchId", brId);
		
	}

	private void updateBranchDetail(HttpServletRequest request) throws Exception {
		String branchId = request.getParameter("branchId");
		String branchName = request.getParameter("branchName");
		String branchAddress = request.getParameter("brenchAddress");

		LibBranch br = new LibBranch();
		int brId = Integer.parseInt(branchId);
		br.setBranchId(brId);
		br.setBranchName(branchName);
		br.setBranchAddress(branchAddress);

		LibrarianService ls = new LibrarianService();
		ls.updateLibraryDetail(br);
	
	}

	private void addNoCopies(HttpServletRequest request) throws Exception {
		String branchId = request.getParameter("branchId");
		String bookId = request.getParameter("bookId");
		String noCopy = request.getParameter("noOfCopies");
		
		Book b = new Book();
		b.setBookId(Integer.parseInt(bookId));
		LibBranch br = new LibBranch();
		br.setBranchId(Integer.parseInt(branchId));
		int newNo = Integer.parseInt(noCopy);
		BookCopies bc = new BookCopies();
		int originNo = bc.getNoOfCopies();
		
		bc.setBook(b);
		bc.setBranch(br);
		bc.setNoOfCopies(newNo+originNo);
		LibrarianService ls = new LibrarianService();
		ls.AddBookCopies(bc);

	}
	private void showAllBooksByBranch(HttpServletRequest request) throws Exception {
		Integer pageNo= Integer.parseInt(request.getParameter("pageNo"));
		Integer branchId = Integer.parseInt(request.getParameter("branchId"));
		List<Book> books = new ArrayList<>();
		books = AdminstratorService.getIntance().getAllBooksByBranch(branchId, pageNo, null);
		if(books != null){
			request.setAttribute("books", books);
		}
	}

}
