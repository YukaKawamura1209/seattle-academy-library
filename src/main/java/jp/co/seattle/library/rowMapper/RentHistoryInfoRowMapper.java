package jp.co.seattle.library.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;

import jp.co.seattle.library.dto.RentHistoryInfo;

@Configuration
public class RentHistoryInfoRowMapper implements RowMapper<RentHistoryInfo> {

	@Override
	public RentHistoryInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		// Query結果（ResultSet rs）を、オブジェクトに格納する実装
		RentHistoryInfo rentHistoryInfo = new RentHistoryInfo();
		rentHistoryInfo.setId(rs.getInt("id"));
		rentHistoryInfo.setTitle(rs.getString("title"));
		rentHistoryInfo.setRentDate(rs.getString("rent_date"));
		rentHistoryInfo.setReturnDate(rs.getString("return_date"));
		return rentHistoryInfo;
	}

}