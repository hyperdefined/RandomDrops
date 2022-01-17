package lol.hyper.randomdrops.commands;

import lol.hyper.randomdrops.RandomDrops;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class RandomDropsCommand implements TabExecutor {

    private final RandomDrops randomDrops;

    public RandomDropsCommand(RandomDrops randomDrops) {
        this.randomDrops = randomDrops;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0 || sender instanceof ConsoleCommandSender) {
            sender.sendMessage(ChatColor.GREEN + "RandomDrops version " + randomDrops.getDescription().getVersion() + ". Created by hyperdefined.");
            return true;
        }
        if (args[0].equalsIgnoreCase("reset")) {
            sender.sendMessage(ChatColor.GREEN + "Reset all item drops from memory!");
            randomDrops.dropCalculation.itemDrops.clear();
            randomDrops.dropCalculation.mobDrops.clear();
        } else {
            sender.sendMessage("RandomDrops version " + randomDrops.getDescription().getVersion() + ". Created by hyperdefined.");
        }
        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 0) {
            return Collections.singletonList("reset");
        } else {
            return null;
        }
    }
}
