package net.danizen.bakeoff;


import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;


@SpringBootApplication
public class BakeoffApplication {
	public static void main(String[] args) {
		SpringApplication.run(BakeoffApplication.class, args);
	}

	@Bean
	public JdbcTemplate getJdbcTemplate(DataSource dataSource) {
	    return new JdbcTemplate(dataSource);
	}
}
