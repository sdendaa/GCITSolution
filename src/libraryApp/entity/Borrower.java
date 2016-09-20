/**
 * 
 */
package libraryApp.entity;

import java.util.ArrayList;
import java.util.List;


public class Borrower implements BaseEntity {
	private int cardNo;
	private String name;
	private String address;
	private String phone;
	
	private static List<BookLoan> loan;
	
	
	public List<BookLoan> getLoan() {
		return loan;
	}
	
	public void setLoan(List<BookLoan> loan) {
		Borrower.loan = loan;
	}
	
	public int getCardNo() {
		return cardNo;
	}
	
	public void setCardId(int cardNo) {
		this.cardNo = cardNo;
	}
	
	public String getBorrowerName() {
		return name;
	}
	
	public void setBorrowerName(String borrowerName) {
		this.name = borrowerName;
	}
	
	public String getBorrowerAddress() {
		return address;
	}
	
	public void setBorrowerAddress(String borrowerAddress) {
		this.address = borrowerAddress;
	}
	
	public String getBorrowerPhone() {
		return phone;
	}
	
	public void setBorrowerPhone(String borrowerPhone) {
		this.phone = borrowerPhone;
	}
	
	public static void addBookLoan(BookLoan e) {
		if(loan == null)
			loan = new ArrayList<BookLoan>();
			loan.add(e);
	}

}
