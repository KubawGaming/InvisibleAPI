package me.kubaw208.invisibleAPI;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.protocol.entity.data.EntityData;
import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityMetadata;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.List;

public class InvisibleUtils {

    public static byte getMetadataByte(Entity entity) {
        byte output = 0x00;

        if(entity.isVisualFire()) output |= 0x01;

        if(entity instanceof Player player) {
            if(player.isSneaking()) output |= 0x02;
            if(player.isSprinting()) output |= 0x08;
            if(player.isSwimming()) output |= 0x10;
            if(player.isGliding()) output |= (byte) 0x80;
        }

        if(entity instanceof LivingEntity livingEntity) {
            if(livingEntity.isInvisible()) output |= 0x20;
        }

        if(entity.isGlowing()) output |= 0x40;

        return output;
    }

    public static void resendEntityMetadata(Player receiver, Entity target) {
        var packet = new WrapperPlayServerEntityMetadata(
                target.getEntityId(),
                List.of(
                        new EntityData(0, EntityDataTypes.BYTE, getMetadataByte(target))
                )
        );

        PacketEvents.getAPI().getPlayerManager().sendPacket(receiver, packet);
    }

}