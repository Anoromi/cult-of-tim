package com.example.cult_of_tim.cultoftim.util;

import com.example.cult_of_tim.cultoftim.dto.BookPurchaseDto;
import com.example.cult_of_tim.cultoftim.entity.Purchase;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Date;

@Component
public class PurchaseReader extends JdbcCursorItemReader<BookPurchaseDto> {

    public PurchaseReader(@Autowired DataSource dataSource) {
        setDataSource(dataSource);
        setSql("SELECT id, username, purchase_date FROM purchase INNER JOIN USER_TABLE ON USER_TABLE.user_id = purchase.user_id "
                //+ "WHERE purchase_date > " + new java.sql.Date(previousRun.getTime()).toString()
        );
        setFetchSize(100);

        setRowMapper(new BookPurchaseRowMapper());
    }


    public class BookPurchaseRowMapper implements RowMapper<BookPurchaseDto> {
        @Override
        public BookPurchaseDto mapRow(ResultSet rs, int rowNum) throws SQLException {

            return new BookPurchaseDto(
                    rs.getLong("id"),
                    rs.getDate("purchase_date").toLocalDate().atStartOfDay(),
                    rs.getString("username")
            );
        }
    }
}
