package uk.co.CyniCode.CyniChat.Command;

import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import uk.co.CyniCode.CyniChat.CyniChat;
import uk.co.CyniCode.CyniChat.DataManager;
import uk.co.CyniCode.CyniChat.PermissionManager;
import uk.co.CyniCode.CyniChat.Channel.Channel;
import uk.co.CyniCode.CyniChat.Chatting.UserDetails;

public class GeneralCommand {

	public static boolean save( CommandSender player ) {
		if ( PermissionManager.checkPerm( (Player) player, "cynichat.admin.save") ) {
			DataManager.saveChannelConfig();
			DataManager.saveUserDetails();
			return true;
		}
		return false;
	}
	
	public static boolean reload( CommandSender player ) {
		if ( PermissionManager.checkPerm( (Player) player, "cynichat.admin.reload") ) {
			CyniChat.reload();
		}
		return false;
	}
	
	public static boolean info( CommandSender player, String channel ) {
		if ( PermissionManager.checkPerm( (Player) player, "cynichat.basic.info."+channel) ) {
			Channel chan = DataManager.getChannel(channel);
			player.sendMessage( "Name: "+chan.getColour()+chan.getName() );
			player.sendMessage( "Nick: "+chan.getColour()+"["+chan.getNick()+"]" );
			player.sendMessage( "Description: "+chan.getDesc() );
			return true;
		}
		return false;
	}
	
	public static boolean list( CommandSender player, int page ) {
		if ( PermissionManager.checkPerm( (Player) player, "cynichat.basic.list" ) ) {
			Map<String, Channel> channels = DataManager.returnAllChannels();
			Object[] keys = channels.keySet().toArray();
			for ( int i=0; i<keys.length; i++ ) {
				Channel current = channels.get(keys[i]);
				player.sendMessage( current.getColour() +"["+ current.getNick() +"] "+ current.getName() );
			}
			return true;
		}
		return false;
	}
	
	public static boolean who( CommandSender player, String channel ) {
		if ( PermissionManager.checkPerm( (Player) player, "cynichat.basic.who."+channel) ) {
			String players = "";
			Map<String, UserDetails> online = DataManager.returnAllOnline();
			Object[] keys = online.keySet().toArray();
			for ( int i=0; i<keys.length; i++ ) {
				UserDetails current = online.get(keys[i]);
				if ( current.getAllChannels().contains(channel) ) {
					players = players + current.getName()+" ";
				}
			}
			player.sendMessage(players);
			return true;
		}
		return false;
	}
	
	public static boolean quickMessage( CommandSender player, String channel, String Message ) {
		if ( PermissionManager.checkPerm( (Player) player, "cynichat.basic.qm") ) {
			UserDetails user = DataManager.getOnlineDetails( (Player) player );
			if ( user.getAllChannels().contains(channel) ) {
				Channel curChan = DataManager.getChannel(channel);
				Map<String, UserDetails> online = DataManager.returnAllOnline();
				Object[] keys = online.keySet().toArray();
				for ( int i=0; i<keys.length; i++ ) {
					UserDetails current = online.get(keys[i]);
					if ( current.getAllChannels().contains(channel) ) {
						current.getPlayer().sendMessage(curChan.getColour()
								+"["+curChan.getNick()+"] <"
								+current.getPlayer().getDisplayName()+"> "
								+ Message );
					}
				}
				return true;
			}
			player.sendMessage("You are not in this channel");
			return true;
		}
		return false;
	}
	
	public static boolean qmInfo( CommandSender player ) {
		player.sendMessage(ChatColor.RED+"Invalid Syntax!");
		player.sendMessage("/qm "+ChCommand.necessary("channel")+" "+ChCommand.necessary("message"));
		return true;
	}
}