package libraryApp.cleint;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import libraryApp.entity.Book;
import libraryApp.entity.BookLoan;
import libraryApp.entity.Borrower;
import libraryApp.entity.LibBranch;
import libraryApp.service.BorrowerService;

/**
 * Servlet implementation class BorrowerServlet
 */

@WebServlet(name ="BorrowerServlet", 
urlPatterns = {"/cardAuthenR", "/cardAuthenB", "/checkOut", "/returnBook","/chowAllBookCheckOutByBranch",
		"/showAllBranchsBor", "/chooseBranch"})

public class BorrowerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BorrowerServlet() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String function = request.getServletPath();
		String forwardPage = "/index.jsp";
		System.out.print("function");
		try {
			switch (function) {
			case "/check":{
				System.out.print("here2");
				checkOutBook(request);
				forwardPage ="/showAllBranchBor.jsp";
				request.setAttribute("result", "Book checked out sucessfully!");
				break;

			}default:
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("result", "operation fails"+ e.getMessage());
		}
		RequestDispatcher rd = getServletContext().getRequestDispatcher(forwardPage);
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String function = request.getServletPath();
		String forwardPage = "/index.jsp";
		try {
			switch (function) {
			case "/checkOut":{
				checkOutBook(request);
				forwardPage ="/showAllBranchsBor.jsp";
				request.setAttribute("result", "Book checked out sucessfully!");
				break;

			}

			case "/returnBook":{
				returnBook(request);
				request.setAttribute("result", "Book returned sucessfully!");
				break;

			}	
			case "/cardAuthenB":{
				boolean valid = cardAuthentication(request,response);
				if(valid == true)
				{
					request.setAttribute("result", "sucessfully login!");
					forwardPage = "/showAllBranchsBor.jsp";	
				}
				else{
					JOptionPane.showMessageDialog(null, "You Entered Invalid Card Number!");
					//forwardPage = "/cardAuthenCheckOut.jsp";
				}
				break;

			}
			case "/cardAuthenR":{
				boolean valid = cardAuthentication(request,response);
				if(valid){
					request.setAttribute("result", "sucessfully login!");
					forwardPage = "/showAllBranchToReturn.jsp";
				}else{
					JOptionPane.showMessageDialog(null, "You Entered Invalid Card Number!");
					//forwardPage = "/cardAuthenRet.jsp";
				}
				break;

			}
			case "/showAllBookCheckOutByBranch":{
				chooseBook(request);
				request.setAttribute("result", "sucessfully login!");
				forwardPage = "/showAllBookCheckOutByBranch.jsp";
				break;

			}
			case "/showAllBranchsBor":{
				chooseBranch(request,response);
				request.setAttribute("result", "sucessfully login!");
				forwardPage = "/chowAllBookCheckOutByBranch.jsp";
				break;

			}
			default:
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("result", "operation fails"+ e.getMessage());
		}
		RequestDispatcher rd = getServletContext().getRequestDispatcher(forwardPage);
		rd.forward(request, response);

	}

	private void chooseBranch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String branchId = request.getParameter("branchId");
			String cardNo = request.getParameter("cardNo");
			request.setAttribute("branchId", branchId);
			request.setAttribute("cardNo", cardNo);

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("result", "Operation failed "+ e.getMessage());
		}	
	}
	private void chooseBook(HttpServletRequest request) throws Exception {

		try {
			String branchId = request.getParameter("branchId");
			String bookId = request.getParameter("bookId");
			request.setAttribute("branchId", branchId);
			request.setAttribute("bookId", bookId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean cardAuthentication(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cardNo = request.getParameter("cardNo");
		BorrowerService myBorrower = new BorrowerService();
		Borrower b = new Borrower();
		boolean valid=false;
		try {
			b = myBorrower.getBorrowerById(Integer.parseInt(cardNo));
			if(b == null){
				request.setAttribute("result", "Card number is not valid");
				valid=false; 
			}else{
				request.setAttribute("cardNo", cardNo);
				valid = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("result", "Couldn't Authenticate you card number "+ e.getMessage());
		}
		return  valid;
	}

	private void returnBook(HttpServletRequest request) throws Exception {
			String branchId = request.getParameter("branchId");
			String bookId = request.getParameter("bookId");
			String cardNo = request.getParameter("cardId");

			Book b = new Book();
			LibBranch br = new LibBranch();
			BookLoan bl = new BookLoan();
			Borrower bor = new Borrower();

			bor.setCardId(Integer.parseInt(cardNo));
			b.setBookId(Integer.parseInt(bookId));
			br.setBranchId(Integer.parseInt(branchId));
			bl.setBranch(br);
			bl.setBook(b);

			BorrowerService borSer = new BorrowerService();
			//borSer.returnBook(bl);
		
	}

	private void checkOutBook(HttpServletRequest request) throws Exception {
		String branchId = request.getParameter("branchId");
		String bookId = request.getParameter("bookId");
		String cardNo = request.getParameter("cardNo");
		Book b = new Book();
		LibBranch br = new LibBranch();
		BookLoan bl = new BookLoan();
		Borrower bor = new Borrower();

		bor.setCardId(Integer.parseInt(cardNo));
		b.setBookId(Integer.parseInt(bookId));
		br.setBranchId(Integer.parseInt(branchId));
		bl.setBranch(br);
		bl.setBook(b);
		bl.setBorrower(bor);
		BorrowerService borSer = new BorrowerService();

		borSer.checkOutBook(bl);

	}

}
