package uk.co.CyniCode.CyniChat.Command;

import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import uk.co.CyniCode.CyniChat.CyniChat;
import uk.co.CyniCode.CyniChat.DataManager;
import uk.co.CyniCode.CyniChat.objects.UserDetails;

/**
 * As much as this is playing with fire... this is for the /me command
 * so a player can write in the context of themselves
 * i.e. [g] Steve does this rather than [g] Steve : does that
 * @author Matthew Ball
 *
 */
public class MeCommand implements CommandExecutor {

	public boolean onCommand(CommandSender player, Command command, String key, String[] objects) {
		CyniChat.printDebug("Initialised a /me command");
		if ( objects[0] != null ) {
			String Message = player.getName() + stacker( objects );
			UserDetails user = DataManager.getOnlineDetails( (Player) player );
			String curChan = user.getCurrentChannel();
			String TMessage = DataManager.getChannel(curChan).getColour()+"["+DataManager.getChannel(curChan).getNick()+"] " + Message;
			Map<String, UserDetails> online = DataManager.returnAllOnline();
			Object[] all = online.keySet().toArray();
			for ( int i=0; i<all.length; i++ ) {
				UserDetails current = online.get(all[i]);
				if ( current.getAllChannels().contains(curChan) && !current.getIgnoring().contains(player.getName().toLowerCase() ) ) {
					current.getPlayer().sendMessage(TMessage);
				}
			}
			
			String linkedChan = DataManager.getChannel(curChan).getIRC();
			
			CyniChat.printDebug("End of a /me command");
			return true;
		}
		player.sendMessage("Please provide an action to go with the /me action");
		return true;
	}
	
	public String stacker( String[] message ) {
		String stackedMessage = "";
		for ( int i=0; i<message.length; i++ ) {
			stackedMessage += " "+message[i];
		}
		return stackedMessage;
	}

}
