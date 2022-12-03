package com.noron.commons.data.model.analytics;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class UserPostViewTime {
    private String postId;
    private String userId;
    private Long numPageview;
    private List<Long> dates;
}