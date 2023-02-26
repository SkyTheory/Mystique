package skytheory.mystique.init;

import net.minecraft.world.item.Item;
import skytheory.mystique.item.FieldMarkerItem;
import skytheory.mystique.item.ManaCapacitorCreativeItem;
import skytheory.mystique.item.ManaCapacitorItem;

public class MystiqueItems {

	public static final Item CONTRACT_DOCUMENT = new Item(simpleItem().stacksTo(1));

	public static final Item TOOL_STYLUS = new FieldMarkerItem(simpleItem().stacksTo(1));

	public static final Item MANA_CAPACITOR = new ManaCapacitorItem(simpleItem().stacksTo(1));
	public static final Item MANA_CAPACITOR_CREATIVE = new ManaCapacitorCreativeItem(simpleItem().stacksTo(1));
	
	public static final Item SCHIST = new Item(simpleItem());
	
	public static final Item SCHIST_FIRE = new Item(simpleItem());
	public static final Item SCHIST_AIR = new Item(simpleItem());
	public static final Item SCHIST_WATER = new Item(simpleItem());
	public static final Item SCHIST_EARTH = new Item(simpleItem());
	
	public static final Item SHARD_FIRE = new Item(simpleItem());
	public static final Item SHARD_AIR = new Item(simpleItem());
	public static final Item SHARD_WATER = new Item(simpleItem());
	public static final Item SHARD_EARTH = new Item(simpleItem());

	public static final Item CRUX_FIRE = new Item(simpleItem());
	public static final Item CRUX_AIR = new Item(simpleItem());
	public static final Item CRUX_WATER = new Item(simpleItem());
	public static final Item CRUX_EARTH = new Item(simpleItem());
	
	public static final Item DUST_FIRE = new Item(simpleItem());
	public static final Item DUST_AIR = new Item(simpleItem());
	public static final Item DUST_WATER = new Item(simpleItem());
	public static final Item DUST_EARTH = new Item(simpleItem());
	
	public static Item.Properties simpleItem() {
		return new Item.Properties();
	}

}
