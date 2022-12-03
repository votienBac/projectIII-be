package com.noron.commons.data.model.meeting;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class Meeting {
    @JsonProperty("_id")
    private String id;
    private String uuid;
    private String roomZoomId;
    private String hostId;
    private String hostMail;
    private String topic;// tương ứng với tittle
    private String duration; // thời gian diễn ra
    private String startUrl; // link bắt đầu
    private String joinUrl; // link vào
    private String registrationUrl; // link đăng ký
    private String password;
    private String contactName;
    private String contactMail;
    private String createAt;
    private Long startTime;
    private Long endTime;
    //
    private String typePlatform;
    private Integer numView;
    private Integer numComment;
    private Integer numLike;
    private Integer numDislike;
    private Integer numPageview;
    private String src;
    private String title;
    private String ownerId;
    private String type;
    private String coverUrl;
    private String description;
    private List<String> topicIds;
    private Integer status;
    private Long creationTime;
    private Long lastModifiedTime;
    private Long lastCommentAt;
    private Long numCoin = 0L;
}
