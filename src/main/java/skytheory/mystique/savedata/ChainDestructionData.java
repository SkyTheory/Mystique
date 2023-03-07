package skytheory.mystique.savedata;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.util.INBTSerializable;

public class ChainDestructionData implements INBTSerializable<CompoundTag> {

	public static final String KEY_DATA = "Data";
	public static final int DELAY_TICKS = 4;

	private final MystiqueLevelData storage;
	private final LinkedList<Set<ChainTarget>> queueList = new LinkedList<>(); 

	public ChainDestructionData(MystiqueLevelData storage) {
		this.storage = storage;
	}

	@Override
	public CompoundTag serializeNBT() {
		CompoundTag nbt = new CompoundTag();
		ListTag listTag = new ListTag();
		for (Set<ChainTarget> dataSet : queueList) {
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
		for (int i = 0; i < listTag.size(); i++) {
			ListTag dataList = listTag.getList(i);
			Set<ChainTarget> datas = new HashSet<>();
			for (int j = 0; j < dataList.size(); j++) {
				CompoundTag data = dataList.getCompound(j);
				datas.add(ChainTarget.deserializeNBT(data));
			}
			queueList.addLast(datas);
		}
	}

	public void enqueue(ChainTarget target) {
		this.register(target, target.target().distManhattan(target.origin()) * DELAY_TICKS);
	}

	public void enqueue(Collection<ChainTarget> targets) {
		Set<ChainTarget> reg = new HashSet<>();
		List<Set<ChainTarget>> indexVarList = new ArrayList<>();
		reg.addAll(targets);
		for (int i = 0; !reg.isEmpty(); i++) {
			int dist = i;
			Set<ChainTarget> holder = new HashSet<>();
			reg.stream()
			.filter(t -> t.target().distManhattan(t.origin()) == dist)
			.forEach(holder::add);
			reg.removeIf(holder::contains);
			indexVarList.add(holder);
		}
		for (int i = 0; i < indexVarList.size(); i++) register(indexVarList.get(i), i * DELAY_TICKS);
	}

	private void register(ChainTarget target, int index) {
		register(Collections.singleton(target), index);
	}

	private void register(Set<ChainTarget> targets, int index) {
		if (targets.isEmpty()) return;
		while(queueList.size() <= index) {
			queueList.addLast(new HashSet<>());
		}
		queueList.get(index).addAll(targets);
		storage.setDirty();
	}

	public Set<ChainTarget> dequeue() {
		if (queueList.isEmpty()) return Collections.emptySet();
		storage.setDirty();
		return queueList.poll();
	}
	
	public Set<ChainTarget> peek() {
		return queueList.peek();
	}

}
