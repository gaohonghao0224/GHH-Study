package com.zc.demo.ying;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;

import javax.sql.DataSource;


public  class AbstractTest implements ApplicationEventPublisher {

    @Override
    public void publishEvent(Object event) {

    }
}
