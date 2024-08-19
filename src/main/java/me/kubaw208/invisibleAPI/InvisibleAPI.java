package me.kubaw208.invisibleAPI;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import lombok.Getter;
import me.kubaw208.invisibleAPI.events.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class InvisibleAPI {

    protected final JavaPlugin plugin;
    @Getter private static InvisibleAPI instance;
    @Getter private final HashMap<UUID, ArrayList<UUID>> invisiblePlayers = new HashMap<>();

    public InvisibleAPI(JavaPlugin plugin) {
        this.plugin = plugin;
        instance = this;

        PacketEvents.getAPI().getEventManager().registerListener(new InvisibilityUpdateListener(this), PacketListenerPriority.HIGH);
        plugin.getServer().getPluginManager().registerEvents(new QuitListener(this), plugin);
        plugin.getServer().getPluginManager().registerEvents(new ChangeWorldListener(this, plugin), plugin);
    }

    /**
     * Sends packet with fake invisibility such as from invisibility potion to selected players.
     * @param target player that will be set as invisible
     * @param packetReceivers Players who will see invisibility effect on target
     */
    public void setInvisible(Player target, Player... packetReceivers) {
        invisiblePlayers.compute(target.getUniqueId(), (k, previousReceivers) -> new ArrayList<>() {{
            if(previousReceivers != null)
                this.addAll(previousReceivers);

            for(Player receiver : packetReceivers) {
                if(!contains(receiver.getUniqueId()))
                    add(receiver.getUniqueId());
            }
        }});

        for(Player receiver : packetReceivers) {
            if(!receiver.canSee(target)) continue;

            receiver.hidePlayer(plugin, target);
            receiver.showPlayer(plugin, target);
        }
    }

    /**
     * Sends packet with fake invisibility such as from invisibility potion to selected players.
     * @param target player that will be set as invisible
     * @param packetReceivers Players who will see invisibility effect on target
     */
    public void setInvisible(Player target, Collection<? extends Player> packetReceivers) {
        setInvisible(target, packetReceivers.toArray(new Player[0]));
    }

    /**
     * Sends packet with fake invisibility such as from invisibility potion to selected players.
     * @param target player that will be set as invisible
     * @param packetReceivers Players who will see invisibility effect on target
     */
    public void setInvisible(Player target, List<Player> packetReceivers) {
        setInvisible(target, packetReceivers.toArray(new Player[0]));
    }

    /**
     * Sends packet to remove invisibility on the target.
     * @param target player on which there will no longer be invisibility for packetReceivers
     * @param packetReceivers Players who will no longer see invisibility on target
     */
    public void unsetInvisible(Player target, Player... packetReceivers) {
        if(invisiblePlayers.get(target.getUniqueId()) == null || invisiblePlayers.get(target.getUniqueId()).isEmpty()) return;

        for(Player receiver : packetReceivers) {
            invisiblePlayers.get(target.getUniqueId()).remove(receiver.getUniqueId());

            if(!receiver.canSee(target)) continue;

            receiver.hidePlayer(plugin, target);
            receiver.showPlayer(plugin, target);
        }
    }

    /**
     * Sends packet to remove invisibility on the target.
     * @param target player on which there will no longer be invisibility for packetReceivers
     * @param packetReceivers Players who will no longer see invisibility on target
     */
    public void unsetInvisible(Player target, Collection<? extends Player> packetReceivers) {
        unsetInvisible(target, packetReceivers.toArray(new Player[0]));
    }

    /**
     * Sends packet to remove invisibility on the target.
     * @param target player on which there will no longer be invisibility for packetReceivers
     * @param packetReceivers Players who will no longer see invisibility on target
     */
    public void unsetInvisible(Player target, List<Player> packetReceivers) {
        unsetInvisible(target, packetReceivers.toArray(new Player[0]));
    }

    /**
     * Checks whether the player should see the target for this API.
     * Note - this method does not check the actual visibility of the player!
     * @returns true if player can see target. Else returns false.
     */
    public boolean canSee(Player player, Player target) {;
        return invisiblePlayers.get(target.getUniqueId()) != null &&
                invisiblePlayers.get(target.getUniqueId()).contains(player.getUniqueId());
    }

}