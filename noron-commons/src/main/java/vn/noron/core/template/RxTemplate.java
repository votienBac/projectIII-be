package vn.noron.core.template;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import vn.noron.core.extension.SupplierThrowable;
import vn.noron.core.log.Logger;

public class RxTemplate {
    private static final Logger log = Logger.getLogger(RxTemplate.class);

    private RxTemplate() {
    }

    public static <T> Single<T> rxSchedulerIo(SupplierThrowable<T> supplier) {
        return Single.just("io")
                .subscribeOn(Schedulers.io())
                .flatMap(s -> rxBlockingAsync(supplier));
    }

    public static <T> Single<T> rxSchedulerNewThread(SupplierThrowable<T> supplier) {
        return Single.just("new-thread")
                .subscribeOn(Schedulers.newThread())
                .flatMap(s -> rxBlockingAsync(supplier));
    }

    public static <T> Single<T> rxBlockingAsync(SupplierThrowable<T> supplier) {
        return Single.create(emitter -> {
            try {
                final T value = supplier.get();
                emitter.onSuccess(value);
            } catch (Exception exception) {
                log.error("[RX-BLOCKING-ASYNC] cause", exception);
                emitter.onError(exception);
            }
        });
    }

    public static <T> void rxSchedulerNewThreadSubscribe(SupplierThrowable<T> supplier) {
        Single.just("new-thread")
                .subscribeOn(Schedulers.newThread())
                .flatMap(s -> rxBlockingAsync(supplier))
                .subscribe(
                        result -> log.info("[rx-subscribe] execute job {}", result),
                        throwable -> log.error("[rx-subscribe] fail to execute job", throwable));
    }

    public static <T> void rxSubscribe(Single<T> single) {
        single.subscribe(
                result -> log.info("[rx-subscribe] execute job {}", result),
                throwable -> log.error("[rx-subscribe] fail to execute job", throwable));
    }

    public static <T> void rxSubscribe(Single<T> single1, Single<T> single2) {
        Single
                .zip(single1, single2, (t, t2) -> true)
                .subscribe(
                        result -> log.info("[rx-subscribe] execute job {}", result),
                        throwable -> log.error("[rx-subscribe] fail to execute job", throwable));
    }
}
