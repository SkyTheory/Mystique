package skytheory.mystique.entity;

import java.util.Optional;

import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkHooks;
import skytheory.lib.block.DataSync;
import skytheory.lib.util.BlockRotation;
import skytheory.lib.util.UpdateFrequency;

public abstract class AbstractCoordinatedEntity extends Entity implements IEntityAdditionalSpawnData, DataSync {

	private final UpdateFrequency freq = new UpdateFrequency(10);

	protected BlockRotation rotation = BlockRotation.NORTH_0;

	public AbstractCoordinatedEntity(EntityType<?> pEntityType, Level pLevel) {
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

	public abstract boolean shouldDiscard();

	public boolean isPickable() {
		return DistExecutor.unsafeRunForDist(
				() -> () -> canInteract(Minecraft.getInstance().player),
				() -> () -> false);
	}
	
	public boolean skipAttackInteraction(Entity pEntity) {
		if (this.canInteract(pEntity)) {
			if (!this.isRemoved() && !this.level.isClientSide) {
				this.playDiscardSound();
				this.kill();
			}
		}
		return true;
	}

	public abstract boolean canInteract(Entity pEntity);

	@Override
	public Direction getDirection() {
		return getRotation().getFront();
	}

	public void setRotation(Direction direction) {
		setRotation(BlockRotation.fromDirection(direction));
	}

	public void setRotation(BlockRotation rotation) {
		this.rotation = rotation;
		this.updateBoundingBox();
	}

	public BlockRotation getRotation() {
		return this.rotation;
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

	@Override
	public void thunderHit(ServerLevel pLevel, LightningBolt pLightning) {
	}

	protected abstract void playDiscardSound();

	protected abstract void updateBoundingBox();

	public void setPos(double x, double y, double z) {
		this.setPosRaw(x, y, z);
	}

	protected float getEyeHeight(Pose pPose, EntityDimensions pDimensions) {
		return pDimensions.height * 0.5f;
	}
	
	@Override
	protected void readAdditionalSaveData(CompoundTag pCompound) {
		Optional<BlockRotation> r = BlockRotation.fromName(pCompound.getString("Rotation"));
		r.ifPresent(rotation -> this.rotation = rotation);
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag pCompound) {
		pCompound.putString("Rotation", this.rotation.getName());
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	public void writeSpawnData(FriendlyByteBuf buffer) {
		buffer.writeEnum(rotation);
	}

	@Override
	public void readSpawnData(FriendlyByteBuf additionalData) {
		this.rotation = additionalData.readEnum(BlockRotation.class);
		this.updateBoundingBox();
	}

	@Override
	public CompoundTag writeSyncTag() {
		CompoundTag nbt = new CompoundTag();
		nbt.putString("Rotation", this.rotation.getName());
		return nbt;
	}

	@Override
	public void readSyncTag(CompoundTag tag) {
		Optional<BlockRotation> r = BlockRotation.fromName(tag.getString("Rotation"));
		r.ifPresent(rotation -> this.rotation = rotation);
		this.updateBoundingBox();
	}

}
