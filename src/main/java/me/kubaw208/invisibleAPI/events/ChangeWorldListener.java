package me.kubaw208.invisibleAPI.events;

import me.kubaw208.invisibleAPI.InvisibleAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class ChangeWorldListener implements Listener {

    private final JavaPlugin plugin;
    private final InvisibleAPI invisibleAPI;

    public ChangeWorldListener(InvisibleAPI invisibleAPI, JavaPlugin plugin) {
        this.invisibleAPI = invisibleAPI;
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerChangeWorld(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();

        if(invisibleAPI.getInvisiblePlayers().get(player.getUniqueId()) == null) return;

        for(var uuid : invisibleAPI.getInvisiblePlayers().get(player.getUniqueId())) {
            Player target = Bukkit.getPlayer(uuid);

            if(target == null) continue;
            if(!target.canSee(player)) continue;

            target.hidePlayer(plugin, player);
            target.showPlayer(plugin, player);
        }
    }

}