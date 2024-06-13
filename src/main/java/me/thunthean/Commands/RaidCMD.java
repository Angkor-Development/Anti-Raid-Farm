package me.thunthean.Commands;

import me.thunthean.AntiRaidFarm;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class RaidCMD implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                Long lastRaidTime = AntiRaidFarm.getInstance().getLastRaidCache().getIfPresent(player.getUniqueId());

                if (lastRaidTime != null) {
                    long cooldown = AntiRaidFarm.getInstance().getConfig().getInt("raid-cooldown-seconds", 180);
                    long elapsedTime = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - lastRaidTime);
                    long remainingTime = cooldown - elapsedTime;

                    if (remainingTime > 0) {
                        player.sendMessage(AntiRaidFarm.PREFIX + "Time until raid expires: " + remainingTime + " seconds.");
                    } else {
                        player.sendMessage(AntiRaidFarm.PREFIX + "You can trigger a raid now.");
                    }
                } else {
                    player.sendMessage(AntiRaidFarm.PREFIX + "You have not triggered any raids recently.");
                }
            } else {
                return true;
            }
        } else {
            sender.sendMessage("This command can only be run by a player.");
        }
        return true;
    }
}
