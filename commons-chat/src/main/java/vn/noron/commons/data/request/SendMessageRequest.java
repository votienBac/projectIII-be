package vn.noron.commons.data.request;

import lombok.Data;
import lombok.experimental.Accessors;
import vn.noron.commons.data.pojo.Attachment;

import java.util.List;

@Data
@Accessors(chain = true)
public class SendMessageRequest {

    private String senderId;
    private String text;
    private List<Attachment> attachments;        // các url đi kèm (đối với loại tin nhắn attachment)
    private String messageType;                 // loại tin nhắn : TEXT, ATTACHMENT
    private String dialogId;

}
