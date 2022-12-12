package vn.noron.commons.data.model.user;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AmbassadorPointActive {
    private String userId;
    private Integer point;
    private String date;
    private String monthOfYear;
    private Integer year;
    private Long creationTime;
}
