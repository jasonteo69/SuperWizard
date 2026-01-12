package com.superwizard.dao;

import com.superwizard.model.Player;

import javax.sql.DataSource;
import java.sql.*;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public class PlayerDAO {
    private final DataSource ds;

    public PlayerDAO(DataSource ds) {
        this.ds = ds;
    }

    /**
     * Save or update a player. Portable approach: try UPDATE first; if 0 rows affected then INSERT.
     */
    public void save(Player p) throws SQLException {
        String updateSql = "UPDATE players SET username = ?, level = ?, xp = ?, inventory = ?, last_saved = ? WHERE id = ?";
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(updateSql)) {
            ps.setString(1, p.getUsername());
            ps.setInt(2, p.getLevel());
            ps.setLong(3, p.getXp());
            ps.setString(4, p.getInventoryJson());
            ps.setTimestamp(5, p.getLastSaved() == null ? null : Timestamp.from(p.getLastSaved()));
            ps.setObject(6, p.getId());
            int updated = ps.executeUpdate();
            if (updated == 0) {
                String insertSql = "INSERT INTO players (id, username, level, xp, inventory, last_saved) VALUES (?,?,?,?,?,?)";
                try (PreparedStatement ps2 = conn.prepareStatement(insertSql)) {
                    ps2.setObject(1, p.getId());
                    ps2.setString(2, p.getUsername());
                    ps2.setInt(3, p.getLevel());
                    ps2.setLong(4, p.getXp());
                    ps2.setString(5, p.getInventoryJson());
                    ps2.setTimestamp(6, p.getLastSaved() == null ? null : Timestamp.from(p.getLastSaved()));
                    ps2.executeUpdate();
                }
            }
        }
    }

    public Optional<Player> findById(UUID id) throws SQLException {
        String sql = "SELECT id, username, level, xp, inventory, last_saved FROM players WHERE id = ?";
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Player p = new Player((UUID) rs.getObject("id"), rs.getString("username"));
                    p.setLevel(rs.getInt("level"));
                    p.setXp(rs.getLong("xp"));
                    p.setInventoryJson(rs.getString("inventory"));
                    Timestamp ts = rs.getTimestamp("last_saved");
                    if (ts != null) p.setLastSaved(ts.toInstant());
                    return Optional.of(p);
                }
            }
        }
        return Optional.empty();
    }

    // Additional convenience methods can be added: delete, listAll, topScores etc.
}
