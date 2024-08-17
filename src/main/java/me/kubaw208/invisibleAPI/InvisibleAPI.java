package me.kubaw208.invisibleAPI;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class InvisibleAPI {

    private final JavaPlugin plugin;
    @Getter private static InvisibleAPI instance;
    @Getter private final HashMap<UUID, HashMap<UUID, String>> fakeNames = new HashMap<>();

    public InvisibleAPI(JavaPlugin plugin) {
        this.plugin = plugin;
        instance = this;

        //PacketEvents.getAPI().getEventManager().registerListener(new PlayerInfoUpdateListener(this), PacketListenerPriority.HIGH);
        //plugin.getServer().getPluginManager().registerEvents(new PlayerQuitListener(this), plugin);
    }

}