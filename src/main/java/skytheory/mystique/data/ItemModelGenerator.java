package skytheory.mystique.data;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import skytheory.lib.data.AbstractItemModelGenerator;

public class ItemModelGenerator extends AbstractItemModelGenerator {

	public ItemModelGenerator(PackOutput generator, String modid, ExistingFileHelper existingFileHelper) {
		super(generator, modid, existingFileHelper);
	}
	
	@Override
	public void registerItemModels() {
	}
	
}
