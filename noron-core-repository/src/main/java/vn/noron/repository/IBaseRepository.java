package vn.noron.repository;

import io.reactivex.rxjava3.core.Single;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.TableField;
import vn.noron.core.exception.DBException;
import vn.noron.data.model.paging.Pageable;
import vn.noron.repository.utils.UpdateField;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface IBaseRepository<P, ID> {
    /**
     * Saves a given entity. Use the returned instance for further operations as the save operation might have changed the
     * entity instance completely.
     *
     * @param pojo must not be {@literal null}.
     * @return the saved entity will never be {@literal null}.
     */
    Single<Integer> insert(P pojo);

    Single<Optional<P>> insertReturning(P pojo);

    Single<Optional<P>> insertOnDuplicateKeyUpdate(P pojo);

    /**
     * Saves all given entities.
     *
     * @param pojos must not be {@literal null}.
     * @return the saved entities will never be {@literal null}.
     * @throws IllegalArgumentException in case the given entity is {@literal null}.
     */
    Single<List<Integer>> insert(Collection<P> pojos);

    Single<List<Integer>> insertOnDuplicateKeyIgnore(Collection<P> pojos);

    Single<List<Integer>> insertOnDuplicateKeyUpdate(Collection<P> pojos);


    Single<List<Integer>> insertOnConflictKeyUpdate(Collection<P> pojos, Collection<? extends Field<?>> fieldConflict);

    Single<Optional<P>> insertOnConflictKeyUpdate(P pojo, Collection<? extends Field<?>> fieldConflict);

    List<Integer> insertOnConflictKeyUpdateBlocking(Collection<P> pojos,
                                                    Collection<? extends Field<?>> fieldConflict,
                                                    DSLContext context);

    Single<Integer> update(ID id, P pojo);

    Single<Integer> update(ID id, P pojo, List<String> fields);

    Single<Integer> update(ID id, UpdateField updateField);

    Single<Integer> update(P pojo, Condition condition);

    Single<Integer> update(P pojo, Condition... conditions);


    /**
     * Retrieves an entity by its id.
     *
     * @param id must not be {@literal null}.
     * @return the entity with the given id or {@literal Optional#empty()} if none found
     * @throws DBException if {@code id} is {@literal null}.
     */
    Single<Optional<P>> findById(ID id);
    List<Integer> insertBlocking(Collection<P> pojos, DSLContext context);
    Optional<P> insertRetuningBlocking(P pojo, DSLContext context);
    Single<Optional<P>> findByIdWithDeleted(ID id);

    /**
     * Returns all instances of the type with the given IDs.
     *
     * @param ids
     * @return
     */
    Single<List<P>> findAllById(Collection<ID> ids);

    Single<List<P>> findAllByIdsWithDeleted(Collection<ID> ids);

    /**
     * Returns all instances of the type.
     *
     * @return all entities
     */
    Single<List<P>> findAll();

    /**
     * Deletes the entity with the given id.aa
     *
     * @param id must not be {@literal null}.
     */
    Single<Integer> deletedById(ID id);

    Single<List<P>> getByPageable(Pageable pageable);

    Single<List<P>> getByPageable(Pageable pageable, Condition condition);

    /**
     * Returns the number of entities available.
     *
     * @return the number of entities
     */
    Single<Long> count();
}
