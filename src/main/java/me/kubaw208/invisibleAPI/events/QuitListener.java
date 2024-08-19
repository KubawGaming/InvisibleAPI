package me.kubaw208.invisibleAPI.events;

import me.kubaw208.invisibleAPI.InvisibleAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {

    private final InvisibleAPI invisibleAPI;

    public QuitListener(InvisibleAPI invisibleAPI) {
        this.invisibleAPI = invisibleAPI;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        invisibleAPI.getInvisiblePlayers().remove(player.getUniqueId());

        for(var playersUuids : invisibleAPI.getInvisiblePlayers().values()) {
            if(playersUuids.contains(player.getUniqueId())) continue;

            playersUuids.remove(player.getUniqueId());
        }
    }

}