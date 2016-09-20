/**
 * 
 */
package libraryApp.entity;

import java.sql.Timestamp;
import java.util.Date;

public class BookLoan implements BaseEntity {
	private Timestamp dueDate;
	private Timestamp checkOutDate;
	private Timestamp CheckInDate;
	
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
	
	
	public Timestamp getDueDate() {
		return dueDate;
	}
	
	public void setDueDate(Timestamp dueDate) {
		this.dueDate = dueDate;
	}
	
	public Timestamp getCheckOutDate() {
		return checkOutDate;
	}
	
	public void setCheckOutDate(Timestamp checkDate) {
		this.checkOutDate = checkDate;
	}
	
	public Timestamp getCheckInDate() {
		return CheckInDate;
	}
	
	public void setCheckInDate(Timestamp checkIn) {
		CheckInDate = checkIn;
	}
	
}
