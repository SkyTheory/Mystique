package skytheory.mystique.recipe;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.JsonOps;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import skytheory.mystique.Mystique;
import skytheory.mystique.capability.ElementComponent;
import skytheory.mystique.init.MystiqueRecipeTypes;

public class PreferenceRecipe implements Recipe<Container> {

	public static final ResourceLocation TYPE_LOCATION = new ResourceLocation(Mystique.MODID, "preference");
	
	private static final Logger LOGGER = LogUtils.getLogger();
	private static final Random RANDOM = new Random();

	protected final ResourceLocation id;
	protected final EntityType<?> type;
	protected final Ingredient food;
	protected final int likeability;
	protected final ItemStack giftItem;
	protected final float giftChance;
	protected final ElementComponent component;

	public PreferenceRecipe(ResourceLocation id, Item item, int likeability, ElementComponent component) {
		this(id, new ItemStack(item), likeability, component);
	}

	public PreferenceRecipe(ResourceLocation id, ItemStack food, int likeability, ElementComponent component) {
		this(id, food, likeability, ItemStack.EMPTY, 0.0f, component);
	}

	public PreferenceRecipe(ResourceLocation id, ItemStack food, int likeability, ItemStack gift, float chance, ElementComponent component) {
		this(id, Ingredient.of(food), likeability, gift, chance, component);
	}

	public PreferenceRecipe(ResourceLocation id, Ingredient food, int likeability, ItemStack gift, float chance, ElementComponent component) {
		this(id, null, food, likeability, gift, chance, component);
	}

	public PreferenceRecipe(ResourceLocation id, EntityType<?> type, Ingredient food, int likeability, ItemStack gift, float chance, ElementComponent component) {
		this.id = id;
		this.type = type;
		this.food = food;
		this.likeability = likeability;
		this.giftItem = gift;
		this.giftChance = chance;
		this.component = component;
	}

	@Override
	public boolean matches(Container pContainer, Level pLevel) {
		throw new UnsupportedOperationException("Use static getRecipe method.");
	}

	public boolean matches(Entity entity, ItemStack stack) {
		if (this.type != null && this.type != entity.getType()) return false;
		return this.food.test(stack);
	}
	
	public static Optional<PreferenceRecipe> getRecipe(Entity entity, ItemStack stack) {
		return getRecipes(entity, stack).stream().filter(recipe -> recipe.matches(entity, stack))
				.findFirst();
	}
	
	public static List<PreferenceRecipe> getRecipes(Entity entity, ItemStack stack) {
		return entity.getLevel().getRecipeManager().getAllRecipesFor(MystiqueRecipeTypes.PREFERENCE).stream()
				.filter(recipe -> recipe.matches(entity, stack))
				.toList();
	}
	
	public boolean chance() {
		return RANDOM.nextFloat() <= this.giftChance;
	}

	@Override
	public ItemStack assemble(Container pContainer) {
		return this.giftItem.copy();
	}

	@Override
	public boolean canCraftInDimensions(int pWidth, int pHeight) {
		return true;
	}

	@Override
	public ItemStack getResultItem() {
		return ItemStack.EMPTY;
	}

	@Override
	public ResourceLocation getId() {
		return id;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return SERIALIZER;
	}

	@Override
	public RecipeType<?> getType() {
		return MystiqueRecipeTypes.PREFERENCE;
	}
	
	public JsonObject serializeRecipe() {
		JsonObject result = new JsonObject();
		result.addProperty("type", TYPE_LOCATION.toString());
		if (this.type != null) {
			result.addProperty("entity_type", ForgeRegistries.ENTITY_TYPES.getKey(this.type).toString());
		}
		result.add("food", this.food.toJson());
		result.addProperty("likeability", this.likeability);
		JsonElement gift = ItemStack.CODEC.encodeStart(JsonOps.INSTANCE, this.giftItem).getOrThrow(true, LOGGER::error);
		result.add("gift_item", gift);
		result.addProperty("gift_chance", this.giftChance);
		ElementComponent.encode(result, this.component);
		return result;
	}

	public static final RecipeSerializer<PreferenceRecipe> SERIALIZER = new RecipeSerializer<>() {
		
		@Override
		public PreferenceRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
			Ingredient food = Ingredient.fromJson(pSerializedRecipe.get("food"));
			EntityType<?> type = null;
			if (pSerializedRecipe.has("entity_type")) {
				ResourceLocation location = new ResourceLocation(GsonHelper.getAsString(pSerializedRecipe, "entity_type"));
				if (ForgeRegistries.ENTITY_TYPES.containsKey(location)) {
					type = ForgeRegistries.ENTITY_TYPES.getValue(location);
				} else {
					throw new RuntimeException("Missing EntityType entry: " + pRecipeId);
				}
			}
			int likeability = GsonHelper.getAsInt(pSerializedRecipe, "likeability");
			ItemStack gift = ItemStack.CODEC.parse(JsonOps.INSTANCE, pSerializedRecipe.get("gift_item")).getOrThrow(true, LOGGER::error);
			float chance = GsonHelper.getAsFloat(pSerializedRecipe, "gift_chance", 0.0f);
			ElementComponent component = ElementComponent.decode(pSerializedRecipe);
			return new PreferenceRecipe(pRecipeId, type, food, likeability, gift, chance, component);
		}

		@Override
		public @Nullable PreferenceRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
			Ingredient food = Ingredient.fromNetwork(pBuffer);
			int likeability = pBuffer.readInt();
			ItemStack gift = pBuffer.readItem();
			float chance = pBuffer.readFloat();
			ElementComponent component = ElementComponent.readFromBuffer(pBuffer);
			return new PreferenceRecipe(pRecipeId, food, likeability, gift, chance, component);
		}

		@Override
		public void toNetwork(FriendlyByteBuf pBuffer, PreferenceRecipe pRecipe) {
			pRecipe.food.toNetwork(pBuffer);
			pBuffer.writeVarInt(pRecipe.likeability);
			pBuffer.writeItem(pRecipe.giftItem);
			pBuffer.writeFloat(pRecipe.giftChance);
			ElementComponent.writeToBuffer(pRecipe.component, pBuffer);
		}

	};	

}
