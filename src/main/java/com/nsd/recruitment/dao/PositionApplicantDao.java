package com.nsd.recruitment.dao;

import com.nsd.recruitment.domain.PositionApplicant;
import com.nsd.recruitment.tkmapper.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface PositionApplicantDao extends MyMapper<PositionApplicant> {
}
