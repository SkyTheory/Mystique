package skytheory.mystique.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import skytheory.mystique.entity.FieldMarker;
import skytheory.mystique.init.MystiqueEntityTypes;

public class FieldMarkerItem extends AbstractCoordinatedEntityPlacerItem<FieldMarker> {

	public FieldMarkerItem(Properties pProperties) {
		super(pProperties);
	}

	/*
	public boolean checkCondition(Level level, Player player, BlockPos placePos, BlockPos supportPos, Direction face, ItemStack stack) {
		if  (!super.checkCondition(level, player, placePos, supportPos, face, stack)) {
			return false;
		}
		return level.getBlockState(supportPos).isFaceSturdy(level, supportPos, face, SupportType.CENTER);
	}
	 */
	@Override
	protected void finishUse(Player player, Level level, BlockPos pos, Direction face, ItemStack stack) {
	}

	@Override
	protected EntityType<FieldMarker> getEntityType() {
		return MystiqueEntityTypes.FIELD_MARKER;
	}

	@Override
	protected void playPlaceSound(Level level, Player player, double x, double y, double z) {
		float volume = 1.0f;
		float pitch = 2.0f;
		level.playSound(player, x, y, z, SoundEvents.STONE_PLACE, SoundSource.NEUTRAL, volume, pitch);
	}

}