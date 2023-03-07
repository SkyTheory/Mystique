package skytheory.mystique.init;

import com.google.common.base.Suppliers;

import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import skytheory.mystique.item.FieldMarkerItem;
import skytheory.mystique.item.ManaCapacitorCreativeItem;
import skytheory.mystique.item.ManaCapacitorItem;
import skytheory.mystique.item.TinyTNTItem;

public class MystiqueItems {
	
	public static final Item CONTRACT_DOCUMENT = new Item(simpleItem().stacksTo(1));

	public static final Item TOOL_STYLUS = new FieldMarkerItem(simpleItem().stacksTo(1));
	public static final Item TOOL_TINY_TNT = new TinyTNTItem(simpleItem());

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

	public static final Item SPAWN_EGG_SALAMANDER = new ForgeSpawnEggItem(Suppliers.ofInstance(MystiqueEntityTypes.SALAMANDER), 0xFFF07A, 0xED2700, simpleItem());
	public static final Item SPAWN_EGG_SYLPH = new ForgeSpawnEggItem(Suppliers.ofInstance(MystiqueEntityTypes.SYLPH), 0xC3FF7A, 0x00ED27, simpleItem());
	public static final Item SPAWN_EGG_UNDINE = new ForgeSpawnEggItem(Suppliers.ofInstance(MystiqueEntityTypes.UNDINE), 0x7AD9FF, 0x0000ED, simpleItem());
	public static final Item SPAWN_EGG_GNOME = new ForgeSpawnEggItem(Suppliers.ofInstance(MystiqueEntityTypes.GNOME), 0xE1FF7A, 0xED7600, simpleItem());
	
	public static Item.Properties simpleItem() {
		return new Item.Properties();
	}

}
