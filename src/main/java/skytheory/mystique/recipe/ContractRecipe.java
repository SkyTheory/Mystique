package skytheory.mystique.recipe;

import org.jetbrains.annotations.Nullable;

import com.google.gson.JsonObject;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import skytheory.mystique.Mystique;
import skytheory.mystique.entity.AbstractElemental;
import skytheory.mystique.entity.ai.contract.MystiqueContract;
import skytheory.mystique.init.MystiqueRecipeTypes;
import skytheory.mystique.init.MystiqueRegistries;

public class ContractRecipe implements Recipe<Container> {

	public static final ResourceLocation TYPE_LOCATION = new ResourceLocation(Mystique.MODID, "contract");

	protected final ResourceLocation recipeId;
	protected final Ingredient item;
	private final MystiqueContract contract;

	public ContractRecipe(ResourceLocation id, Item item, MystiqueContract contract) {
		this.recipeId = id;
		this.item = Ingredient.of(item);
		this.contract = contract;
	}

	public ContractRecipe(ResourceLocation id, Ingredient item, MystiqueContract contract) {
		this.recipeId = id;
		this.item = item;
		this.contract = contract;
	}
	
	@Override
	public boolean matches(Container pContainer, Level pLevel) {
		throw new UnsupportedOperationException("Cannnot determine recipe: use static methods.");
	}

	@Override
	public ItemStack assemble(Container pContainer) {
		throw new UnsupportedOperationException("Cannnot assemble recipe: use static methods.");
	}

	@Override
	public boolean canCraftInDimensions(int pWidth, int pHeight) {
		return false;
	}

	@Override
	public ItemStack getResultItem() {
		throw new UnsupportedOperationException("Not supported.");
	}

	@Override
	public ResourceLocation getId() {
		return recipeId;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return SERIALIZER;
	}

	@Override
	public RecipeType<?> getType() {
		return MystiqueRecipeTypes.CONTRACT;
	}

	public boolean matches(AbstractElemental entity) {
		return item.test(entity.getContractItem());
	}
	
	public static boolean canApplyContract(AbstractElemental entity, MystiqueContract contract) {
		return entity.getLevel().getRecipeManager().getAllRecipesFor(MystiqueRecipeTypes.CONTRACT).stream()
				.filter(recipe -> recipe.contract.equals(contract))
				.anyMatch(recipe -> recipe.matches(entity));
	}
	
	public JsonObject serializeRecipe() {
		JsonObject result = new JsonObject();
		result.addProperty("type", TYPE_LOCATION.toString());
		result.add("item", this.item.toJson());
		result.addProperty("contract", MystiqueRegistries.CONTRACTS.getKey(getContract()).toString());
		return result;
	}

	public MystiqueContract getContract() {
		return contract;
	}

	public static final RecipeSerializer<ContractRecipe> SERIALIZER = new RecipeSerializer<>() {
		
		@Override
		public ContractRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
			Ingredient item = Ingredient.fromJson(pSerializedRecipe.get("item"));
			ResourceLocation location = new ResourceLocation(GsonHelper.getAsString(pSerializedRecipe, "contract"));
			MystiqueContract contract = MystiqueRegistries.CONTRACTS.getValue(location);
			return new ContractRecipe(pRecipeId, item, contract);
		}

		@Override
		public @Nullable ContractRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
			Ingredient item = Ingredient.fromNetwork(pBuffer);
			MystiqueContract contract = MystiqueRegistries.CONTRACTS.getValue(pBuffer.readResourceLocation());
			return new ContractRecipe(pRecipeId, item, contract);
		}

		@Override
		public void toNetwork(FriendlyByteBuf pBuffer, ContractRecipe pRecipe) {
			pRecipe.item.toNetwork(pBuffer);
			pBuffer.writeResourceLocation(MystiqueRegistries.CONTRACTS.getKey(pRecipe.getContract()));
		}

	};

}
