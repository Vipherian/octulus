package de.vipherian.octulus.commons;

import java.io.File;

public final class ParentDictionary {

    private final File file;

    public ParentDictionary(String name) {
        this.file = Files.of(name);
        if(!file.exists()){
            Files.createDictionary(file);
        }
    }

    public File getChildFile(String name){
        return Files.of(file, name);
    }

}
