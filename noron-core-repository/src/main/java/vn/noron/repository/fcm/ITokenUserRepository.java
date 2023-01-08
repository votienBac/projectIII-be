package vn.noron.repository.fcm;

import io.reactivex.rxjava3.core.Single;
import vn.noron.data.tables.pojos.FcmTokenUser;
import vn.noron.repository.IBaseRepository;

import java.util.List;

public interface ITokenUserRepository extends IBaseRepository<FcmTokenUser, Long> {

    Single<List<FcmTokenUser>> getByUserIds(List<Long> userIds);

}

