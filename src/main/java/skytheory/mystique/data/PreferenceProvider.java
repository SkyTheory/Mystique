package skytheory.mystique.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.data.ExistingFileHelper;
import skytheory.mystique.Mystique;
import skytheory.mystique.init.MystiqueEntityTypes;
import skytheory.mystique.recipe.PreferenceRecipe;
import skytheory.mystique.util.ManaComponent;

public class PreferenceProvider implements DataProvider {

	protected final PackOutput.PathProvider pathProvider;
	protected final String modid;
	protected final List<PreferenceRecipe> recipes = new ArrayList<>();

	public PreferenceProvider(PackOutput gen, String modid, ExistingFileHelper exFileHelper) {
		this.pathProvider = gen.createPathProvider(PackOutput.Target.DATA_PACK, "recipes/preferences");
		this.modid = modid;
	}

	@Override
	public CompletableFuture<?> run(CachedOutput pOutput) {
		Set<ResourceLocation> set = new HashSet<>();
		this.addRecipes();
	      List<CompletableFuture<?>> list = new ArrayList<>();
		this.recipes.forEach(recipe -> {
			if (!set.add(recipe.getId())) {
				throw new IllegalStateException("Duplicate recipe " + recipe.getId());
			} else {
				list.add(DataProvider.saveStable(pOutput, recipe.serializeRecipe(), pathProvider.json(recipe.getId())));
			}
		});
		return CompletableFuture.allOf(list.toArray((i) -> new CompletableFuture[i]));
	}

	protected void addRecipes() {
		this.recipes.add(
				new PreferenceRecipe(
						new ResourceLocation(Mystique.MODID, "apple"),
						Items.APPLE,
						3,
						ManaComponent.FIRE.amountOf(3)));
		this.recipes.add(
				new PreferenceRecipe(
						new ResourceLocation(Mystique.MODID, "bread"),
						MystiqueEntityTypes.SALAMANDER,
						Ingredient.of(new ItemStack(Items.BREAD)),
						3, ItemStack.EMPTY,
						0.0f,
						ManaComponent.FIRE.amountOf(3)));
	}

	@Override
	public String getName() {
		return "Mystique Preferences";
	}

}
