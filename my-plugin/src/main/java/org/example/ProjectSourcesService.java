package org.example;

import org.gradle.api.services.BuildService;
import org.gradle.api.services.BuildServiceParameters;

import java.io.File;
import java.util.*;

public abstract class ProjectSourcesService implements BuildService<BuildServiceParameters.None>, AutoCloseable {
    private final Map<String, List<File>> sourcesByProject = new LinkedHashMap<>();

    public synchronized void registerSources(String projectPath, List<File> dirs) {
        sourcesByProject.put(projectPath, new ArrayList<>(dirs));
    }

    public synchronized Map<String, List<File>> getAll() {
        return new LinkedHashMap<>(sourcesByProject);
    }

    @Override public void close() {}
}
