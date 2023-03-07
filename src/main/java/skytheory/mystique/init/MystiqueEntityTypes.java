package skytheory.mystique.init;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import skytheory.mystique.entity.FieldMarker;
import skytheory.mystique.entity.Gnome;
import skytheory.mystique.entity.TinyTNT;
import skytheory.mystique.entity.Salamander;
import skytheory.mystique.entity.Sylph;
import skytheory.mystique.entity.Undine;
import skytheory.mystique.util.ElementalUtils;

public class MystiqueEntityTypes {

	public static final EntityType<Gnome> GNOME = ElementalUtils.createEntityType(Gnome::new, "gnome");
	public static final EntityType<Salamander> SALAMANDER = ElementalUtils.createEntityType(Salamander::new, "salamander");
	public static final EntityType<Sylph> SYLPH = ElementalUtils.createEntityType(Sylph::new, "sylph");
	public static final EntityType<Undine> UNDINE = ElementalUtils.createEntityType(Undine::new, "sylph");
	public static final EntityType<FieldMarker> FIELD_MARKER = EntityType.Builder
			.of(FieldMarker::new, MobCategory.MISC)
			.sized(1.0f, 1.0f)
			.build("mystique:field_marker");
	public static final EntityType<TinyTNT> MICRO_DYNAMIS = EntityType.Builder
			.of(TinyTNT::new, MobCategory.MISC)
			.sized(1.0f, 1.0f)
			.build("mystique:micro_dynamis");
}
