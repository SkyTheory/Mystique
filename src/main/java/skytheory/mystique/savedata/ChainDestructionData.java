package skytheory.mystique.savedata;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.util.INBTSerializable;

public class ChainDestructionData implements INBTSerializable<CompoundTag> {

	public static final String KEY_DATA = "Data";
	public static final int DELAY_TICKS = 4;

	private final MystiqueLevelData storage;
	private final Deque<Set<ChainTarget>> targets = new ArrayDeque<>();

	public ChainDestructionData(MystiqueLevelData storage) {
		this.storage = storage;
	}

	@Override
	public CompoundTag serializeNBT() {
		CompoundTag nbt = new CompoundTag();
		ListTag listTag = new ListTag();
		for (Set<ChainTarget> dataSet : targets) {
			ListTag dataTag = new ListTag();
			for (ChainTarget entry : dataSet) {
				dataTag.add(entry.serializeNBT());
			}
			listTag.add(dataTag);
		}
		nbt.put(KEY_DATA, listTag);
		return nbt;
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		ListTag listTag = nbt.getList(KEY_DATA, Tag.TAG_LIST);
		for (int i = 0; i < listTag.size() && i <= DELAY_TICKS; i++) {
			ListTag dataList = listTag.getList(i);
			Set<ChainTarget> datas = new HashSet<>();
			for (int j = 0; j < dataList.size(); j++) {
				CompoundTag data = dataList.getCompound(j);
				datas.add(ChainTarget.deserializeNBT(data));
			}
			targets.addLast(datas);
		}
	}

	public void push(Collection<ChainTarget> entries) {
		for (int i = targets.size(); i < DELAY_TICKS; i++) {
			targets.addLast(new HashSet<>());
		}
		targets.peekLast().addAll(entries);
		storage.setDirty();
	}

	public Set<ChainTarget> poll() {
		if (targets.isEmpty()) return Collections.emptySet();
		storage.setDirty();
		return targets.poll();
	}

}
