package com.nsd.recruitment.dao;

import com.nsd.recruitment.domain.ChatRecord;
import com.nsd.recruitment.tkmapper.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ChatRecordDao extends MyMapper<ChatRecord> {


}
