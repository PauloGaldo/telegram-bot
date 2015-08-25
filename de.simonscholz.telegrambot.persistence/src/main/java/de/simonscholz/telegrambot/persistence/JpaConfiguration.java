package de.simonscholz.telegrambot.persistence;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;

@Configuration
@EnableJpaRepositories(basePackages = "de.simonscholz.telegrambot.persistence.repository")
public class JpaConfiguration extends JpaBaseConfiguration {

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource,
			JpaVendorAdapter jpaVendorAdapter) {
		LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
		emfb.setDataSource(dataSource);
		emfb.setJpaVendorAdapter(jpaVendorAdapter);
		emfb.setPackagesToScan("de.simonscholz.telegrambot.persistence.domain");
		return emfb;
	}

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		String dbUrl = System.getenv("OPENSHIFT_MYSQL_DB_URL");
		String dbUser = System.getenv("OPENSHIFT_MYSQL_DB_USERNAME");
		String dbPassword = System.getenv("OPENSHIFT_MYSQL_DB_PASSWORD");

		// dataSource.setUrl("jdbc:mysql://" + dbHost + ":" + dbPort + "/java");
		if (dbUrl != null)
			dataSource.setUrl(dbUrl);
		dataSource.setUsername(dbUser);
		dataSource.setPassword(dbPassword);

		return dataSource;
	}

	@Override
	protected AbstractJpaVendorAdapter createJpaVendorAdapter() {
		EclipseLinkJpaVendorAdapter adapter = new EclipseLinkJpaVendorAdapter();
		adapter.setDatabase(Database.MYSQL);
		adapter.setDatabasePlatform("MySQLPlatform");
		adapter.setShowSql(true);
		adapter.setGenerateDdl(false);

		return adapter;
	}

	@Override
	protected Map<String, Object> getVendorProperties() {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put(PersistenceUnitProperties.WEAVING, detectWeavingMode());

		return map;
	}

	private String detectWeavingMode() {
		return InstrumentationLoadTimeWeaver.isInstrumentationAvailable() ? "true" : "static";
	}

}
