package skytheory.mystique.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import skytheory.mystique.event.ChainDestructionEvent;
import skytheory.mystique.init.MystiqueItems;

public class TinyTNT extends AbstractCoordinatedEntity {

	public static final int MAX_DIST = 32;
	public static final int MAX_BLOCKS = 256;

	protected int fuse;

	public TinyTNT(EntityType<TinyTNT> pEntityType, Level pLevel) {
		super(pEntityType, pLevel);
		this.fuse = 50;
	}

	@Override
	public void tick() {
		super.tick();
		this.fuse--;
		if (fuse <= 0) {
			explode();
			discard();
		}
	}

	public void explode() {
		if (!level.isClientSide) {
			if(level instanceof ServerLevel sLevel) {
				BlockPos targetPos = this.blockPosition().relative(this.getRotation().getFront());
				ChainDestructionEvent.trigger(sLevel, targetPos, MAX_BLOCKS, MAX_DIST);
				sLevel.playSound(this, blockPosition(), SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 2.0f, 1.0f + this.level.random.nextFloat() * 0.2f);
			}
		}
	}

	public void setFuse(int length) {
		this.fuse = length;
	}

	public int getFuse() {
		return fuse;
	}

	@Override
	protected void defineSynchedData() {}

	@Override
	public boolean shouldDiscard() {
		return this.fuse <= 0;
	}
	@Override
	public boolean canInteract(Entity pEntity) {
		return true;
	}

	@Override
	protected void playDiscardSound() {
		float volume = 1.0f;
		float pitch = 1.0f;
		this.level.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ITEM_PICKUP, SoundSource.NEUTRAL, volume, pitch);
	}

	@Override
	protected void updateBoundingBox() {
		AABB aabb = switch (this.rotation) {
		case NORTH_0: {
			yield AABB.ofSize(this.blockPosition().getCenter().add(0.0d,  0.0d, -0.4d), 0.1875d, 0.1875d, 0.1875d);
		}
		case EAST_0: {
			yield AABB.ofSize(this.blockPosition().getCenter().add(0.4d,  0.0d, 0.0d), 0.1875d, 0.1875d, 0.1875d);
		}
		case SOUTH_0: {
			yield AABB.ofSize(this.blockPosition().getCenter().add(0.0d,  0.0d, 0.4d), 0.1875d, 0.1875d, 0.1875d);
		}
		case WEST_0: {
			yield AABB.ofSize(this.blockPosition().getCenter().add(-0.4d,  0.0d, 0.0d), 0.1875d, 0.1875d, 0.1875d);
		}
		case UP_0: {
			yield AABB.ofSize(this.blockPosition().getCenter().add(0.0d, 0.4d, 0.0d), 0.1875d, 0.1875d, 0.1875d);
		}
		case UP_1: {
			yield AABB.ofSize(this.blockPosition().getCenter().add(0.0d, 0.4d, 0.0d), 0.1875d, 0.1875d, 0.1875d);
		}
		case UP_2: {
			yield AABB.ofSize(this.blockPosition().getCenter().add(0.0d, 0.4d, 0.0d), 0.1875d, 0.1875d, 0.1875d);
		}
		case UP_3: {
			yield AABB.ofSize(this.blockPosition().getCenter().add(0.0d, 0.4d, 0.0d), 0.1875d, 0.1875d, 0.1875d);
		}
		case DOWN_0: {
			yield AABB.ofSize(this.blockPosition().getCenter().add(0.0d, -0.4d, 0.0d), 0.1875d, 0.1875d, 0.1875d);
		}
		case DOWN_1: {
			yield AABB.ofSize(this.blockPosition().getCenter().add(0.0d, -0.4d, 0.0d), 0.1875d, 0.1875d, 0.1875d);
		}
		case DOWN_2: {
			yield AABB.ofSize(this.blockPosition().getCenter().add(0.0d, -0.4d, 0.0d), 0.1875d, 0.1875d, 0.1875d);
		}
		case DOWN_3: {
			yield AABB.ofSize(this.blockPosition().getCenter().add(0.0d, -0.4d, 0.0d), 0.1875d, 0.1875d, 0.1875d);
		}
		default:
			yield new AABB(blockPosition());
		};
		this.setBoundingBox(aabb);
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag pCompound) {
		super.addAdditionalSaveData(pCompound);
		pCompound.putInt("Fuse", fuse);
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag pCompound) {
		super.readAdditionalSaveData(pCompound);
		this.fuse = pCompound.getInt("Fuse");
	}

	@Override
	public void writeSpawnData(FriendlyByteBuf buffer) {
		super.writeSpawnData(buffer);
		buffer.writeInt(fuse);
	}

	@Override
	public void readSpawnData(FriendlyByteBuf additionalData) {
		super.readSpawnData(additionalData);
		this.fuse = additionalData.readInt();
	}

	@Override
	public ItemStack getPickResult() {
		return new ItemStack(MystiqueItems.TOOL_TINY_TNT);
	}

	public boolean skipAttackInteraction(Entity pEntity) {
		if (this.canInteract(pEntity)) {
			if (!this.isRemoved() && !this.level.isClientSide) {
				this.playDiscardSound();
				this.spawnAtLocation(getPickResult());
				this.kill();
			}
		}
		return true;
	}
	
}
