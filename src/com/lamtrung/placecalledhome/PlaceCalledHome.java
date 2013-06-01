package com.lamtrung.placecalledhome;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class PlaceCalledHome extends JavaPlugin implements Listener{

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		
		if (command.getName().equalsIgnoreCase("remember")) {
			Player player = (Player)sender;
			Location loc = player.getLocation();
			this.getConfig().set(player.getName() + ".x", loc.getX());
			this.getConfig().set(player.getName() + ".y", loc.getY());
			this.getConfig().set(player.getName() + ".z", loc.getZ());
			
			this.saveConfig();
			
			player.sendMessage(ChatColor.GREEN + "There's no place like home");
			
		}
		return super.onCommand(sender, command, label, args);
	}

	@Override
	public void onDisable() {
		// TODO Auto-generated method stub
		super.onDisable();
	}

	@Override
	public void onEnable() {
		// TODO Auto-generated method stub
		super.onEnable();
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	@EventHandler
	public void onPlayerRightClick(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (event.getPlayer().getItemInHand().getType().equals(Material.FEATHER)) {
				if (this.getConfig().contains(event.getPlayer().getName())) {
					int value = event.getPlayer().getItemInHand().getAmount() - 1;
					
					if (value == 0)
						event.getPlayer().setItemInHand(new ItemStack(Material.AIR));
					else
						event.getPlayer().getItemInHand().setAmount(value);
					
					Location loc = new Location( event.getPlayer().getWorld(),
							this.getConfig().getDouble(event.getPlayer().getName() + ".x"),
							this.getConfig().getDouble(event.getPlayer().getName() + ".y"),
							this.getConfig().getDouble(event.getPlayer().getName() + ".z"));
					
					event.getPlayer().sendMessage(ChatColor.YELLOW + "You close your eyes and remember the place you call home");
					
					event.getPlayer().teleport(loc);	
				}
				else {
					event.getPlayer().sendMessage(ChatColor.GRAY + "You haven't /remember yet!");
				}
			}
		}
	}

}
