/**
 * 
 */
package libraryApp.entity;
import java.sql.Date;
public class BookLoan implements BaseEntity {
	private Date dueDate;
	private Date checkOutDate;
	private Date CheckInDate;
	
	private Book book;
	private LibBranch branch;
	private Borrower borrower;
	
	public Book getBook() {
		return book;
	}
	
	public void setBook(Book book) {
		this.book = book;
	}
	
	public LibBranch getBranch() {
		return branch;
	}
	
	public void setBranch(LibBranch branch) {
		this.branch = branch;
	}
	
	public Borrower getBorrower() {
		return borrower;
	}
	
	public void setBorrower(Borrower borrower) {
		this.borrower = borrower;
	}
	
	
	public Date getDueDate() {
		return dueDate;
	}
	
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	
	public Date getCheckOutDate() {
		return checkOutDate;
	}
	
	public void setCheckOutDate(Date dateOut) {
		this.checkOutDate = dateOut;
	}
	
	public Date getCheckInDate() {
		return CheckInDate;
	}
	
	public void setCheckInDate(Date checkIn) {
		CheckInDate = checkIn;
	}
	
}
