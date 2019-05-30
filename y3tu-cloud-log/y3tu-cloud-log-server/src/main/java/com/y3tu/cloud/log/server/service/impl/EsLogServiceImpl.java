package com.y3tu.cloud.log.server.service.impl;

import com.y3tu.cloud.log.server.dao.EsLogDao;
import com.y3tu.cloud.log.server.model.dto.SearchDto;
import com.y3tu.cloud.log.server.service.EsLogService;
import com.y3tu.cloud.log.server.model.entity.EsLog;
import com.y3tu.tool.core.date.DateUtil;
import com.y3tu.tool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * elasticsearch 操作日志服务实现
 *
 * @author y3tu
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class EsLogServiceImpl implements EsLogService {

    @Autowired
    private EsLogDao logDao;

    @Override
    public EsLog saveLog(EsLog esLog) {
        return logDao.save(esLog);
    }

    @Override
    public void deleteLog(String id) {
        logDao.deleteById(id);
    }

    @Override
    public void deleteAll() {
        logDao.deleteAll();
    }

    @Override
    public Page<EsLog> findByCondition(Integer type, String key, SearchDto searchDto, Pageable pageable) {
        if (type == null && StrUtil.isBlank(key) && StrUtil.isBlank(searchDto.getStartDate())) {
            // 无过滤条件获取全部
            return logDao.findAll(pageable);
        } else if (type != null && StrUtil.isBlank(key) && StrUtil.isBlank(searchDto.getStartDate())) {
            // 仅有type
            return logDao.findByType(type, pageable);
        }

        QueryBuilder qb;

        QueryBuilder qb0 = QueryBuilders.termQuery("logType", type);
        QueryBuilder qb1 = QueryBuilders.multiMatchQuery(key, "name", "requestUrl", "requestType", "requestParam", "username", "ip", "ipInfo");
        // 在有type条件下
        if (StrUtil.isNotBlank(key) && StrUtil.isBlank(searchDto.getStartDate()) && StrUtil.isBlank(searchDto.getEndDate())) {
            // 仅有key
            qb = QueryBuilders.boolQuery().must(qb0).must(qb1);
        } else if (StrUtil.isBlank(key) && StrUtil.isNotBlank(searchDto.getStartDate()) && StrUtil.isNotBlank(searchDto.getEndDate())) {
            // 仅有时间范围
            Long start = DateUtil.parse(searchDto.getStartDate()).getTime();
            Long end = DateUtil.endOfDay(DateUtil.parse(searchDto.getEndDate())).getTime();
            QueryBuilder qb2 = QueryBuilders.rangeQuery("timeMillis").gte(start).lte(end);
            qb = QueryBuilders.boolQuery().must(qb0).must(qb2);
        } else {
            // 两者都有
            Long start = DateUtil.parse(searchDto.getStartDate()).getTime();
            Long end = DateUtil.endOfDay(DateUtil.parse(searchDto.getEndDate())).getTime();
            QueryBuilder qb2 = QueryBuilders.rangeQuery("timeMillis").gte(start).lte(end);
            qb = QueryBuilders.boolQuery().must(qb0).must(qb1).must(qb2);
        }

        //多字段搜索
        return logDao.search(qb, pageable);
    }
}
