package vn.noron.data.log;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ReferralData {
    private String referrerUri;
    private String referralSource;
    private String referralMedium;
    private String referralChannel;
    private String referralDomain;
    private String referralApp;
    private String referralOs;
}
