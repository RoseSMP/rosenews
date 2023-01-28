package me.byteful.plugin.rosenews;

import com.google.gson.JsonArray;
import org.bukkit.Bukkit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class DataManager {
  private static final URL API_URL;

  static {
    try {
      API_URL = new URL("https://edge.fizz.red/all");
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }
  }

  private final Map<String, APIData> cached = new HashMap<>();
  private final RoseNewsPlugin plugin;

  public DataManager(RoseNewsPlugin plugin) {
    this.plugin = plugin;
    Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, this::update, 0L, plugin.getConfig().getInt("time_between_updates"));
  }

  public void update() {
    final JsonArray fetched = fetch();
    final List<APIData> found = new ArrayList<>();
    fetched.forEach(element -> found.add(APIData.fromJSON(element)));
    found.sort(Comparator.comparing(d -> plugin.parseDefaultDate(d.getCreatedAt()),
      Comparator.comparing(Date::toInstant).reversed()));
    for (int i = 0; i < found.size(); i++) {
      cached.put("" + (i + 1), found.get(i));
    }
  }

  private JsonArray fetch() {
    try (final InputStream stream = API_URL.openStream(); final InputStreamReader inputStreamReader = new InputStreamReader(stream); final BufferedReader reader = new BufferedReader(inputStreamReader)) {
      return APIData.GSON.fromJson(reader, JsonArray.class);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public APIData getDataFor(String id) {
    return cached.get(id);
  }

  public boolean isIdAvailable(String id) {
    return cached.containsKey(id);
  }
}
