package com.noron.commons.repository.impl.meeting;

import com.noron.commons.data.model.meeting.Meeting;
import com.noron.commons.repository.IMongoRepository;

import java.util.List;
import java.util.Map;

public interface IMeetingRepository extends IMongoRepository<Meeting> {
    String getPageView(String meetingId);

    Integer updateNumView(String meetingId, Integer numView);

    Map<String, Integer> getNumView(List<String> meetingIds);
}
