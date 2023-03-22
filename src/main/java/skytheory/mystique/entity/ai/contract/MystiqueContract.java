package skytheory.mystique.entity.ai.contract;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import skytheory.mystique.client.model.pose.DefaultPose;
import skytheory.mystique.client.model.pose.ElementalPoseTransformer;
import skytheory.mystique.client.renderer.layer.DefaultItemRenderer;
import skytheory.mystique.client.renderer.layer.ElementalItemRenderer;
import skytheory.mystique.entity.AbstractElemental;
import skytheory.mystique.recipe.ContractRecipe;

public interface MystiqueContract {

	/**
	 * Contractの優先度を指定する
	 * 値が小さいほど優先して適用する
	 */
	default int getPriority() {
		return 100;
	}

	/**
	 * Contractを適用する条件を指定する
	 */
	default boolean canApplyContract(AbstractElemental entity) {
		return ContractRecipe.canApplyContract(entity, this);
	}

	/**
	 * 登録するMemoryModuleTypeを取得する
	 */
	Collection<MemoryModuleType<?>> getMemoryModules();

	/**
	 * 登録するSensorTypeを取得する
	 * 現在のContractに係わらず発火するため、複数のSensorTypeで同じMemoryModuleを共有する際は十分に注意すること
	 */
	Collection<SensorType<? extends Sensor<? super AbstractElemental>>> getSensorTypes();

	/**
	 * Contractにかかわらず、常に使用するBehaviorを登録する
	 */
	default List<? extends BehaviorControl<? super AbstractElemental>> getCoreActions() {return Collections.emptyList();};
	
	/**
	 * Contract時に使用するBehaviorを登録する
	 */
	List<? extends BehaviorControl<? super AbstractElemental>> getActions();

	/**
	 * Contract適用時に呼ばれる右クリック処理
	 * @param entity
	 * @param player
	 * @return
	 */
	default InteractionResult activeInteract(AbstractElemental entity, Player player) {
		return InteractionResult.PASS;
	}
	
	/**
	 * Contract非適用時に呼ばれる右クリック処理 
	 * @param entity
	 * @param player
	 * @return
	 */
	default InteractionResult nonActiveInteract(AbstractElemental entity, Player player) {
		return InteractionResult.PASS;
	}

	default List<EntityDataEntry<?>> createDataSyncChannel(){return Collections.emptyList();}
	default void saveAdditionalData(AbstractElemental entity, CompoundTag nbt) {}
	default void loadAdditionalData(AbstractElemental entity, CompoundTag nbt) {}
	default void enterContract(AbstractElemental entity) {}
	default void leaveContract(AbstractElemental entity) {}
	
	@OnlyIn(Dist.CLIENT)
	default ElementalPoseTransformer getPoseTransformer(AbstractElemental entity) {
		return DefaultPose::transform;
	}

	@OnlyIn(Dist.CLIENT)
	default ElementalItemRenderer getItemRenderer(AbstractElemental entity) {
		return DefaultItemRenderer::render;
	}
	
	public static record EntityDataEntry<T>(EntityDataAccessor<T> accessor, T initialValue) {}
	
}
