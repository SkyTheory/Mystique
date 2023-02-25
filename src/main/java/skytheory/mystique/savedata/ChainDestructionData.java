package skytheory.mystique.savedata;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.INBTSerializable;

public class ChainDestructionData implements INBTSerializable<CompoundTag> {

	public static final String KEY_DATA = "Data";
	public static final String KEY_VERSION = "TagVersion";
	public static final String KEY_UUID = "UUID";
	public static final String KEY_BLOCK = "Block";
	public static final String KEY_POS = "Pos";
	public static final String KEY_ORIGIN = "Origin";
	public static final int TAG_VERSION = 1;
	public static final int DELAY_TICKS = 4;

	private final MystiqueLevelData storage;
	private final Deque<Set<TargetEntry>> targets = new ArrayDeque<>();

	public ChainDestructionData(MystiqueLevelData storage) {
		this.storage = storage;
	}

	@Override
	public CompoundTag serializeNBT() {
		CompoundTag nbt = new CompoundTag();
		ListTag listTag = new ListTag();
		for (Set<TargetEntry> dataSet : targets) {
			ListTag dataTag = new ListTag();
			for (TargetEntry entry : dataSet) {
				CompoundTag data = new CompoundTag();
				if (entry.uuid != null) data.putUUID(KEY_UUID, entry.uuid);
				data.putInt(KEY_BLOCK, Block.getId(entry.state));
				data.putLong(KEY_POS, entry.pos.asLong());
				data.putLong(KEY_ORIGIN, entry.origin.asLong());
				dataTag.add(data);
			}
			listTag.add(dataTag);
		}
		nbt.put(KEY_DATA, listTag);
		nbt.putInt(KEY_VERSION, TAG_VERSION);
		return nbt;
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		ListTag listTag = nbt.getList(KEY_DATA, Tag.TAG_LIST);
		for (int i = 0; i < listTag.size() && i <= DELAY_TICKS; i++) {
			ListTag dataList = listTag.getList(i);
			Set<TargetEntry> datas = new HashSet<>();
			targets.push(datas);
			for (int j = 0; j < dataList.size(); j++) {
				CompoundTag data = dataList.getCompound(j);
				UUID uuid = data.contains(KEY_UUID, Tag.TAG_INT_ARRAY) ? data.getUUID(KEY_UUID) : null;
				BlockState state = Block.stateById(data.getInt(KEY_BLOCK));
				BlockPos pos = BlockPos.of(data.getLong(KEY_POS));
				BlockPos origin = BlockPos.of(data.getLong(KEY_ORIGIN));
				datas.add(new TargetEntry(uuid, state, pos, origin));
			}
		}
	}

	public void push(UUID uuid, BlockState state, BlockPos pos, BlockPos origin) {
		for (int i = targets.size(); i < DELAY_TICKS; i++) {
			targets.push(new HashSet<>());
		}
		targets.peekLast().add(new TargetEntry(uuid, state, pos, origin));
		storage.setDirty();
	}

	public Set<TargetEntry> pop() {
		if (targets.isEmpty()) return Collections.emptySet();
		Set<TargetEntry> result = targets.pop();
		storage.setDirty();
		return result;
	}

	public static class TargetEntry {

		public final UUID uuid;
		public final BlockState state;
		public final BlockPos pos;
		public final BlockPos origin;

		private TargetEntry(UUID uuid, BlockState state, BlockPos pos, BlockPos origin) {
			this.uuid = uuid;
			this.state = state;
			this.pos = pos;
			this.origin = origin;
		}
	}
}
