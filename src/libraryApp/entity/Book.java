package libraryApp.entity;

import java.util.ArrayList;
import java.util.List;

public class Book implements BaseEntity {

	private int bookId;
	private String title;
	private Publisher publisher;
	private Author author;
	
	private List<Author> authors;
	private List<Genre> genres;
	private List<BookCopies> copies;
	
	public Author getAuthor() {
		return author;
	}
	public void setAuthor(Author author) {
		this.author = author;
	}
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	public String getBookTitle() {
		return title;
	}
	public void setBookTitle(String title) {
		this.title = title;
	}
	public Publisher getPublisher() {
		return publisher;
	}
	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}
	public List<Author> getAuthors() {
		return authors;
	}
	public void addAuthor(Author a){
		if(authors == null){
			authors = new ArrayList<Author>();
			authors.add(a);
		}
	}
	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}
	public List<Genre> getGenres() {
		return genres;
	}
	public void setGenres(List<Genre> genres) {
		this.genres = genres;
	}
	public List<BookCopies> getCopies() {
		return copies;
	}
	public void setCopies(List<BookCopies> copies) {
		this.copies = copies;
	}






}
