package de.vipherian.octulus.commons;

import de.vipherian.octulus.commons.exception.SecureException;

import java.io.File;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public final class Files {

    public static Feedback createDictionary(File file){
        var feedback = new AtomicReference<>(Feedback.SUCCESS);
        SecureException.secure(() -> java.nio.file.Files.createDirectory(file.toPath()), exception -> feedback.set(Feedback.FAILURE));
        return feedback.get();
    }

    public static Feedback write(File file, String text){
        var feedback = new AtomicReference<>(Feedback.SUCCESS);
        SecureException.secure(() -> java.nio.file.Files.write(file.toPath(), text.getBytes()), exception -> feedback.set(Feedback.FAILURE));
        return feedback.get();
    }

    public static Feedback writeLines(File file, List<String> lines){
        var feedback = new AtomicReference<>(Feedback.SUCCESS);
        SecureException.secure(() -> {
            var text = String.join("\n", lines);
            feedback.set(write(file, text));
        });
        return feedback.get();
    }


    public static File copyOf(File file){
        return new File(file.getAbsolutePath());
    }

    public static File of(String path){
        return new File(path);
    }

    public static File of(File parent, String child){
        return new File(parent, child);
    }

}
