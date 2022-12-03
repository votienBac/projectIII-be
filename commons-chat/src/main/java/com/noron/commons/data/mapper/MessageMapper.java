package com.noron.commons.data.mapper;

import com.noron.commons.constant.meta.MessageConstant;
import com.noron.commons.data.api.tables.pojos.User;
import com.noron.commons.data.model.Message;
import com.noron.commons.data.response.message.MessageInfoResponse;
import com.noron.commons.data.response.message.MessageResponse;
import com.noron.commons.data.response.user.UserInfoResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class MessageMapper {
    public static MessageResponse getMessageResponse(Message message, User user, String dialog) {
        UserInfoResponse userInfoResponse = new UserInfoResponse();
        userInfoResponse.setType(message.getSenderType())
                .setAvatar(user.getAvatarUrl())
                .setFullName(user.getFullName())
                .setId(String.valueOf(user.getId()));
        MessageInfoResponse messageInfoResponse = toMessageInfoResponse(message);
        return new MessageResponse()
                .setTimeStamp(message.getCreationTime().toString())
                .setSender(userInfoResponse)
                .setMessage(messageInfoResponse);
    }
    public static MessageInfoResponse toMessageInfoResponse(Message message) {
        String text = message.getStatus().equals(MessageConstant.ACTIVE) ? message.getText() : "Tin nhắn đã xóa";
        return new MessageInfoResponse()
                .setMid(message.getId())
                .setText(text)
                .setStatus(message.getStatus().equals(MessageConstant.ACTIVE) ? "active" : "deleted")
                .setCreationTime(message.getCreationTime())
                .setMessageType(message.getMessageType())
                .setAttachments(message.getAttachments());
    }


}
