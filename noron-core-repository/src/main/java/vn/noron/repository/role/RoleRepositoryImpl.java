package vn.noron.repository.role;

import io.reactivex.rxjava3.core.Single;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;
import vn.noron.data.tables.pojos.Role;
import vn.noron.data.tables.records.RoleRecord;
import vn.noron.repository.AbsRepository;

import java.util.List;
import java.util.Set;

import static vn.noron.core.template.RxTemplate.rxSchedulerIo;
import static vn.noron.data.tables.Role.ROLE;

@Repository
public class RoleRepositoryImpl extends AbsRepository<RoleRecord, Role, Long> implements IRoleRepository{
    @Override
    protected TableImpl<RoleRecord> getTable() {
        return ROLE;
    }

    @Override
    public Single<List<Role>> getByNames(Set<String> names) {
        return rxSchedulerIo(()->dslContext
                .select()
                .from(ROLE)
                .where(ROLE.NAME.in(names))
                .fetchInto(Role.class));
    }

    @Override
    public Single<List<Role>> getByName(String name) {
        return rxSchedulerIo(()->dslContext
                .select()
                .from(ROLE)
                .where(ROLE.NAME.equalIgnoreCase(""))
                .fetchInto(Role.class));
    }

    @Override
    public Single<List<Role>> getAll() {
        return rxSchedulerIo(() -> dslContext.selectFrom(ROLE)
                .where(ROLE.DELETED_AT.isNull())
                .fetchInto(Role.class));
    }
}
