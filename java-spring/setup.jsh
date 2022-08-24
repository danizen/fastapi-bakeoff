/open DEFAULT
/open PRINTING

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import net.danizen.bakeoff.model.*;
import net.danizen.bakeoff.service.*;
import net.danizen.bakeoff.BakeoffApp;
import net.danizen.bakeoff.persistence.*;
import net.danizen.bakeoff.controller.*;

public void startapp() {
	System.setProperty("spring.profiles.active", "jshell");
	SpringApplication.run(BakeoffApp.class);
}

public Object getBean(String beanName) {
	return BakeoffApp.getBean(beanName);
}

public Object getBean(Class beanClass) {
	return BakeoffApp.getBean(beanClass);
}
