/**
 * 
 */
package libraryApp.data;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import libraryApp.entity.Book;
import libraryApp.entity.Genre;


public class GenreDAO extends BaseDAO<Genre>{

	private static final String SELECT_ALL = "select * from tbl_genre";
	private static final String SELECT_ALL_SEARCH = "select * from tbl_author where authorName like ?";
	private static final String SELECT_ONE = "select * from tbl_genre where genre_id = ?";
	private static final String SELECT_ID_BY_NAME = "select authorId from tbl_author where authorName = ?";
	private static final String INSERT = "insert into tbl_genre(genre_name)values(?)";
	private static final String DELETE = "delete from tbl_genre where genre = ?";
	private static final String UPDATE = "update tbl_genre set genre_name = ? where genre_id = ?";
	private static final String GET_COUNT = "select count(*) from tbl_author";
	private static final String SELECT_ALL_FROM_BOOK =  "SELECT g.genre_id, g.genre_name FROM tbl_genre g "
			+ "join tbl_book_genres bg on bg.genre_id = g.genre_id"
			+ "join tbl_book b on b.bookId = bg.bookId"
			+ "where b.bookId = ?";
	
	private static final String TABLE_NAME = "tbl_genre";
	private static final String KEY_COLUMN = "genre_id";
	
	public GenreDAO(Connection conn) {
		super(conn);
		
	}

	@Override
	public void create(Genre genre) throws SQLException {
		save(INSERT, new Object[]{genre.getGenreName()});
		
	}


	@Override
	public void update(Genre genre) throws SQLException{
		save(UPDATE, new Object[]{genre.getGenreName(), genre.getGenreId()});
		
	}

	@Override
	public void delete(Genre genre) throws SQLException{
		save(DELETE, new Object[]{genre.getGenreId()});
		
	}

	@Override
	public Genre read(Genre g) throws SQLException {
		return read(SELECT_ONE, new Object[]{g.getGenreId()});
	}

	@Override
	public List<Genre> readAll(String sql, Object[] vals) throws SQLException {
		return readAll(SELECT_ALL, new Object[]{});
	}
	
	public List<Genre> getAllGenre() throws SQLException {
		return readAll(SELECT_ALL, new Object[]{});
	}
	
	@Override
	public List<Genre> readResult(ResultSet rs) throws SQLException {
		List<Genre> list = new ArrayList<>();
		while(rs.next()){
			Genre ger = new Genre();
			ger.setGenreId(rs.getInt("genre_id"));
			ger.setGenreName(rs.getString("genre_name"));
			
			list.add(ger);
		}
		
		return list;
	}

	public List<Genre> getAllGenresBooK() throws SQLException {
		Book myBook = new Book();
		return readAll(SELECT_ALL_FROM_BOOK, new Object[]{myBook.getBookId()});
	}
	
	public Integer getCount() throws SQLException{
		return getCount(GET_COUNT, new Object[]{});
	}

	@Override
	public String getTableName() {
		
		return TABLE_NAME;
	}

	@Override
	public String getKeyColumn() {
		return KEY_COLUMN;
	}


}
