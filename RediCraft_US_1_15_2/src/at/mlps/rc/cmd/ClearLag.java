package at.mlps.rc.cmd;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import at.mlps.rc.api.APIs;
import at.mlps.rc.main.Main;
import at.mlps.rc.main.Serverupdater;

public class ClearLag implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			Bukkit.getConsoleSender().sendMessage(Main.consolesend);
		}else {
			APIs api = new APIs();
			Player p = (Player)sender;
			if(p.hasPermission("mlps.clearLag")) {
				Serverupdater.time = 869;
			}else {
				api.noPerm(p);
			}
		}
		return true;
	}
}