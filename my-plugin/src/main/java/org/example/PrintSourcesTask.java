package org.example;

import java.io.File;
import org.gradle.api.DefaultTask;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Internal;
import org.gradle.api.tasks.TaskAction;

public abstract class PrintSourcesTask extends DefaultTask {

  @Internal
  public abstract Property<ProjectSourcesService> getService();

  @TaskAction
  public void run() {
    var service = getService().get();
    getLogger().lifecycle("=== Dependent project sources ===");
    getLogger().lifecycle("Service instance hash: {}", System.identityHashCode(service));

    var all = service.getAll();
    all.forEach(
        (project, dirs) -> {
          getLogger().lifecycle("Project: {}", project);
          for (File dir : dirs) {
            getLogger().lifecycle("  {}", dir);
          }
        });
  }
}
