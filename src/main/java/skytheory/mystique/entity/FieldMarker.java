package skytheory.mystique.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import skytheory.lib.util.BlockRotation;
import skytheory.mystique.init.MystiqueItems;

public class FieldMarker extends AbstractCoordinatedEntity {
	
	public FieldMarker(EntityType<FieldMarker> pEntityType, Level pLevel) {
		super(pEntityType, pLevel);
	}

	@Override
	protected void defineSynchedData() {}

	@Override
	public boolean shouldDiscard() {
		Direction direction = this.getRotation().getFront();
		BlockPos below = blockPosition().relative(direction);
		return level.getBlockState(below).isAir();
	}

	@Override
	protected void updateBoundingBox() {
		BlockRotation rotation = this.getRotation();
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
		this.setBoundingBox(new AABB(minX, minY, minZ, maxX, maxY, maxZ));
	}

	@Override
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

	@Override
	public void playDiscardSound() {
		float volume = 1.0f;
		float pitch = 1.0f;
		this.level.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.STONE_BREAK, SoundSource.NEUTRAL, volume, pitch);
	}

}
