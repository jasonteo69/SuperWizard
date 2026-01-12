package com.superwizard.model;

import java.time.Instant;
import java.util.UUID;

public class Player {
    private UUID id;
    private String username;
    private int level;
    private long xp;
    private String inventoryJson; // flexible storage as JSON string
    private Instant lastSaved;

    public Player(UUID id, String username) {
        this.id = id;
        this.username = username;
    }

    // getters and setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }
    public long getXp() { return xp; }
    public void setXp(long xp) { this.xp = xp; }
    public String getInventoryJson() { return inventoryJson; }
    public void setInventoryJson(String inventoryJson) { this.inventoryJson = inventoryJson; }
    public Instant getLastSaved() { return lastSaved; }
    public void setLastSaved(Instant lastSaved) { this.lastSaved = lastSaved; }
}
