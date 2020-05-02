package com.nsd.recruitment.service;

import com.nsd.recruitment.domain.JobInfo;
import com.nsd.recruitment.dto.model.PostDetail;
import com.nsd.recruitment.vo.CompanyMessageVo;
import com.nsd.recruitment.vo.JobQueryVo;
import com.nsd.recruitment.vo.PostJobVo;
import com.nsd.recruitment.vo.SenderAndReceiverVo;

import java.util.List;

public interface JobInfoService {
    int saveJobInfo(JobInfo jobInfo);
    List<JobInfo> showAllJobs(Long companyId);
    JobInfo getDetailById(Long id);
    int updateJobInfo(JobInfo jobInfo);
    PostDetail getPostDetailByApplicant(Long id,String openid);
    CompanyMessageVo showChatJobInfo(Long deliveryId);
    PostDetail showPostDetailByCompanyId(Long jobId);
    List<JobInfo> listJobByCompany(Long companyId);
    List<PostJobVo> findJobsByApplicant(Long applicantId);
    List<JobInfo> listAllJobs();
    List<PostJobVo>listAllPostJobs();
    SenderAndReceiverVo findChatJob(Long jobId);
    List<JobInfo> searchJobByKeyword(JobQueryVo jobQueryVo);
    List<PostJobVo> findJobsByConditions(JobQueryVo jobQueryVo);


}
