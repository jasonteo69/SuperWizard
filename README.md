```markdown
# SuperWizard — DB storage quickstart

1. Add the files above to the repository.
2. Configure DB connection:
   - Copy `src/main/resources/application.properties.sample` to `application.properties` and edit OR set env vars `DB_URL`, `DB_USER`, `DB_PASSWORD`.
   - For local development use `jdbc:h2:./data/superwizard;DB_CLOSE_DELAY=-1;MODE=PostgreSQL`.
3. Build & run:
   - mvn clean package
   - Run the example or your game main which calls:
     DatabaseManager.init(jdbcUrl, dbUser, dbPass);
     // then use new PlayerDAO(DatabaseManager.getInstance().getDataSource()) to save/load Player objects.
4. Migrations:
   - Migrations run automatically on DatabaseManager.init(...) using Flyway and files in `src/main/resources/db/migration`.
5. Tests:
   - Unit tests can use H2 and the same migrations folder to run against an in-memory DB.
```
