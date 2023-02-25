package skytheory.mystique.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WallShelfBlock extends AbstractHorizontalWaterLoggedBlock {

	public static final VoxelShape SHAPE_NORTH = Block.box(0.0d, 0.0d, 10.0d, 16.0d, 16.0d, 16.0d);
	public static final VoxelShape SHAPE_SOUTH = Block.box(0.0d, 0.0d, 0.0d, 16.0d, 16.0d, 6.0d);
	public static final VoxelShape SHAPE_EAST = Block.box(0.0d, 0.0d, 0.0d, 6.0d, 16.0d, 16.0d);
	public static final VoxelShape SHAPE_WEST = Block.box(10.0d, 0.0d, 0.0d, 16.0d, 16.0d, 16.0d);

	public WallShelfBlock(Properties pProperties) {
		super(pProperties);
	}


	public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
		return switch ((Direction)pState.getValue(FACING)) {
		case NORTH:
			yield SHAPE_NORTH;
		case SOUTH:
			yield SHAPE_SOUTH;
		case EAST:
			yield SHAPE_EAST;
		case WEST:
			yield SHAPE_WEST;
		default:
			yield SHAPE_NORTH;
		};
	}

}
