package havenmc.xyz.bloodbath;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class ConfigLis
  implements Listener
{
  Core plugin;

  public ConfigLis(Core instance)
  {
    this.plugin = instance;
  }

  @EventHandler
  public void onJoin(PlayerJoinEvent e)
  {
  }
}