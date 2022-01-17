/*
 * This file is part of RandomDrops.
 *
 * RandomDrops is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * RandomDrops is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with RandomDrops.  If not, see <https://www.gnu.org/licenses/>.
 */

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
