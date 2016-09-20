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
import libraryApp.entity.Publisher;


public class PublisherDAO extends BaseDAO<Publisher> {
	private static final String SELECT_ALL = "select * from tbl_publisher";
	private static final String SELECT_ALL_SEARCH = "select * from tbl_publisher where publisherName like ?";
	private static final String SELECT_ONE = "select * from tbl_publisher where publisherId = ?";
	private static final String SELECT_ID_BY_NAME = "select authorId from tbl_author where authorName = ?";
	private static final String INSERT = "insert into tbl_publisher(publisherName, publisherAddress,publisherPhone)values(?,?,?)";
	private static final String DELETE = "delete from tbl_publisher where publisherId = ?";
	private static final String UPDATE = "update tbl_publisher set publisherName = ?, publisherAddress = ?, publisherPhone = ? where publisherId = ?";
	private static final String GET_COUNT = "select count(*) from tbl_publisher";
	private static final String SELECT_ALL_FROM_BOOK=
	"SELECT * FROM tbl_publisher p"
	+ "join tbl_book b on p.publisherId = b.pubId"
	+ "where b.bookId = ?";
	private static final String TABLE_NAME = "tbl_publisher";
	private static final String KEY_COLUMN = "publisherId";

	public PublisherDAO(Connection conn) {
		super(conn);
		
	}

	@Override
	public void create(Publisher publ) throws SQLException {
		save(INSERT, new Object[]{publ.getPublisherName(), publ.getPublisherAddress(), publ.getPublisherPhone()});
		
	}

	
	@Override
	public void update(Publisher publ) throws SQLException{
		save(UPDATE, new Object[]{publ.getPublisherName(), publ.getPublisherAddress(), publ.getPublisherPhone(), publ.getPublisherId()});
		
	}

	
	@Override
	public void delete(Publisher publ) throws SQLException{
		save(DELETE, new Object[]{publ.getPublisherId()});
		
		
	}

	@Override
	public Publisher read(Publisher p) throws SQLException {
		return read(SELECT_ONE, new Object[]{p.getPublisherId()});
	}


	public List<Publisher> readAllPublisher(int pageNo, int pageSize, String searchString) throws SQLException {
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
	public List<Publisher> readResult(ResultSet rs) throws SQLException {
		List<Publisher> list = new ArrayList<>();
		while(rs.next()){
			Publisher pub = new Publisher();
			pub.setPublisherId(rs.getInt("publisherId"));
			pub.setPublisherName(rs.getString("publisherName"));
			pub.setPublisherAddress(rs.getString("publisherAddress"));
			pub.setPublisherPhone(rs.getString("publisherPhone"));
			
			list.add(pub);
		}
		return list;	
	}
	
	public Integer getCount() throws SQLException{
		return getCount(GET_COUNT, new Object[]{});
	}


	public List<Publisher> readAllPublishers() throws SQLException {
		Book myBo = new Book();
		
		return readAll(SELECT_ALL_FROM_BOOK, new Object[]{myBo.getBookId()});
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
