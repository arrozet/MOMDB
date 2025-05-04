package es.uma.taw.momdb.entity;

public interface EntityWithNameAndId<T> {
    T getId();
    String getName();
}
