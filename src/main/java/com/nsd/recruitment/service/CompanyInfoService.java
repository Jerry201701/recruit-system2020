package com.nsd.recruitment.service;

import com.github.pagehelper.Page;
import com.nsd.recruitment.common.PageRequest;
import com.nsd.recruitment.common.PageResult;
import com.nsd.recruitment.domain.Applicant;
import com.nsd.recruitment.domain.CompanyInfo;
import com.nsd.recruitment.domain.ConversationMessage;
import com.nsd.recruitment.domain.ChatMessage;
import com.nsd.recruitment.vo.CompanyAndManagerVo;
import com.nsd.recruitment.vo.CompanyCenterVo;

import java.util.List;

public interface CompanyInfoService {

    List<CompanyInfo> findAllCompanyInfo();
    CompanyInfo getCompanyInfoById(Long id);
    List<CompanyInfo> getCompanysByName(CompanyInfo companyInfo);
    Long saveCompany(CompanyInfo companyInfo);
    int updateCompany(CompanyInfo companyInfo);
    int moveCompany(CompanyInfo companyInfo);
    List<CompanyInfo> queryOnConditions(CompanyInfo companyInfo);
    Page<CompanyInfo> findByPage(int pageNo, int pageSize);
    CompanyInfo showCompanyInfo(String code);
    List<CompanyInfo> findCompanyByKeyWord(String keyWord,Integer choose);
    Long saveChatRecord(ChatMessage systemMessage);
    List<ChatMessage> showChatHistory(ChatMessage systemMessage);
    List<ChatMessage> listMessageByCompany(Long companyId);
    List<ChatMessage> unreadChatRecord(ChatMessage chatMessage);
    List<Applicant> findApplicantByKeyword(String keyword);
    CompanyCenterVo companyCenter(Long companyId);
    CompanyAndManagerVo companyAndManager(Long companyId);
    PageResult findPage(PageRequest pageRequest);
    int checkCompany(CompanyInfo companyInfo);
    CompanyInfo detailForChat(Long companyId);
    List<Long>findIdByFullName(String fullName);




}
