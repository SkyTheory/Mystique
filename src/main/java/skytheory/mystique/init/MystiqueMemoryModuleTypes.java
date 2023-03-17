package skytheory.mystique.init;

import java.util.Optional;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.item.ItemEntity;

public class MystiqueMemoryModuleTypes {

	public static final MemoryModuleType<LivingEntity> COMMON_NEAREST_HOSTILE_WITHOUT_SPIDER = new MemoryModuleType<>(Optional.empty());
	public static final MemoryModuleType<Integer> COMMON_LOOK_COOLDOWN = new MemoryModuleType<>(Optional.empty());
	public static final MemoryModuleType<ItemEntity> COLLECT_ITEM = new MemoryModuleType<>(Optional.empty());
	
}
