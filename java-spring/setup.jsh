/open DEFAULT
/open PRINTING

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import net.danizen.bakeoff.model.*;
import net.danizen.bakeoff.service.*;
import net.danizen.bakeoff.BakeoffApplication;
import net.danizen.bakeoff.persistence.*;
import net.danizen.bakeoff.controller.*;

public void startapp() {
	SpringApplication.run(BakeoffApplication.class);
}
