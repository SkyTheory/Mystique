package skytheory.mystique.entity.ai.behavior.eat;

import java.util.Collections;

import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import skytheory.lib.util.FloatUtils;
import skytheory.lib.util.RandomHelper;
import skytheory.mystique.entity.AbstractElemental;
import skytheory.mystique.entity.ai.contract.EatItemContract;
import skytheory.mystique.recipe.PreferenceRecipe;

public class EatItem extends Behavior<AbstractElemental> {

	public static final int DURATION_EAT = 32;
	public static final int DURATION_EAT_BEFORE = 2;

	public EatItem() {
		super(Collections.emptyMap());
	}
	
	@Override
	protected boolean checkExtraStartConditions(ServerLevel pLevel, AbstractElemental pOwner) {
		ItemStack stack = EatItemContract.getEatingItem(pOwner);
		return PreferenceRecipe.getRecipe(pOwner, stack).isPresent();
	}
	
	@Override
	protected boolean canStillUse(ServerLevel pLevel, AbstractElemental pEntity, long pGameTime) {
		return checkExtraStartConditions(pLevel, pEntity);
	}
	
	@Override
	protected void start(ServerLevel pLevel, AbstractElemental pEntity, long pGameTime) {
		pEntity.getEntityData().set(EatItemContract.DATA_IS_EATING, true);
		pEntity.getNavigation().stop();
	}
	
	@Override
	protected void tick(ServerLevel pLevel, AbstractElemental pEntity, long pGameTime) {
		int ticks = pEntity.getEntityData().get(EatItemContract.DATA_EATING_TICKS);
		ItemStack stack = EatItemContract.getEatingItem(pEntity);
		pEntity.getEntityData().set(EatItemContract.DATA_EATING_TICKS, ++ticks);
		if (ticks > DURATION_EAT_BEFORE) {
			int eatingTicks = ticks - DURATION_EAT_BEFORE;
			if (shouldTriggerEatEffects(eatingTicks)) eatProcess(pEntity, stack);
			if (eatingTicks >= DURATION_EAT) eatComplete(pEntity, stack);
		}
	}

	protected void eatProcess(AbstractElemental pEntity, ItemStack pStack) {
		RandomSource random = pEntity.getRandom();
		for (int i = 0; i < 2; i++, this.triggerItemUseEffects(pEntity, pStack));
		pEntity.playSound(pEntity.getEatingSound(pStack), 0.5f + 0.5f * (float)random.nextInt(2), (random.nextFloat() - random.nextFloat()) * 0.2f + 1.0f);
	}

	protected void eatComplete(AbstractElemental pEntity, ItemStack pStack) {
		for (int i = 0; i < 2; i++, this.triggerItemUseEffects(pEntity, pStack));
		EatItemContract.setEatingItem(pEntity, ItemStack.EMPTY);
		pEntity.heal(pEntity.getMaxHealth());
		pEntity.playSound(SoundEvents.ITEM_PICKUP, 1.0f, pEntity.getRandom().nextFloat() * 0.2f + 0.9f);
	}

	protected boolean shouldTriggerEatEffects(int progress) {
		int freq = 8;
		int attack = 7;
		return (progress > attack) && progress % freq == 0;
	}

	protected void triggerItemUseEffects(AbstractElemental pEntity, ItemStack pStack) {
		Vec3 pos = getEffectPosition(pEntity, 0.8d, 0.4d);
		Vec3 speed = getEffectSpeed(pEntity, 0.1d);
		((ServerLevel) pEntity.getLevel()).sendParticles(new ItemParticleOption(ParticleTypes.ITEM, pStack), pos.x, pos.y, pos.z, 1, speed.x, speed.y + 0.05d, speed.z, 0.0d);

	}

	protected Vec3 getEffectPosition(AbstractElemental pEntity, double height, double dist) {
		RandomSource random = pEntity.getRandom();
		Vec3 offset = new Vec3(RandomHelper.rangeSigned(random, 0.1d), RandomHelper.rangeSigned(random, 0.1d), RandomHelper.rangeSigned(random, 0.1d) + dist * pEntity.getScale());
		offset = modulateEffectVector(pEntity, offset);
		offset = offset.add(pEntity.getX(), pEntity.getY() + height * pEntity.getScale(), pEntity.getZ());
		return offset;
	}

	protected Vec3 getEffectSpeed(AbstractElemental pEntity, double radius) {
		RandomSource random = pEntity.getRandom();
		Vec3 vec3 = new Vec3(RandomHelper.rangeSigned(random, radius), RandomHelper.rangeSigned(random, radius) + 0.1d, RandomHelper.rangeSigned(random, radius));
		return modulateEffectVector(pEntity, vec3);
	}

	protected Vec3 modulateEffectVector(AbstractElemental pEntity, Vec3 vec3) {
		Vec3 result = vec3;
		result = result.xRot(-pEntity.xRotO * FloatUtils.PI / 180.0f);
		result = result.yRot(-pEntity.yBodyRot * FloatUtils.PI / 180.0f);
		return result;
	}

	@Override
	protected void stop(ServerLevel pLevel, AbstractElemental pEntity, long pGameTime) {
		pEntity.getEntityData().set(EatItemContract.DATA_IS_EATING, false);
		pEntity.getEntityData().set(EatItemContract.DATA_EATING_TICKS, 0);
		super.stop(pLevel, pEntity, pGameTime);
	}
	
	@Override
	protected boolean timedOut(long pGameTime) {
		return false;
	}
}
