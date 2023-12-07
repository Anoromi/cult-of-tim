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
        //System.out.println("Run " + previousRun);
        setDataSource(dataSource);
        setSql("SELECT id, username, purchase_date FROM purchase INNER JOIN USER_TABLE ON USER_TABLE.user_id = purchase.user_id "
                //+ "WHERE purchase_date > " + new java.sql.Date(previousRun.getTime()).toString()
        );
        //setSql("SELECT id, purchase_date FROM purchase");
        //setSql("SELECT id FROM purchase");
        //dataSource.getConnection().prepareCall()
        setFetchSize(100);

        try {
            //var statement = dataSource.getConnection().prepareCall("SELECT id, purchase_date FROM purchase");
            //statement.execute();
            //while (statement.getMoreResults()) {
            //    var heh = statement.getLong("id");
            //}
        } catch (Exception e) {

        }
        //set
        setRowMapper(new BookPurchaseRowMapper());
    }


    public class BookPurchaseRowMapper implements RowMapper<BookPurchaseDto> {
        @Override
        public BookPurchaseDto mapRow(ResultSet rs, int rowNum) throws SQLException {

            //Employee employee = new Employee();
            //employee.setId(rs.getInt("id"));
            //employee.setName(rs.getString("name"));
            //employee.setSalary(rs.getInt("salary"));
            //return new BookPurchaseDto(2L, LocalDateTime.now(), "Hello");
            return new BookPurchaseDto(
                    rs.getLong("id"),
                    rs.getDate("purchase_date").toLocalDate().atStartOfDay(),
                    rs.getString("username")
            );
        }
    }
}
