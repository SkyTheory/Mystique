package skytheory.mystique.savedata;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.util.INBTSerializable;

public class ChainHarvestData implements INBTSerializable<CompoundTag> {

	public static final int DELAY_TICKS = 4;
	
	private final Deque<Set<ChainTarget>> queue;

	protected ChainHarvestData() {
		this.queue = new ArrayDeque<>();
	}
	
	public void push(Set<ChainTarget> entries) {
		for (int i = queue.size(); i < DELAY_TICKS; i++) {
			queue.addLast(new HashSet<>());
		}
		queue.peekLast().addAll(entries);
	}
	
	public Set<ChainTarget> poll() {
		if (queue.isEmpty()) return Collections.emptySet();
		return queue.poll();
	}
	@Override
	public CompoundTag serializeNBT() {
		CompoundTag nbt = new CompoundTag();
		ListTag ticksTag = new ListTag();
		for (var positions : queue) {
			ListTag positionsTag = new ListTag();
			for(var position : positions) {
				positionsTag.add(position.serializeNBT());
			}
			ticksTag.add(positionsTag);
		}
		nbt.put("Data", ticksTag);
		return nbt;
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		ListTag ticksTag = nbt.getList("Data", Tag.TAG_LIST);
		List<Set<ChainTarget>> list = ticksTag.stream()
				.filter(ListTag.class::isInstance)
				.map(ListTag.class::cast)
				.map(positions -> positions.stream()
						.filter(CompoundTag.class::isInstance)
						.map(CompoundTag.class::cast)
						.map(ChainTarget::deserializeNBT)
						.collect(Collectors.toSet()))
				.toList();
		for (var tickPositionEntries : list) {
			queue.push(tickPositionEntries);
		}
	}
	
}
