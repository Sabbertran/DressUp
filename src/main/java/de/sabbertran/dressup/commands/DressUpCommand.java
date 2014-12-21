package de.sabbertran.dressup.commands;

import de.sabbertran.dressup.DressUp;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DressUpCommand implements CommandExecutor
{

    private DressUp main;

    public DressUpCommand(DressUp main)
    {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
    {
        if (sender instanceof Player)
        {
            Player p = (Player) sender;
            if (p.hasPermission("dressup.dressup"))
            {
                main.getWardrobe().openMainMenu(p);
                return true;
            } else
            {
                p.sendMessage("You don't have permission to use this command.");
                return true;
            }
        } else
        {
            sender.sendMessage("You need to be a player to use this command.");
            return true;
        }
    }
}
