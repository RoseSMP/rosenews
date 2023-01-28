package me.byteful.plugin.rosenews;

import org.bukkit.plugin.java.JavaPlugin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class RoseNewsPlugin extends JavaPlugin {
  private final SimpleDateFormat defaultFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
  private SimpleDateFormat configFormat;
  private DataManager dataManager;

  public static String truncate(String str, int end) {
    if (end > str.length()) {
      end = str.length();
    }
    return str.substring(0, end);
  }

  @Override
  public void onEnable() {
    saveDefaultConfig();
    configFormat = new SimpleDateFormat(getConfig().getString("date_format"));
    dataManager = new DataManager(this);
    new PlaceholderExtension(dataManager, this).register();
  }

  public String formatDate(String from) {
    try {
      return configFormat.format(defaultFormat.parse(from));
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
  }

  public Date parseDefaultDate(String toParse) {
    try {
      return defaultFormat.parse(toParse);
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
  }
}
