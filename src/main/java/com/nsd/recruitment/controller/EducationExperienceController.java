package com.nsd.recruitment.controller;

import com.nsd.recruitment.common.HttpResult;
import com.nsd.recruitment.domain.EducationExperience;
import com.nsd.recruitment.domain.ResultVo;
import com.nsd.recruitment.service.EducationExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/education")
public class EducationExperienceController {
    @Autowired
    private EducationExperienceService educationExperienceService;
    /*
     * 添加教育经历
     */
    @PostMapping(value = "/add")
    public HttpResult saveEducationExperience(@RequestBody EducationExperience educationExperience){

        if (educationExperience.getId()==null){
        Long i=educationExperienceService.saveEducationExperience(educationExperience);
        return HttpResult.ok(i);

        }
        return HttpResult.ok(educationExperienceService.updateEducationExperience(educationExperience));


    }
    /*
    获取单条教育经历记录
     */
    @GetMapping(value = "/get/one")
    public HttpResult getOneById(@RequestParam(value = "id") Long id){

    return  HttpResult.ok(educationExperienceService.getOneById(id));
}
    /*
    删除教育经历
     */
    @GetMapping(value = "/move")
        public HttpResult moveEducationExperience(@RequestParam(value = "id") Long id){

        return HttpResult.ok(educationExperienceService.removeEducationExperience(id));
    }


}
