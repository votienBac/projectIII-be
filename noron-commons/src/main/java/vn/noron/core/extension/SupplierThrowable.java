package vn.noron.core.extension;

@FunctionalInterface
public interface SupplierThrowable<T> {
    T get() throws Exception;
}
