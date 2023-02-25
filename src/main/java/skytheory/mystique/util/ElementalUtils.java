package skytheory.mystique.util;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import skytheory.mystique.entity.AbstractElemental;

public class ElementalUtils {

	public static <T extends Entity> EntityType<T> createEntityType(EntityType.EntityFactory<T> factory, String name) {
		return EntityType.Builder.of(factory, MobCategory.CREATURE)
				.sized(AbstractElemental.HITBOX_WIDTH, AbstractElemental.HITBOX_HEIGHT)
				.build("mystique:" + name);
	}

	/**
	 * Entityのワールド内での基本情報を設定する
	 */
	public static AttributeSupplier createElementalAttributeSupplier() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes()
				.add(Attributes.MOVEMENT_SPEED, 0.3d)
				.add(Attributes.MAX_HEALTH, 20.0d)
				.add(Attributes.FOLLOW_RANGE, 48.0d);
		return builder.build();
	}

}
