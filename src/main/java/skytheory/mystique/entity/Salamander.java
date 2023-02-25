package skytheory.mystique.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import skytheory.mystique.init.MystiqueItems;

public class Salamander extends AbstractElemental {
	
	public Salamander(EntityType<Salamander> type, Level level) {
		super(type, level);
	}

	@Override
	public ItemStack getPickedResult(HitResult target) {
		ItemStack stack = super.getPickedResult(target);
		stack = new ItemStack(MystiqueItems.CRUX_FIRE);
		return stack;
	}
	
}
