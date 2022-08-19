package net.danizen.dburl;

import java.net.URI;
import java.net.URISyntaxException;

import javax.naming.ConfigurationException;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DatabaseUrlConfiguration {
	
	public String getDatabaseURL() throws URISyntaxException, ConfigurationException {
		String uriString = System.getenv("DATABASE_URL");
		if (uriString == null) {
			// raise some sort of runtime exception
			throw new ConfigurationException("postgres DATABASE_URL is required");
		}
		URI uri = new URI(uriString);
		
		
		if (uri.getScheme() == "jdbc") {
			return uri.toString();
		} else if (uri.getScheme() != "postgres") {
		}
		// determine connection parameters
		String hostname = uri.getHost();
		int port = uri.getPort();
		if (port == -1) {
			port = 5432;
		}
		String databaseName = uri.getPath();
		String[] userAndPassword = uri.getUserInfo().split(":");
		
		return String.format(
			"jdbc:postgresql://%s:%d%s?user=%s&password=%s&ssl=true",
			hostname, port, databaseName,
			userAndPassword[0], userAndPassword[1]
		);
	}
	
	@Bean
	public DataSource getDataSource() throws ConfigurationException, URISyntaxException {
		String jdbcUrl = getDatabaseURL();
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl(jdbcUrl);
		// set additional parameters based on configuration
		return new HikariDataSource(config);
	}

}
