package vn.noron.commons.data.response.message;

import vn.noron.commons.data.pojo.Attachment;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class MessageInfoResponse {
    private String mid;
    private String dialogId;
    private String text;
    private String status;
    private String messageType;
    private Long creationTime;
    private List<Attachment> attachments;
}
