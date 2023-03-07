package skytheory.mystique.savedata;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public record ChainTarget(ResourceKey<Level> level, BlockPos target, BlockPos origin, BlockState state, int maxDistance) {

	public static final String KEY_DIMENSION = "Dimension";
	public static final String KEY_TARGET_POS = "TargetPos";
	public static final String KEY_ORIGIN_POS = "OriginPos";
	public static final String KEY_BLOCKSTATE_ID = "BlockStateId";
	public static final String KEY_MAX_DIST = "MaxDist";
	
	public CompoundTag serializeNBT() {
		CompoundTag nbt = new CompoundTag();
		nbt.putString(KEY_DIMENSION, level.location().toString());
		nbt.putLong(KEY_TARGET_POS, target.asLong());
		nbt.putLong(KEY_ORIGIN_POS, origin.asLong());
		nbt.putInt(KEY_BLOCKSTATE_ID, Block.getId(state));
		nbt.putInt(KEY_MAX_DIST, maxDistance);
		return nbt;
	}

	public static ChainTarget deserializeNBT(CompoundTag nbt) {
		ResourceKey<Level> levelKey = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(nbt.getString(KEY_DIMENSION)));
		BlockPos target = BlockPos.of(nbt.getLong(KEY_TARGET_POS));
		BlockPos origin = BlockPos.of(nbt.getLong(KEY_ORIGIN_POS));
		BlockState state = Block.stateById(nbt.getInt(KEY_BLOCKSTATE_ID));
		int maxDistance = nbt.getInt(KEY_MAX_DIST);
		return new ChainTarget(levelKey, target, origin, state, maxDistance);
	}
	
	public boolean isPosLoaded(MinecraftServer server) {
		return server.getLevel(level).isLoaded(target);
	}
	
	public boolean isValid(Level pLevel) {
		if (origin.distManhattan(target) > maxDistance) return false;
		if (!pLevel.dimension().equals(level)) return false;
		if (!pLevel.isLoaded(target)) return false;
		if (pLevel.getBlockState(target).getBlock() != state.getBlock()) return false;
		return true;
	}
	
}