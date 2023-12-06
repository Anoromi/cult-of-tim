package com.example.cult_of_tim.cultoftim.util;

import com.example.cult_of_tim.cultoftim.entity.Purchase;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class PurchaseReader extends JdbcCursorItemReader<Purchase> implements ItemReader<Purchase> {

    public PurchaseReader(@Autowired DataSource dataSource) {
        setDataSource(dataSource);
        setSql("SELECT id FROM purchase");
        setFetchSize(100);
        //set
        setRowMapper(new EmployeeRowMapper());
    }

    public class EmployeeRowMapper implements RowMapper<Purchase> {
        @Override
        public Purchase mapRow(ResultSet rs, int rowNum) throws SQLException {
            //Employee employee = new Employee();
            //employee.setId(rs.getInt("id"));
            //employee.setName(rs.getString("name"));
            //employee.setSalary(rs.getInt("salary"));
            return new Purchase();
        }
    }
}
