package com.tech.paper;

import com.tech.paper.app.InitApplication;
import com.tech.paper.audit.AuditorAwareImpl;
import com.tech.paper.crawler.SpringGeccoEngine;
import com.tech.paper.domain.ArxivId;
import com.tech.paper.service.InitService;
import com.tech.paper.service.impl.ResetServiceImpl;
import com.tech.paper.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.geccocrawler.gecco.GeccoEngine;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.annotation.PostConstruct;

//http://dolszewski.com/spring/running-code-on-spring-boot-startup/
//https://stackoverflow.com/questions/45747933/best-way-to-initialize-beans-in-spring-context-after-application-started
@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@Configuration
public class PaperApplication {

	private static final Logger log = LoggerFactory.getLogger(PaperApplication.class);

	@Autowired
	private InitService initService;

	@Autowired
	private ResetServiceImpl resetServiceImpl;

	@Bean
	public SpringGeccoEngine initGecco() {
		log.info("initGecco");
		/**
		 * 这里的queryArxivId就代表是这次需要执行的id，所以每次成功保存之后都是更新ArxivId为下次需要执行的值
		 */
		ArxivId curId = initService.queryArxivId();
		log.info(curId.toString());
//		log.info(CommonUtil.getNextArxivIdYearMonth(curId).toString());
		String url = CommonUtil.buildUrl(curId);
		log.info(url);
		return new SpringGeccoEngine() {
			@Override
			public void init() {
				GeccoEngine.create()
						.pipelineFactory(springPipelineFactory)
						.classpath("com.tech.paper.crawler")
						.start(url)
						.thread(1)
						.interval(15000)
						.loop(true)
						.mobile(false)
						.start();
			}
		};
	}

	@Bean
	public AuditorAware<String> auditorAware() {
		return new AuditorAwareImpl();
	}

	@PostConstruct
	private void init() {
		log.info("PaperApplication initialization logic ...");
		// ...
//		resetServiceImpl.reset();
	}


	public static void main(String[] args) throws Exception {
		SpringApplication.run(PaperApplication.class, args);
	}

}
