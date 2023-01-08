package vn.noron.repository.user;

import io.reactivex.rxjava3.core.Single;
import vn.noron.data.fcm.FcmTokenRequest;
import vn.noron.data.model.paging.Pageable;
import vn.noron.data.tables.pojos.Role;
import vn.noron.data.tables.pojos.User;
import vn.noron.repository.IBaseRepository;

import java.util.List;
import java.util.Optional;

public interface IUserRepository extends IBaseRepository<User, Long> {
    Single<Optional<User>> findByUsername(String username);

    Single<Long> filterAndSearchCount(String keyword);

    Single<List<User>> filterAndPageable(String keyword, Pageable pageable);

    Single<List<Role>> getRolesByUsername(String username);
    Single<Optional<User>> findByEmail(String email);

    Single<List<User>> findByEmails(List<String> emails);

}
