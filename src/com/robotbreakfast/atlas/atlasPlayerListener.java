package com.robotbreakfast.atlas;

import java.awt.Toolkit;

import org.bukkit.event.Listener;

import org.bukkit.event.player.PlayerLoginEvent;


public class atlasPlayerListener implements Listener {	
	private Atlas _plugin = null;

	public atlasPlayerListener(Atlas plugin)
	{
		_plugin = plugin;
	}
	
	public void onPlayerLogin(PlayerLoginEvent event) {
	    if (_plugin.isbeeping)
	    {
	    	Toolkit.getDefaultToolkit().beep();
	    }
	}

}
