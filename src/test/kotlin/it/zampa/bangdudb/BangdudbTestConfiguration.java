package it.zampa.bangdudb;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@TestConfiguration
@PropertySource("application-test.properties")
class BangdudbTestConfiguration {

	@Bean
	@Profile("test")
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl("jdbc:postgresql://localhost:5432/bangdudb-test?createDatabaseIfNotExist=true");
		dataSource.setUsername("springuser");
		dataSource.setPassword("ThePassword1!");

		return dataSource;
	}


}
