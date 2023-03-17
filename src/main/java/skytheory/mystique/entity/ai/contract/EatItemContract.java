package skytheory.mystique.entity.ai.contract;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import skytheory.mystique.client.model.pose.EatingPose;
import skytheory.mystique.client.model.pose.ElementalPoseTransformer;
import skytheory.mystique.client.renderer.itemlayer.EatingItemRenderer;
import skytheory.mystique.client.renderer.itemlayer.ElementalItemRenderer;
import skytheory.mystique.entity.AbstractElemental;
import skytheory.mystique.entity.ai.behavior.eat.EatItem;
import skytheory.mystique.entity.ai.behavior.eat.ThrowNonEdibleItem;
import skytheory.mystique.recipe.PreferenceRecipe;

public class EatItemContract implements MystiqueContract {

	public static final EntityDataAccessor<Boolean> DATA_IS_EATING = SynchedEntityData.defineId(AbstractElemental.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<ItemStack> DATA_EATING_ITEM = SynchedEntityData.defineId(AbstractElemental.class, EntityDataSerializers.ITEM_STACK);
	public static final EntityDataAccessor<Integer> DATA_EATING_TICKS = SynchedEntityData.defineId(AbstractElemental.class, EntityDataSerializers.INT);

	@Override
	public boolean canApplyContract(AbstractElemental entity) {
		if (entity.getBrain().hasMemoryValue(MemoryModuleType.IS_PANICKING)) return false;
		return !getEatingItem(entity).isEmpty(); 
	}

	/**
	 * 通常のContractより優先して実行する
	 * Interactingより優先度は下
	 */
	@Override
	public int getPriority() {
		return 50;
	}

	@Override
	public Collection<MemoryModuleType<?>> getMemoryModules() {
		return Set.of();
	}

	@Override
	public Collection<SensorType<? extends Sensor<? super AbstractElemental>>> getSensorTypes() {
		return Set.of();
	}

	@Override
	public List<? extends BehaviorControl<? super AbstractElemental>> getActions() {
		return List.of(
				new EatItem(),
				new ThrowNonEdibleItem()
				);
	}
	
	@Override
	public InteractionResult nonActiveInteract(AbstractElemental entity, Player player) {
		ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND).copy();
		Optional<PreferenceRecipe> recipe = PreferenceRecipe.getRecipe(entity, stack);
		if (recipe.isPresent() && EatItemContract.getEatingItem(entity).isEmpty()) {
			EatItemContract.setEatingItem(entity, stack.split(1));
			if (!player.isCreative()) {
				if (stack.isEmpty()) stack = ItemStack.EMPTY;
				player.setItemInHand(InteractionHand.MAIN_HAND, stack);
			}
			return InteractionResult.CONSUME;
		}
		return InteractionResult.PASS;
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public ElementalPoseTransformer getPoseTransformer(AbstractElemental entity) {
		return EatingPose::transform;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public ElementalItemRenderer getItemRenderer(AbstractElemental entity) {
		return EatingItemRenderer::render;
	}
	
	@Override
	public List<EntityDataEntry<?>> createDataSyncChannel() {
		return List.of(
				new EntityDataEntry<>(DATA_IS_EATING, false),
				new EntityDataEntry<>(DATA_EATING_ITEM, ItemStack.EMPTY),
				new EntityDataEntry<>(DATA_EATING_TICKS, 0)
				);
	}
	
	@Override
	public void saveAdditionalData(AbstractElemental entity, CompoundTag nbt) {
		nbt.put("EatingItem", getEatingItem(entity).serializeNBT());
		nbt.putInt("EatingTicks", entity.getEntityData().get(DATA_EATING_TICKS));
	}
	
	@Override
	public void loadAdditionalData(AbstractElemental entity, CompoundTag nbt) {
		entity.getEntityData().set(DATA_EATING_ITEM, ItemStack.of(nbt.getCompound("EatingItem")));
		entity.getEntityData().set(DATA_EATING_TICKS, nbt.getInt("EatingTicks"));
	}

	public static void setEatingItem(AbstractElemental entity, ItemStack stack) {
		entity.getEntityData().set(DATA_EATING_ITEM, stack);
	}
	
	public static ItemStack getEatingItem(AbstractElemental entity) {
		return entity.getEntityData().get(DATA_EATING_ITEM);
	}
	
}
