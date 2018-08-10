package com.example.android.contentprovidersample.data;

import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.arch.persistence.room.SharedSQLiteStatement;
import android.database.Cursor;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;

@SuppressWarnings("unchecked")
public class CheeseDao_Impl implements CheeseDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfCheese;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfCheese;

  private final SharedSQLiteStatement __preparedStmtOfDeleteById;

  public CheeseDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCheese = new EntityInsertionAdapter<Cheese>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `cheeses`(`_id`,`name`) VALUES (nullif(?, 0),?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Cheese value) {
        stmt.bindLong(1, value.id);
        if (value.name == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.name);
        }
      }
    };
    this.__updateAdapterOfCheese = new EntityDeletionOrUpdateAdapter<Cheese>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `cheeses` SET `_id` = ?,`name` = ? WHERE `_id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Cheese value) {
        stmt.bindLong(1, value.id);
        if (value.name == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.name);
        }
        stmt.bindLong(3, value.id);
      }
    };
    this.__preparedStmtOfDeleteById = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM cheeses WHERE _id = ?";
        return _query;
      }
    };
  }

  @Override
  public long insert(Cheese cheese) {
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfCheese.insertAndReturnId(cheese);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public long[] insertAll(Cheese[] cheeses) {
    __db.beginTransaction();
    try {
      long[] _result = __insertionAdapterOfCheese.insertAndReturnIdsArray(cheeses);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int update(Cheese cheese) {
    int _total = 0;
    __db.beginTransaction();
    try {
      _total +=__updateAdapterOfCheese.handle(cheese);
      __db.setTransactionSuccessful();
      return _total;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int deleteById(long id) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteById.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      _stmt.bindLong(_argIndex, id);
      final int _result = _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteById.release(_stmt);
    }
  }

  @Override
  public int count() {
    final String _sql = "SELECT COUNT(*) FROM cheeses";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _result;
      if(_cursor.moveToFirst()) {
        _result = _cursor.getInt(0);
      } else {
        _result = 0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Cursor selectAll() {
    final String _sql = "SELECT * FROM cheeses";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _tmpResult = __db.query(_statement);
    return _tmpResult;
  }

  @Override
  public Cursor selectById(long id) {
    final String _sql = "SELECT * FROM cheeses WHERE _id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final Cursor _tmpResult = __db.query(_statement);
    return _tmpResult;
  }
}
