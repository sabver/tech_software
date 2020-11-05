package com.tech.paper.crawler;

import com.geccocrawler.gecco.spring.ConsolePipeline;
import com.geccocrawler.gecco.spring.SpringPipelineFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfigure {

    @Bean
    public SpringPipelineFactory springPipelineFactory() {
        return new SpringPipelineFactory();
    }

    @Bean(name="consolePipeline")
    public ConsolePipeline consolePipeline() {
        return new ConsolePipeline();
    }

    @Bean(name="arxivListPipeline")
    public ArxivListPipeline arxivListPipeline(){return new ArxivListPipeline();}

    @Bean(name="arxivPipeline")
    public ArxivPipeline arxivPipeline(){return new ArxivPipeline();}

    @Bean(name="arxivDetailPipeline")
    public ArxivDetailPipeline arxivDetailPipeline(){return new ArxivDetailPipeline();}
}
