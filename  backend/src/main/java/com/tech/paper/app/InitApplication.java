package com.tech.paper.app;

import com.tech.paper.domain.ArxivId;
import com.tech.paper.service.InitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Deprecated
@Component
public class InitApplication {

    private static final Logger log = LoggerFactory.getLogger(InitApplication.class);

    @Autowired
    private InitService initService;

    @PostConstruct
    private void init() {
        log.info("InitApplication initialization logic ...");
        // ...
//        initService.init();
    }

}
