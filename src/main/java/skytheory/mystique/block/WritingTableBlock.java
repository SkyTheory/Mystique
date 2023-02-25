package skytheory.mystique.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WritingTableBlock extends AbstractHorizontalWaterLoggedBlock {

	public static final VoxelShape SHAPE_BOARD = Block.box(0.0d, 10.0d, 0.0d, 16.0d, 12.0d, 16.0d);
	public static final VoxelShape SHAPE_BASE_NORTH = Block.box(0.0d, 12.0d, 10.0d, 16.0d, 16.0d, 16.0d);
	public static final VoxelShape SHAPE_BASE_SOUTH = Block.box(0.0d, 12.0d, 0.0d, 16.0d, 16.0d, 6.0d);
	public static final VoxelShape SHAPE_BASE_EAST = Block.box(0.0d, 12.0d, 0.0d, 6.0d, 16.0d, 16.0d);
	public static final VoxelShape SHAPE_BASE_WEST = Block.box(10.0d, 12.0d, 0.0d, 16.0d, 16.0d, 16.0d);
	public static final VoxelShape SHAPE_BACK_NORTH = Block.box(0.0d, 0.0d, 14.0d, 16.0d, 10.0d, 16.0d);
	public static final VoxelShape SHAPE_BACK_SOUTH = Block.box(0.0d, 0.0d, 0.0d, 16.0d, 10.0d, 2.0d);
	public static final VoxelShape SHAPE_BACK_EAST = Block.box(0.0d, 0.0d, 0.0d, 2.0d, 10.0d, 16.0d);
	public static final VoxelShape SHAPE_BACK_WEST = Block.box(14.0d, 0.0d, 0.0d, 16.0d, 10.0d, 16.0d);
	public static final VoxelShape SHAPE_FOOT = Shapes.or(
			Block.box(0.0d, 0.0d, 0.0d, 2.0d, 10.0d, 2.0d),
			Block.box(0.0d, 0.0d, 14.0d, 2.0d, 10.0d, 16.0d),
			Block.box(14.0d, 0.0d, 0.0d, 16.0d, 10.0d, 2.0d),
			Block.box(14.0d, 0.0d, 14.0d, 16.0d, 10.0d, 16.0d)
			);
	public static final VoxelShape SHAPES_NORTH = Shapes.or(SHAPE_BOARD, SHAPE_BASE_NORTH, SHAPE_BACK_NORTH, SHAPE_FOOT);
	public static final VoxelShape SHAPES_SOUTH = Shapes.or(SHAPE_BOARD, SHAPE_BASE_SOUTH, SHAPE_BACK_SOUTH, SHAPE_FOOT);
	public static final VoxelShape SHAPES_EAST = Shapes.or(SHAPE_BOARD, SHAPE_BASE_EAST, SHAPE_BACK_EAST, SHAPE_FOOT);
	public static final VoxelShape SHAPES_WEST = Shapes.or(SHAPE_BOARD, SHAPE_BASE_WEST, SHAPE_BACK_WEST, SHAPE_FOOT);

	public WritingTableBlock(Properties pProperties) {
		super(pProperties);
	}


	public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
		return switch ((Direction)pState.getValue(FACING)) {
		case NORTH:
			yield SHAPES_NORTH;
		case SOUTH:
			yield SHAPES_SOUTH;
		case EAST:
			yield SHAPES_EAST;
		case WEST:
			yield SHAPES_WEST;
		default:
			yield SHAPE_BOARD;
		};
	}

}
