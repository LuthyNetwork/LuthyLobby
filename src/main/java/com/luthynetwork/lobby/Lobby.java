package com.luthynetwork.lobby;

import com.luthynetwork.core.libs.location.LocationSerializer;
import com.luthynetwork.core.libs.scoreboard.ScoreboardManager;
import com.luthynetwork.lobby.commands.chat.ClearCommand;
import com.luthynetwork.lobby.commands.chat.LockCommand;
import com.luthynetwork.lobby.commands.chat.UnlockCommand;
import com.luthynetwork.lobby.commands.location.CoordsCommand;
import com.luthynetwork.lobby.commands.settings.SettingsCommand;
import com.luthynetwork.lobby.helper.PluginHelper;
import com.luthynetwork.lobby.listeners.chat.ChatListener;
import com.luthynetwork.lobby.listeners.player.PlayerListeners;
import com.luthynetwork.lobby.listeners.world.WorldListeners;
import com.luthynetwork.lobby.npc.list.RankupNPC;
import com.luthynetwork.lobby.scoreboard.LobbyScoreboard;
import com.luthynetwork.lobby.settings.Settings;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.luthynetwork.core.libs.scoreboard.settings.BoardSettings;
import com.luthynetwork.core.libs.scoreboard.settings.ScoreDirection;
import fr.minuskube.inv.InventoryManager;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import net.jitse.npclib.NPCLib;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.io.File;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

@Accessors(fluent = true)
public class Lobby extends PluginHelper {

    private Gson gson;
    private File settingsFile;

    @Getter private static Lobby instance;
    @Getter private static NPCLib npcLib;
    @Getter private static LuckPerms luckPerms;
    @Getter private static ScoreboardManager scoreboardManager;
    @Getter private static InventoryManager inventoryManager;

    @Getter private static Location spawnLocation;

    private static Map<String, Settings> settings = Maps.newHashMap();

    @Override
    public void load() {}

    @Override
    @SneakyThrows
    public void enable() {
        instance = this;

        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            luckPerms = provider.getProvider();
        }

        spawnLocation = LocationSerializer.deserialize(Lobby.instance().getConfig().getString("spawn.location"));
        npcLib = new NPCLib(this);
        gson = new GsonBuilder().setPrettyPrinting().enableComplexMapKeySerialization().create();
        scoreboardManager = new ScoreboardManager(this, BoardSettings.builder().boardProvider(new LobbyScoreboard()).scoreDirection(ScoreDirection.UP).build());

        inventoryManager = new InventoryManager(this);
        inventoryManager.init();

        new File(getDataFolder().getAbsolutePath()).mkdir();

        settingsFile = new File(getDataFolder().getAbsolutePath() + "\\settings.json");
        settingsFile.createNewFile();

        saveDefaultConfig();
        loadGSON();

        new RankupNPC(new Location(Bukkit.getWorlds().get(0), 100, 70, 100, 0, 0)).build(npcLib);

        listener(new PlayerListeners(), new WorldListeners(), new ChatListener());
        register(new ClearCommand(), new LockCommand(), new UnlockCommand(), new SettingsCommand(), new CoordsCommand());
    }

    @Override
    public void disable() {
        saveGSON();
        scoreboardManager.onDisable();
    }

    @SneakyThrows
    public void loadGSON() {
        final Reader reader = Files.newBufferedReader(Paths.get(getDataFolder().getAbsolutePath() + "\\settings.json"));
        final Type empMapType = new TypeToken<Map<String, Settings>>() {}.getType();

        final Map<String, Settings> settingsMap = gson.fromJson(reader, empMapType);

        if (settingsMap != null) {
            settings = settingsMap;
        }
        reader.close();
    }

    @SneakyThrows
    private void saveGSON() {
        if (fileChecks(settingsFile) && settings.size() >= 1) {
            final Writer fileWriter = new FileWriter(this.getDataFolder().getAbsolutePath() + "\\settings.json");

            gson.toJson(settings, fileWriter);

            fileWriter.close();
        }
    }

    @SneakyThrows
    private boolean fileChecks(final File file) {
        if (!file.exists()) {
            return false;
        }

        boolean delete = file.delete();
        boolean create = file.createNewFile();

        return delete && create;
    }

    public static Settings getPlayerSettings(UUID uuid) {
        return settings.get(uuid.toString());
    }

    public static void defaultSettings(UUID uuid) {
        settings.put(uuid.toString(), Settings.defaultSettings());
    }

}
