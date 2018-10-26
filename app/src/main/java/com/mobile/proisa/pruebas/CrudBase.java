package com.mobile.proisa.pruebas;

import android.database.sqlite.SQLiteDatabase;

import java.util.List;

public abstract class CrudBase<T> {
    private SQLiteDatabase database;

    public CrudBase(SQLiteDatabase database) {
        this.database = database;
    }

    public abstract List<T> getAll();
    public abstract List<T> getAll(int count);
    public abstract List<T> getAll(Object id);
    public abstract List<T> getAll(String rawQuery);

    public abstract boolean insert(T item);
    public abstract boolean update(T item);
    public abstract boolean delete(T item);

    public SQLiteDatabase getDatabase() {
        return database;
    }

    public void closeDatabase(){
        this.database.close();
    }
}
