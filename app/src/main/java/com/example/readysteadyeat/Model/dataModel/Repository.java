package com.example.readysteadyeat.Model.dataModel;
import com.google.android.gms.tasks.Task;

public interface Repository<TEntity extends Identifiable<TKey>, TKey> {

    Task<Boolean> exists(TKey id);
    Task<TEntity> get(TKey id);
    Task<Void> create(TEntity entity);
    Task<Void> update(TEntity entity);
    Task<Void> delete(TKey id);



}