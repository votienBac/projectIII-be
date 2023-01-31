package vn.noron.api.service.reportroom;

import io.reactivex.rxjava3.core.Single;
import vn.noron.data.model.paging.Pageable;
import vn.noron.data.request.reportroom.ReportRoomRequest;
import vn.noron.data.response.reportroom.ReportRoomResponse;

import java.util.List;

public interface IReportRoomService {
    Single<String> insert(ReportRoomRequest request);

    Single<List<ReportRoomResponse>> getPageable(Pageable pageable, String keyword);

    Single<List<ReportRoomResponse>> getAllReportOfRoom(String roomId, Pageable pageable);
}
