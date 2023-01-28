package me.byteful.plugin.rosenews;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class PlaceholderExtension extends PlaceholderExpansion {
  private final DataManager dataManager;
  private final RoseNewsPlugin plugin;

  public PlaceholderExtension(DataManager dataManager, RoseNewsPlugin plugin) {
    this.dataManager = dataManager;
    this.plugin = plugin;
  }

  @Override
  public String getIdentifier() {
    return "rosenews";
  }

  @Override
  public String getAuthor() {
    return "byteful";
  }

  @Override
  public String getVersion() {
    return "1.0.0";
  }

  @Override
  public String onPlaceholderRequest(Player player, String params) {
    final String[] args = params.split("_");
    if (args.length < 2) {
      return null;
    }

    final String id = args[0].toLowerCase();
    if (!dataManager.isIdAvailable(id)) {
      return null;
    }
    final APIData data = dataManager.getDataFor(id);
    final String option = args[1].toLowerCase();
    String modifier = null;
    if (args.length >= 3) {
      modifier = args[2];
    }

    switch (option) {
      case "description": {
        if (modifier != null && modifier.equalsIgnoreCase("truncated")) {
          return RoseNewsPlugin.truncate(data.getDescription(), plugin.getConfig().getInt("truncated_length"));
        }

        return data.getDescription();
      }

      case "title": {
        return data.getTitle();
      }

      case "path": {
        return data.getPath();
      }

      case "publishdate": {
        if (modifier != null && modifier.equalsIgnoreCase("formatted")) {
          return plugin.formatDate(data.getCreatedAt());
        }

        return data.getCreatedAt();
      }

      default: {
        return plugin.getConfig().getString("placeholder_not_resolved");
      }
    }
  }
}
