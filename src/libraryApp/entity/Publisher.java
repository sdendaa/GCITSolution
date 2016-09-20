/**
 * 
 */
package libraryApp.entity;

import java.util.ArrayList;
import java.util.List;

public class Publisher implements BaseEntity{
	private int publisherId;
	private String publisherName;
	private String publisherAddress;
	private String publisherPhone;
	
	private List<Book> books;
	
	
	public int getPublisherId() {
		return publisherId;
	}
	
	public void setPublisherId(int publisherId) {
		this.publisherId = publisherId;
	}
	
	public String getPublisherName() {
		return publisherName;
	}
	
	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}
	
	public String getPublisherAddress() {
		return publisherAddress;
	}
	
	public void setPublisherAddress(String publisherAddress) {
		this.publisherAddress = publisherAddress;
	}
	
	public String getPublisherPhone() {
		return publisherPhone;
	}
	
	public void setPublisherPhone(String publisherPhone) {
		this.publisherPhone = publisherPhone;
	}
	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}
	
	public void addBook(Book book){
		if(books == null){
			books = new ArrayList<Book>();
			books.add(book);
		}
		
	}
	
}
