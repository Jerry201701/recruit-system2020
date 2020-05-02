package com.nsd.recruitment.controller;

import com.nsd.recruitment.common.HttpResult;
import com.nsd.recruitment.domain.JobInfo;
import com.nsd.recruitment.domain.PositionApplicant;
import com.nsd.recruitment.domain.ResultVo;
import com.nsd.recruitment.dto.model.PostDetail;
import com.nsd.recruitment.service.JobInfoService;
import com.nsd.recruitment.service.PositionApplicantService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/job/info")
public class JobController {
    @Autowired
    private JobInfoService jobInfoService;
    @Autowired
    private PositionApplicantService positionApplicantService;
    @PostMapping(value = "/save")
    public HttpResult saveJobInfo(@RequestBody JobInfo jobInfo){

        return HttpResult.ok(jobInfoService.saveJobInfo(jobInfo));
    }
    @GetMapping(value = "/applicant/jobs")
    public HttpResult listJobsByApplicant(){
        return HttpResult.ok(jobInfoService.listAllPostJobs());
      //  return HttpResult.ok(jobInfoService.listAllJobs());
    }


    @GetMapping(value = "/list")
    public HttpResult showAllJobs(@RequestParam(value = "companyId")Long companyId){

        return HttpResult.ok(jobInfoService.showAllJobs(companyId));
    }
    @GetMapping(value = "/detail/applicant")
    public HttpResult getJobDetail(@RequestParam(value = "id") Long id,@RequestParam(value = "openid")String openid){

        return HttpResult.ok(jobInfoService.getPostDetailByApplicant(id,openid));

    }
    @PostMapping(value = "/update")
    public HttpResult updateJobInfo(@RequestBody JobInfo jobInfo){

        return HttpResult.ok(jobInfoService.updateJobInfo(jobInfo));
    }
    /*
    标记面试结果
     */
    @PostMapping(value = "/interview")
    public HttpResult interviewResult(@RequestBody PositionApplicant positionApplicant){

        return HttpResult.ok(positionApplicantService.updatePositionApplicant(positionApplicant));
    }
    /*
    批量标记查看简历结果
     */
    @PostMapping(value = "/batch/handle")
    public HttpResult batchHandleResume(@RequestBody List<PositionApplicant> positionApplicantList){

        return  HttpResult.ok(positionApplicantService.handleBatchResume(positionApplicantList));
    }
    /*
    根据投递记录查询发布的岗位信息
     */
    @GetMapping(value = "/chat")
   public HttpResult showChatJob(@RequestParam(value = "deliveryId")Long deliveryId){


        return HttpResult.ok(jobInfoService.showChatJobInfo(deliveryId));
    }
    @GetMapping(value = "/list/by/applicant")
    public HttpResult listJobByApplicant(@RequestParam(value = "applicantId")Long applicantId){


        return HttpResult.ok(jobInfoService.findJobsByApplicant(applicantId));
    }


}
