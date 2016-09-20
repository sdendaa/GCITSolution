/**
 * 
 */
package libraryApp.data;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import libraryApp.entity.Author;
import libraryApp.entity.Book;

public class AuthorDAO extends BaseDAO<Author>{
	
	private static final String SELECT_ALL = "select * from tbl_author";
	private static final String SELECT_ALL_SEARCH = "select * from tbl_author where authorName like ?";
	private static final String SELECT_ONE = "select * from tbl_author where authorId = ?";
	private static final String SELECT_ID_BY_NAME = "select authorId from tbl_author where authorName = ?";
	private static final String INSERT = "insert into tbl_author (authorName) values (?)";
	private static final String DELETE = "delete from tbl_author where authorId = ?";
	private static final String UPDATE = "update tbl_author set authorName = ? where authorId = ?";
	private static final String GET_COUNT = "select count(*) from tbl_author";
	private static final String GET_COUNT_BY_NAME = "select count(*) from tbl_author where authorName like ?";
	
	private static final String TABLE_NAME = "tbl_author";
	private static final String KEY_COLUMN = "authorId";

	public AuthorDAO(Connection conn){
		super(conn);
	}

	@Override
	public void create(Author author) throws SQLException {
		
		save(INSERT, new Object[]{author.getAuthorName()});
	}

	@Override
	public void update(Author author) throws SQLException {
		save(UPDATE, new Object[]{author.getAuthorName(), author.getAuthorId()});

	}


	@Override
	public void delete(Author author) throws SQLException {
		save(DELETE, new Object[]{author.getAuthorId()});

	}
	public Integer getCount() throws SQLException{
		return getCount(GET_COUNT, new Object[]{});
	}
	
	//public void readAll()throws SQLException {
//		String query = "select * from tbl_author";
//		PreparedStatement stmt = conn.prepareStatement(query);
//		ResultSet rs = stmt.executeQuery();
//		ResultSetMetaData metaData = (ResultSetMetaData) rs.getMetaData();
//		int count = metaData.getColumnCount(); 
//		String columnName[] = new String[count];
//		
//		while(rs.next()){
//			for (int i = 1; i <= count; i++){
//				columnName[i-1] = metaData.getColumnLabel(i); 
//				Object mO = rs.getObject(columnName[i-1]);
//				System.out.print(mO.toString()+" ");;
//			}
//			System.out.println();
//		}

//	}
	
	@Override
	public Author read(Author a) throws SQLException {
		return read(SELECT_ONE, new Object[]{a.getAuthorId()});
	}

//	@Override
//	public List<Author> readAllADO(String sql, Object[] vals) throws SQLException {
//		return readAll("select * from tbl_author", new Object[]{});
//	}

	public List<Author> readAllAuthors(Integer pageNo, Integer pageSize, String searchString) throws SQLException {
		setPageNo(pageNo);
		setPageSize(pageSize);
		
		if(searchString !=null && !"".equals(searchString)){
			List<Object> valueList = new ArrayList<Object>();
			searchString = "%"+searchString+"%";
			valueList.add(searchString);
			return readAll(SELECT_ALL_SEARCH, new Object[]{searchString});
		}else{
			return readAll(SELECT_ALL, new Object[]{});
		}
	}
		
	@Override
	public List<Author> readResult(ResultSet rs) throws SQLException {
		List<Author> list = new ArrayList<>();
		while(rs.next()){
			Author au = new Author();
			
			au.setAuthorId(rs.getInt("AuthorId"));
			au.setAuthorName(rs.getString("AuthorName"));
			list.add(au);
		}
		
		return list;
	}
	public List<Author> readAllAthors() throws SQLException {
		Book myBo = new Book();
		String sql = "SELECT a.authorId, a.authorName FROM tbl_author a "
				+ "join tbl_book_authors ba on ba.authorId = a.authorId"
				+ "join tbl_book b on b.bookId = ba.bookId"
				+ "where b.bookId = ?";
		return readAll(sql, new Object[]{myBo.getBookId()});
	}
	
	public static void main(String[]arg) throws IOException{
		AuthorDAO au;
		String path = new File(".").getCanonicalPath();
		System.out.println(path);
		try {
			au = new AuthorDAO(ConnectionUtils.getConnetion());
			
//			System.out.print(au.read(3).getAuthorId()+" ");
//			System.out.println(au.read(3).getAuthorName());
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	@Override
	public String getTableName() {
		return TABLE_NAME;
	}

	@Override
	public String getKeyColumn() {
		return KEY_COLUMN;
	}

	public Integer getCountByName(String searchString) throws SQLException{
		List<Object> valueList = new ArrayList<Object>();
		searchString = "%"+searchString+"%";
		valueList.add(searchString);
		return getCount(GET_COUNT_BY_NAME, new Object[]{searchString});
	}
	
}
