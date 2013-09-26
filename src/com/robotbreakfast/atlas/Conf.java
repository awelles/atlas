package com.robotbreakfast.atlas;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

public class Conf {
	
	public Map<String,String> mapNames = new HashMap<String,String>();
	
	private Atlas _plugin = null;
	
	public Conf(Atlas plugin){
		_plugin = plugin;
	}
	
	public Boolean exists(){
		File dataFolder = _plugin.getDataFolder();
		if (dataFolder.exists()){
			if (dataFolder.isDirectory()){
				_plugin.logWrite("files in dataFolder: " + Helpers.print.printArray(dataFolder.list()));
				
				List<String> files = Arrays.asList(dataFolder.list());
				if (files.contains("mapNames.yml"))
				{
					_plugin.logWrite("mapNames found.");
					return true;
				}			
			}
		}
		return false;
	}
		

	
	public void Create() throws IOException{

		Map<String,String> mNames = new HashMap<String,String>();
		Yaml yaml = new Yaml();
		if (!(_plugin.getDataFolder().exists()))
		{
			_plugin.getDataFolder().mkdir();
		}
		File file = new File(_plugin.getDataFolder().getPath(), "mapNames.yml");
		file.createNewFile();
		FileWriter writer = new FileWriter(file);
		file.setWritable(true);
		yaml.dump(mNames,writer);
	}
	
	public void Load(){
		
		FileReader reader = null;
		Yaml yaml = new Yaml();
		File file = new File(_plugin.getDataFolder().getPath(), "mapNames.yml");
		try {
			reader = new FileReader(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		mapNames = (Map<String, String>) yaml.load(reader);		
	}
	
	public void Save() throws IOException{
		Yaml yaml = new Yaml();
		if (!(_plugin.getDataFolder().exists()))
		{
			_plugin.getDataFolder().mkdir();
		}
		File file = new File(_plugin.getDataFolder().getPath(), "mapNames.yml");
		//file.delete();
		FileWriter writer = new FileWriter(file);
		file.setWritable(true);
		yaml.dump(mapNames,writer);
	}
	
	public String giveName(Short mapNumber, String name)
	{
		this.mapNames.put(name,  mapNumber.toString());
		return "Map " + mapNumber.toString() + " is now known as " + name;
	}
	
	public String delName(Short mapNumber, String name)
	{

		String v1 = null;
		try
		{
			v1 = mapNames.get(name);	
		}
		catch (NullPointerException e)
		{
			return "Nope.";
		}
		if (v1==null) { return "Is it named that?"; }
		
		String v2 = mapNumber.toString();

		if(v1.equals(v2)){

			mapNames.remove(name);
			return "Unnamed.";
		}

		return "Is it named that?";

	}
	
	public short getMap(String name) throws NullPointerException {
		String idString = mapNames.get(name);


		if (idString==null) 
		{
			throw new NullPointerException();
		}

		short id = 0;
		try
		{
			id = (short) Integer.parseInt(idString);
		}
		catch (NumberFormatException e)
		{
		
			e.printStackTrace();
		}
		
		return id;
		
	}
	
	

}
