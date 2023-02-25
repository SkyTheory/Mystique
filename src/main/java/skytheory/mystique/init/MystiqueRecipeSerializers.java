package skytheory.mystique.init;

import net.minecraft.world.item.crafting.RecipeSerializer;
import skytheory.mystique.recipe.ContractRecipe;
import skytheory.mystique.recipe.PreferenceRecipe;

public class MystiqueRecipeSerializers {

	public static final RecipeSerializer<PreferenceRecipe> PREFERENCE = PreferenceRecipe.SERIALIZER;
	public static final RecipeSerializer<ContractRecipe> CONTRACT = ContractRecipe.SERIALIZER;

}
