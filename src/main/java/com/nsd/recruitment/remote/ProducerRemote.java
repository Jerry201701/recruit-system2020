package com.nsd.recruitment.remote;


import com.nsd.recruitment.domain.Applicant;
import com.nsd.recruitment.domain.CompanyInfo;
import com.nsd.recruitment.domain.JobInfo;
import com.nsd.recruitment.vo.JobQueryVo;
import com.nsd.recruitment.vo.SearchApplicantVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 调用生产者服务
 */
@FeignClient("elastic-service-test")
public interface ProducerRemote {

    @PostMapping("/applicant/keyword")
    List<Applicant> listApplicantByKeyword(@RequestBody SearchApplicantVo searchApplicantVo);
    @GetMapping("/company/keyword")
    List<CompanyInfo> searchCompanyByKeyword(@RequestParam(value = "keyword")String keyword,
                                             @RequestParam(value = "choose") Integer choose);
    @PostMapping(value = "/search/job")
    List<JobInfo> searchJobByKeyword(@RequestBody JobQueryVo jobQueryVo);
}
