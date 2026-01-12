package com.superwizard;

import com.superwizard.dao.PlayerDAO;
import com.superwizard.db.DatabaseManager;
import com.superwizard.model.Player;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public class MainExample {
    public static void main(String[] args) throws Exception {
        // initialize DB (use env vars or properties; shown here inline for example)
        String jdbcUrl = System.getenv().getOrDefault("DB_URL", "jdbc:h2:./data/superwizard;DB_CLOSE_DELAY=-1;MODE=PostgreSQL");
        String dbUser = System.getenv().getOrDefault("DB_USER", "");
        String dbPass = System.getenv().getOrDefault("DB_PASSWORD", "");
        DatabaseManager.init(jdbcUrl, dbUser, dbPass);

        PlayerDAO dao = new PlayerDAO(DatabaseManager.getInstance().getDataSource());

        Player p = new Player(UUID.randomUUID(), "wizard123");
        p.setLevel(5);
        p.setXp(3450);
        p.setInventoryJson("{\"gold\":100, \"potions\":3}");
        p.setLastSaved(Instant.now());
        dao.save(p);

        Optional<Player> loaded = dao.findById(p.getId());
        loaded.ifPresent(lp -> System.out.println("Loaded player: " + lp.getUsername()));

        DatabaseManager.getInstance().shutdown();
    }
}
