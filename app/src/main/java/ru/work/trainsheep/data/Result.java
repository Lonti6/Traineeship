package ru.work.trainsheep.data;


import androidx.annotation.NonNull;

import org.jetbrains.annotations.Contract;

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

    public static class Error<T> extends Result<T> {
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

    @NonNull
    @Contract("_ -> new")
    public static <T> Result<T> success(T t){
        return new Success<>(t);
    }
    @NonNull
    @Contract("_ -> new")
    public static <T> Result<T> error(Exception e){
        return new Error<>(e);
    }

    public boolean isError(){
        return this instanceof Error;
    }

    public boolean isSuccess(){
        return this instanceof Success;
    }

    public T getResult(){
        return ((Success<T>) this).result;
    }

}
