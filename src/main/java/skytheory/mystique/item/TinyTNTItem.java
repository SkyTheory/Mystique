package skytheory.mystique.item;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import skytheory.lib.util.BlockRotation;
import skytheory.mystique.entity.TinyTNT;
import skytheory.mystique.init.MystiqueEntityTypes;

public class TinyTNTItem extends AbstractCoordinatedEntityPlacerItem<TinyTNT> {

	public TinyTNTItem(Properties pProperties) {
		super(pProperties);
	}

	@Override
	protected EntityType<TinyTNT> getEntityType() {
		return MystiqueEntityTypes.MICRO_DYNAMIS;
	}


	@Override
	protected void playPlaceSound(Level level, Player player, double x, double y, double z) {
		float volume = 1.0f;
		float pitch = 2.0f;
		level.playSound(player, x, y, z, SoundEvents.TNT_PRIMED, SoundSource.NEUTRAL, volume, pitch);
	}
	
//	@Override
	public BlockRotation getRotation(RightClickBlock event, Level level, @Nullable Direction face) {
		BlockHitResult hitResult = event.getHitVec();
		Direction direction = hitResult.getDirection();
		return switch (direction) {
		case UP: {
			yield BlockRotation.fromDirection(direction.getOpposite(), event.getEntity().getDirection().getOpposite());
		}
		case DOWN: {
			yield BlockRotation.fromDirection(direction.getOpposite(), event.getEntity().getDirection());
		}
		default:
			yield BlockRotation.fromDirection(face.getOpposite());
		};
	}
	
}
