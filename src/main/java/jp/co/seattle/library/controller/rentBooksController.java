package jp.co.seattle.library.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.seattle.library.service.BooksService;

/**
 * 詳細表示コントローラー
 */
@Controller
public class rentBooksController {
    final static Logger logger = LoggerFactory.getLogger(BooksService.class);

    @Autowired
    private BooksService booksService;

    /**
     * 詳細画面に遷移する
     * @param locale
     * @param bookId
     * @param model
     * @return
     */
    @Transactional
    @RequestMapping(value = "/rentBook", method = RequestMethod.POST)
    public String rentBook(Locale locale,
            @RequestParam("bookId") Integer bookId,
            Model model) {
        // デバッグ用ログ
        logger.info("Welcome rentBooksControler.java! The client locale is {}.", locale);
        
        int size = booksService.count();
        booksService.rentBook(bookId);
        
        int count = booksService.count();
        
        if (size == count) {
        	model.addAttribute("rentMessage", "貸出済みです");

        }
        
        int booklentnumber = booksService.getBooklentnumber (bookId);
        
        if (booklentnumber == 0) {
        	model.addAttribute("statusMessage", "貸出可能");	
        	 //bookIdが入っていれば貸出不可
        }else {
        	model.addAttribute("statusMessage", "貸出中");
        }
        
            model.addAttribute("bookDetailsInfo", booksService.getBookInfo(bookId));
    
         
            return "details";
    

	}

}
    

