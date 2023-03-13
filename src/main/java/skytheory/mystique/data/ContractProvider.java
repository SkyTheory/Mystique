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
import net.minecraftforge.common.data.ExistingFileHelper;
import skytheory.mystique.Mystique;
import skytheory.mystique.init.MystiqueContracts;
import skytheory.mystique.init.MystiqueItems;
import skytheory.mystique.recipe.ContractRecipe;

public class ContractProvider implements DataProvider {

	protected final PackOutput.PathProvider pathProvider;
	protected final String modid;
	protected final List<ContractRecipe> recipes = new ArrayList<>();

	public ContractProvider(PackOutput gen, String modid, ExistingFileHelper exFileHelper) {
		this.pathProvider = gen.createPathProvider(PackOutput.Target.DATA_PACK, "recipes/contracts");
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
				new ContractRecipe(
						new ResourceLocation(Mystique.MODID, "collect"),
						MystiqueItems.CONTRACT_DOCUMENT,
						MystiqueContracts.COLLECT));
	}

	@Override
	public String getName() {
		return "Mystique Contructs";
	}

}
