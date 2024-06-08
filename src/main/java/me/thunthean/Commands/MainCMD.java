package me.thunthean.Commands;

import me.thunthean.AntiRaidFarm;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static me.thunthean.AntiRaidFarm.PREFIX;

public class MainCMD implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {


        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(args.length == 0) {
                player.sendMessage(PREFIX + "&7 Please do /anf <cmd>");
                return true;
            }
            if(args[0].equalsIgnoreCase("raid")) {
                if(args.length == 1) {
                    player.sendMessage(PREFIX + "&7 Please do /anf raif <on/off>");
                    return true;
                }

                if(args[1].equalsIgnoreCase("on")) {
                    AntiRaidFarm.getInstance().raid = true;
                    player.sendMessage(PREFIX + "Anti-Raid-Farm is now &7on");
                }
                if(args[1].equalsIgnoreCase("off")) {
                    AntiRaidFarm.getInstance().raid = false;
                    player.sendMessage(PREFIX + "Anti-Raid-Farm is now &7off");
                }
            }
        }
        return true;
    }
}
