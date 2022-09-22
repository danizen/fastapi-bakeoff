package net.danizen.bakeoff;

import org.springframework.context.ApplicationContext;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContextAware;

@SpringBootApplication
public class BakeoffApp implements ApplicationContextAware {
    private static ApplicationContext bakeoffContext = null;

	public static void main(String[] args) {
		SpringApplication.run(BakeoffApp.class, args);
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
