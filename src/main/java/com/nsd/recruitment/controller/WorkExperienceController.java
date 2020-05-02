package com.nsd.recruitment.controller;

import com.nsd.recruitment.common.HttpResult;
import com.nsd.recruitment.domain.WorkExperience;
import com.nsd.recruitment.service.WorkExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/work")
public class WorkExperienceController {

    @Autowired
    private WorkExperienceService workExperienceService;


    /*
     * 添加和修改工作经历
     */
    @PostMapping(value = "/add")
    public HttpResult saveWorkExperience(@RequestBody WorkExperience workExperience){

        if (workExperience.getId()==null){

        return HttpResult.ok(workExperienceService.saveWorkExperience(workExperience));
        }


        return HttpResult.ok(workExperienceService.updateWorkExperience(workExperience));
    }

        /*
        获取单条工作经历
         */
        @GetMapping(value = "/get/one")
        public HttpResult getOneById(@RequestParam(value = "id") Long id){

            return  HttpResult.ok(workExperienceService.getOneById(id));
        }

        /*
       修改工作经历
         */
        @PostMapping(value = "/update")
        public HttpResult updateWorkExperience(@RequestBody WorkExperience workExperience){


            return HttpResult.ok(workExperienceService.updateWorkExperience(workExperience));
        }
    /*
    删除单条工作经历
     */
    @GetMapping(value = "/move")
    public  HttpResult moveWorkExperience(@RequestParam(value = "id") Long id){

        return  HttpResult.ok(workExperienceService.moveWorkExperience(id));

    }
}
