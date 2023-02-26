package skytheory.mystique.item;

import java.util.Collection;
import java.util.Collections;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.item.ItemStack;
import skytheory.mystique.Mystique;
import skytheory.mystique.entity.AbstractElemental;

public interface MystiqueContract {

	public static final ResourceLocation REGISTRY_LOCATION = new ResourceLocation(Mystique.MODID, "contract");

	public static final MystiqueContract EMPTY = new MystiqueContract() {

		/**
		 * 登録するMemoryModuleTypeを取得する
		 */
		@Override
		public Collection<MemoryModuleType<?>> getMemoryModules() {
			return Collections.emptySet();
		}

		/**
		 * 登録するSensorTypeを取得する
		 * 現在のContractに係わらず発火するため、複数のSensorTypeで同じMemoryModuleを使いまわすのは避けること
		 */
		@Override
		public Collection<SensorType<Sensor<? super AbstractElemental>>> getSensorTypes() {
			return Collections.emptySet();
		}

		/**
		 * Contract時に使用するBehaviorを登録する
		 */
		@Override
		public void registerActions(Brain<AbstractElemental> pEntity, ItemStack pStack) {
		}
	};

	Collection<MemoryModuleType<?>> getMemoryModules();
	Collection<SensorType<Sensor<? super AbstractElemental>>> getSensorTypes();
	void registerActions(Brain<AbstractElemental> pEntity, ItemStack pStack);
}
