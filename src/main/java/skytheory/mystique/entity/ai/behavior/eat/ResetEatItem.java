package skytheory.mystique.entity.ai.behavior.eat;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.OneShot;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import skytheory.mystique.entity.AbstractElemental;
import skytheory.mystique.recipe.PreferenceRecipe;

public class ResetEatItem extends OneShot<AbstractElemental> {

	@Override
	public boolean trigger(ServerLevel pLevel, AbstractElemental pEntity, long pGameTime) {
		ItemStack stack = pEntity.getEatingItem();
		if (pEntity.getBrain().hasMemoryValue(MemoryModuleType.IS_PANICKING) || PreferenceRecipe.getRecipe(pEntity, stack).isEmpty()) {
			if (!stack.isEmpty()) {
				ItemEntity itemEntity = new ItemEntity(pLevel, pEntity.getX(), pEntity.getY(), pEntity.getZ(), stack, 0.0d, 0.1d, 0.0d);
				pLevel.addFreshEntity(itemEntity);
				pEntity.setEatingItem(ItemStack.EMPTY);
			}
			pEntity.getEntityData().set(AbstractElemental.DATA_IS_EATING, false);
			pEntity.getEntityData().set(AbstractElemental.DATA_EATING_TICKS, 0);
			pEntity.getBrain().useDefaultActivity();
		}
		return true;
	}

}
