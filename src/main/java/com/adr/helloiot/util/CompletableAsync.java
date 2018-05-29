//    HelloIoT is a dashboard creator for MQTT
//    Copyright (C) 2017-2018 Adrián Romero Corchado.
//
//    This file is part of HelloIot.
//
//    HelloIot is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    HelloIot is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with HelloIot.  If not, see <http://www.gnu.org/licenses/>.
//
package com.adr.helloiot.util;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableScheduledFuture;
import com.google.common.util.concurrent.ListeningScheduledExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import javafx.application.Platform;

/**
 *
 * @author adrian
 */
public class CompletableAsync<T> {

    private final static Logger logger = Logger.getLogger(CompletableAsync.class.getName());
    private final static ListeningScheduledExecutorService service = MoreExecutors.listeningDecorator(Executors.newScheduledThreadPool(5));

    public static Executor fxThread() {
        return new Executor() {
            @Override
            public void execute(Runnable command) {
                if (Platform.isFxApplicationThread()) {
                    command.run();
                } else {
                    Platform.runLater(() -> {
                        command.run();
                    });        
                }
            }
        };
    }
    public static ListenableScheduledFuture<?> scheduleTask(long millis, Runnable r) {
        return service.schedule(r, millis, TimeUnit.MILLISECONDS);
    }

    public static ListenableScheduledFuture<?> scheduleTask(long millis, long period, Runnable r) {
        return service.scheduleAtFixedRate(r, millis, period, TimeUnit.MILLISECONDS);
    }

    public static <U> ListenableFuture<U> supplyAsync(Callable<U> s) {
        return service.submit(s);
    }

    public static ListenableFuture<?> runAsync(Runnable runnable) {
        return service.submit(runnable);
    }
    
    public static <T> void handle(ListenableFuture<T> future, HandlerConsumer<T> success, HandlerConsumer<Throwable> failure) {
        Futures.addCallback(future, new FutureCallback<T>() {
            @Override
            public void onSuccess(T v) {
                success.accept(v);
            }
            @Override
            public void onFailure(Throwable ex) {
                failure.accept(ex);
            }
        }, CompletableAsync.fxThread());          
    }
    
    public static <T> void handle(ListenableFuture<T> future, HandlerConsumer<T> success) {
        Futures.addCallback(future, new FutureCallback<T>() {
            @Override
            public void onSuccess(T v) {
                success.accept(v);
            }
            @Override
            public void onFailure(Throwable ex) {
            }
        }, CompletableAsync.fxThread());          
    }
    
    public static <T> void handleError(ListenableFuture<T> future, HandlerConsumer<Throwable> failure) {
        Futures.addCallback(future, new FutureCallback<T>() {
            @Override
            public void onSuccess(T v) {
            }
            @Override
            public void onFailure(Throwable ex) {
                failure.accept(ex);
            }
        }, CompletableAsync.fxThread());          
    }

    public static void shutdown() {       
        MoreExecutors.shutdownAndAwaitTermination(service, 60, TimeUnit.SECONDS);
    }
    
    @FunctionalInterface
    public interface HandlerConsumer<T> {
        public void accept(T value);
    }
}
