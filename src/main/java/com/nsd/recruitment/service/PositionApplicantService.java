package com.nsd.recruitment.service;

import com.nsd.recruitment.domain.PositionApplicant;
import com.nsd.recruitment.domain.query.DeliveryResume;

import java.util.List;

public interface PositionApplicantService {
    Long savePositionApplicant(PositionApplicant positionApplicant);
    int updatePositionApplicant(PositionApplicant positionApplicant);
    int  handleBatchResume(List<PositionApplicant> positionApplicantList);
    PositionApplicant getOneById(Long id);

}
