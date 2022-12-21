package vn.noron.repository;

import io.reactivex.rxjava3.core.Single;
import lombok.SneakyThrows;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;
import org.jooq.impl.TableRecordImpl;
import org.springframework.beans.factory.annotation.Autowired;
import vn.noron.core.exception.DBException;
import vn.noron.core.log.Logger;
import vn.noron.data.model.paging.Pageable;
import vn.noron.repository.utils.UpdateField;

import javax.annotation.PostConstruct;
import java.lang.reflect.ParameterizedType;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.partition;
import static java.time.ZonedDateTime.now;
import static java.util.Optional.ofNullable;
import static vn.noron.core.template.RxTemplate.rxSchedulerIo;
import static vn.noron.data.constant.MessageResponse.ID_MUST_NOT_BE_NULL;
import static vn.noron.repository.utils.MysqlUtil.toInsertQueries;
import static vn.noron.repository.utils.MysqlUtil.toSortField;

public abstract class AbsRepository<R extends TableRecordImpl<R>, P, ID> implements IBaseRepository<P, ID> {
    private final Logger log = Logger.getLogger(this.getClass());

    @Autowired protected DSLContext dslContext;
    protected R record;
    private Class<P> pojoClass;
    protected TableField<R, ID> fieldID;
    protected TableField<R, ID> fieldDeleted;

    protected abstract TableImpl<R> getTable();

    @SneakyThrows
    @PostConstruct
    public void init() {
        log.info(true, "init class {}", this.getClass().getSimpleName());
        this.record = ((Class<R>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0])
                .getDeclaredConstructor()
                .newInstance();
        this.pojoClass = ((Class<P>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[1]);
        this.fieldID = (TableField<R, ID>) Arrays.stream(getTable().fields())
                .filter(field -> field.getName().equalsIgnoreCase("id"))
                .findFirst()
                .orElse(null);
        this.fieldDeleted = (TableField<R, ID>) Arrays.stream(getTable().fields())
                .filter(field -> field.getName().equalsIgnoreCase("deleted_at"))
                .findFirst()
                .orElse(null);
    }


    @Override
    public Single<Integer> insert(P pojo) {
        return rxSchedulerIo(() -> dslContext.insertInto(getTable())
                .set(toInsertQueries(record, pojo))
                .execute());
    }
    @Override
    public List<Integer> insertBlocking(Collection<P> pojos, DSLContext context) {
        final List<InsertSetMoreStep<R>> insertSetMoreSteps = pojos.stream()
                .map(p -> toInsertQueries(record, p))
                .map(fieldObjectMap -> context
                        .insertInto(getTable())
                        .set(fieldObjectMap))
                .collect(Collectors.toList());

        return partition(insertSetMoreSteps, 1000)
                .stream()
                .flatMap(lists -> Arrays.stream(context.batch(lists).execute()).boxed())
                .collect(Collectors.toList());
    }

    @Override
    public Optional<P> insertRetuningBlocking(P pojo, DSLContext context) {
        Optional<P> p = ofNullable(context.insertInto(getTable())
                .set(toInsertQueries(record, pojo))
                .returning()
                .fetchOne())
                .map(r -> r.into(pojoClass));
        return p;
    }

    @Override
    public Single<Optional<P>> insertReturning(P pojo) {
        return rxSchedulerIo(() -> ofNullable(dslContext.insertInto(getTable())
                .set(toInsertQueries(record, pojo))
                .returning()
                .fetchOne())
                .map(r -> r.into(pojoClass)));
    }

    @Override
    public Single<Optional<P>> insertOnDuplicateKeyUpdate(P pojo) {
        return rxSchedulerIo(() -> ofNullable(dslContext.insertInto(getTable())
                .set(toInsertQueries(record, pojo))
                .onDuplicateKeyUpdate()
                .set(onDuplicateKeyUpdate(pojo))
                .returning()
                .fetchOne())
                .map(r -> r.into(pojoClass)));
    }

    @Override
    public Single<List<Integer>> insert(Collection<P> pojos) {
        final List<InsertSetMoreStep<R>> insertSetMoreSteps = pojos.stream()
                .map(p -> toInsertQueries(record, p))
                .map(fieldObjectMap -> dslContext
                        .insertInto(getTable())
                        .set(fieldObjectMap))
                .collect(Collectors.toList());

        return rxSchedulerIo(() -> partition(insertSetMoreSteps, 1000)
                .stream()
                .flatMap(lists -> Arrays.stream(dslContext.batch(lists).execute()).boxed())
                .collect(Collectors.toList()));
    }

    @Override
    public Single<List<Integer>> insertOnDuplicateKeyIgnore(Collection<P> pojos) {
        final List<InsertReturningStep<R>> stepList = pojos.stream()
                .map(p -> toInsertQueries(record, p))
                .map(fieldObjectMap -> dslContext
                        .insertInto(getTable())
                        .set(fieldObjectMap)
                        .onDuplicateKeyIgnore())
                .collect(Collectors.toList());

        return rxSchedulerIo(() -> partition(stepList, 1000)
                .stream()
                .flatMap(lists -> {
                    log.info(true,
                            "[INSERT-MANY] class: {}, size: {}",
                            pojos.getClass().getSimpleName(), lists.size());
                    return Arrays.stream(dslContext.batch(lists).execute()).boxed();
                })
                .collect(Collectors.toList()));
    }

    @Override
    public Single<List<Integer>> insertOnDuplicateKeyUpdate(Collection<P> pojos) {
        final List<InsertOnDuplicateSetMoreStep<R>> moreStepList = pojos.stream()
                .map(p -> toInsertQueries(record, p))
                .map(fieldObjectMap -> dslContext
                        .insertInto(getTable())
                        .set(fieldObjectMap)
                        .onDuplicateKeyUpdate()
                        .set(fieldObjectMap))
                .collect(Collectors.toList());

        return rxSchedulerIo(() -> partition(moreStepList, 1000)
                .stream()
                .flatMap(lists -> Arrays.stream(dslContext.batch(lists).execute()).boxed())
                .collect(Collectors.toList()));
    }

    @Override
    public Single<Integer> update(ID id, P pojo) {
        if (fieldID != null)
            return rxSchedulerIo(() -> dslContext.update(getTable())
                    .set(toInsertQueries(record, pojo))
                    .where(fieldID.eq(id))
                    .execute());
        return Single.error(new DBException(ID_MUST_NOT_BE_NULL));
    }

    @Override
    public Single<Integer> update(ID id, P pojo, List<String> fields) {
        if (fieldID != null)
            return rxSchedulerIo(() -> dslContext.update(getTable())
                    .set(toInsertQueries(record, pojo, fields))
                    .where(fieldID.eq(id))
                    .execute());
        return Single.error(new DBException(ID_MUST_NOT_BE_NULL));
    }

    @Override
    public Single<Integer> update(ID id, UpdateField updateField) {
        if (fieldID != null)
            return rxSchedulerIo(() -> dslContext.update(getTable())
                    .set(updateField.getFieldValueMap())
                    .where(fieldID.eq(id))
                    .execute());
        return Single.error(new DBException(ID_MUST_NOT_BE_NULL));
    }

    @Override
    public Single<Integer> update(P pojo, Condition condition) {
        return rxSchedulerIo(() -> dslContext.update(getTable())
                .set(toInsertQueries(record, pojo))
                .where(condition)
                .execute());
    }

    @Override
    public Single<Integer> update(P pojo, Condition... conditions) {
        return rxSchedulerIo(() -> dslContext.update(getTable())
                .set(toInsertQueries(record, pojo))
                .where(conditions)
                .execute());
    }

    @Override
    public Single<Optional<P>> findById(ID id) {
        if (fieldID == null) return Single.just(Optional.empty());
        return rxSchedulerIo(() -> dslContext.selectFrom(getTable())
                .where(fieldID.eq(id).and(filterActive()))
                .limit(1)
                .fetchOptionalInto(pojoClass));
    }

    @Override
    public Single<Optional<P>> findByIdWithDeleted(ID id) {
        if (fieldID == null) return Single.just(Optional.empty());
        return rxSchedulerIo(() -> dslContext.selectFrom(getTable())
                .where(fieldID.eq(id))
                .limit(1)
                .fetchOptionalInto(pojoClass));
    }

    @Override
    public Single<List<P>> findAllById(Collection<ID> ids) {
        if (fieldID == null) return Single.just(new ArrayList<>());
        return rxSchedulerIo(() -> dslContext.selectFrom(getTable())
                .where(fieldID.in(ids).and(filterActive()))
                .fetchInto(pojoClass));
    }

    @Override
    public Single<List<P>> findAllByIdsWithDeleted(Collection<ID> ids) {
        if (fieldID == null) return Single.just(new ArrayList<>());
        return rxSchedulerIo(() -> dslContext.selectFrom(getTable())
                .where(fieldID.in(ids))
                .fetchInto(pojoClass));
    }

    @Override
    public Single<List<P>> findAll() {
        return rxSchedulerIo(() -> dslContext.selectFrom(getTable())
                .where(filterActive())
                .fetchInto(pojoClass));
    }

    @Override
    public Single<Integer> deletedById(ID id) {
        if (fieldID != null)
            return rxSchedulerIo(() -> dslContext.update(getTable())
                    .set((Field<LocalDateTime>) getTable().field("deleted_at"), now().toLocalDateTime())
                    .where(fieldID.eq(id))
                    .execute());
        return Single.error(new DBException(ID_MUST_NOT_BE_NULL));
    }


    protected Condition filterActive() {
        return DSL.trueCondition();
    }

    @Override
    public Single<List<P>> getByPageable(Pageable pageable) {
        return rxSchedulerIo(() -> {
            final List<P> pojos = dslContext.selectFrom(getTable())
                    .orderBy(toSortField(pageable.getSort(), getTable().fields()))
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetchInto(pojoClass);
            if (pojos.size() > pageable.getPageSize()) {
                pageable.setLoadMoreAble(true);
                return pojos.stream().limit(pageable.getPageSize()).collect(Collectors.toList());
            } else {
                pageable.setLoadMoreAble(false);
                return pojos;
            }
        });
    }

    @Override
    public Single<List<P>> getByPageable(Pageable pageable, Condition condition) {
        return rxSchedulerIo(() -> {
            final List<P> pojos = dslContext.selectFrom(getTable())
                    .where(condition)
                    .orderBy(toSortField(pageable.getSort(), getTable().fields()))
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize() + 1)
                    .fetchInto(pojoClass);
            if (pojos.size() > pageable.getPageSize()) {
                pageable.setLoadMoreAble(true);
                return pojos.stream().limit(pageable.getPageSize()).collect(Collectors.toList());
            } else {
                pageable.setLoadMoreAble(false);
                return pojos;
            }
        });
    }

    @Override
    public Single<Long> count() {
        return rxSchedulerIo(() -> dslContext.selectCount()
                .from(getTable())
                .where(filterActive())
                .fetchOneInto(Long.class));
    }

    protected Single<Integer> update(Condition condition, Map<Field<?>, Object> values) {
        return rxSchedulerIo(() -> dslContext.update(getTable())
                .set(values)
                .where(condition)
                .execute());
    }

    protected <T> Single<Integer> update(Condition condition, TableField<R, T> tableField, T value) {
        return rxSchedulerIo(() -> dslContext.update(getTable())
                .set(tableField, value)
                .where(condition)
                .execute());
    }

    protected Single<Long> count(Condition condition) {
        return rxSchedulerIo(() -> dslContext.selectCount()
                .from(getTable())
                .where(condition)
                .fetchOneInto(Long.class));
    }

    protected Single<Optional<P>> getOneOptional(OrderField<?> orderField, Condition... conditions) {
        return rxSchedulerIo(() -> dslContext.selectFrom(getTable())
                .where(conditions)
                .orderBy(orderField)
                .limit(1)
                .fetchOptionalInto(pojoClass));
    }

    protected Single<Optional<P>> getOneOptional(Condition condition) {
        return rxSchedulerIo(() -> ofNullable(dslContext.selectFrom(getTable())
                .where(condition)
                .limit(1)
                .fetchOneInto(pojoClass)));
    }

    protected Single<Optional<P>> getOneOptional(Condition... conditions) {
        return rxSchedulerIo(() -> ofNullable(dslContext.selectFrom(getTable())
                .where(conditions)
                .limit(1)
                .fetchOneInto(pojoClass)));
    }

    protected List<P> getList(Condition conditions) {
        return dslContext.selectFrom(getTable())
                .where(conditions)
                .fetchInto(pojoClass);
    }

    protected Single<List<P>> getList(Condition... conditions) {
        return rxSchedulerIo(() -> dslContext.selectFrom(getTable())
                .where(conditions)
                .fetchInto(pojoClass));
    }

    protected Single<List<P>> getActiveList(Condition conditions) {
        return rxSchedulerIo(() -> dslContext.selectFrom(getTable())
                .where(conditions.and(filterActive()))
                .fetchInto(pojoClass));
    }

    protected Map<Field<?>, Object> onDuplicateKeyUpdate(P p) {
        return toInsertQueries(record, p);
    }
}
