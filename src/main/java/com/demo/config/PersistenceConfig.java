package com.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

import jakarta.persistence.EntityManagerFactory;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = {
        "com.demo.dao",
        "com.demo.service"
})
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
public class PersistenceConfig {

  @Bean
  public DataSource dataSource(
      @Value("${db.url}") String url,
      @Value("${db.username}") String user,
      @Value("${db.password}") String pass
  ) {
    HikariDataSource ds = new HikariDataSource();
    ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
    ds.setJdbcUrl(url);
    ds.setUsername(user);
    ds.setPassword(pass);
    ds.setMaximumPoolSize(5);
    ds.setMinimumIdle(1);
    ds.setConnectionTimeout(10000);
    ds.setIdleTimeout(600000);
    ds.setMaxLifetime(1800000);
    
    return ds;
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(
      DataSource ds,
      @Value("${hibernate.hbm2ddl.auto:none}") String ddl,
      @Value("${hibernate.show_sql:false}") String showSql,
      @Value("${hibernate.format_sql:true}") String formatSql
  ) {
    LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
    emf.setDataSource(ds);
    emf.setPackagesToScan("com.demo.entity");
    emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

    Properties props = new Properties();
//    props.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
    props.put("hibernate.hbm2ddl.auto", ddl);
    props.put("hibernate.show_sql", showSql);
    props.put("hibernate.format_sql", formatSql);
 // ✅ optional: giảm log/overhead
    props.put("hibernate.generate_statistics", false);
    
    emf.setJpaProperties(props);

    return emf;
  }

  @Bean
  public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
    return new JpaTransactionManager(emf);
  }
}

