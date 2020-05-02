package com.nsd.recruitment.controller;

import com.github.pagehelper.PageInfo;
import com.nsd.recruitment.common.HttpResult;
import com.nsd.recruitment.common.PageRequest;
import com.nsd.recruitment.domain.JobCategory;
import com.nsd.recruitment.domain.RecruitRegion;
import com.nsd.recruitment.service.JobCategoryService;
import com.nsd.recruitment.vo.JobCategoryRespVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping(value = "/manage")
public class ManageController {
    @Autowired
    private JobCategoryService jobCategoryService;
    @RequestMapping(value = "/add/category")
    public HttpResult addJobCategory(@RequestBody JobCategory jobCategory){
        jobCategory.setCreateTime(new Timestamp(System.currentTimeMillis()));
        jobCategory.setDelFlag(false);

        return HttpResult.ok(jobCategoryService.addJobCategory(jobCategory));
    }
    @PostMapping(value = "/find/page")
    public HttpResult  findCategoryByPage(@RequestBody PageRequest pageRequest){
       // PageInfo<JobCategoryRespVo>pageInfo=new PageInfo<>(jobCategoryService.findJobCategoryPage(pageRequest));

        return HttpResult.ok(jobCategoryService.findJobCategoryPage(pageRequest));
    }
    @PostMapping(value = "/category/delete")
    public HttpResult batchDelete(@RequestBody List<JobCategory> jobCategoryList){


        return HttpResult.ok(jobCategoryService.deleteBatch(jobCategoryList));
    }
    @GetMapping("/category/tree")
    public HttpResult findCategoryTree(@RequestParam(value = "parentId")Long parentId){


        return HttpResult.ok(jobCategoryService.findCategoryTree(parentId));
    }
    @PostMapping(value = "/region/add")
    public HttpResult addRecruitRegion(@RequestBody RecruitRegion recruitRegion){
        if (recruitRegion.getId()==null||recruitRegion.getId()==0){
        return HttpResult.ok(jobCategoryService.addRecruitRegion(recruitRegion));

        }
        return HttpResult.ok(jobCategoryService.editRegion(recruitRegion));


    }
    @PostMapping(value = "/region/page")
    public HttpResult findRegionPage(@RequestBody PageRequest pageRequest){


        return HttpResult.ok(jobCategoryService.findRecruitRegionPage(pageRequest));
    }
    @PostMapping(value = "/region/edit")
    public HttpResult editRegion(@RequestBody RecruitRegion recruitRegion){

        return HttpResult.ok(jobCategoryService.editRegion(recruitRegion));
    }
    @PostMapping(value = "/region/batch/delete")
    public HttpResult regionMove(@RequestBody List<RecruitRegion> list){


        return HttpResult.ok(jobCategoryService.regionMove(list));
    }


}
