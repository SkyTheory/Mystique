package skytheory.mystique.data;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;
import skytheory.mystique.init.MystiqueBlocks;
import skytheory.mystique.init.MystiqueEntityTypes;
import skytheory.mystique.init.MystiqueItems;

public class LanguageProviderEN extends LanguageProvider {

	public LanguageProviderEN(PackOutput gen, String modid) {
		super(gen, modid, "en_us");
	}

	@Override
	protected void addTranslations() {

		add(MystiqueItems.MANA_CAPACITOR, "Mana Capacitor");
		add(MystiqueItems.MANA_CAPACITOR_CREATIVE, "Creative Mana Capacitor");

		add(MystiqueItems.CONTRACT_DOCUMENT, "Contract Document");
		
		add(MystiqueItems.TOOL_STYLUS, "Mystical Stylus");

		add(MystiqueItems.SCHIST, "Schist");
		
		add(MystiqueItems.SCHIST_FIRE, "Fire Schist");
		add(MystiqueItems.SCHIST_AIR, "Air Schist");
		add(MystiqueItems.SCHIST_WATER, "Water Schist");
		add(MystiqueItems.SCHIST_EARTH, "Earth Schist");
		
		add(MystiqueItems.SHARD_FIRE, "Fire Shard");
		add(MystiqueItems.SHARD_AIR, "Air Shard");
		add(MystiqueItems.SHARD_WATER, "Water Shard");
		add(MystiqueItems.SHARD_EARTH, "Earth Shard");

		add(MystiqueItems.CRUX_FIRE, "Crux of Fire");
		add(MystiqueItems.CRUX_AIR, "Crux of Air");
		add(MystiqueItems.CRUX_WATER, "Crux of Water");
		add(MystiqueItems.CRUX_EARTH, "Crux of Earth");

		add(MystiqueItems.DUST_FIRE, "Fire Element Dust");
		add(MystiqueItems.DUST_AIR, "Air Element Dust");
		add(MystiqueItems.DUST_WATER, "Water Element Dust");
		add(MystiqueItems.DUST_EARTH, "Earth Element Dust");

		add(MystiqueBlocks.ORE_STONE_FIRE, "Fire Element Ore");
		add(MystiqueBlocks.ORE_STONE_AIR, "Air Element Ore");
		add(MystiqueBlocks.ORE_STONE_WATER, "Water Element Ore");
		add(MystiqueBlocks.ORE_STONE_EARTH, "Eatrh Element Ore");

		add(MystiqueBlocks.ORE_DEEPSLATE_FIRE, "Deepslate Fire Element Ore");
		add(MystiqueBlocks.ORE_DEEPSLATE_AIR, "Deepslate Air Element Ore");
		add(MystiqueBlocks.ORE_DEEPSLATE_WATER, "Deepslate Water Element Ore");
		add(MystiqueBlocks.ORE_DEEPSLATE_EARTH, "Deepslate Eatrh Element Ore");

		add(MystiqueBlocks.DEVICE_MANA_INFUSER, "Mana Infuser");
		add(MystiqueBlocks.DEVICE_WRITING_TABLE, "Writing Table");
		add(MystiqueBlocks.DEVICE_WRITING_CHEST, "Writing Chest");
		add(MystiqueBlocks.DEVICE_WALL_BOOKSHELF, "Wall Bookshelf");

		add(MystiqueEntityTypes.SALAMANDER, "Salamander");
		add(MystiqueEntityTypes.SYLPH, "Sylph");
		add(MystiqueEntityTypes.UNDINE, "Undine");
		add(MystiqueEntityTypes.GNOME, "Gnome");
		
		add(MystiqueEntityTypes.FIELD_MARKER, "Marker");
		
		add("itemGroup.mystique", "Mystique");
	}

}
