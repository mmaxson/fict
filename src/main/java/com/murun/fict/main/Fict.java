package com.murun.fict.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration;
import org.springframework.context.ApplicationContext;

@SpringBootApplication(exclude={HibernateJpaAutoConfiguration.class, JpaRepositoriesAutoConfiguration.class, JpaBaseConfiguration.class, HibernateJpaAutoConfiguration.class})
public class Fict {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(Fict.class, args);


		System.out.println(ctx.getApplicationName());
		System.out.println(ctx.getDisplayName());

	}
}
