package com.nsd.recruitment.controller;

import com.nsd.recruitment.common.HttpResult;
import com.nsd.recruitment.common.PageRequest;
import com.nsd.recruitment.domain.*;
import com.nsd.recruitment.remote.ProducerRemote;
import com.nsd.recruitment.service.*;
import com.nsd.recruitment.vo.CompanyBasicRespVo;
import com.nsd.recruitment.vo.SearchApplicantVo;
import com.nsd.recruitment.vo.SenderAndReceiverVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/company")
public class CompanyController {
    Logger logger= LoggerFactory.getLogger(CompanyController.class);
    @Autowired
    private CompanyInfoService companyInfoService;
    @Autowired
    private ManagerService managerService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private ApplicantService applicantService;
    @Autowired
    private JobInfoService jobInfoService;
    @Autowired
    private PositionApplicantService positionApplicantService;
    @Autowired
    private ChatMessageService chatMessageService;
    @Autowired
    private ProducerRemote producerRemote;
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private JobCategoryService jobCategoryService;

    @PostMapping(value = "/save")
    public HttpResult saveCustomer(@RequestBody CompanyInfo companyInfo) {
        Long i=companyInfoService.saveCompany(companyInfo);
        if (i==0L){
            return HttpResult.error(123,"全名重复");
        }
        return HttpResult.ok(i);
    }

    @GetMapping(value = "/get/detail")
    public HttpResult showCompanyInfo(String code){
       return HttpResult.ok(companyInfoService.showCompanyInfo(code));

    }

    @GetMapping(value = "/detail/by/id")
    public HttpResult getSingleDetailById(@RequestParam(value = "id")Long id){
        return HttpResult.ok(companyInfoService.companyAndManager(id));
       // return HttpResult.ok(companyInfoService.getCompanyInfoById(id));
    }
    @PostMapping(value = "/update")
    public HttpResult updateCompanyInfo(@RequestBody CompanyInfo companyInfo){



        return HttpResult.ok(companyInfoService.updateCompany(companyInfo));
    }
    @GetMapping(value = "/list")
    public  HttpResult listAllCompany(){


        return HttpResult.ok(companyInfoService.findAllCompanyInfo());
    }

    @PostMapping(value = "/manager/add")
    public HttpResult addManager(@RequestBody ManagerInfo managerInfo){

        return HttpResult.ok(managerService.saveManagerInfo(managerInfo));
    }
    @GetMapping(value = "/detail/manager")
    public HttpResult findManagerDetail(@RequestParam(value = "id")Long id){


    return  HttpResult.ok(managerService.getOneById(id));
    }

    @GetMapping(value = "/search")
    public HttpResult searchCompany(@RequestParam(value = "keyword")String keyword,
                                    @RequestParam(value = "choose")Integer choose){

        return HttpResult.ok(companyInfoService.findCompanyByKeyWord(keyword,choose));
    }
    @GetMapping(value = "/basic/openid")
    public HttpResult findCompanyBasicByOpenid(@RequestParam(value = "openid")String openid){
        logger.info("获取招聘者基本信息");
        CompanyBasicRespVo respVo=userInfoService.companyBasicByOpenid(openid);
        if (respVo==null){
            return HttpResult.ok(1);
        }

    return HttpResult.ok(respVo);
      //  return HttpResult.ok(userInfoService.companyBasicByOpenid(openid));
    }

    @GetMapping(value = "/find/manager/status")
    public HttpResult findManagerStatus(@RequestParam(value = "companyId")Long companyId,
                                        @RequestParam(value = "userId")Long userId){
        Integer status=managerService.findManagerStatus(companyId,userId);
        if (status==null){
            return HttpResult.ok(5);


        }
        return HttpResult.ok(status);

    }

    @GetMapping(value = "/show/resume/detail")
    public HttpResult showResumeDetail(@RequestParam(value = "applicantId")Long applicantId){


        return HttpResult.ok(applicantService.deliveryApplicantPosition(applicantId));

    }

    @GetMapping(value = "/message/list")
    public HttpResult listMessage(@RequestParam(value = "companyId")Long companyId){


        return HttpResult.ok(companyInfoService.listMessageByCompany(companyId));
    }

    @GetMapping(value = "/show/job/detail")
    public HttpResult showJobDetail(@RequestParam(value = "jobId")Long jobId){

        return  HttpResult.ok(jobInfoService.getDetailById(jobId));
    }
    @GetMapping(value = "/delivery/single")
    public HttpResult getDeliveryOne(@RequestParam(value = "deliveryId")Long deliveryId){

        return HttpResult.ok(positionApplicantService.getOneById(deliveryId));
    }
    @PostMapping(value = "/chat/record")
    public HttpResult chatRecord(@RequestBody ChatMessage chatMessage){
      //  return HttpResult.ok(companyInfoService.saveChatRecord(systemMessage));
        return HttpResult.ok(chatMessageService.saveChatRecord(chatMessage));
    }


    @PostMapping(value = "/history/chat")
    public HttpResult showChatHistory(@RequestBody ChatMessage systemMessage){

        return HttpResult.ok(companyInfoService.showChatHistory(systemMessage));

    }

    @PostMapping(value = "/message/record")
    public HttpResult unReadChatRecord(@RequestBody ChatMessage chatMessage){

        return HttpResult.ok(companyInfoService.unreadChatRecord(chatMessage));
    }


    @GetMapping(value = "/post/detail")
    public HttpResult showPostJobDetail(@RequestParam(value = "jobId")Long jobId){

        return  HttpResult.ok(jobInfoService.showPostDetailByCompanyId(jobId));
    }

    @GetMapping(value = "/jobs/release")
    public HttpResult listJobByCompany(@RequestParam(value = "companyId")Long companyId){

        return HttpResult.ok(jobInfoService.listJobByCompany(companyId));
    }
    @PostMapping(value = "/key/word/employee")
    public  HttpResult findEmployeeByKeyword(@RequestBody SearchApplicantVo searchApplicantVo){

        return HttpResult.ok(producerRemote.listApplicantByKeyword(searchApplicantVo));

    }
    @GetMapping(value = "/menu")
    public HttpResult findMenu(@RequestParam(value = "userName")String userName){


        return HttpResult.ok(sysMenuService.findTree(userName,1));
    }
    @GetMapping(value = "/tree/job/category")
    public HttpResult findCategoryTree(@RequestParam(value = "parentId")Long parentId){


        return HttpResult.ok(jobCategoryService.findCategoryTree(parentId));
    }
    @GetMapping("/center/count")
    public HttpResult centerInfoShow(@RequestParam(value = "companyId")Long companyId){

    return HttpResult.ok(companyInfoService.companyCenter(companyId));

    }
    @GetMapping(value = "/managers/list")
    public HttpResult listManager(@RequestParam(value = "companyId")Long companyId){


        return HttpResult.ok(managerService.listManagerByCompany(companyId));
    }
    @PostMapping(value = "/approves/manager")
    public HttpResult approvesManager(@RequestBody ManagerInfo managerInfo){


        return HttpResult.ok(managerService.approvesManager(managerInfo));
    }
    @PostMapping("/add/wei/user")
    public HttpResult addWeiUser(@RequestBody UserInfo userInfo){


        return HttpResult.ok(userInfoService.weiUserInfo(userInfo));
    }

    @PostMapping(value = "/remind/admin")
    public HttpResult remindAdmin(@RequestBody ChatMessage chatMessage){




        return HttpResult.ok(chatMessageService.saveChatRecord(chatMessage));
    }
    @PostMapping(value = "/page")
    public HttpResult findCompanyPage(@RequestBody PageRequest pageRequest){



        return HttpResult.ok(companyInfoService.findPage(pageRequest));
    }

    @PostMapping(value = "/check")
    public HttpResult checkCompany(@RequestBody CompanyInfo companyInfo){


        return HttpResult.ok(companyInfoService.checkCompany(companyInfo));
    }
    @GetMapping(value = "/single")
    public HttpResult getSingleDetail(@RequestParam(value = "id")Long id){

        return HttpResult.ok(companyInfoService.getCompanyInfoById(id));
    }
    @GetMapping(value = "/conversation/basic")
    public  HttpResult findSenderAndReceiver(@RequestParam(value = "jobId")Long jobId,
                                             @RequestParam(value = "applicantId")Long applicantId){
            SenderAndReceiverVo senderAndReceiverVo=new SenderAndReceiverVo();
            Applicant applicant=applicantService.getDetailById(applicantId);
            SenderAndReceiverVo basic=jobInfoService.findChatJob(jobId);
        if (basic==null){
            senderAndReceiverVo.setApplicantName(applicant.getApplicantName());
            senderAndReceiverVo.setHeadUrl(applicant.getHeadUrl());
            return HttpResult.ok(senderAndReceiverVo);
        }
        basic.setApplicantName(applicant.getApplicantName());
        basic.setHeadUrl(applicant.getHeadUrl());
        return HttpResult.ok(basic);


    }
    @GetMapping(value = "/name/to/chat")
    public HttpResult findCompanyNameForChat(@RequestParam(value = "companyId")Long companyId){


        return HttpResult.ok(companyInfoService.detailForChat(companyId));
    }
    @GetMapping(value = "/applicant/basic")
    public HttpResult findChatApplicant(@RequestParam(value = "applicantId")Long applicantId){
        return HttpResult.ok(applicantService.getDetailById(applicantId));
    }
    @GetMapping(value = "/user/by/openid")
    public HttpResult findUserByOpenid(@RequestParam(value = "openid")String openid){


        return HttpResult.ok(userInfoService.findUserByopenid(openid));
    }


}
