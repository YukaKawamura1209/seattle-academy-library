package jp.co.seattle.library.dto;

import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * 書籍詳細情報格納DTO
 *
 */
@Configuration
@Data
public class RentHistoryInfo {

	private int id;

	private String rentDate;

	private String returnDate;

	private String title;

	public RentHistoryInfo() {

	}

}
