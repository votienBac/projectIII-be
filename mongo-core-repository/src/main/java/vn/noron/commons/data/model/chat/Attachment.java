package vn.noron.commons.data.model.chat;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Attachment {
    private String type;
    private Payload payload;
}
