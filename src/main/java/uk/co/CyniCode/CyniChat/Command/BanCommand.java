package uk.co.CyniCode.CyniChat.Command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import uk.co.CyniCode.CyniChat.DataManager;
import uk.co.CyniCode.CyniChat.PermissionManager;
import uk.co.CyniCode.CyniChat.objects.Channel;

/**
 * All the relevant snippits on banning/kicking etc.
 * @author Matthew Ball
 *
 */
public class BanCommand {

	/**
	 * Ban a player from a channel
	 * @param player : The player doing the banning
	 * @param channel : The channel the other player is being banned in
	 * @param banee : The naughty person being banned
	 * @return true when complete
	 */
	public static boolean ban(CommandSender player, Channel channel, String banee) {
		if ( player instanceof Player )
			if ( !PermissionManager.checkPerm( (Player) player, "cynichat.mod.ban."+channel.getName().toLowerCase() ) )
				return false;
		
		DataManager.getDetails( banee.toLowerCase() ).newBan(player, channel);
		return true;
	}

	/**
	 * Unban a player from a channel
	 * @param player : The player doing the unbanning
	 * @param channel : The channel the other player is being unbanned in
	 * @param banee : The lucky player who has a second chance
	 * @return true when complete
	 */
	public static boolean unban(CommandSender player, Channel channel, String banee) {
		if ( player instanceof Player )
			if ( !PermissionManager.checkPerm( (Player) player, "cynichat.mod.ban."+channel.getName().toLowerCase() ) )
				return false;
		
		DataManager.getDetails( banee.toLowerCase() ).remBan(player, channel);
		return true;
	}

	/**
	 * Return the information about the ban command
	 * @param player : The person we're giving the information
	 * @return true when complete
	 */
	public static boolean banInfo( CommandSender player ) {
		player.sendMessage(ChatColor.RED+"Invalid Syntax!");
		player.sendMessage("/ch ban "+ChCommand.necessary("player")+" "+ChCommand.optional("channel"));
		return true;
	}

	/**
	 * Return the information about the unban command
	 * @param player : The player we're giving the information to
	 * @return true when complete
	 */
	public static boolean unbanInfo( CommandSender player ) {
		player.sendMessage(ChatColor.RED+"Invalid Syntax!");
		player.sendMessage("/ch unban "+ChCommand.necessary("player")+" "+ChCommand.optional("channel"));
		return true;
	}

	/**
	 * Kick the player from the channel
	 * @param player : The player trying to kick someone else
	 * @param channel : The channel the other player is being kicked from
	 * @param kickee : And stay out!
	 * @return true when complete
	 */
	public static boolean kick(CommandSender player, Channel channel, String kickee) {
		if ( player instanceof Player )
			if ( !PermissionManager.checkPerm( (Player) player, "cynichat.mod.kick."+channel.getName().toLowerCase() ) )
				return false;
		
		DataManager.getDetails( kickee.toLowerCase() ).Kick(player, channel);
		return true;
		
	}

	/**
	 * Return the information on how to kick a player
	 * @param player : Firstly, you should pick them up
	 * @return And then boot them out
	 */
	public static boolean kickInfo(CommandSender player) {
		player.sendMessage(ChatColor.RED+"Invalid Syntax!");
		player.sendMessage("/ch kick "+ChCommand.necessary("player")+" "+ChCommand.optional("channel"));
		return true;
	}

}
