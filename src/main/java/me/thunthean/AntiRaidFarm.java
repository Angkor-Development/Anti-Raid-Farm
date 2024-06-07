package me.thunthean;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.raid.RaidTriggerEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import static me.thunthean.Ults.Checks.CheckPaperServer;
import static me.thunthean.Ults.Checks.isVersionInRange;


public final class AntiRaidFarm extends JavaPlugin implements Listener {

    private Cache<UUID, Long> lastRaidCache;
    private static final String RAID_COOLDOWN_CONFIG = "raid-cooldown-seconds";
    private static final String BYPASS_PERMISSION = "antiraidfarm.bypass";
    private Logger logger;
    private static final String MIN_VERSION = "1.17.1";
    private static final String MAX_VERSION = "1.20.1";

    @Override
    public void onEnable() {
        // Initialize the logger
        logger = this.getLogger();

        //Check Server Version
        String serverVersion = Bukkit.getBukkitVersion().split("-")[0];
        if (isVersionInRange(serverVersion, MIN_VERSION, MAX_VERSION)) {
            getLogger().info("Plugin enabled successfully. Server version: " + serverVersion);
        } else {
            getLogger().warning("Plugin disabled. Server version " + serverVersion + " is not within the supported range (" + MIN_VERSION + " to " + MAX_VERSION + ").");
            getServer().getPluginManager().disablePlugin(this);
        }

        long time = System.currentTimeMillis();

        //Check Paper Server
        CheckPaperServer();

        // Save the default configuration file if it does not exist
        this.saveDefaultConfig();

        // Reload the configuration to ensure we have the latest values
        this.reloadConfig();

        // Get the raid cooldown period from the config, with a default value of 180 seconds
        final int raidCooldownSeconds = this.getConfig().getInt(RAID_COOLDOWN_CONFIG, 180);

        // Build the cache with the specified expiration time
        this.lastRaidCache = CacheBuilder.newBuilder()
                .expireAfterWrite(raidCooldownSeconds, TimeUnit.SECONDS)
                .build();

        // Register the event listener
        this.getServer().getPluginManager().registerEvents(this, this);

        long duration = System.currentTimeMillis() - time;

        // Log plugin enablement
        logger.info("is Enabled took " + duration + "ms");
        logger.info("AntiRaidFarm plugin by " + ChatColor.DARK_RED + "thunthean" + ChatColor.WHITE + " with a raid cooldown of " + raidCooldownSeconds + " seconds.");

    }

    @EventHandler
    public void onRaidTrigger(final RaidTriggerEvent event) {
        final Player player = event.getPlayer();

        // Check if the player has the bypass permission
        if (player.hasPermission(BYPASS_PERMISSION)) {
            logger.info("Player " + player.getName() + " has bypass permission. Raid allowed.");
            return;
        }

        // Check if the player has triggered a raid within the cooldown period
        if (this.lastRaidCache.getIfPresent(player.getUniqueId()) != null) {
            event.setCancelled(true);
            logger.info("Raid triggered by player " + player.getName() + " blocked due to cooldown.");
        } else {
            this.lastRaidCache.put(player.getUniqueId(), System.currentTimeMillis());
            logger.info("Raid triggered by player " + player.getName() + " allowed. Cooldown started.");
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("This plugin is disabled!!");
    }

}
