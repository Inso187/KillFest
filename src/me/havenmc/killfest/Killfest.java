package me.havenmc.killfest;

import java.io.File;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
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
  main plugin;
  public static ArmorStand IH1;
  public static ArmorStand IH2;
  public static ArmorStand IH3;
  public static ArmorStand IH4;
  public static ArmorStand IH5;
  public static ArmorStand chestHolo1;
  public static ArmorStand chestHolo2;
  public static ArmorStand chestHolo3;

  public Killfest(main instance)
  {
    this.plugin = instance;
  }

  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    if (cmd.getName().equalsIgnoreCase("killfest")) {
      if (!(sender instanceof Player)) {
        Bukkit.getConsoleSender().sendMessage(c.format(this.plugin.getConfig().getString("only-players")));
        return true;
      }
      FileConfiguration config = this.plugin.getConfig();
      Player p = (Player)sender;

      if (args.length == 0) {
        KillfestHelpMSG(p);
      }

      if ((args.length == 1) && (args[0].equalsIgnoreCase("reload"))) {
        if (p.hasPermission(config.getString("permission.reload"))) {
          this.plugin.reloadConfig();
          this.plugin.reloadConfig();
          Bukkit.getConsoleSender().sendMessage(c.format(config.getString("console-config-reloaded")));
          p.sendMessage(c.format(config.getString("prefix") + config.getString("MainC") + " Config has been reloaded!"));
        } else {
          p.sendMessage(c.format(config.getString("no-permissions")));
        }

      }
      


      if ((args.length == 1) && (args[0].equalsIgnoreCase("timer")))
      {
        int minutes = main.Cooldown / 60;
        int seconds = main.Cooldown % 60;

        String msg = minutes + " minutes and " + seconds + " seconds";
        if (ChestOpen.killfeststatus) {
          p.sendMessage(c.format(config.getString("prefix") + config.getString("message.AlreadyRunning")));
        } else {
          String path1 = config.getString("message.NextKillfestTime");
          path1 = path1.replaceAll("%time%", msg);

          p.sendMessage(c.format(config.getString("prefix") + path1));
        }

      }

      if ((args.length == 1) && (args[0].equalsIgnoreCase("InfoHologram")))
      {
        if (p.hasPermission(config.getString("permission.spawnholo")))
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
            main.infoholo1 = (ArmorStand)Bukkit.getWorld(config.getString("hologram.loc.w")).spawnEntity(tloc, EntityType.ARMOR_STAND);
            main.infoholo1.setGravity(false);
            main.infoholo1.setVisible(false);
            main.infoholo1.setCustomNameVisible(true);
            main.infoholo1.setCustomName(c.format("&4&k|||&f &c&lKill Fest &4&k|||"));

            y -= 0.3D;
            x -= 0.5D;
            z -= 0.5D;
            tloc = new Location(w, x, y - 0.3D, z);
            main.infoholo2 = (ArmorStand)Bukkit.getWorld(config.getString("hologram.loc.w")).spawnEntity(tloc, EntityType.ARMOR_STAND);
            main.infoholo2.setGravity(false);
            main.infoholo2.setVisible(false);
            main.infoholo2.setCustomNameVisible(true);
            main.infoholo2.setCustomName(c.format("[Reload]"));

            y -= 0.5D;
            x -= 0.5D;
            z -= 0.5D;
            tloc = new Location(w, x, y - 0.3D, z);
            main.infoholo3 = (ArmorStand)Bukkit.getWorld(config.getString("hologram.loc.w")).spawnEntity(tloc, EntityType.ARMOR_STAND);
            main.infoholo3.setGravity(false);
            main.infoholo3.setVisible(false);
            main.infoholo3.setCustomNameVisible(true);
            main.infoholo3.setCustomName(c.format("[Reload]"));

            p.sendMessage(c.format(config.getString("prefix") + config.getString("message.hologramspawn") + " &7(reload for hologram to work)"));

            return true;
          }
          p.sendMessage(c.format(config.getString("prefix") + config.getString("message.alreadyhaveHologram")));
        }
        else
        {
          p.sendMessage(c.format(config.getString("no-permissions")));
        }

      }

      if ((args.length == 1) && (args[0].equalsIgnoreCase("delInfoHologram")))
      {
        if (p.hasPermission(config.getString("permission.spawnholo"))) {
          if (config.getBoolean("hologram.DontEDIT") == Boolean.TRUE.booleanValue()) {
            main.infoholo1.remove();
            main.infoholo2.remove();
            main.infoholo3.remove();
            this.plugin.getConfig().set("hologram.DontEDIT", Boolean.FALSE);
            this.plugin.saveConfig();
            p.sendMessage(c.format(config.getString("prefix") + config.getString("message.hologramDeleted")));

            return true;
          }
          p.sendMessage(c.format(config.getString("prefix") + config.getString("message.NohologramSPAWN")));
        }
        else
        {
          p.sendMessage(c.format(config.getString("no-permissions")));
        }

      }

      if ((args.length == 1) && (args[0].equalsIgnoreCase("setLocation"))) {
        if (p.hasPermission(config.getString("permission.setlootloc"))) {
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

          p.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("prefix") + config.getString("message.chestloc.update")));
        }
        else {
          p.sendMessage(c.format(config.getString("no-permissions")));
        }

      }

      if ((args.length == 1) && (args[0].equalsIgnoreCase("start")))
      {
        if (ChestOpen.killfeststatus == Boolean.TRUE.booleanValue()) {
          p.sendMessage(c.format(config.getString("prefix") + config.getString("message.killfestAlreadyStarted")));
          return true;
        }

        World w = Bukkit.getWorld(config.getString("chestloc.w"));
        double x = config.getDouble("chestloc.x");
        double y = config.getDouble("chestloc.y");
        double z = config.getDouble("chestloc.z");
        Location cloc = new Location(w, x, y, z);

        cloc.getBlock().setType(Material.CHEST);
        Chest chest = (Chest)cloc.getBlock().getState();
        y += .3D;
        x += .5D;
        z += .5D;

        String path1 = config.getString("hologram.chestholo.l1");
        path1 = path1.replaceAll("%status%", "Left Click");
        path1 = path1.replaceAll("%time%", "");

        Location hloc = new Location(w, x, y - 0.3D, z);
        chestHolo1 = (ArmorStand)p.getWorld().spawnEntity(hloc, EntityType.ARMOR_STAND);
        chestHolo1.setGravity(false);
        chestHolo1.setVisible(false);
        chestHolo1.setCustomNameVisible(true);
        chestHolo1.setCustomName(c.format(path1));

        String path2 = config.getString("hologram.chestholo.l2");
        path2 = path2.replaceAll("%status%", "Left Click");
        path2 = path2.replaceAll("%time%", "");
        y -= 0.3D;
        hloc = new Location(w, x, y - 0.3D, z);
        chestHolo2 = (ArmorStand)p.getWorld().spawnEntity(hloc, EntityType.ARMOR_STAND);
        chestHolo2.setGravity(false);
        chestHolo2.setVisible(false);
        chestHolo2.setCustomNameVisible(true);
        chestHolo2.setCustomName(c.format(path2));

        String path3 = config.getString("hologram.chestholo.l3");
        path3 = path3.replaceAll("%status%", "Left Click");
        path3 = path3.replaceAll("%time%", "");
        hloc = new Location(w, x, y - 0.6D, z);
        chestHolo3 = (ArmorStand)p.getWorld().spawnEntity(hloc, EntityType.ARMOR_STAND);
        chestHolo3.setGravity(false);
        chestHolo3.setVisible(false);
        chestHolo3.setCustomNameVisible(true);
        chestHolo3.setCustomName(c.format(path3));

        ChestOpen.killfeststatus = true;
        ChestOpen.cooldownstatus = false;
        ChestOpen.cheststatus = true;
        p.sendMessage(c.format(config.getString("prefix") + config.getString("message.startkillfest")));

        Bukkit.broadcastMessage(c.format(""));
        Bukkit.broadcastMessage(c.format(""));
        p.sendMessage(c.format(config.getString("prefix") + config.getString("message.broadcast.staredKillfest")));
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tm bc " + config.getString("message.BroadcastEvent"));
        Bukkit.broadcastMessage(c.format(""));
        Bukkit.broadcastMessage(c.format(""));
        return true;
      }

      if ((args.length == 1) && (args[0].equalsIgnoreCase("stop"))) {
        if (ChestOpen.killfeststatus == Boolean.FALSE.booleanValue()) {
          p.sendMessage(c.format(config.getString("prefix") + config.getString("message.NoKillFestRunning")));
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
        Bukkit.broadcastMessage(c.format(config.getString("prefix") + config.getString("message.killfestStop")));
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
    p.sendMessage(c.format("&8&m------------------" + config.getString("MainC") + " &lKillFest " + "&8&m------------------"));
    if (p.hasPermission(config.getString("permission.timer"))) {
      p.sendMessage(c.format(config.getString("MainC") + "/killfest timer »" + config.getString("SecC") + " Time left till the next KillFest"));
    }
    if (p.hasPermission(config.getString("permission.start"))) {
      p.sendMessage(c.format(config.getString("MainC") + "/killfest start »" + config.getString("SecC") + " Starts a KillFest"));
      p.sendMessage(c.format(config.getString("MainC") + "/killfest stop »" + config.getString("SecC") + " Stops the KillFest"));
    }
    if (p.hasPermission(config.getString("permission.reload"))) {
      p.sendMessage(c.format(config.getString("MainC") + "/killfest reload »" + config.getString("SecC") + " Reloads KillFest Config"));
    }
    if (p.hasPermission(config.getString("permission.setlootloc"))) {
      p.sendMessage(c.format(config.getString("MainC") + "/killfest setLocation »" + config.getString("SecC") + " Sets location where chest spawns"));
      p.sendMessage(c.format(config.getString("MainC") + "  Chest Location  » &7(x:" + config.getString("chestloc.x") + ", y:" + config.getString("chestloc.y") + ", z:" + config.getString("chestloc.z") + ")"));
    }
    if (p.hasPermission(config.getString("permission.spawnholo"))) {
      p.sendMessage(c.format(config.getString("MainC") + "/killfest InfoHologram »" + config.getString("SecC") + " Creates an infomation hologram"));
      p.sendMessage(c.format(config.getString("MainC") + "/killfest delInfoHologram »" + config.getString("SecC") + " Deletes The infomation hologram"));
    }
    p.sendMessage(c.format("&8&m--------------------------------------------"));
  }
}