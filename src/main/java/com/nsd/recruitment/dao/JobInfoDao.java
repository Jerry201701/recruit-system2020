package com.nsd.recruitment.dao;

import com.nsd.recruitment.domain.JobInfo;
import com.nsd.recruitment.dto.model.PostDetail;
import com.nsd.recruitment.tkmapper.MyMapper;
import com.nsd.recruitment.vo.CompanyMessageVo;
import com.nsd.recruitment.vo.JobQueryVo;
import com.nsd.recruitment.vo.PostJobVo;
import com.nsd.recruitment.vo.SenderAndReceiverVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface JobInfoDao extends MyMapper<JobInfo> {
   PostDetail getPostDetailByApplicant(@Param(value = "id") Long id,@Param(value = "openid")String openid);
   CompanyMessageVo showChatJobInfo(@Param(value = "deliveryId")Long deliveryId);
   PostDetail showPostDetailByCompanyId(@Param(value = "jobId")Long jobId);
   List<PostJobVo> findDeliveryJobByApplicant(@Param(value = "applicantId")Long applicantId);
   List<PostJobVo>listAllPostJobs();
   SenderAndReceiverVo findChatJob(@Param(value = "jobId")Long jobId);
   List<PostJobVo> findJobsByConditions(JobQueryVo jobQueryVo);
}
