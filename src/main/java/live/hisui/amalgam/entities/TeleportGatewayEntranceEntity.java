package live.hisui.amalgam.entities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

public class TeleportGatewayEntranceEntity extends Entity {

    protected static final EntityDataAccessor<Integer> DATA_ID_HURT = SynchedEntityData.defineId(TeleportGatewayEntranceEntity.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Float> DATA_ID_DAMAGE = SynchedEntityData.defineId(TeleportGatewayEntranceEntity.class, EntityDataSerializers.FLOAT);

    public TeleportGatewayEntranceEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        // TODO: spawn exit gateway at top solid y in facing direction, tp player to it
        return super.interact(player, hand);
    }

    @Override
    public void tick() {
        super.tick();

        // Save previous rotation for interpolation
        this.yRotO = this.getYRot();

        // Increase yaw by 1 degree per tick
        this.setYRot(this.getYRot() + 1.0F);

        // Sync head/body so the renderer doesn’t look weird
        this.setYHeadRot(this.getYRot());
        this.setYBodyRot(this.getYRot());

        // Prevent float precision loss by normalizing every once in a while
        if (this.getYRot() > 1_000_000F) {
            float normalized = this.getYRot() % 360F;
            this.setYRot(normalized);
            this.yRotO = normalized;
        }
    }

    @Override
    public void setYRot(float yRot) {
        // Store unbounded (no Mth.wrapDegrees)
        this.yRot = yRot;
    }



    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (this.level().isClientSide || this.isRemoved()) {
            return true;
        } else if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            this.setHurtTime(10);
            this.markHurt();
            this.setDamage(this.getDamage() + amount * 10.0F);
            this.gameEvent(GameEvent.ENTITY_DAMAGE, source.getEntity());
            boolean flag = source.getEntity() instanceof Player && ((Player)source.getEntity()).getAbilities().instabuild;
            if ((flag || !(this.getDamage() > 40.0F))) {
                if (flag) {
                    this.discard();
                }
            } else {
                this.kill();
            }

            return true;
        }
    }

    public void setHurtTime(int hurtTime) {
        this.entityData.set(DATA_ID_HURT, hurtTime);
    }

    public void setDamage(float damage) {
        this.entityData.set(DATA_ID_DAMAGE, damage);
    }

    public float getDamage() {
        return this.entityData.get(DATA_ID_DAMAGE);
    }

    public int getHurtTime() {
        return this.entityData.get(DATA_ID_HURT);
    }


    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(DATA_ID_DAMAGE, 0.0f);
        builder.define(DATA_ID_HURT, 0);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {

    }
}
