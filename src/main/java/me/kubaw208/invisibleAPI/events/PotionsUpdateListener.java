package me.kubaw208.invisibleAPI.events;

import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityEffect;
import me.kubaw208.invisibleAPI.InvisibleAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PotionsUpdateListener implements PacketListener {

    private final InvisibleAPI InvisibleAPI;

    public PotionsUpdateListener(InvisibleAPI InvisibleAPI) {
        this.InvisibleAPI = InvisibleAPI;
    }

    @Override
    public void onPacketSend(PacketSendEvent event) {
        if(event.getPacketType() != PacketType.Play.Server.ENTITY_EFFECT) return;
        if(!(event.getPlayer() instanceof Player player)) return;

        var packet = new WrapperPlayServerEntityEffect(event);

        if(!InvisibleAPI.getInvisiblePlayers().containsKey(player.getUniqueId())) return;
        if(getPlayerByEntityID(packet.getEntityId()) == null) return;
        if(!InvisibleAPI.getInvisiblePlayers().get(player.getUniqueId()).contains(getPlayerByEntityID(packet.getEntityId()).getUniqueId())) return;

        packet.setVisible(false);
        packet.setAmbient(false);
    }

    /**
     * @returns player by entityID if exists. Else returns false.
     */
    public static Player getPlayerByEntityID(int entityID) {
        for(Player player : Bukkit.getOnlinePlayers()) {
            if(player.getEntityId() == entityID) {
                return player;
            }
        }
        return null;
    }

}