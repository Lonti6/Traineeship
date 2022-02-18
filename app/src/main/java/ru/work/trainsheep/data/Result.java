package ru.work.trainsheep.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Result<T> {

    static class Success<T> extends Result<T >{
        private T result;

        public Success(T result) {
            this.result = result;
        }

        public T getResult() {
            return result;
        }

        public void setResult(T result) {
            this.result = result;
        }
    }

    static class Error<T> extends Result<T> {
        private Exception exception;

        public Error(Exception exception) {
            this.exception = exception;
        }

        public Exception getException() {
            return exception;
        }

        public void setException(Exception exception) {
            this.exception = exception;
        }
    }

    public static <T> Result<T> success(T t){
        return new Success<>(t);
    }
    public static <T> Result<T> error(Exception e){
        return new Error<>(e);
    }


}
