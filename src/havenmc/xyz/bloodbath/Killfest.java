package havenmc.xyz.bloodbath;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import net.md_5.bungee.api.ChatColor;

@SuppressWarnings("unused")
public class Killfest
  implements CommandExecutor, Listener
{
  Core plugin;
  public static ArmorStand IH1;
  public static ArmorStand IH2;
  public static ArmorStand IH3;
  public static ArmorStand IH4;
  public static ArmorStand IH5;
  public static ArmorStand chestHolo1;
  public static ArmorStand chestHolo2;
  public static ArmorStand chestHolo3;

  public Killfest(Core instance)
  {
    this.plugin = instance;
  }

  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    if (cmd.getName().equalsIgnoreCase("killfest")) {
      if (!(sender instanceof Player)) {
        Bukkit.getConsoleSender().sendMessage(Colour.format(this.plugin.getConfig().getString("only-players")));
        return true;
      }
      FileConfiguration config = this.plugin.getConfig();
      Player p = (Player)sender;

      if (args.length == 0) {
        KillfestHelpMSG(p);
      }

      if ((args.length == 1) && (args[0].equalsIgnoreCase("reload"))) {
        if (p.hasPermission("killfest.reload")) {
          this.plugin.reloadConfig();
          this.plugin.reloadConfig();
          Bukkit.getConsoleSender().sendMessage(Colour.format(config.getString("config-reloaded")));
          p.sendMessage(Colour.format(config.getString("config-reloaded")));
        } else {
          p.sendMessage(Colour.format(config.getString("no-permission")));
        }

      }
      


      if ((args.length == 1) && (args[0].equalsIgnoreCase("timer")))
      {
        int minutes = Core.Cooldown / 60;
        int seconds = Core.Cooldown % 60;

        String msg = minutes + " minutes and " + seconds + " seconds";
        if (ChestOpen.killfeststatus) {
          p.sendMessage(Colour.format(config.getString("already-running")));
        } else {
          String path1 = config.getString("next-killfest");
          path1 = path1.replaceAll("%time%", msg);
        }

      }

      if ((args.length == 1) && (args[0].equalsIgnoreCase("InfoHologram")))
      {
        if (p.hasPermission("killfest.spawnhologram"))
        {
          if (config.getBoolean("hologram.DontEDIT") == Boolean.FALSE.booleanValue()) {
            Location loc = p.getLocation();
            String ww = p.getWorld().getName();
            double x = loc.getBlockX();
            double y = loc.getBlockY();
            double z = loc.getBlockZ();
            this.plugin.getConfig().set("hologram.loc.w", ww);
            this.plugin.getConfig().set("hologram.loc.x", Double.valueOf(x));
            this.plugin.getConfig().set("hologram.loc.y", Double.valueOf(y));
            this.plugin.getConfig().set("hologram.loc.z", Double.valueOf(z));
            this.plugin.getConfig().set("hologram.DontEDIT", Boolean.TRUE);
            this.plugin.saveConfig();

            World w = Bukkit.getWorld(config.getString("hologram.loc.w"));
            Location tloc = new Location(w, x, y - 0.3D, z);
            Core.infoholo1 = (ArmorStand)Bukkit.getWorld(config.getString("hologram.loc.w")).spawnEntity(tloc, EntityType.ARMOR_STAND);
            Core.infoholo1.setGravity(false);
            Core.infoholo1.setVisible(false);
            Core.infoholo1.setCustomNameVisible(true);
            Core.infoholo1.setCustomName(Colour.format("&4&k|||&f &c&lKill Fest test &4&k|||"));

            y -= 0.5D;
            x -= 0.5D;
            z -= 0.5D;
            tloc = new Location(w, x, y - 0.3D, z);
            Core.infoholo2 = (ArmorStand)Bukkit.getWorld(config.getString("hologram.loc.w")).spawnEntity(tloc, EntityType.ARMOR_STAND);
            Core.infoholo2.setGravity(false);
            Core.infoholo2.setVisible(false);
            Core.infoholo2.setCustomNameVisible(true);
            Core.infoholo2.setCustomName(Colour.format("[Reload]"));

            y -= 0.5D;
            x -= 0.5D;
            z -= 0.5D;
            tloc = new Location(w, x, y - 0.3D, z);
            Core.infoholo3 = (ArmorStand)Bukkit.getWorld(config.getString("hologram.loc.w")).spawnEntity(tloc, EntityType.ARMOR_STAND);
            Core.infoholo3.setGravity(false);
            Core.infoholo3.setVisible(false);
            Core.infoholo3.setCustomNameVisible(true);
            Core.infoholo3.setCustomName(Colour.format("[Reload]"));

            p.sendMessage(Colour.format(config.getString("hologram-spawned") + " &8[&7&oreload for hologram to work&8]"));

            return true;
          }
          p.sendMessage(Colour.format(config.getString("hologram-already-spawned")));
        }
        else
        {
          p.sendMessage(Colour.format(config.getString("no-permission")));
        }

      }

      if ((args.length == 1) && (args[0].equalsIgnoreCase("delInfoHologram")))
      {
        if (p.hasPermission("killfest.spawnholo")) {
          if (config.getBoolean("hologram.DontEDIT") == Boolean.TRUE.booleanValue()) {
            Core.infoholo1.remove();
            Core.infoholo2.remove();
            Core.infoholo3.remove();
            this.plugin.getConfig().set("hologram.DontEDIT", Boolean.FALSE);
            this.plugin.saveConfig();
            p.sendMessage(Colour.format(config.getString("hologram-deleted")));

            return true;
          }
          p.sendMessage(Colour.format(config.getString("no-hologram-to-delete")));
        }
        else
        {
          p.sendMessage(Colour.format(config.getString("no-permission")));
        }

      }

      if ((args.length == 1) && (args[0].equalsIgnoreCase("setLocation"))) {
        if (p.hasPermission("killfest.setlootloc")) {
          Location loc = p.getLocation();
          String w = p.getWorld().getName();
          double x = loc.getBlockX();
          double y = loc.getBlockY();
          double z = loc.getBlockZ();
          this.plugin.getConfig().set("chestloc.w", w);
          this.plugin.getConfig().set("chestloc.x", Double.valueOf(x));
          this.plugin.getConfig().set("chestloc.y", Double.valueOf(y));
          this.plugin.getConfig().set("chestloc.z", Double.valueOf(z));

          this.plugin.saveConfig();

          p.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("location-set")));
        }
        else {
          p.sendMessage(Colour.format(config.getString("no-permission")));
        }

      }

      if ((args.length == 1) && (args[0].equalsIgnoreCase("start")))
      {
        if (ChestOpen.killfeststatus == Boolean.TRUE.booleanValue()) {
          p.sendMessage(Colour.format(config.getString("already-running")));
          return true;
        }

        World w = Bukkit.getWorld(config.getString("chestloc.w"));
        double x = config.getDouble("chestloc.x");
        double y = config.getDouble("chestloc.y");
        double z = config.getDouble("chestloc.z");
        Location cloc = new Location(w, x, y, z);

        cloc.getBlock().setType(Material.CHEST);
        Chest chest = (Chest)cloc.getBlock().getState();
        y += .5D;
        x += .5D;
        z += .5D;

        String path1 = config.getString("chesthologram-1");
        path1 = path1.replaceAll("%status%", "Left Click");
        path1 = path1.replaceAll("%time%", "");

        Location hloc = new Location(w, x, y - 0.3D, z);
        chestHolo1 = (ArmorStand)p.getWorld().spawnEntity(hloc, EntityType.ARMOR_STAND);
        chestHolo1.setGravity(false);
        chestHolo1.setVisible(false);
        chestHolo1.setCustomNameVisible(true);
        chestHolo1.setCustomName(Colour.format(path1));

        String path2 = config.getString("chesthologram-2");
        path2 = path2.replaceAll("%status%", "Left Click");
        path2 = path2.replaceAll("%time%", "");
        y -= 0.3D;
        hloc = new Location(w, x, y - 0.3D, z);
        chestHolo2 = (ArmorStand)p.getWorld().spawnEntity(hloc, EntityType.ARMOR_STAND);
        chestHolo2.setGravity(false);
        chestHolo2.setVisible(false);
        chestHolo2.setCustomNameVisible(true);
        chestHolo2.setCustomName(Colour.format(path2));

        String path3 = config.getString("chesthologram-3");
        path3 = path3.replaceAll("%status%", "Left Click");
        path3 = path3.replaceAll("%time%", "");
        hloc = new Location(w, x, y - 0.6D, z);
        chestHolo3 = (ArmorStand)p.getWorld().spawnEntity(hloc, EntityType.ARMOR_STAND);
        chestHolo3.setGravity(false);
        chestHolo3.setVisible(false);
        chestHolo3.setCustomNameVisible(true);
        chestHolo3.setCustomName(Colour.format(path3));

        ChestOpen.killfeststatus = true;
        ChestOpen.cooldownstatus = false;
        ChestOpen.cheststatus = true;
        p.sendMessage(Colour.format(config.getString("killfest-force-start")));

        ArrayList<String> startedkillfest = new ArrayList<String>();

  		for (String startedk : config.getStringList("started-killfest.lore")) {
  			startedkillfest.add(Colour.format(startedk));

          Bukkit.broadcastMessage(Colour.format(startedk));
      }
//        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tm bc " + config.getString("message.BroadcastEvent"));
        return true;
      }

      if ((args.length == 1) && (args[0].equalsIgnoreCase("stop"))) {
        if (ChestOpen.killfeststatus == Boolean.FALSE.booleanValue()) {
          p.sendMessage(Colour.format(config.getString("no-killfest-stop")));
          return true;
        }

        chestHolo1.remove();
        chestHolo2.remove();
        chestHolo3.remove();
        World w = Bukkit.getWorld(config.getString("chestloc.w"));
        double x = config.getDouble("chestloc.x");
        double y = config.getDouble("chestloc.y");
        double z = config.getDouble("chestloc.z");
        Location cloc = new Location(w, x, y, z);
        cloc.getBlock().setType(Material.AIR);
        
        ArrayList<String> stopkillfest = new ArrayList<String>();

  		for (String stopk : config.getStringList("stop-killfest.lore")) {
  			stopkillfest.add(Colour.format(stopk));

          Bukkit.broadcastMessage(Colour.format(stopk));
      }
        ChestOpen.cooldownstatus = false;
        ChestOpen.killfeststatus = false;
        ChestOpen.cheststatus = true;
        return true;
      }

    }

    return true;
  }

  public void KillfestHelpMSG(Player p)
  {
    FileConfiguration config = this.plugin.getConfig();
      p.sendMessage(Colour.format("&c&l(!) &c&nKillFest&c:"));
      p.sendMessage(Colour.format("&7A &aSaiCo&dPvP&c BloodBath&7 fork by &b&n@Yuuji"));
      p.sendMessage(Colour.format("&7on &3MC-Market.org"));
      p.sendMessage(Colour.format(""));
    if (p.hasPermission(config.getString("killfest.start"))) {
      p.sendMessage(Colour.format("&c&l* &7/killfest start &8- &7Forcefully starts a KillFest."));
    if (p.hasPermission(config.getString("killfest.stop"))) {
      p.sendMessage(Colour.format("&c&l* &7/killfest stop &8- &7Forcefully stops a KillFest."));
    if (p.hasPermission(config.getString("killfest.reload"))) {  
      p.sendMessage(Colour.format("&c&l* &7/killfest reload &8- &7Reloads the configuration."));
    if (p.hasPermission(config.getString("killfest.timer"))) {
      p.sendMessage(Colour.format("&c&l* &7/killfest timer &8- &7Shows the time till the next KillFest."));
    if (p.hasPermission(config.getString("killfest.setlootloc"))) {
      p.sendMessage(Colour.format("&c&l* &7/killfest setlocation &8- &7Sets the KillFest start location."));
    if (p.hasPermission(config.getString("killfest.spawnholo"))) {
      p.sendMessage(Colour.format("&c&l* &7/killfest infohologram &8- &7Creates the information hologram."));
    if (p.hasPermission(config.getString("killfest.spawnholo"))) {
      p.sendMessage(Colour.format("&c&l* &7/killfest delinfohologram &8- &7Deletes the information hologram."));
  }
    else {
        p.sendMessage(Colour.format(config.getString("no-permission")));
      }
}
}
}
  }
}
    }
  }
}