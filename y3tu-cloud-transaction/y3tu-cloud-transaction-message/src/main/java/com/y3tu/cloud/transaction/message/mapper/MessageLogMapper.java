package com.y3tu.cloud.transaction.message.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.y3tu.cloud.transaction.message.pojo.MessageLog;
import org.apache.ibatis.annotations.Select;


public interface MessageLogMapper extends BaseMapper<MessageLog> {

    @Select("SELECT msg.* FROM `message_log` msg WHERE message_id = #{messageId}")
    MessageLog queryMessageLogByMessageId(String messageId);
}
