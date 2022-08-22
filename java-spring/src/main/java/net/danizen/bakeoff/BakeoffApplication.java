package net.danizen.bakeoff;


import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;


@SpringBootApplication
public class BakeoffApplication implements ApplicationContextAware {
    // this is for testing only
    private static ApplicationContext bakeoffContext;

	public static void main(String[] args) {
		SpringApplication.run(BakeoffApplication.class, args);
	}

	@Bean(name = "jdbcTemplate")
	public JdbcTemplate getJdbcTemplate(DataSource dataSource) {
	    return new JdbcTemplate(dataSource);
	}

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        bakeoffContext = context;
    }

    public static Object getBean(String beanName) {
        return bakeoffContext.getBean(beanName);
    }

    public static Object getBean(Class beanClass) {
        return bakeoffContext.getBean(beanClass);
    }
}
