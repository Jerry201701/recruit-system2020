package com.nsd.recruitment.dao;

import com.nsd.recruitment.domain.ChatMessage;
import com.nsd.recruitment.vo.ChatRespVo;
import com.nsd.recruitment.vo.ChatVo;
import com.nsd.recruitment.vo.EmployeeMessageVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface SystemMessageDao {
    int addSystemMessage(ChatMessage systemMessage);
    int updateSystemMessageById(ChatMessage systemMessage);
    List<ChatMessage> listMessageByReceiver(@Param(value = "receiverId")Long receiverId);
    Integer countUnreadMessageByApplicant(@Param(value = "applicantId")Long applicantId);
    int changeMessageStatus(@Param(value = "messageId")Long messageId,@Param(value = "status")Boolean status);
    List<ChatRespVo> showChatRecordByReceiver(@Param(value = "receiverId")Long receiverId);
    Long findMessageByReceiverAndSender(ChatMessage systemMessage);
    List<ChatVo>listChatRecordByApplicant(@Param(value = "messageId")Long messageId);
    List<ChatMessage>listChatMessageByApplicantId(@Param(value = "applicantId")Long applicantId);
    List<ChatMessage>listChatMessageByCompanyId(@Param(value = "companyId")Long companyId);
}
