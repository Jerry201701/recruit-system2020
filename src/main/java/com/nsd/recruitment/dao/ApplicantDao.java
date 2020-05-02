package com.nsd.recruitment.dao;

import com.nsd.recruitment.domain.Applicant;
import com.nsd.recruitment.dto.model.DeliveryInfo;
import com.nsd.recruitment.tkmapper.MyMapper;
import com.nsd.recruitment.vo.ApplicantRespVo;
import com.nsd.recruitment.vo.EducationEsVo;
import com.nsd.recruitment.vo.WorkEsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ApplicantDao extends MyMapper<Applicant> {
    Long getIdByPhone(@Param(value = "phone") String phone);
    List<DeliveryInfo> showAllDeliveryInfo(@Param("company")Long company);
    ApplicantRespVo findApplicantByOpenid(@Param(value = "openid")String openid);
    Applicant applicantDetailByOpenid(@Param(value = "openid")String openid);
    ApplicantRespVo basicApplicantInfo(@Param(value = "openid")String openid);
    Applicant findDetailById(@Param(value = "id")Long id);
    Applicant deliveryApplicantPosition(@Param(value = "applicantId") Long applicantId);
    String findEducationLevel(@Param(value = "applicantId") Long applicantId);
    Long findApplicantIdByOpenid(@Param(value = "openid")String openid);
    List<EducationEsVo> listEducationExperience(@Param(value = "applicantId")Long applicantId);
    List<WorkEsVo> listWorkExperience(@Param(value = "applicantId")Long applicantId);
}
