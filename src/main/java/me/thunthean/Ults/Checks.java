package me.thunthean.Ults;

import org.bukkit.Bukkit;

import java.util.logging.Logger;

public class Checks {
    private static final Logger logger = Bukkit.getLogger();

    public static void CheckPaperServer() {
        try {
            Class.forName("com.destroystokyo.paper.utils.PaperPluginLogger");
            logger.info("This server is running" +  Bukkit.getServer().getVersion());
        } catch (ClassNotFoundException e) {
            logger.info("We recommended you to change to PaperMC!! | https://papermc.io/");
        }
    }
}
