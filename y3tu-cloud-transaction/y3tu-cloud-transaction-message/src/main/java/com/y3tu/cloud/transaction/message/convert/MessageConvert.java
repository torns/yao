package com.y3tu.cloud.transaction.message.convert;

import com.y3tu.cloud.transaction.message.dto.MessageLogDto;
import com.y3tu.cloud.transaction.message.pojo.MessageLog;
import org.mapstruct.factory.Mappers;


@org.mapstruct.Mapper
public interface MessageConvert {
    MessageConvert MAPPER = Mappers.getMapper(MessageConvert.class);

    MessageLog dtoToPojo(MessageLogDto messageLogDto);

}
