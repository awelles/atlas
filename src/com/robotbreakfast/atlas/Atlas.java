package com.robotbreakfast.atlas;
import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.map.MapView;
import org.bukkit.plugin.java.JavaPlugin;




public class Atlas extends JavaPlugin {
	
	String logPreFix = "[Atlas] ";
	
	public void logWrite(String msg){
		log.info(this.logPreFix + msg);
	}
	public Logger log = Logger.getLogger("Minecraft");
	public Boolean isbeeping = false;
	public Conf conf = new Conf(this);
		
	public void onEnable(){
		
		if (conf.exists())
		{
			conf.Load();
		}
		else
		{
			logWrite("NoConf. trying create");
			try {
				conf.Create();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		log.info("[Atlas] Shits been enabled yo!");
		String s = conf.mapNames.toString();
		
		logWrite(s);

		}

	
	public void onDisable(){
		try {
			conf.Save();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info("Disabling Atlas.");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		if(cmd.getName().equalsIgnoreCase("map")){
			
			if (args.length==0) {
				sender.sendMessage("Check yer args");
				return false;
			}
			
			String name = args[0];
			short id = 0;
			try
			{
				id = conf.getMap(name);
			}
			catch (NullPointerException e)
			{
				sender.sendMessage("Map not found.");
				return false;
			}
			
			if (sender instanceof Player)
			{
				ItemStack mapStack = new ItemStack(358,1,id);

				Player player = (Player) sender;
				PlayerInventory inventory = player.getInventory();
				
				ItemStack heldItem = player.getInventory().getItemInHand();
				if (heldItem.getTypeId()==358)
				{

					inventory.setItemInHand(mapStack);

				}
				else
				{

					inventory.addItem(mapStack);
				}
				
				
				player.sendMessage("Map " + id + " sent.");
				
				return true;
				
			}
			else
			{
				String r = name + " --> " + id;
				
				
				sender.sendMessage(r);
				
				return true;	
			}
		}

		
		
		if(cmd.getName().equalsIgnoreCase("mapfix")){
			if (!(sender instanceof Player)) {
				sender.sendMessage("Only works for players at the moment.");
				return true;
			}
			
			Player player = (Player)sender;
			short id = 0;
			
			if (args.length==0)
			{
				ItemStack heldItem = player.getInventory().getItemInHand();
				if (heldItem.getTypeId()==358)
				{
					id = heldItem.getData().getData();
				}
				else
				{
					player.sendMessage("Fix wut?");
					return false;
				}
			}
			else
			{
				id = (short) Integer.parseInt(args[0]);
			}
			
			player.sendMessage("Sending: " + id);
			
			MapView mapview = this.getServer().getMap(id);			
			player.sendMap(mapview);
			player.sendMessage("Done.");

			
			return true;
		}
		
		if(cmd.getName().equalsIgnoreCase("togglebeep")){
			this.isbeeping = !(this.isbeeping);
			sender.sendMessage(this.isbeeping+"");
			
			return true;
		}
		if(cmd.getName().equalsIgnoreCase("mapsave")){
			try {
				logWrite("Saving.");
				conf.Save();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return true;
		}
		if(cmd.getName().equalsIgnoreCase("togglebeep")){
			this.isbeeping = !(this.isbeeping);
			sender.sendMessage(this.isbeeping+"");
			
			return true;
		} 
		
		if(cmd.getName().equalsIgnoreCase("mapload")){
			conf.Load();
			return true;
		}
		if(cmd.getName().equalsIgnoreCase("mapnames")){
			sender.sendMessage(conf.mapNames.toString());
			return true;
		}
		if(cmd.getName().equalsIgnoreCase("mapgivename")){
			
			short id = 0;
			String name = null;
			
			if (args.length==0) { sender.sendMessage("Check yer args."); return false; }
			
			if ((args.length==1)&&(sender instanceof Player))
			{
				Player player = (Player)sender;

				ItemStack heldItem = player.getInventory().getItemInHand();
				if (heldItem.getTypeId()==358)
				{
					name = args[0];
					id = heldItem.getData().getData();
				}
				else
				{
					sender.sendMessage("Check yer args.");
					return false;
				}
			}
			else if (args.length==2)
			{
				name = args[0];
				try
				{
				id = (short) Integer.parseInt(args[1]);
				}
				catch (NumberFormatException e)
				{
					sender.sendMessage("Error parsing map id into Short");
					return false;
				}
			}
			else
			{
				sender.sendMessage("Check yer args.");
				return false;
			}
			
			String r = conf.giveName(id, name);
			
			sender.sendMessage(r);
			return true;
		}
		if(cmd.getName().equalsIgnoreCase("mapdelname")){
			short id = 0;
			String name = null;
			
			if (args.length==0) {sender.sendMessage("Check yer args."); return false; }
			
			if ((args.length==1)&&(sender instanceof Player))
			{
				Player player = (Player)sender;

				ItemStack heldItem = player.getInventory().getItemInHand();
				if (heldItem.getTypeId()==358)
				{
					name = args[0];
					id = heldItem.getData().getData();
				}
				else
				{
					sender.sendMessage("Check yer args.");
					return false;
				}
			}
			else if (args.length==2)
			{
			
				name = args[0];
				try
				{
				id = (short) Integer.parseInt(args[1]);
				}
				catch (NumberFormatException e)
				{
					sender.sendMessage("Error parsing map id into Short");
					return true;
				}
			}
			else
			{
				sender.sendMessage("Check yer args.");
				return false;
			}
			
			String r = conf.delName(id, name);
			
			sender.sendMessage(r);
			return true;
		}
		if(cmd.getName().equalsIgnoreCase("mapinfo")){
			
			short id = 0;
			
			if ((args.length==0)&&(sender instanceof Player)){
				Player player = (Player)sender;

				ItemStack heldItem = player.getInventory().getItemInHand();
				if (heldItem.getTypeId()==358)
				{
					id = heldItem.getData().getData();
				}
			}
			else if (args.length==1){
				try
				{
					id = (short) Integer.parseInt(args[0]);
				}
				catch (NumberFormatException e)
				{
					sender.sendMessage("Error parsing map id into Short");
					return false;
				}
			}
			else
			{
				sender.sendMessage("Check yer args.");
				return false;
			}
			
			MapView mapView = this.getServer().getMap(id);
									
			sender.sendMessage("Map: " + mapView.getId() +"\n");
			sender.sendMessage("Center (x,z): " + mapView.getCenterX() + ", " + mapView.getCenterZ() + "\n");
			sender.sendMessage("Scale: " + mapView.getScale().getValue() + "\n");
			sender.sendMessage("World: " + mapView.getWorld().getName() + "\n");
		
			
			return true;
				
		}

				
		return false;
	}
}
