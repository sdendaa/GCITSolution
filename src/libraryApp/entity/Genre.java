/**
 * 
 */
package libraryApp.entity;

import java.util.ArrayList;
import java.util.List;


public class Genre implements BaseEntity{
	private int genreId;
	private String genreName;
	private static List<Book> books;
	
	
	public static void add(Book book) {
		if(books == null)
			books = new ArrayList<Book>();
		 	books.add(book);
	}
	
	public int getGenreId() {
		return genreId;
	}
	
	public void setGenreId(int genreId) {
		this.genreId = genreId;
	}
	
	public String getGenreName() {
		return genreName;
	}
	
	public void setGenreName(String genreName) {
		this.genreName = genreName;
	}
	
	public List<Book> getBooks() {
		return books;
	}
	
	public void setBooks(List<Book> books) {
		Genre.books = books;
	}
	

}
