package skytheory.mystique.init;

import java.util.Optional;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.item.ItemEntity;
import skytheory.mystique.item.MystiqueContract;

public class MystiqueMemoryModuleTypes {

	public static final MemoryModuleType<LivingEntity> NEAREST_HOSTILE = new MemoryModuleType<>(Optional.empty());
	public static final MemoryModuleType<ItemEntity> COLLECT_ITEM = new MemoryModuleType<>(Optional.empty());
	public static final MemoryModuleType<MystiqueContract> CONTRACT = new MemoryModuleType<>(Optional.empty());
	
}
