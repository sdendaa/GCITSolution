package libraryApp.entity;

public class Book implements BaseEntity {

	private int bookId;
	private String title;
	private Publisher publisher;
	private LibBranch branch;
	private Author author;
//	private List<Author> authors;
//	private List<Genre> genres;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public LibBranch getBranch() {
		return branch;
	}
	public void setBranch(LibBranch branch) {
		this.branch = branch;
	}
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	public Publisher getPublisher() {
		return publisher;
	}
	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}
	public Author getAuthor() {
		return author;
	}
	public void setAuthor(Author author) {
		this.author = author;
	}
	
//	public List<Author> getAuthors() {
//		return authors;
//	}
//	public void addAuthor(Author a){
//		if(authors == null){
//			authors = new ArrayList<Author>();
//			authors.add(a);
//		}
//	}
//	public void setAuthors(List<Author> authors) {
//		this.authors = authors;
//	}
//	public List<Genre> getGenres() {
//		return genres;
//	}
//	public void setGenres(List<Genre> genres) {
//		this.genres = genres;
//	}
//	public void addGenre(Genre g){
//		if(genres == null){
//			genres = new ArrayList<Genre>();
//			genres.add(g);
//		}
//	}
}
