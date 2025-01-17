package jp.co.seattle.library.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import jp.co.seattle.library.dto.BookDetailsInfo;
import jp.co.seattle.library.dto.BookInfo;
import jp.co.seattle.library.dto.RentHistoryInfo;
import jp.co.seattle.library.rowMapper.BookDetailsInfoRowMapper;
import jp.co.seattle.library.rowMapper.BookInfoRowMapper;
import jp.co.seattle.library.rowMapper.RentHistoryInfoRowMapper;

/**
 * 書籍サービス
 * 
 * booksテーブルに関する処理を実装する
 */
@Service
public class BooksService {

	final static Logger logger = LoggerFactory.getLogger(BooksService.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 書籍リストを取得する
	 *
	 * @return 書籍リスト
	 */
	public List<BookInfo> getBookList() {

		// TODO 取得したい情報を取得するようにSQLを修正
		List<BookInfo> getedBookList = jdbcTemplate.query(
				"select id,title,author,publisher,publish_date,thumbnail_name,thumbnail_url from books order by title ASC",
				new BookInfoRowMapper());

		return getedBookList;
	}

	/**
	 * 書籍を検索
	 *
	 * @param title
	 * @return 書籍リスト
	 */

	public List<BookInfo> search(String title) {

		// TODO 取得したい情報を取得するようにSQLを修正
		List<BookInfo> getedBookList = jdbcTemplate
				.query("select id,title,author,publisher,publish_date,thumbnail_url from books where title like '%"
						+ title + "%' order by title ASC", new BookInfoRowMapper());

		return getedBookList;
	}
	
	/**
	 * 書籍を検索
	 *
	 * @param title
	 * @return 書籍リスト
	 */

	public List<RentHistoryInfo> rentHistory() {

		// TODO 取得したい情報を取得するようにSQLを修正
		List<RentHistoryInfo> RentHistoryList = jdbcTemplate.query(
				"SELECT id, title,rent_date,return_date FROM books INNER join rentbooks on books.id = rentbooks.book_id",
				 new RentHistoryInfoRowMapper());

		return RentHistoryList;
		
	}
	
    /**
     * 書籍を登録する
     *
     * @param bookInfo 書籍情報
     */
    public void registBook(BookDetailsInfo bookInfo) {

        String sql = "INSERT INTO books (title,author,publisher,publish_date,thumbnail_name,thumbnail_url,isbn,description,reg_date,upd_date) VALUES ('"
                + bookInfo.getTitle() + "','" 
        		+ bookInfo.getAuthor() + "','" 
                + bookInfo.getPublisher() + "','" 
        		+ bookInfo.getPublishDate() + "','"
                + bookInfo.getThumbnailName() + "','"
                + bookInfo.getThumbnailUrl() + "','"
                + bookInfo.getIsbn() + "','"
                + bookInfo.getDescription() + "',"
                + "now(),"
                + "now())";

        jdbcTemplate.update(sql);       
    }
	/**
	 * 書籍IDに紐づく書籍詳細情報を取得する
	 *
	 * @param bookId 書籍ID
	 * @return 書籍情報
	 */
	public BookDetailsInfo getBookInfo(int bookId) {

		// JSPに渡すデータを設定する
		String sql ="SELECT * , case when rent_date is null then '貸出可' else '貸出不可' end as status from books left outer join rentbooks on books.id = rentbooks.book_id where books.id=" + bookId;
	    BookDetailsInfo bookDetailsInfo = jdbcTemplate.queryForObject(sql, new BookDetailsInfoRowMapper());

		return bookDetailsInfo;
	}

	/**
	 * 書籍ををする
	 *
	 * @param bookInfo 書籍情報
	 */

	public void Returnbooks(int bookId) {

		String sql = "update rentbooks set rent_date = null,return_date = now() where rentbooks.book_id="+bookId+" and return_date is null" ;
		jdbcTemplate.update(sql);

	}

	/**
	 * 書籍を削除する
	 *
	 * @param bookId 書籍ID
	 *
	 */

	public void deleteBook(int bookId) {

		String sql = "DELETE FROM books WHERE id =" + bookId;

		jdbcTemplate.update(sql);
	}

 public int size (int bookId) {
	 String sql = "select count (rent_date) from rentbooks WHERE book_id=" + bookId;
	 
	 return jdbcTemplate.queryForObject(sql , int.class);
 
}
	/**
	 * 
	 * 書籍IDに紐づく書籍詳細情報を取得する
	 * 
	 * @param bookId 書籍ID
	 * @return 書籍情報
	 */

	public int getmaxbookid() {

		String sql = "SELECT MAX(id) FROM books";
		int bookId = jdbcTemplate.queryForObject(sql, Integer.class);
		return bookId;
	}

	/**
	 * 
	 * 書籍情報を更新する
	 * 
	 * @param locale       ローケル情報
	 * @param title        書籍名
	 * @param author       著者名
	 * @param publisher    出版社
	 * @param publish_date 出版日
	 * @param file         サムネファイル
	 * @palam model モデル
	 * @param isbn コード
	 * @param bio  説明文
	 * @param id   書籍ID
	 * @return 遷移先画面
	 */

	public void updateBook(BookDetailsInfo bookInfo) {
		String sql = "update books set title ='" + bookInfo.getTitle() + "', author ='" + bookInfo.getAuthor()
				+ "' , publisher ='" + bookInfo.getPublisher() + "', publish_date ='" + bookInfo.getPublishDate()
				+ "' , upd_date = 'now()'" + ",isbn = '" + bookInfo.getIsbn() + "', description= '"
				+ bookInfo.getDescription() + "' where id =" + bookInfo.getBookId() + ";";

		jdbcTemplate.update(sql);
	}

	/**
	 * 一括登録をする
	 *
	 * @param bookInfo 書籍情報
	 */

	public void BulkRegist(BookDetailsInfo bookInfo) {

		String sql = "INSERT INTO books (title,author,publisher,publish_date,isbn,description,upd_date) VALUES ('"
				+ bookInfo.getTitle() + "','" + bookInfo.getAuthor() + "','" + bookInfo.getPublisher() + "','"
				+ bookInfo.getPublishDate() + "','" + bookInfo.getIsbn() + "','" + bookInfo.getDescription() + "',"
				+ "now())";

		jdbcTemplate.update(sql);
	}

	/**
	 * 書籍を貸出する
	 *
	 * @param bookId 書籍ID
	 */

	public void rentBook(int bookId) {

		String sql ="insert into rentbooks (book_id,rent_date) values ("+bookId+",now()) on conflict (book_id) do update set return_date=null,rent_date=now();";

		jdbcTemplate.update(sql);

	}
	/**
	 * 行数を取得する
	 *
	 * @param bookId 書籍ID
	 * @return
	 */

	public int count() {
		String sql = "select count (*) book_id from rentbooks";

		return jdbcTemplate.queryForObject(sql, int.class);
	}
	
}
