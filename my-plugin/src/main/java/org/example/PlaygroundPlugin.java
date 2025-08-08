package org.example;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.tasks.SourceSetContainer;

public class PlaygroundPlugin implements Plugin<Project> {
  @Override
  public void apply(Project project) {
    var serviceProvider =
        project
            .getGradle()
            .getSharedServices()
            .registerIfAbsent("projectSourcesService", ProjectSourcesService.class, spec -> {});

    project.getLogger().lifecycle("Starting PlaygroundPlugin for project: {}", project.getPath());

    var sourceSets = project.getExtensions().findByType(SourceSetContainer.class);
    if (sourceSets != null) {
      ProjectSourcesService projectSourcesService = serviceProvider.get();
      sourceSets.forEach(
          ss -> {
            project.getLogger().lifecycle("Registering sources for project: {}", project.getPath());
            project
                .getLogger()
                .lifecycle(
                    "Service instance hash: {}", System.identityHashCode(serviceProvider.get()));
            projectSourcesService.registerSources(
                project.getPath(), ss.getAllSource().getSrcDirs().stream().toList());
          });
      project
          .getLogger()
          .lifecycle(
              "Following source directories registered for project: {}",
              projectSourcesService.getAll());
    }

    project
        .getTasks()
        .register(
            "printOwnSources",
            PrintSourcesTask.class,
            task -> {
              task.getService().set(serviceProvider);
              task.usesService(serviceProvider);
            });
  }
}
