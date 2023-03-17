package skytheory.mystique.entity.ai.behavior.eat;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.OneShot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import skytheory.mystique.entity.AbstractElemental;
import skytheory.mystique.entity.ai.contract.EatItemContract;
import skytheory.mystique.recipe.PreferenceRecipe;

public class ThrowNonEdibleItem extends OneShot<AbstractElemental> {

	@Override
	public boolean trigger(ServerLevel pLevel, AbstractElemental pEntity, long pGameTime) {
		ItemStack stack = EatItemContract.getEatingItem(pEntity);
		if (!stack.isEmpty() && PreferenceRecipe.getRecipe(pEntity, stack).isEmpty()) {
			ItemEntity itemEntity = new ItemEntity(pLevel, pEntity.getX(), pEntity.getEyeY(), pEntity.getZ(), stack, 0.0d, 0.1d, 0.0d);
			pLevel.addFreshEntity(itemEntity);
			EatItemContract.setEatingItem(pEntity, ItemStack.EMPTY);
			pEntity.getEntityData().set(EatItemContract.DATA_IS_EATING, false);
			pEntity.getEntityData().set(EatItemContract.DATA_EATING_TICKS, 0);
		}
		return true;
	}

}
