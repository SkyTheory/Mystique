package skytheory.mystique.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WritingChestBlock extends AbstractHorizontalWaterLoggedBlock {

	public static final VoxelShape SHAPE_BASE = Block.box(0.0d, 0.0d, 0.0d, 16.0d, 12.0d, 16.0d);
	public static final VoxelShape SHAPE_HEAD_NORTH = Block.box(0.0d, 12.0d, 10.0d, 16.0d, 16.0d, 16.0d);
	public static final VoxelShape SHAPE_HEAD_SOUTH = Block.box(0.0d, 12.0d, 0.0d, 16.0d, 16.0d, 6.0d);
	public static final VoxelShape SHAPE_HEAD_EAST = Block.box(0.0d, 12.0d, 0.0d, 6.0d, 16.0d, 16.0d);
	public static final VoxelShape SHAPE_HEAD_WEST = Block.box(10.0d, 12.0d, 0.0d, 16.0d, 16.0d, 16.0d);
	public static final VoxelShape SHAPES_NORTH = Shapes.or(SHAPE_BASE, SHAPE_HEAD_NORTH);
	public static final VoxelShape SHAPES_SOUTH = Shapes.or(SHAPE_BASE, SHAPE_HEAD_SOUTH);
	public static final VoxelShape SHAPES_EAST = Shapes.or(SHAPE_BASE, SHAPE_HEAD_EAST);
	public static final VoxelShape SHAPES_WEST = Shapes.or(SHAPE_BASE, SHAPE_HEAD_WEST);

	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	public WritingChestBlock(Properties pProperties) {
		super(pProperties);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, Boolean.valueOf(false)));
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
			yield SHAPE_BASE;
		};
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
		pBuilder.add(FACING, WATERLOGGED);
	}

}
