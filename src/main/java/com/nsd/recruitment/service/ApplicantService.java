package com.nsd.recruitment.service;

import com.nsd.recruitment.domain.Applicant;
import com.nsd.recruitment.domain.ChatMessage;
import com.nsd.recruitment.domain.ConversationMessage;
import com.nsd.recruitment.dto.model.DeliveryInfo;
import com.nsd.recruitment.vo.ApplicantRespVo;
import com.nsd.recruitment.vo.ChatRespVo;
import com.nsd.recruitment.vo.EmployeeMessageVo;



import java.util.List;

public interface ApplicantService {
    Long saveApplicant(Applicant applicants);
    Applicant getDetailById(Long id);
    int updateApplicantInfo(Applicant applicant);
    List<DeliveryInfo> showAllDeliveryInfo(Long companyId);
    ApplicantRespVo basicApplicantInfo(String openid);
    Applicant applicantDetailById(Long id);
    Applicant deliveryApplicantPosition(Long applicantId);
    List<ChatMessage> listMessageByReceiver(Long receiverId);
    Integer countUnreadMessage(String openid);
    int changeMessageStatus(Long messageId);
    List<ChatRespVo> showChatRecordByReceiver(Long receiverId);
    List<ChatMessage> listChatMessage(Long messageId);
    List<ChatMessage> chatRecordHistory(ChatMessage chatMessage);
    Long findApplicantIdByOpenid(String openid);
    List<Applicant> listApplicant();





}
