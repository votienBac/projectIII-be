package vn.noron.repository.role;

import io.reactivex.rxjava3.core.Single;
import vn.noron.data.tables.pojos.Role;
import vn.noron.repository.IBaseRepository;

import java.util.List;
import java.util.Set;

public interface IRoleRepository extends IBaseRepository<Role, Long> {
    Single<List<Role>> getByNames(Set<String> names);

    Single<List<Role>> getByName(String name);

    Single<List<Role>> getAll();
}
