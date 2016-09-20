/**
 * 
 */
package libraryApp.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class LibBranch implements BaseEntity {
	private  int branchId;
	private String branchName;
	private String branchAddress;
	
	private HashMap<Book, Integer> NoCopies;
	private List<BookLoan> bookLoans;

	public int getBranchId() {
		return branchId;
	}
	
	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}
	
	public String getBranchName() {
		return branchName;
	}
	
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	
	public String getBranchAddress() {
		return branchAddress;
	}
	
	public void setBranchAddress(String branchAddress) {
		this.branchAddress = branchAddress;
	}
	public HashMap<Book, Integer> getNoCopies() {
		return NoCopies;
	}


	public void setNoCopies(HashMap<Book, Integer> noCopies) {
		this.NoCopies = noCopies;
	}

	public void AddNoCopie(Book book){
		if(NoCopies == null){
			NoCopies = new HashMap<Book, Integer>();
			NoCopies.put(book,1);
		}
	}

	public List<BookLoan> getbookLoans() {
		return bookLoans;
	}

	
	public void setBookLoans(List<BookLoan> loans) {
		this.bookLoans = loans;
	}
	
	public void addBookLoan(BookLoan bl){
		if(bookLoans == null){
			bookLoans = new ArrayList<BookLoan>();
			bookLoans.add(bl);
		}
	}
}
