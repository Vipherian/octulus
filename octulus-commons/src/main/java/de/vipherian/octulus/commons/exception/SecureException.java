package de.vipherian.octulus.commons.exception;

import java.util.Arrays;
import java.util.function.Consumer;

public final class SecureException {

    @SafeVarargs
    public static void secure(ExceptionRunnable runnable, Consumer<Exception>... onFailure){
        try {
            runnable.run();
        }catch (Exception exception){
            Arrays.stream(onFailure).forEach(consumer -> consumer.accept(exception));
        }
    }

    public static void secureAndPrint(ExceptionRunnable runnable){
        secure(runnable, Throwable::printStackTrace);
    }

    private SecureException() {}

}
