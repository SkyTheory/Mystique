package skytheory.mystique.entity.ai.contract;

import java.util.Collection;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import skytheory.mystique.Mystique;
import skytheory.mystique.client.model.pose.DefaultPose;
import skytheory.mystique.client.model.pose.ElementalPoseTransformer;
import skytheory.mystique.client.renderer.itemlayer.DefaultItemRenderer;
import skytheory.mystique.client.renderer.itemlayer.ElementalItemRenderer;
import skytheory.mystique.entity.AbstractElemental;
import skytheory.mystique.recipe.ContractRecipe;

public interface MystiqueContract {

	public static final ResourceLocation REGISTRY_LOCATION = new ResourceLocation(Mystique.MODID, "contract");
	public static final MystiqueContract DEFAULT = new DefaultContract();

	/**
	 * Contract利用時に設定するActivityを指定する
	 * 必ず一対一で対応させること
	 */
	Activity getActivity();

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
		return ContractRecipe.getContract(entity, entity.getContractItem()) == this;
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
	 * Contract時に使用するBehaviorを登録する
	 */
	Collection<? extends BehaviorControl<? super AbstractElemental>> getActions();

	default InteractionResult interaction(AbstractElemental entity, Player player) {
		return InteractionResult.PASS;
	}

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
	
}
