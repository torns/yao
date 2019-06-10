package com.y3tu.yao.log.server.dao;

import com.y3tu.yao.log.server.model.entity.EsLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

/**
 * elasticsearch 操作日志服务
 *
 * @author y3tu
 */
@Component
public interface EsLogDao extends ElasticsearchRepository<EsLog, String> {

    /**
     * 通过类型获取
     *
     * @param type
     * @return
     */
    Page<EsLog> findByType(Integer type, Pageable pageable);
}
