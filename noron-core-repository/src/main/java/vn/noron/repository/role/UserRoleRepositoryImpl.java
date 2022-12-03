package vn.noron.repository.role;

import io.reactivex.rxjava3.core.Single;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;
import vn.noron.data.model.user.UserRoleDetail;
import vn.noron.data.tables.pojos.UserRole;
import vn.noron.data.tables.records.UserRoleRecord;
import vn.noron.repository.AbsRepository;

import java.util.List;

import static vn.noron.core.template.RxTemplate.rxSchedulerIo;
import static vn.noron.data.tables.Role.ROLE;
import static vn.noron.data.tables.UserRole.USER_ROLE;

@Repository
public class UserRoleRepositoryImpl
        extends AbsRepository<UserRoleRecord, UserRole, Long>
        implements IUserRoleRepository {
    @Override
    protected TableImpl<UserRoleRecord> getTable() {
        return USER_ROLE;
    }

    @Override
    public Single<List<UserRole>> findByUserId(Long userId) {
        return rxSchedulerIo(() -> dslContext
                .select()
                .from(USER_ROLE)
                .where(USER_ROLE.USER_ID.eq(userId))
                .fetchInto(UserRole.class));
    }
    @Override
    public Single<List<UserRole>> findByUserIds(List<Long> userIds) {
        return rxSchedulerIo(() -> dslContext
                .select()
                .from(USER_ROLE)
                .where(USER_ROLE.USER_ID.in(userIds))
                .fetchInto(UserRole.class));
    }

    @Override
    public Single<List<UserRoleDetail>> findDetailByUserIds(List<Long> userIds) {
        return rxSchedulerIo(() -> dslContext
                .select(USER_ROLE.USER_ID, USER_ROLE.ROLE_ID, ROLE.NAME)
                .from(USER_ROLE)
                .join(ROLE).on(USER_ROLE.ROLE_ID.eq(ROLE.ID))
                .where(USER_ROLE.USER_ID.in(userIds))
                .and(ROLE.DELETED_AT.isNull())
                .fetchInto(UserRoleDetail.class));
    }
}
