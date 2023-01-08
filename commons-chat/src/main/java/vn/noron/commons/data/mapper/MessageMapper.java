package vn.noron.commons.data.mapper;

import vn.noron.commons.constant.meta.MessageConstant;

import vn.noron.commons.data.model.Message;
import vn.noron.commons.data.response.message.MessageInfoResponse;
import vn.noron.commons.data.response.message.MessageResponse;
import vn.noron.commons.data.response.user.UserInfoResponse;
import org.mapstruct.Mapper;
import vn.noron.data.tables.pojos.User;

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
                .setTimeStamp(message.getCreatedTime().toString())
                .setSender(userInfoResponse)
                .setMessage(messageInfoResponse);
    }
    public static MessageInfoResponse toMessageInfoResponse(Message message) {
        String text = message.getStatus().equals(MessageConstant.ACTIVE) ? message.getText() : "Tin nhắn đã xóa";
        return new MessageInfoResponse()
                .setMid(message.getId())
                .setText(text)
                .setStatus(message.getStatus().equals(MessageConstant.ACTIVE) ? "active" : "deleted")
                .setCreatedTime(message.getCreatedTime())
                .setMessageType(message.getMessageType())
                .setAttachments(message.getAttachments());
    }


}
