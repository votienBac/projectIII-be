package vn.noron.repository.fcm;

import io.reactivex.rxjava3.core.Single;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;
import vn.noron.data.tables.pojos.FcmTokenUser;
import vn.noron.data.tables.records.FcmTokenUserRecord;
import vn.noron.repository.AbsRepository;

import java.util.List;

import static vn.noron.core.template.RxTemplate.rxSchedulerIo;
import static vn.noron.data.Tables.FCM_TOKEN_USER;
@Repository
public class TokenUserRepositoryImpl extends AbsRepository<FcmTokenUserRecord, FcmTokenUser, Long> implements ITokenUserRepository {
    @Override
    protected TableImpl<FcmTokenUserRecord> getTable() {
        return FCM_TOKEN_USER;
    }

    @Override
    public Single<List<FcmTokenUser>> getByUserIds(List<Long> userIds) {
        return rxSchedulerIo(() -> dslContext.select()
                .from(FCM_TOKEN_USER)
                .where(FCM_TOKEN_USER.USER_ID.in(userIds))
                .fetchInto(FcmTokenUser.class));
    }
}
