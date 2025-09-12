package live.hisui.amalgam.entities;

import live.hisui.amalgam.Amalgam;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEntities {

    private static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, Amalgam.MODID);

    public static final Supplier<EntityType<TeleportGatewayEntranceEntity>> TELEPORT_GATEWAY = ENTITY_TYPES.register("teleport_gateway_entrance",
            () -> EntityType.Builder.<TeleportGatewayEntranceEntity>of(TeleportGatewayEntranceEntity::new, MobCategory.MISC)
                    .sized(1.0f, 2.0f)
                    .clientTrackingRange(16)
                    .build("teleport_gateway_entrance"));


    public static void register(IEventBus modBus){
        ENTITY_TYPES.register(modBus);
    }
}
