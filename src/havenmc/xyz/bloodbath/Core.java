package havenmc.xyz.bloodbath;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Chest;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

@SuppressWarnings("unused")
public class Core extends JavaPlugin
implements Listener {
 public static long serverStartTime = System.currentTimeMillis();
 public static ArmorStand infoholo1;
 public static ArmorStand infoholo2;
 public static ArmorStand infoholo3;
 public static int Cooldown = 86400;
 public static int StartCooldown = 300;

 private int autostartt;
 public static int placeholder;

 public void onDisable() {
  if (getConfig().getBoolean("hologram.DontEDIT") == Boolean.TRUE.booleanValue()) {
   infoholo1.remove();
   infoholo2.remove();
   infoholo3.remove();
  }

  if (ChestOpen.killfeststatus == Boolean.TRUE.booleanValue()) {
   Killfest.chestHolo1.remove();
   Killfest.chestHolo2.remove();
   Killfest.chestHolo3.remove();
   World w = Bukkit.getWorld(getConfig().getString("chestloc.w"));
   double x = getConfig().getDouble("chestloc.x");
   double y = getConfig().getDouble("chestloc.y");
   double z = getConfig().getDouble("chestloc.z");
   Location cloc = new Location(w, x, y, z);
   cloc.getBlock().setType(Material.AIR);
   ChestOpen.cooldownstatus = false;
   ChestOpen.killfeststatus = false;
   ChestOpen.cheststatus = true;
  }

  Bukkit.getConsoleSender().sendMessage(Colour.format("&8[&c&lBloodBath&8] &c&nDisabled&c!"));
  saveConfig();
 }

 public void onEnable() {
  getConfig().options().copyDefaults(true);
  saveConfig();
  getServer().getPluginManager().registerEvents(this, this);

  registerListeners();
  registerCMD();

  Bukkit.getConsoleSender().sendMessage(Colour.format("&8[&a&lBloodBath&8] &a&nEnabled&a!"));;
  Bukkit.getConsoleSender().sendMessage(Colour.format("&8[&a&lBloodBath&8] &aThank you for buying &nKillFest&a!"));

  autostart();

  if (getConfig().getBoolean("hologram.DontEDIT") == Boolean.TRUE.booleanValue()) {
   World w = Bukkit.getWorld(getConfig().getString("hologram.loc.w"));

   double x = getConfig().getDouble("hologram.loc.x");
   double y = getConfig().getDouble("hologram.loc.y");
   double z = getConfig().getDouble("hologram.loc.z");
   Location tloc = new Location(w, x - 0.59D, y, z);

   x -= -1.12D;
   y -= 2.0D;
   z -= -0.5D;
   tloc = new Location(w, x - 0.59D, y, z);
   infoholo1 = (ArmorStand) Bukkit.getWorld(getConfig().getString("hologram.loc.w")).spawnEntity(tloc, EntityType.ARMOR_STAND);
   infoholo1.setGravity(false);
   infoholo1.setVisible(false);
   infoholo1.setCustomNameVisible(true);
   infoholo1.setCustomName(Colour.format("&4&k|||&f &c&lKill Fest test &4&k|||"));

   y -= 0.3D;
   //z -= 0.1D;
   tloc = new Location(w, x - 0.59D, y, z);
   infoholo2 = (ArmorStand) Bukkit.getWorld(getConfig().getString("hologram.loc.w")).spawnEntity(tloc, EntityType.ARMOR_STAND);
   infoholo2.setGravity(false);
   infoholo2.setVisible(false);
   infoholo2.setCustomNameVisible(true);
   infoholo2.setCustomName(Colour.format("[Error]"));

   y -= 0.3D;
   tloc = new Location(w, x - 0.59D, y, z);
   infoholo3 = (ArmorStand) Bukkit.getWorld(getConfig().getString("hologram.loc.w")).spawnEntity(tloc, EntityType.ARMOR_STAND);
   infoholo3.setGravity(false);
   infoholo3.setVisible(false);
   infoholo3.setCustomNameVisible(true);
   infoholo3.setCustomName(Colour.format("[Error]"));

   placeholders();
  }
 }

 public void registerListeners() {
  PluginManager pm = getServer().getPluginManager();
  pm.registerEvents(new ConfigLis(this), this);
  pm.registerEvents(new ChestOpen(this), this);
 }
 public void registerCMD() {
  getCommand("killfest").setExecutor(new Killfest(this));
 }

 public void autostart() {
  final FileConfiguration config = getConfig();
  this.autostartt = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
   public void run() {
    Core.Cooldown -= 1;

    if (Core.Cooldown <= 0) {
     if (!ChestOpen.killfeststatus) {
      World w = Bukkit.getWorld(config.getString("chestloc.w"));
      double x = config.getDouble("chestloc.x");
      double y = config.getDouble("chestloc.y");
      double z = config.getDouble("chestloc.z");
      Location cloc = new Location(w, x, y, z);

      cloc.getBlock().setType(Material.CHEST);
      Chest chest = (Chest) cloc.getBlock().getState();
      String path1 = config.getString("chesthologram-1");
      path1 = path1.replaceAll("%status%", "Left Click");
      path1 = path1.replaceAll("%time%", "");
      y += .3D;
      x += .5D;
      z += .6D;

      Location hloc = new Location(w, x, y - 0.3D, z);
      Killfest.chestHolo1 = (ArmorStand) Bukkit.getWorld(config.getString("chestloc.w")).spawnEntity(hloc, EntityType.ARMOR_STAND);
      Killfest.chestHolo1.setGravity(false);
      Killfest.chestHolo1.setVisible(false);
      Killfest.chestHolo1.setCustomNameVisible(true);
      Killfest.chestHolo1.setCustomName(Colour.format(path1));

      String path2 = config.getString("chesthologram-2");
      path2 = path2.replaceAll("%status%", "Left Click");
      path2 = path2.replaceAll("%time%", "");
      y -= 0.3D;
      hloc = new Location(w, x, y - 0.3D, z);
      Killfest.chestHolo2 = (ArmorStand) Bukkit.getWorld(config.getString("chestloc.w")).spawnEntity(hloc, EntityType.ARMOR_STAND);
      Killfest.chestHolo2.setGravity(false);
      Killfest.chestHolo2.setVisible(false);
      Killfest.chestHolo2.setCustomNameVisible(true);
      Killfest.chestHolo2.setCustomName(Colour.format(path2));

      String path3 = config.getString("chesthologram-3");
      path3 = path3.replaceAll("%status%", "Left Click");
      path3 = path3.replaceAll("%time%", "");
      hloc = new Location(w, x, y - 0.6D, z);
      Killfest.chestHolo3 = (ArmorStand) Bukkit.getWorld(config.getString("chestloc.w")).spawnEntity(hloc, EntityType.ARMOR_STAND);
      Killfest.chestHolo3.setGravity(false);
      Killfest.chestHolo3.setVisible(false);
      Killfest.chestHolo3.setCustomNameVisible(true);
      Killfest.chestHolo3.setCustomName(Colour.format(path3));
      ChestOpen.killfeststatus = true;
      ChestOpen.cooldownstatus = false;
      ChestOpen.cheststatus = true;

      ArrayList < String > startedkillfest = new ArrayList < String > ();

      for (String startedk: config.getStringList("started-killfest.lore")) {
       startedkillfest.add(Colour.format(startedk));

       Bukkit.broadcastMessage(Colour.format(startedk));
      }

      Core.Cooldown = 86400;
     }
    }
   }
  }, 0L, 20L);
 }

 public void placeholders()

 {
  final FileConfiguration config = getConfig();

  placeholder = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
   public void run() {
    if (Core.this.getConfig().getBoolean("hologram.DontEDIT") == Boolean.TRUE.booleanValue()) {
     String path1 = config.getString("infohologram-1");
     String path2 = config.getString("infohologram-2");
     String path3 = config.getString("infohologram-3");

     if (ChestOpen.cheststatus == Boolean.TRUE.booleanValue()) {
      path1 = path1.replaceAll("%status%", "Resetting");
      path1 = path1.replaceAll("%time%", "");

      path2 = path2.replaceAll("%status%", "Resetting");
      path2 = path2.replaceAll("%time%", "");

      path3 = path3.replaceAll("%status%", "Resetting");
      path3 = path3.replaceAll("%time%", "");

      Core.infoholo1.setCustomName(Colour.format(path1));
      //Core.infoholo2.setCustomName(Colour.format(path2));
      //Core.infoholo3.setCustomName(Colour.format(path3));
     }
     if (ChestOpen.cooldownstatus == Boolean.TRUE.booleanValue()) {
      ChestOpen.Counterdown -= 1;

      int eq = Core.Cooldown / 60;
      int minutes = eq % 5;
      int seconds = Core.Cooldown % 60;

      path1 = path1.replaceAll("%status%", "Unlocking...");
      path1 = path1.replaceAll("%time%", minutes + ":" + seconds);

      path2 = path2.replaceAll("%status%", "Unlocking...");
      path2 = path2.replaceAll("%time%", minutes + ":" + seconds);

      path3 = path3.replaceAll("%status%", "Unlocking...");
      path3 = path3.replaceAll("%time%", minutes + ":" + seconds);
      Core.infoholo1.setCustomName(Colour.format(path1));
      Core.infoholo2.setCustomName(Colour.format(path2));
      Core.infoholo3.setCustomName(Colour.format(path3));
     }
     if ((ChestOpen.cheststatus == Boolean.FALSE.booleanValue()) && (ChestOpen.cooldownstatus == Boolean.FALSE.booleanValue()) && (ChestOpen.killfeststatus == Boolean.TRUE.booleanValue())) {
      path1 = path1.replaceAll("%status%", "Claim Prize");
      path1 = path1.replaceAll("%time%", "00:00");

      path2 = path2.replaceAll("%status%", "Claim Prize");
      path2 = path2.replaceAll("%time%", "00:00");

      path3 = path3.replaceAll("%status%", "Claim Prize");
      path3 = path3.replaceAll("%time%", "00:00");
      Core.infoholo1.setCustomName(Colour.format(path1));
      Core.infoholo2.setCustomName(Colour.format(path2));
      Core.infoholo3.setCustomName(Colour.format(path3));
     }

     if (ChestOpen.cheststatus == Boolean.FALSE.booleanValue()) {
      int hours1 = Core.Cooldown / 3600;
      int A1 = Core.Cooldown / 60;
      int minutes = A1 % 60;
      int hours = hours1 % 24;
      int seconds = Core.Cooldown % 60;


      path1 = path1.replaceAll("%status%", "Cooldown");
      path1 = path1.replaceAll("%time%", hours + ":" + minutes + ":" + seconds);

      path2 = path2.replaceAll("%status%", "Cooldown");
      path2 = path2.replaceAll("%time%", hours + ":" + minutes + ":" + seconds);

      path3 = path3.replaceAll("%status%", "Cooldown");
      path3 = path3.replaceAll("%time%", hours + ":" + minutes + ":" + seconds);
      Core.infoholo1.setCustomName(Colour.format(path1));
      Core.infoholo2.setCustomName(Colour.format(path2));
      Core.infoholo3.setCustomName(Colour.format(path3));
     }
    }
   }
  }, 0L, 20L);
 }
}