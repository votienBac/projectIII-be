package vn.noron.commons.data.model.chat;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Payload {
    private String url;
}
