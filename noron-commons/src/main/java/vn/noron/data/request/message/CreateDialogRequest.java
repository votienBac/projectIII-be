package vn.noron.data.request.message;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CreateDialogRequest {
    Long firstUserId;
    Long secondUserId;

}
