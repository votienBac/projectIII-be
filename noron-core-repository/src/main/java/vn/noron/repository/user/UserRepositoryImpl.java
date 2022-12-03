package vn.noron.repository.user;

import io.reactivex.rxjava3.core.Single;
import org.jooq.Condition;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;
import vn.noron.data.model.paging.Pageable;
import vn.noron.data.tables.pojos.Role;
import vn.noron.data.tables.pojos.User;
import vn.noron.data.tables.records.UserRecord;
import vn.noron.repository.AbsRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static vn.noron.core.template.RxTemplate.rxSchedulerIo;
import static vn.noron.data.Tables.*;
import static vn.noron.repository.utils.MysqlUtil.buildSearchQueries;
import static vn.noron.repository.utils.MysqlUtil.toSortField;

@Repository
public class UserRepositoryImpl extends AbsRepository<UserRecord, User, Long> implements IUserRepository {

    @Override
    protected TableImpl<UserRecord> getTable() {
        return USER;
    }

    @Override
    public Single<Optional<User>> findByUsername(String username) {
        return rxSchedulerIo(() -> dslContext
                .select()
                .from(USER)
                .where((USER.USERNAME.eq(username)
                        .or(USER.EMAIL.eq(username))
                        .or(USER.PHONE_NUMBER.eq(username))).and(fieldDeleted.isNull()))
                .fetchOptionalInto(User.class));
    }
    @Override
    public Single<Long> filterAndSearchCount(String keyword) {
        var conditionVar = new Object() {
            Condition condition = buildSearchQueries(getTable(), keyword);
        };
        return rxSchedulerIo(() -> dslContext.selectCount()
                .from(USER)
                .join(USER_ROLE).on(USER.ID.eq(USER_ROLE.USER_ID))
                .where(conditionVar.condition.and(USER.DELETED_AT.isNull()))
                .fetchOneInto(Long.class));
    }
    @Override
    public Single<List<User>> filterAndPageable(String keyword, Pageable pageable) {
        var conditionVar = new Object() {
            Condition condition = buildSearchQueries(getTable(), keyword);
        };
        return rxSchedulerIo(() -> {
            final List<User> users = dslContext.select(USER.fields())
                    .from(USER)
                    .join(USER_ROLE).on(USER.ID.eq(USER_ROLE.USER_ID))
                    .where(conditionVar.condition.and(USER.DELETED_AT.isNull()))
                    .orderBy(toSortField(pageable.getSort(), getTable().fields()))
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize() + 1)
                    .fetchInto(User.class);
            if (users.size() > pageable.getPageSize()) {
                pageable.setLoadMoreAble(true);
                return users.stream().limit(pageable.getPageSize()).collect(Collectors.toList());
            } else {
                pageable.setLoadMoreAble(false);
                return users;
            }
        });
    }

    @Override
    public Single<List<Role>> getRolesByUsername(String username) {
        return rxSchedulerIo(() -> dslContext.select()
                .from(ROLE)
                .join(USER_ROLE).on(USER_ROLE.ROLE_ID.eq(ROLE.ID))
                .join(USER).on(USER.ID.eq(USER_ROLE.USER_ID))
                .where(USER.USERNAME.eq(username))
                .fetchInto(Role.class));
    }

    @Override
    public Single<Optional<User>> findByEmail(String email) {
        return rxSchedulerIo(() -> dslContext
                .select()
                .from(USER)
                .where(USER.EMAIL.equalIgnoreCase(email))
                .fetchOptionalInto(User.class));
    }
}
