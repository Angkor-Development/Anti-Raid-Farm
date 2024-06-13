package me.thunthean.Ults;

import me.thunthean.AntiRaidFarm;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.logging.Logger;

public class Checks {
    private static final Logger logger = Bukkit.getLogger();

    //Not yet
    public void checkServerVersion() {
        String ver = AntiRaidFarm.getInstance().getServer().getVersion();
        String[] unstableVersions = {"v1_21"};

        for (String unstableVersion : unstableVersions) {
            if (ver.equalsIgnoreCase(unstableVersion)) {
                logger.warning("[WARNING]: You are running on an unstable version. Please update to 1.17.x/1.18.x/1.19.x!");
                AntiRaidFarm.getInstance().getServer().getPluginManager().disablePlugins();
                break;
            }
        }
    }

    public static void CheckPaperServer() {
        try {
            Class.forName("com.destroystokyo.paper.utils.PaperPluginLogger");
            logger.info("This server is running" +  Bukkit.getServer().getVersion());
        } catch (ClassNotFoundException e) {
            logger.info("We recommended you to change to PaperMC!! | https://papermc.io/");
        }
    }
}
