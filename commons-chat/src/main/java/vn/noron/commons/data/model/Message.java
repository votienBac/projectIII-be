package vn.noron.commons.data.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import vn.noron.commons.data.pojo.Attachment;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class Message {
    @JsonProperty("_id")
    private String id;                             // id message (tương đương mid)
    private String senderId;
    private String senderType;
    private Long createdTime;
    private String text;
    private List<Attachment> attachments;        // các url đi kèm (đối với loại tin nhắn attachment)
    private String messageType;                 // loại tin nhắn : TEXT, ATTACHMENT
    private String dialogId;
    private Integer status;                     // trạng thái message (1: active, 0: deleted)
    private Boolean isAnonymous;
}
