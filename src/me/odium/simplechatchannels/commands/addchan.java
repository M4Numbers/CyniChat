package me.odium.simplechatchannels.commands;

import java.util.List;

import me.odium.simplechatchannels.Loader;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class addchan implements CommandExecutor {   

  public Loader plugin;
  public addchan(Loader plugin)  {
    this.plugin = plugin;
  }


  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)  {
    Player player = null;
    if (sender instanceof Player) {
      player = (Player) sender;
    }    

    Player[] players = Bukkit.getOnlinePlayers();
    if (player == null) {
      sender.sendMessage("This command can only be run by a player");
      return true;
    }
    if (args.length == 0) {
      sender.sendMessage("/addchan <channelname> - Create a channel");
      sender.sendMessage("/addchan -l <channelname> - Create a locked channel");
      return true;
    } else if (args.length == 1) {     
      String ChanName = args[0].toLowerCase(); 
      String PlayerName = player.getName().toLowerCase();
      if (plugin.getStorageConfig().contains(ChanName)) {
        sender.sendMessage(plugin.DARK_GREEN+"[SCC] "+plugin.RED + ChanName + plugin.DARK_GREEN+ " already exists");
        return true;
      }
      plugin.getStorageConfig().createSection(ChanName); // create the 'channel'
      List<String> ChList = plugin.getStorageConfig().getStringList(ChanName+".list"); // create/get the player list
      List<String> OwList = plugin.getStorageConfig().getStringList(ChanName+".owner"); // create/get the owner list
      List<String> AccList = plugin.getStorageConfig().getStringList(ChanName+".AccList"); // create/get the owner list
      List<String> ChannelsList = plugin.getStorageConfig().getStringList("Channels"); // create/get the owner list
      List<String> InChatList = plugin.getStorageConfig().getStringList("InChatList"); // get the player list
      ChList.add(PlayerName);  // add the player to the list
      OwList.add(PlayerName);  // add the player to the owner list
      AccList.add(PlayerName);  // add the player to the access list
      ChannelsList.add(ChanName);
      InChatList.add(PlayerName);  // add the player to the list      
      plugin.getStorageConfig().set(ChanName+".list", ChList); // set the new list
      plugin.getStorageConfig().set(ChanName+".owner", OwList); // set the new list
      plugin.getStorageConfig().set(ChanName+".AccList", AccList); // set the new list
      plugin.getStorageConfig().set(ChanName+".Locked", false); // set the new list
      plugin.getStorageConfig().set("Channels", ChannelsList); // set the new list  
      plugin.getStorageConfig().set("InChatList", InChatList); // set the new list
      plugin.saveStorageConfig();
      sender.sendMessage(plugin.DARK_GREEN+"[SCC] "+ plugin.GREEN +  ChanName + plugin.DARK_GREEN + " Created");
      plugin.ChannelThing.put(player, ChanName);
      plugin.pluginEnabled.put(player, true);
      List<String> ChanList = plugin.getStorageConfig().getStringList(ChanName+".list");

      for(Player op: players){
        if(ChanList.contains(op.getName())) {
          op.sendMessage(plugin.DARK_GREEN+"[SCC] "+ ChatColor.GOLD + PlayerName + ChatColor.DARK_GREEN+" joined "+ ChanName);
        }
      }
      return true;
    } else if (args.length == 2 && args[0].equalsIgnoreCase("-l")) {
      String ChanName = args[0].toLowerCase();
      String PlayerName = player.getName().toLowerCase();
      Boolean bool = true;
      if (plugin.getStorageConfig().contains(ChanName)) {
        sender.sendMessage(plugin.DARK_GREEN+"[SCC] "+plugin.RED + ChanName + plugin.DARK_GREEN+ " already exists");
        return true;
      }
      plugin.getStorageConfig().createSection(ChanName); // create the 'channel'
      List<String> ChList = plugin.getStorageConfig().getStringList(ChanName+".list"); // create/get the player list
      List<String> OwList = plugin.getStorageConfig().getStringList(ChanName+".owner"); // create/get the owner list
      List<String> AccList = plugin.getStorageConfig().getStringList(ChanName+".AccList"); // create/get the owner list
      List<String> ChannelsList = plugin.getStorageConfig().getStringList("Channels"); // create/get the owner list
      List<String> InChatList = plugin.getStorageConfig().getStringList("InChatList"); // get the player list
      ChList.add(PlayerName);  // add the player to the list
      OwList.add(PlayerName);  // add the player to the owner list
      AccList.add(PlayerName);  // add the player to the access list
      ChannelsList.add(ChanName);
      InChatList.add(PlayerName);  // add the player to the list
      plugin.getStorageConfig().set(ChanName+".list", ChList); // set the new list
      plugin.getStorageConfig().set(ChanName+".owner", OwList); // set the new list
      plugin.getStorageConfig().set(ChanName+".AccList", AccList); // set the new list
      plugin.getStorageConfig().set(ChanName+".locked", bool); // set the new list
      plugin.getStorageConfig().set("Channels", ChannelsList); // set the new list
      plugin.getStorageConfig().set("InChatList", InChatList); // set the new list
      plugin.saveStorageConfig();
      sender.sendMessage(plugin.DARK_GREEN+"[SCC] "+"Locked Channel " + plugin.GREEN + ChanName + plugin.DARK_GREEN + " Created");
      plugin.ChannelThing.put(player, ChanName);
      plugin.pluginEnabled.put(player, true);
      List<String> ChanList = plugin.getStorageConfig().getStringList(ChanName+".list");
      for(Player op: players){
        if(ChanList.contains(op.getName())) {
          op.sendMessage(plugin.DARK_GREEN+"[SCC] "+ ChatColor.GOLD + PlayerName + ChatColor.DARK_GREEN+" joined "+ ChanName);
        }
      }

      return true;       
    }
    return true;
  }
}