package com.zc.demo.initAndDestroy;

import com.sun.org.apache.xml.internal.security.Init;
import com.zc.demo.beanFactoryPostProcessor.Bean2;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class config {
//    @Bean(initMethod = "init3")
//    public InitBean initBean(){
//        return new InitBean();
//    }

    @Bean(destroyMethod = "destroy3")
    public DestroyBean destroyBean(){
        return new DestroyBean();
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource){
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        return sqlSessionFactoryBean;
    }

}
