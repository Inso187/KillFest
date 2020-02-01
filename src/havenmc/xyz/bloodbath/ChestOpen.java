package havenmc.xyz.bloodbath;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ChestOpen
  implements Listener
{
  Core plugin;
  public static boolean killfeststatus;
  public static boolean cooldownstatus;
  public static boolean cheststatus;
  private int cooldowntimer;
  int delechest;
  public static int Counterdown = 300;

  public ChestOpen(Core instance)
  {
    this.plugin = instance;
  }

  @SuppressWarnings("unused")
@EventHandler
  public void onChestOpen(PlayerInteractEvent e)
  {
	  Action action = e.getAction();
	  Player player = e.getPlayer();
	  Block block  = e.getClickedBlock();
	  
	  
    if(action.equals(Action.LEFT_CLICK_BLOCK)) {
    	if(block.getType().equals(Material.CHEST)) {
      FileConfiguration config = this.plugin.getConfig();
      Player p = e.getPlayer();
      World w = Bukkit.getWorld(config.getString("chestloc.w"));
      double x = config.getDouble("chestloc.x");
      double y = config.getDouble("chestloc.y");
      double z = config.getDouble("chestloc.z");
      Location cloc = new Location(w, x, y, z);

      if (e.getClickedBlock().getLocation().equals(cloc)) {
        if (killfeststatus == Boolean.TRUE.booleanValue())
        {
          if (cheststatus == Boolean.TRUE.booleanValue()) {
            String path1 = config.getString("player-started-countdown");
            path1 = path1.replaceAll("%player%", p.getName());
            path1 = path1.replaceAll("%coords%", "x:" + x + ", y:" + y + ", z:" + z);
            Bukkit.broadcastMessage(Colour.format(""));
            Bukkit.broadcastMessage(Colour.format(path1));
            Bukkit.broadcastMessage(Colour.format(""));
            Counterdown = 300;
            cheststatus = Boolean.FALSE.booleanValue();
            cooldownstatus = Boolean.TRUE.booleanValue();
            cooldown();
            return;
          }

          if (cooldownstatus == Boolean.TRUE.booleanValue()) {
            p.sendMessage(Colour.format(config.getString("already-on-countdown")));
            return;
          }
          
 //         boolean prize = Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "cr give " + p.getName() + " killfestbag 1");
          
          Bukkit.dispatchCommand(Bukkit.getConsoleSender(), config.getString("command").replaceAll("%player%", p.getName()));
          
//          Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "titlemanager bc " + p.getName() + config.getString("message.winnerbc"));

  		ArrayList<String> winnerBroadcast = new ArrayList<String>();

  		for (String winner : config.getStringList("WinnerBroadcast.lore")) {
  			winnerBroadcast.add(Colour.format(winner));
  			winner = winner.replaceAll("%player%", p.getName());

          Bukkit.broadcastMessage(Colour.format(winner));

          Killfest.chestHolo1.remove();
          Killfest.chestHolo2.remove();
          Killfest.chestHolo3.remove();
          cooldownstatus = false;
          killfeststatus = false;
          cheststatus = true;
          cloc.getBlock().setType(Material.AIR);
          return;
        }

        return;
      }

      return;
      
    }

    return;
  }

  return;
}
  }


  public void delechest()
  {
    FileConfiguration config = this.plugin.getConfig();
    World w = Bukkit.getWorld(config.getString("chestloc.w"));
    double x = config.getDouble("chestloc.x");
    double y = config.getDouble("chestloc.y");
    double z = config.getDouble("chestloc.z");
    final Location cloc = new Location(w, x, y, z);
    this.delechest = Bukkit.getScheduler().scheduleSyncRepeatingTask(this.plugin, new Runnable()
    {
      int Counterdownd = 3;

      public void run()
      {
        this.Counterdownd -= 1;

        if (this.Counterdownd == 0) {
          cloc.getBlock().setType(Material.AIR);
          Bukkit.getScheduler().cancelTask(ChestOpen.this.delechest);
        }
      }
    }
    , 0L, 20L);
  }

  public void cooldown()
  {
    final FileConfiguration config = this.plugin.getConfig();
    this.cooldowntimer = Bukkit.getScheduler().scheduleSyncRepeatingTask(this.plugin, new Runnable()
    {
      public void run()
      {
        if (ChestOpen.cooldownstatus) {
          ChestOpen.Counterdown -= 1;

          int mins = ChestOpen.Counterdown / 60;
          int secs = ChestOpen.Counterdown % 60;

          String path1 = config.getString("chesthologram-1");
          path1 = path1.replaceAll("%status%", "Unlocking...");
          path1 = path1.replaceAll("%time%", mins + ":" + secs);
          Killfest.chestHolo1.setCustomName(Colour.format(path1));

          String path2 = config.getString("chesthologram-2");
          path2 = path2.replaceAll("%status%", "Unlocking...");
          path2 = path2.replaceAll("%time%", mins + ":" + secs);
          Killfest.chestHolo2.setCustomName(Colour.format(path2));

          String path3 = config.getString("chesthologram-3");
          path3 = path3.replaceAll("%status%", "Unlocking...");
          path3 = path3.replaceAll("%time%", mins + ":" + secs);
          Killfest.chestHolo3.setCustomName(Colour.format(path3));

          if ((ChestOpen.Counterdown == 60) || (ChestOpen.Counterdown == 120) || (ChestOpen.Counterdown == 180) || (ChestOpen.Counterdown == 240))
          {
        	  ArrayList<String> countdownk = new ArrayList<String>();

        		for (String countdown : config.getStringList("countdown.lore")) {
        			countdownk.add(Colour.format(countdown));

                Bukkit.broadcastMessage(Colour.format(countdown));
                countdown = countdown.replaceAll("%time%", mins + " Minutes");
            
/*                String pathm = config.getString("message.EveryMinDisplay");
           pathm = pathm.replaceAll("%time%", mins + " Minutes");

            Bukkit.broadcastMessage(c.format(""));
            Bukkit.broadcastMessage(c.format(pathm));
            Bukkit.broadcastMessage(c.format(""));
*/          }

          if (ChestOpen.Counterdown <= 0) {
            path1 = path1.replaceAll("%status%", "Collect Prize");
            path1 = path1.replaceAll("%time%", "00:00");
            Killfest.chestHolo1.setCustomName(Colour.format(path1));

            path2 = path2.replaceAll("%status%", "Collect Prize");
            path2 = path2.replaceAll("%time%", "00:00");
            Killfest.chestHolo2.setCustomName(Colour.format(path2));

            path3 = path3.replaceAll("%status%", "Collect Prize");
            path3 = path3.replaceAll("%time%", "00:00");
            Killfest.chestHolo3.setCustomName(Colour.format(path3));
            Bukkit.broadcastMessage(Colour.format(""));
            Bukkit.broadcastMessage(Colour.format(config.getString("cooldownEnded")));
            Bukkit.broadcastMessage(Colour.format(""));
            ChestOpen.cooldownstatus = false;
            Bukkit.getScheduler().cancelTask(ChestOpen.this.cooldowntimer);
          }
        }
        else {
          Bukkit.getScheduler().cancelTask(ChestOpen.this.cooldowntimer);
        }
      }
    }
    }
    , 0L, 20L);
  


}
}