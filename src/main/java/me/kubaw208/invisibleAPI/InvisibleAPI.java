package me.kubaw208.invisibleAPI;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import com.github.retrooper.packetevents.protocol.potion.PotionTypes;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityEffect;
import lombok.Getter;
import me.kubaw208.invisibleAPI.events.PotionsUpdateListener;
import me.kubaw208.invisibleAPI.events.QuitListener;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class InvisibleAPI {

    @Getter private static InvisibleAPI instance;
    @Getter private final HashMap<UUID, ArrayList<UUID>> invisiblePlayers = new HashMap<>();

    public InvisibleAPI(JavaPlugin plugin) {
        instance = this;

        PacketEvents.getAPI().getEventManager().registerListener(new PotionsUpdateListener(this), PacketListenerPriority.HIGH);
        plugin.getServer().getPluginManager().registerEvents(new QuitListener(this), plugin);
    }

    /**
     * Sends packet with fake invisibility such as from invisibility potion to selected players.
     * @param target player that will be set as invisible
     * @param packetReceivers Players who will see invisibility effect on target
     */
    public void setInvisible(Player target, Player... packetReceivers) {
        var packet = new WrapperPlayServerEntityEffect(target.getEntityId(), PotionTypes.INVISIBILITY, 1, -1, (byte) 0);

        invisiblePlayers.put(target.getUniqueId(), new ArrayList<>() {{
            if(invisiblePlayers.get(target.getUniqueId()) != null) {
                this.addAll(invisiblePlayers.get(target.getUniqueId()));
            }

            for(Player receiver : packetReceivers) {
                if(!contains(receiver.getUniqueId()))
                    add(receiver.getUniqueId());

                PacketEvents.getAPI().getPlayerManager().sendPacket(receiver, packet);
            }
        }});
    }

    /**
     * Sends packet to remove invisibility on the target.
     * @param target player on which there will no longer be invisibility for packetReceivers
     * @param packetReceivers Players who will no longer see invisibility on target
     */
    public void unsetInvisible(Player target, Player... packetReceivers) {
        if(invisiblePlayers.get(target.getUniqueId()) == null || invisiblePlayers.get(target.getUniqueId()).isEmpty()) return;

        var packet = new WrapperPlayServerEntityEffect(target.getEntityId(), PotionTypes.INVISIBILITY, 0, 0, (byte) 0);

        for(Player receiver : packetReceivers) {
            invisiblePlayers.get(target.getUniqueId()).remove(receiver.getUniqueId());
            PacketEvents.getAPI().getPlayerManager().sendPacket(receiver, packet);
        }
    }

}