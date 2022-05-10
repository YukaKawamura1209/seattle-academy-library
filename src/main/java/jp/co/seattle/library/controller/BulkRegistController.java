package jp.co.seattle.library.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jp.co.seattle.library.dto.BookDetailsInfo;
import jp.co.seattle.library.service.BooksService;
import jp.co.seattle.library.service.ThumbnailService;

@Controller // APIの入り口
public class BulkRegistController {
	final static Logger logger = LoggerFactory.getLogger(BulkRegistController.class);

	@Autowired
	private BooksService booksService;

	@Autowired
	private ThumbnailService thumbnailService;

	/**
	 * 書籍情報を登録する
	 * 
	 * @param locale ロケール情報
	 * @param bookId bookId
	 * @param model  モデル * @return 遷移先画面
	 **/

	@RequestMapping(value = "/bulkRegist", method = RequestMethod.GET) // value＝actionで指定したパラメータ
	// RequestParamでname属性を取得
	public String BulkRegist(Model model) {
		return "BulkRegist";
	}

	/**
	 * 書籍情報を登録する(CSV一括登録)
	 * 
	 * @param uploadFile CSVファイル
	 * @param model      モデル
	 * @return 遷移先画面
	 **/
	@Transactional
	@RequestMapping(value = "/bulkRegist", method = RequestMethod.POST, produces = "text/plain;charset=utf-8")
	public String uploadFile(@RequestParam("uploadFile") MultipartFile uploadFile, Model model) {

		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(uploadFile.getInputStream(), StandardCharsets.UTF_8))) {

			String line;
			int lineCount = 0;
			List<String> errorMessages = new ArrayList<String>();
			List<BookDetailsInfo> bookLists = new ArrayList<BookDetailsInfo>();

			if (!br.ready()) {
				model.addAttribute("errorMessages", "CSVに書籍情報がありません");
				return "BulkRegist";
			}
			while ((line = br.readLine()) != null) {
				String[] sprit = line.split(",", -1);

				// 行数カウントインクリメント
				lineCount++;

				// 一括登録バリデーション

				if ((sprit[0].isEmpty() || sprit[1].isEmpty() || sprit[2].isEmpty() || sprit[3].isEmpty())
						|| (sprit[3].isEmpty() || sprit[3].length() != 8 || !(sprit[3].matches("^[0-9]*$")))
						|| (sprit[4].length() != 10 && sprit[4].length() != 13 && sprit[4].length() != 0
								|| !(sprit[4].matches("^[0-9]*$")))) {
					errorMessages.add(lineCount + "行目でエラーが発生しました");

				} else {

					BookDetailsInfo bookInfo = new BookDetailsInfo();
					bookInfo.setTitle(sprit[0]);
					bookInfo.setAuthor(sprit[1]);
					bookInfo.setPublisher(sprit[2]);
					bookInfo.setPublishDate(sprit[3]);
					bookInfo.setIsbn(sprit[4]);

					bookLists.add(bookInfo);
				}

			}

			// エラーメッセージあればrender
			if (CollectionUtils.isEmpty(errorMessages)) {

				bookLists.forEach(bookList -> booksService.BulkRegist(bookList));
				return "redirect:home";
			} else {
				model.addAttribute("errorMessages", errorMessages);
				return "BulkRegist";
			}

		} catch (Exception e) {
			System.out.println(e);
			List<String> errorMessages = new ArrayList<String>();
			errorMessages.add("ファイルが読み込めません");
			model.addAttribute("errorMessages", errorMessages);
			return "BulkRegist";

		}
	}
}
