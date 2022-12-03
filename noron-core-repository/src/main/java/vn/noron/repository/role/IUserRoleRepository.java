package vn.noron.repository.role;

import io.reactivex.rxjava3.core.Single;
import vn.noron.data.model.user.UserRoleDetail;
import vn.noron.data.tables.pojos.UserRole;
import vn.noron.repository.IBaseRepository;

import java.util.List;

public interface IUserRoleRepository extends IBaseRepository<UserRole, Long> {
    Single<List<UserRole>> findByUserId(Long userId);
    Single<List<UserRole>> findByUserIds(List<Long> userIds);
    Single<List<UserRoleDetail>> findDetailByUserIds(List<Long> userIds);
}
