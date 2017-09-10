package com.alexandershtanko.quotations.data.utils.paper;


import android.content.Context;

import com.alexandershtanko.quotations.data.utils.ErrorUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.paperdb.BuildConfig;
import io.paperdb.Paper;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;


/**
 * @author Alexander Shtanko ab.shtanko@gmail.com
 *         Created on 19/10/2016.
 **/
public class RxPaper {
    private static final String TAG = RxPaper.class.getSimpleName();

    Map<String, Map<String, PublishSubject<PaperObject>>> keyUpdatesSubjectMap = new HashMap<>();
    Map<String, PublishSubject<Map<String, PaperObject>>> bookUpdatesSubjectMap = new HashMap<>();

    final ConcurrentHashMap<String, Map<String, PaperObject>> bookKeyCacheMap = new ConcurrentHashMap<>();

    public void init(Context context) {
        Paper.init(context);

    }

    public void clearCache() {
        synchronized (bookKeyCacheMap) {
            bookKeyCacheMap.clear();
        }
    }

    public void destroy() {
        try {
            keyUpdatesSubjectMap.clear();
            bookUpdatesSubjectMap.clear();
            bookKeyCacheMap.clear();

        } catch (Exception e) {
            ErrorUtils.log(e);
        }
    }


    //==========================Single item changes===============================


    public <T> Observable<PaperObject<T>> read(String bookName, String key) {
        return Observable.create((ObservableOnSubscribe<PaperObject>) subscriber -> {
            PaperObject<T> paperObject = Paper.book(bookName).read(key);
            if (paperObject != null) {
                subscriber.onNext(paperObject);
            }
        }).mergeWith(getKeyUpdatesSubject(bookName, key).hide())
                .map(paperObject ->
                        (PaperObject<T>) paperObject).subscribeOn(Schedulers.io());
    }


    public <T> Observable<Map<String, PaperObject<T>>> read(String bookName) {
        Observable<Map<String, PaperObject<T>>> observable = read(bookName, true);
        return observable;
    }

    public <T> Observable<Map<String, PaperObject<T>>> read(String bookName, boolean cached) {
        return Observable.create((ObservableOnSubscribe<Map<String, PaperObject>>) subscriber -> {
            Map<String, PaperObject> map = readOnceInternal(bookName, cached);
            subscriber.onNext(map);
        }).mergeWith(getBookUpdatesSubject(bookName).hide())
                .filter(map -> map != null)
                .map(oldMap ->
                {
                    Map<String, PaperObject<T>> map = new HashMap<>();
                    for (String key : oldMap.keySet()) {
                        if (oldMap.get(key) != null)
                            map.put(key, oldMap.get(key));
                    }
                    return map;
                }).subscribeOn(Schedulers.io());
    }



    public <T> Map<String, PaperObject<T>> readOnce(String bookName) {
        return readOnce(bookName, true);
    }

    public <T> Map<String, PaperObject<T>> readOnce(String bookName, boolean cached) {
        List<String> keys = Paper.book(bookName).getAllKeys();
        Map<String, PaperObject<T>> map = new HashMap<>();
        for (String key : keys) {
            PaperObject<T> paperObject = readOnce(bookName, key, cached);
            if (paperObject != null)
                map.put(key, paperObject);
        }
        return map;
    }

    public <T> PaperObject<T> readOnce(String bookName, String key) {
        return readOnce(bookName, key, true);
    }

    public <T> PaperObject<T> readOnce(String bookName, String key, boolean cached) {
        synchronized (bookKeyCacheMap) {
            if (cached && bookKeyCacheMap.containsKey(bookName) && bookKeyCacheMap.get(bookName).containsKey(key)) {
                return (PaperObject<T>) bookKeyCacheMap.get(bookName).get(key);
            }

            return Paper.book(bookName).read(key);
        }
    }


    //Call this methods in background thread

    public <T> void write(String bookName, String key, T object) {
        write(bookName, key, object, true);
    }

    public <T> void write(String bookName, String key, T object, boolean cached) {
        synchronized (bookKeyCacheMap) {

            try {

                PaperObject<T> paperObject = Paper.book(bookName).read(key);
                if (paperObject == null)
                    paperObject = new PaperObject<>(key, object);
                else
                    paperObject.updateObject(object);

                Paper.book(bookName).write(key, paperObject);

                getKeyUpdatesSubject(bookName, key).onNext(paperObject);


                Map<String, PaperObject> map = new HashMap<>();
                map.put(key, paperObject);

                if (cached) {
                    if (bookKeyCacheMap.containsKey(bookName))
                        bookKeyCacheMap.get(bookName).putAll(map);
                }

                getBookUpdatesSubject(bookName).onNext(map);
            } catch (StackOverflowError error) {
                ErrorUtils.log(error);
            }
        }

    }


    public <T> void write(String bookName, Map<String, T> objectMap) {
        write(bookName, objectMap, true);
    }

    public <T> void write(String bookName, Map<String, T> objectMap, boolean cached) {
        synchronized (bookKeyCacheMap) {

            try {
                Map<String, PaperObject> map = new HashMap<>();

                for (String key : objectMap.keySet()) {
                    T object = objectMap.get(key);

                    PaperObject<T> paperObject = Paper.book(bookName).read(key);
                    if (paperObject == null)
                        paperObject = new PaperObject<>(key, object);
                    else
                        paperObject.updateObject(object);

                    Paper.book(bookName).write(key, paperObject);

                    if (cached) {
                        getKeyUpdatesSubject(bookName, key).onNext(paperObject);

                        map.put(key, paperObject);
                    }
                }

                if (cached) {
                    if (bookKeyCacheMap.containsKey(bookName))
                        bookKeyCacheMap.get(bookName).putAll(map);
                }

                getBookUpdatesSubject(bookName).onNext(map);
            } catch (StackOverflowError error) {
                ErrorUtils.log(error);
            }
        }

    }

    public void delete(String bookName, String key) {
        synchronized (bookKeyCacheMap) {

            PaperObject paperObject = Paper.book(bookName).read(key);
            if (paperObject != null) {
                paperObject.markObjectAsRemoved();

                Paper.book(bookName).delete(key);

                getKeyUpdatesSubject(bookName, key).onNext(paperObject);

                Map<String, PaperObject> map = new HashMap<>();
                map.put(key, paperObject);

                if (bookKeyCacheMap.containsKey(bookName))
                    bookKeyCacheMap.get(bookName).remove(key);

                getBookUpdatesSubject(bookName).onNext(map);
            }
        }
    }

    public void delete(String bookName) {
        synchronized (bookKeyCacheMap) {

            try {
                Paper.book(bookName).destroy();
            } catch (Exception e) {
                if (BuildConfig.DEBUG)
                    ErrorUtils.log(TAG, "", e);
            }

            try {
                getBookUpdatesSubject(bookName).onNext(new HashMap<>());

                if (bookKeyCacheMap.containsKey(bookName))
                    bookKeyCacheMap.remove(bookName);
            } catch (Exception e) {
                if (BuildConfig.DEBUG)
                    ErrorUtils.log(TAG, "", e);
            }
        }
    }

    private <T> Map<String, PaperObject> readOnceInternal(String bookName, boolean cached) {
        Map<String, PaperObject> map = new HashMap<>();

        synchronized (bookKeyCacheMap) {

            if (!bookKeyCacheMap.containsKey(bookName) || !cached) {
                List<String> keys = Paper.book(bookName).getAllKeys();

                for (String key : keys) {

                    try {

                        PaperObject<T> paperObject = Paper.book(bookName).read(key);
                        map.put(key, paperObject);
                    } catch (Throwable e) {
                        try {
                            Paper.book(bookName).delete(key);
                        } catch (Throwable ignored) {
                        }
                    }
                }

                if (cached)
                    bookKeyCacheMap.put(bookName, map);
            } else {
                map = new HashMap<>(bookKeyCacheMap.get(bookName));
            }
        }
        return map;
    }


    private PublishSubject<PaperObject> getKeyUpdatesSubject(String bookName, String key) {
        if (!keyUpdatesSubjectMap.containsKey(bookName)) {
            keyUpdatesSubjectMap.put(bookName, new HashMap<>());
        }

        if (!keyUpdatesSubjectMap.get(bookName).containsKey(key)) {
            keyUpdatesSubjectMap.get(bookName).put(key, PublishSubject.create());
        }

        return keyUpdatesSubjectMap.get(bookName).get(key);
    }


    private PublishSubject<Map<String, PaperObject>> getBookUpdatesSubject(String bookName) {
        if (!bookUpdatesSubjectMap.containsKey(bookName)) {
            bookUpdatesSubjectMap.put(bookName, PublishSubject.create());
        }

        return bookUpdatesSubjectMap.get(bookName);
    }


    public List<String> getKeys(String book) {
        return Paper.book(book).getAllKeys();
    }

    public int getItemsCount(String book) {
        return getKeys(book).size();
    }

    public boolean exist(String book, String key) {
        if (Paper.book(book) != null) {
            return Paper.book(book).exist(key);
        }
        return false;
    }


    public static class PaperObject<T> {
        private long updatedAt;
        private long createdAt;
        private ChangesType changesType;
        private T object;
        private String key;

        PaperObject(String key, T object) {
            this.changesType = ChangesType.ADDED;
            this.key = key;
            this.object = object;
            this.updatedAt = new Date().getTime();
            this.createdAt = updatedAt;
        }

        public ChangesType getChangesType() {
            return changesType;
        }


        public T getObject() {
            return object;
        }

        public void updateObject(T object) {
            this.updatedAt = new Date().getTime();
            changesType = ChangesType.UPDATED;
            this.object = object;
        }

        public void markObjectAsRemoved() {
            this.updatedAt = new Date().getTime();
            changesType = ChangesType.REMOVED;
        }


        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public long getUpdatedAt() {
            return updatedAt;
        }

        public long getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(long createdAt) {
            this.createdAt = createdAt;
        }
    }

    public enum ChangesType {
        ADDED, UPDATED, REMOVED
    }
}
