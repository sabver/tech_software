package com.tech.paper.service;

import com.tech.paper.crawler.ArxivDetailBean;
import com.tech.paper.domain.ArxivId;
import com.tech.paper.domain.Paper;
import org.springframework.data.domain.DomainEvents;

import java.io.IOException;

public interface InitService {
    /**
     * 启动web容器之后需要执行的初始化方法
     * 1.queryArxivId，获取上次记录到的最新paper位置
     * -.checkErrorRecord，将过去获取错误的论文重新拉取一次，这里的功能要不放到拓展功能里面？
     * 3.explore,探索论文，执行fetchPaper进行拉取，如果失败要进行记录，
     * 如果成功了，要记录到ArxivId里面同时要save，这里的ArxivId是特指下次需要进行拉取的id
     */
    @Deprecated
    void init();

    /**
     * 获取ArxivId，这里有个规定，就是这个ArxivId只有一个，用来记录最新拉取到的id
     * @return
     */
    ArxivId queryArxivId();

    /**
     * 检测是否有抓取失败的论文，如果有，重新执行一次抓取，如果这次成功就删除对应的error_record
     */
    void checkErrorRecord();

    /**
     * 探索新的论文数据
     * 这部分逻辑被转移到PaperApplication那里了
     * @param cur
     */
    @Deprecated
    void explore(ArxivId cur);

    /**
     * 拉取一份paper数据
     * @param target
     * @return
     */
    @Deprecated
    Paper fetchPaper(ArxivId target);

    /**
     * 保存一份paper
     * @param paper
     * @return
     */
    boolean savePaper(Paper paper);

    /**
     * 修改当前的ArxivId
     * @param arxivId
     * @return
     */
    boolean saveArxivId(ArxivId arxivId);

    /**
     * 将对应的ArxivDetailBean转换为对应的Paper对象
     * @param bean
     * @return
     */
    Paper changeArxivToPaper(ArxivDetailBean bean);

    /**
     * 保存到搜索引擎里面
     * @param paper
     * @return
     */
    boolean saveLucene(Paper paper) throws IOException;
}
