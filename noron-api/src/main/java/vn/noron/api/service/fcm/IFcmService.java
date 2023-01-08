package vn.noron.api.service.fcm;

import io.reactivex.rxjava3.core.Single;
import vn.noron.data.fcm.FcmTokenRequest;
import vn.noron.data.tables.pojos.FcmTokenUser;

import java.util.List;
import java.util.Optional;

public interface IFcmService {

    Single<Optional<FcmTokenUser>> saveUserFcmToken(FcmTokenRequest request);
    Single<Boolean> registerTokensToTopic(List<String> tokens, String topic);
}
