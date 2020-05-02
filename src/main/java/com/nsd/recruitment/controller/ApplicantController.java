package com.nsd.recruitment.controller;

import com.nsd.recruitment.common.HttpResult;
import com.nsd.recruitment.domain.*;
import com.nsd.recruitment.dto.model.DeliveryInfo;
import com.nsd.recruitment.service.*;
import com.nsd.recruitment.vo.ApplicantRespVo;
import com.nsd.recruitment.vo.JobQueryVo;
import com.nsd.recruitment.vo.SenderAndReceiverVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationEvent;
import java.util.List;

@RestController
@RequestMapping("/applicant")
public class ApplicantController {
    @Autowired
    private ApplicantService applicantService;
    @Autowired
    private PositionApplicantService positionApplicantService;
    @Autowired
    private JobInfoService jobInfoService;
    @Autowired
    private ChatMessageService chatMessageService;
    @Autowired
    private CompanyInfoService companyInfoService;
    @Autowired
    private JobCategoryService jobCategoryService;

    @RequestMapping(value = "/save")
    public HttpResult saveApplicantInfo(@RequestBody Applicant applicant){


        return HttpResult.ok(applicantService.saveApplicant(applicant));
    }

    @GetMapping(value = "/show/detail")
    public HttpResult showApplicantByOpenid(@RequestParam(value = "id")Long id){

    Applicant applicant=applicantService.applicantDetailById(id);
    if (applicant==null){
       return HttpResult.error();
    }
//        List<WorkExperience>workExperienceList=workExperienceService.listWorkExperienceByopenid(openid);
//        List<EducationExperience>educationExperienceList=educationExperienceService.listEducationExperienceByopenid(openid);
//        applicant.setWorkExperienceList(workExperienceList);
//        applicant.setEducationExperienceList(educationExperienceList);
        return HttpResult.ok(applicant);
    }

    @GetMapping(value = "/get/detail/id")
    public HttpResult getDetailById(@RequestParam(value = "id") Long id){

       Applicant applicant= applicantService.getDetailById(id);
       if (applicant==null){

           return HttpResult.error();
       }
      return HttpResult.ok(applicant);

    }
    @PostMapping(value = "/update")
    public  HttpResult updateApplicantInfo(@RequestBody Applicant applicant){

        return HttpResult.ok(applicantService.updateApplicantInfo(applicant));
    }
    /*
    投递简历
     */

    @PostMapping(value = "/delivery/resume")
    public HttpResult deliveryResume(@RequestBody PositionApplicant positionApplicant){

        return HttpResult.ok(positionApplicantService.savePositionApplicant(positionApplicant));
    }
    @GetMapping(value = "/show/resume")
    public HttpResult showAllResumeByCompany(@RequestParam(value = "companyId") Long companyId){

        List<DeliveryInfo> list= applicantService.showAllDeliveryInfo(companyId);
        if (list!=null && list.size()>0){
           return HttpResult.ok(list);
        }
        return null;
    }
    /**
     * 根据openid查询求职者是否注册过，是否创建了简历
     */

    @GetMapping(value = "/find/basic")
    public  HttpResult showApplicantInfoByOpenid(@RequestParam(value = "openid")String openid){

        ApplicantRespVo respVo=applicantService.basicApplicantInfo(openid);

        if (respVo==null){
            return HttpResult.ok(1);
        }
        if (respVo.getUserId()!=null&&respVo.getApplicantId()==null){
            respVo.setStatus(2);
        }
        if(respVo.getApplicantId()!=null){
            respVo.setStatus(3);
        }


        return  HttpResult.ok(respVo);
    }

    @GetMapping(value = "/find/job/detail")
    public HttpResult findJobDetail(@RequestParam(value = "jobId")Long jobId){


        return HttpResult.ok(jobInfoService.getDetailById(jobId));
    }
    @GetMapping(value = "/list/message")
    public HttpResult listMessageByEmployee(@RequestParam(value = "receiverId")Long receiverId){

        return HttpResult.ok(applicantService.listMessageByReceiver(receiverId));
    }
    @GetMapping(value = "/count/unread")
    public HttpResult countUnreadMessage(@RequestParam(value = "openid")String openid){

        return HttpResult.ok(applicantService.countUnreadMessage(openid));

    }
    @GetMapping(value = "/message/status")
    public  HttpResult changeMessageStatus(@RequestParam(value = "messageId")Long messageId){
        return HttpResult.ok(applicantService.changeMessageStatus(messageId));
    }
    @PostMapping("/chat/record")
    public HttpResult recordChat(@RequestBody ChatMessage systemMessage){

    return HttpResult.ok(chatMessageService.saveChatRecord(systemMessage));
      //  return HttpResult.ok(companyInfoService.saveChatRecord(systemMessage));
    }
    @GetMapping("/find/chat/record")
    public HttpResult findChatRecord(@RequestParam(value = "receiverId")Long receiverId){


        return HttpResult.ok(applicantService.showChatRecordByReceiver(receiverId));
    }
    @PostMapping(value = "/chat/message/list")
    public HttpResult listChatMessage(@RequestBody ChatMessage chatMessage){


        return HttpResult.ok(chatMessageService.listChatUnReadRecord(chatMessage));

    }

    /*
    查看历史聊天记录
     */
    @RequestMapping(value = "/chat/history")
    public HttpResult showChatRecordHistory(@RequestBody ChatMessage chatMessage){



    return HttpResult.ok(applicantService.chatRecordHistory(chatMessage));

    }
    @GetMapping(value = "/get/applicant/openid")
    public HttpResult getApplicantByOpenid(@RequestParam(value = "openid")String openid){
        return HttpResult.ok(applicantService.findApplicantIdByOpenid(openid));
    }
    @GetMapping(value = "/all")
    public HttpResult listAllApplicant(){
        return HttpResult.ok(applicantService.listApplicant());
    }
    @GetMapping(value = "/user/find/openid")
    public HttpResult findUserIdByOpenid(@RequestParam(value = "openid")String openid){

        return HttpResult.ok(applicantService.basicApplicantInfo(openid));
    }

    @GetMapping(value = "/sender/receiver")
    public HttpResult findSenderAndReceiverInfo(@RequestParam(value = "jobId")Long jobId,
                                                @RequestParam(value = "applicantId")Long applicantId){
        SenderAndReceiverVo senderAndReceiverVo=jobInfoService.findChatJob(jobId);
        Applicant applicant=applicantService.getDetailById(applicantId);
        senderAndReceiverVo.setApplicantName(applicant.getApplicantName());
        senderAndReceiverVo.setHeadUrl(applicant.getHeadUrl());
        return HttpResult.ok(senderAndReceiverVo);

    }
    @GetMapping(value = "/company/basic")
    public HttpResult findCompanyBasic(@RequestParam(value = "companyId")Long companyId){

        return HttpResult.ok(companyInfoService.getCompanyInfoById(companyId));
    }

    @PostMapping(value = "/search/job/key")
    public HttpResult findJobByKeyword(@RequestBody JobQueryVo jobQueryVo){



        return HttpResult.ok(jobInfoService.searchJobByKeyword(jobQueryVo));
    }
    @PostMapping(value = "/job/list")
    public HttpResult findJobByConditions(@RequestBody JobQueryVo jobQueryVo){

        if (jobQueryVo.getCategoryName().length()>9){
            jobQueryVo.setCategoryName(null);
        }
        if (jobQueryVo.getRegionName().length()>9){
            jobQueryVo.setRegionName(null);
        }
        return HttpResult.ok(jobInfoService.findJobsByConditions(jobQueryVo));
    }
    @GetMapping(value = "/region/list")
    public HttpResult findAllRegion(){



        return HttpResult.ok(jobCategoryService.findAllRegion());
    }



}
