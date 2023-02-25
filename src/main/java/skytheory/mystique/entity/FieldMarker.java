package skytheory.mystique.entity;

import java.util.Optional;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.world.phys.Vec3;
import skytheory.lib.util.BlockRotation;
import skytheory.lib.util.SkyTheoryDataSerializers;
import skytheory.lib.util.UpdateFrequency;
import skytheory.mystique.init.MystiqueItems;

public class FieldMarker extends Entity {

	protected static final EntityDataAccessor<BlockRotation> DATA_ROTATION = SynchedEntityData.defineId(FieldMarker.class, SkyTheoryDataSerializers.SERIALIZER_ROTATION);

	private final UpdateFrequency freq = new UpdateFrequency(10);

	public FieldMarker(EntityType<FieldMarker> pEntityType, Level pLevel) {
		super(pEntityType, pLevel);
	}

	public void tick() {
		super.tick();
		if (!this.level.isClientSide) {
			if (!this.isRemoved() && this.freq.shouldUpdate()) {
				if (this.shouldDiscard()) {
					this.discard();
				}
			}
		}
	}

	public boolean shouldDiscard() {
		Direction direction = this.getRotation().getFront();
		BlockPos below = blockPosition().relative(direction);
		if (!level.getBlockState(below).isFaceSturdy(level, below, direction.getOpposite(), SupportType.CENTER)) {
			return true;
		}
		return false;
	}

	@Override
	protected void defineSynchedData() {
		this.entityData.define(DATA_ROTATION, BlockRotation.DOWN_0);
	}
	
	@Override
	public Direction getDirection() {
		return getRotation().getFront();
	}

	public void setRotation(Direction direction) {
		setRotation(BlockRotation.fromDirection(direction));
	}

	public void setRotation(BlockRotation rotation) {
		this.entityData.set(DATA_ROTATION, rotation);
	}

	public BlockRotation getRotation() {
		return this.entityData.get(DATA_ROTATION);
	}


	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
		if (pKey == DATA_ROTATION) {
			updateBoundingBox();
		}
		super.onSyncedDataUpdated(pKey);
	}

	protected void updateBoundingBox() {
		BlockRotation rotation = this.entityData.get(DATA_ROTATION);
		Direction front = rotation.getFront();
		double minX = this.blockPosition().getX();
		double maxX = this.blockPosition().getX() + 1.0d;
		double minY = this.blockPosition().getY();
		double maxY = this.blockPosition().getY() + 1.0d;
		double minZ = this.blockPosition().getZ();
		double maxZ = this.blockPosition().getZ() + 1.0d;
		minX = Math.max(minX, minX + (double)front.getStepX() * 0.8d);
		minY = Math.max(minY, minY + (double)front.getStepY() * 0.8d);
		minZ = Math.max(minZ, minZ + (double)front.getStepZ() * 0.8d);
		maxX = Math.min(maxX, maxX + (double)front.getStepX() * 0.8d);
		maxY = Math.min(maxY, maxY + (double)front.getStepY() * 0.8d);
		maxZ = Math.min(maxZ, maxZ + (double)front.getStepZ() * 0.8d);
//		this.setBoundingBox(new AABB(minX, minY, minZ, maxX, maxY, maxZ));
	}

	public boolean canInteract(Entity entity) {
		if (entity instanceof Player player) {
			if (this.level.mayInteract(player, this.blockPosition())) {
				if (player.getMainHandItem().getItem() == MystiqueItems.TOOL_STYLUS) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean isPickable() {
		return false;
	}

	public boolean skipAttackInteraction(Entity pEntity) {
		return !canInteract(pEntity);
	}

	public boolean hurt(DamageSource pSource, float pAmount) {
		if (pSource instanceof EntityDamageSource eDamege) {
			if (eDamege.getMsgId().equals("player") && canInteract(eDamege.getEntity())) {
				this.markHurt();
				if (!this.isRemoved() && !this.level.isClientSide) {
					this.playDiscardSound();
					this.kill();
				}
			}
		}
		return false;
	}

	public void move(MoverType pType, Vec3 pPos) {
		if (!this.level.isClientSide && !this.isRemoved() && pPos.lengthSqr() > 0.0d) {
			this.playDiscardSound();
			this.kill();
		}
	}

	public void push(double pX, double pY, double pZ) {
		if (!this.level.isClientSide && !this.isRemoved() && (pX != 0.0d || pY != 0.0d || pZ != 0.0d)) {
			this.playDiscardSound();
			this.discard();
		}
	}

	public void playDiscardSound() {
		float volume = 1.0f;
		float pitch = 1.0f;
		this.level.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.STONE_BREAK, SoundSource.NEUTRAL, volume, pitch);
	}

	@Override
	public void thunderHit(ServerLevel pLevel, LightningBolt pLightning) {
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag pCompound) {
		pCompound.putString("Rotation", this.entityData.get(DATA_ROTATION).getSerializedName());
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag pCompound) {
		Optional<BlockRotation> rotation = BlockRotation.fromName(pCompound.getString("Rotation"));
		rotation.ifPresent(r -> this.entityData.set(DATA_ROTATION, r));
	}

}
