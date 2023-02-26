package skytheory.mystique.init;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.entity.schedule.Schedule;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryBuilder;
import skytheory.lib.init.ResourceRegister;
import skytheory.mystique.Mystique;
import skytheory.mystique.client.model.FieldMarkerModel;
import skytheory.mystique.client.model.GnomeModel;
import skytheory.mystique.client.model.SalamanderModel;
import skytheory.mystique.client.model.SylphModel;
import skytheory.mystique.client.model.UndineModel;
import skytheory.mystique.client.renderer.FieldMarkerRenderer;
import skytheory.mystique.client.renderer.GnomeRenderer;
import skytheory.mystique.client.renderer.SalamanderRenderer;
import skytheory.mystique.client.renderer.SylphRenderer;
import skytheory.mystique.client.renderer.UndineRenderer;
import skytheory.mystique.data.BlockModelGenerator;
import skytheory.mystique.data.BlockTagsGenerator;
import skytheory.mystique.data.ContractProvider;
import skytheory.mystique.data.ItemModelGenerator;
import skytheory.mystique.data.LanguageProviderEN;
import skytheory.mystique.data.PreferenceProvider;
import skytheory.mystique.item.MystiqueContract;
import skytheory.mystique.network.MystiqueNetwork;
import skytheory.mystique.util.ElementalUtils;

public class SetupEvent {

	@SubscribeEvent
	public static void newRegistry(NewRegistryEvent event) {
		RegistryBuilder<MystiqueContract> builder = new RegistryBuilder<>();
		builder.setName(MystiqueContract.REGISTRY_LOCATION);
		builder.setDefaultKey(new ResourceLocation(Mystique.MODID, "empty"));
		builder.disableOverrides();
		builder.disableSync();
		builder.onAdd(ElementalAIRegistry::onAdd);
		event.create(builder);
	}

	@SubscribeEvent
	public static void registerEntries(RegisterEvent event) {
		ResourceRegister.registerAll(event, Item.class, ForgeRegistries.Keys.ITEMS, MystiqueItems.class);
		ResourceRegister.registerAll(event, Block.class, ForgeRegistries.Keys.BLOCKS, MystiqueBlocks.class);
		ResourceRegister.registerAll(event, BlockEntityType.class, ForgeRegistries.Keys.BLOCK_ENTITY_TYPES, MystiqueBlockEntities.class);
		ResourceRegister.registerAll(event, EntityType.class, ForgeRegistries.Keys.ENTITY_TYPES, MystiqueEntityTypes.class);
		ResourceRegister.registerAll(event, Activity.class, ForgeRegistries.Keys.ACTIVITIES, MystiqueEntityActivities.class);
		ResourceRegister.registerAll(event, Schedule.class, ForgeRegistries.Keys.SCHEDULES, MystiqueEntitySchedules.class);
		ResourceRegister.registerAll(event, SensorType.class, ForgeRegistries.Keys.SENSOR_TYPES, MystiqueEntitySensors.class);
		ResourceRegister.registerAll(event, RecipeType.class, ForgeRegistries.Keys.RECIPE_TYPES, MystiqueRecipeTypes.class);
		ResourceRegister.registerAll(event, RecipeSerializer.class, ForgeRegistries.Keys.RECIPE_SERIALIZERS, MystiqueRecipeSerializers.class);
		ResourceRegister.registerAll(event, MystiqueContract.class, MystiqueRegistries.Keys.CONTRACTS, MystiqueEntityContracts.class);
	}

	@SubscribeEvent
	public static void modConstruct(FMLConstructModEvent event) {
		MystiqueNetwork.setup();
	}

	@SubscribeEvent
	public static void commonSetup(FMLCommonSetupEvent event) {
	}

	@SubscribeEvent
	public static void clientSetup(FMLClientSetupEvent event) {
	}

	@SubscribeEvent
	public static void enqueueIMC(InterModEnqueueEvent event) {
	}

	@SubscribeEvent
	public static void processIMC(InterModProcessEvent event) {
	}

	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		ExistingFileHelper exFileHelper = event.getExistingFileHelper();
		CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
		generator.addProvider(true, new ItemModelGenerator(generator.getPackOutput(), Mystique.MODID, exFileHelper));
		generator.addProvider(true, new BlockModelGenerator(generator.getPackOutput(), Mystique.MODID, exFileHelper));
		generator.addProvider(true, new LanguageProviderEN(generator.getPackOutput(), Mystique.MODID));
		generator.addProvider(true, new PreferenceProvider(generator.getPackOutput(), Mystique.MODID, exFileHelper));
		generator.addProvider(true, new ContractProvider(generator.getPackOutput(), Mystique.MODID, exFileHelper));
		generator.addProvider(true, new BlockTagsGenerator(generator.getPackOutput(), lookupProvider, Mystique.MODID,exFileHelper));
	}

	@SubscribeEvent
	public static void registerCreativeModeTabs(CreativeModeTabEvent.Register event) {
		event.registerCreativeModeTab(new ResourceLocation(Mystique.MODID, "main"), MystiqueCreattiveModeTabs::buildMainTab);
	}

	@SubscribeEvent	
	public static void entityAttributeCreation(EntityAttributeCreationEvent event) {
		event.put(MystiqueEntityTypes.GNOME, ElementalUtils.createElementalAttributeSupplier());
		event.put(MystiqueEntityTypes.SALAMANDER, ElementalUtils.createElementalAttributeSupplier());
		event.put(MystiqueEntityTypes.SYLPH, ElementalUtils.createElementalAttributeSupplier());
		event.put(MystiqueEntityTypes.UNDINE, ElementalUtils.createElementalAttributeSupplier());
	}

	@SubscribeEvent
	public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(MystiqueEntityTypes.GNOME, GnomeRenderer::new);
		event.registerEntityRenderer(MystiqueEntityTypes.SALAMANDER, SalamanderRenderer::new);
		event.registerEntityRenderer(MystiqueEntityTypes.SYLPH, SylphRenderer::new);
		event.registerEntityRenderer(MystiqueEntityTypes.UNDINE, UndineRenderer::new);
		event.registerEntityRenderer(MystiqueEntityTypes.FIELD_MARKER, FieldMarkerRenderer::new);
	}

	@SubscribeEvent
	public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(GnomeModel.LAYER_LOCATION, GnomeModel::createBodyLayer);
		event.registerLayerDefinition(SalamanderModel.LAYER_LOCATION, SalamanderModel::createBodyLayer);
		event.registerLayerDefinition(SylphModel.LAYER_LOCATION, SylphModel::createBodyLayer);
		event.registerLayerDefinition(UndineModel.LAYER_LOCATION, UndineModel::createBodyLayer);
		event.registerLayerDefinition(FieldMarkerModel.LAYER_LOCATION, FieldMarkerModel::createBodyLayer);
	}
}
