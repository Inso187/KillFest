package me.havenmc.killfest;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class ConfigLis
  implements Listener
{
  main plugin;

  public ConfigLis(main instance)
  {
    this.plugin = instance;
  }

  @EventHandler
  public void onJoin(PlayerJoinEvent e)
  {
  }
}