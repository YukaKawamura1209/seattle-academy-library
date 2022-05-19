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


@Controller //APIの入り口
public class SearchController {
    final static Logger logger = LoggerFactory.getLogger(SearchController.class);

    @Autowired
    private BooksService booksService ;

    @Transactional
    @RequestMapping(value = "/searchbook", method = RequestMethod.POST, produces = "text/plain;charset=utf-8")
    public String search(Locale locale,
    		@RequestParam("title") String title,Model model) {
    	
    	  model.addAttribute("bookList", booksService.search(title));
          return "home";
    }
}
