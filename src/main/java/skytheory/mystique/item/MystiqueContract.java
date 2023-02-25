package skytheory.mystique.item;

import java.util.Collection;
import java.util.Collections;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.item.ItemStack;
import skytheory.mystique.Mystique;
import skytheory.mystique.entity.AbstractElemental;

@FunctionalInterface
public interface MystiqueContract {

	public static final ResourceLocation REGISTRY_LOCATION = new ResourceLocation(Mystique.MODID, "contract");

	public static final MystiqueContract EMPTY = (brain, stack) -> Collections.emptyList();

	Collection<BehaviorControl<? extends AbstractElemental>> registerActions(Brain<AbstractElemental> pEntity, ItemStack pStack);
}
