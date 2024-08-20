package me.kubaw208.invisibleAPI.events;

import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.entity.data.EntityData;
import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityMetadata;
import me.kubaw208.invisibleAPI.InvisibleAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class InvisibilityUpdateListener implements PacketListener {

    private final InvisibleAPI invisibleAPI;

    public InvisibilityUpdateListener(InvisibleAPI invisibleAPI) {
        this.invisibleAPI = invisibleAPI;
    }

    @Override
    public void onPacketSend(PacketSendEvent event) {
        if(event.getPacketType() != PacketType.Play.Server.ENTITY_METADATA) return;
        if(!(event.getPlayer() instanceof Player player)) return;

        var packet = new WrapperPlayServerEntityMetadata(event);
        Player target = getPlayerByEntityID(packet.getEntityId());

        if(target == null) return;
        if(!invisibleAPI.getInvisiblePlayers().containsKey(target.getUniqueId())) return;
        if(!invisibleAPI.getInvisiblePlayers().get(target.getUniqueId()).contains(player.getUniqueId())) return;

        //Checking if metadata with index 0 is already added (to not repeating the same metadata in list). If exists - just edit it (add invisible)
        for(var metaData : packet.getEntityMetadata()) {
            if(metaData.getIndex() != 0) continue;

            byte newValue = (byte) ((byte) metaData.getValue() | 0x20);

            metaData.setValue(newValue);
            return;
        }
        packet.getEntityMetadata().add(new EntityData(0, EntityDataTypes.BYTE, (byte) 0x20));
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