package vn.noron.api.service.fcm;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import io.reactivex.rxjava3.core.Single;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.noron.data.fcm.FcmTokenRequest;
import vn.noron.data.tables.pojos.FcmTokenUser;
import vn.noron.repository.fcm.ITokenUserRepository;
import vn.noron.repository.user.IUserRepository;

import java.util.List;
import java.util.Optional;

import static vn.noron.data.Tables.*;

@Service
@Slf4j
@Log4j2
public class FcmServiceImpl implements IFcmService{
    private final IUserRepository userRepository;
    private final ITokenUserRepository tokenUserRepository;
    private final FirebaseMessaging firebaseMessaging;

    public FcmServiceImpl(IUserRepository userRepository, ITokenUserRepository tokenUserRepository, FirebaseMessaging firebaseMessaging) {
        this.userRepository = userRepository;
        this.tokenUserRepository = tokenUserRepository;
        this.firebaseMessaging = firebaseMessaging;
    }

    @Override
    public Single<Optional<FcmTokenUser>> saveUserFcmToken(FcmTokenRequest request) {
        return tokenUserRepository.insertOnConflictKeyUpdate(new FcmTokenUser()
                .setUserId(request.getUserId())
                .setToken(request.getToken()),
                List.of(FCM_TOKEN_USER.USER_ID, FCM_TOKEN_USER.TOKEN));
    }

    @Override
    public Single<Boolean> registerTokensToTopic(List<String> tokens, String topic) {
        try {
            firebaseMessaging.subscribeToTopic(tokens, topic);
        } catch (FirebaseMessagingException e) {
            log.error("Firebase subscribe to topic fail", e);
        }
        return Single.just(true);
    }


}
