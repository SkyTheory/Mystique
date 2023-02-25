package skytheory.mystique.init;

import net.minecraft.world.item.crafting.RecipeType;
import skytheory.mystique.recipe.ContractRecipe;
import skytheory.mystique.recipe.PreferenceRecipe;

public class MystiqueRecipeTypes {

	public static final RecipeType<PreferenceRecipe> PREFERENCE = RecipeType.simple(PreferenceRecipe.TYPE_LOCATION);
	public static final RecipeType<ContractRecipe> CONTRACT = RecipeType.simple(ContractRecipe.TYPE_LOCATION);
	
}
