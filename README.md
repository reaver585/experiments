Try running:
```bash
./gradlew --no-daemon :proj-0:printOwnSources
```

Note the difference if you disable configuration cache:
```bash
./gradlew --no-daemon :proj-0:printOwnSources --no-configuration-cache
```

---

To invalidate the configuration cache, you can run:
```bash
rm -rf .gradle
```