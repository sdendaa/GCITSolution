/**
 * 
 */
package libraryApp.entity;

import java.util.ArrayList;
import java.util.List;

public class Author implements BaseEntity{
	
	
	public Author() {
		super();
		
		
	}
	private int authorId;
	private String authorName;
	
	private List<Book> books;
	
	
	
	public int getAuthorId() {
		return authorId;
	}
	
	public void setAuthorId(int autherId) {
		this.authorId = autherId;
	}
	
	public String getAuthorName() {
		return authorName;
	}
	
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	
	public List<Book> getBooks() {
		return books;
	}
	
	public void setBooks(List<Book> books) {
		this.books = books;
	}
	public void addBook(Book bk){
		if(books == null)
			books = new ArrayList<Book>();
			books.add(bk);
	}
}
