package vn.noron.commons.data.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class Attachment {
    private String type;
    private Payload payload;
}
