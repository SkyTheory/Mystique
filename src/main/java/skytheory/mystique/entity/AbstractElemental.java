package skytheory.mystique.entity;

import java.util.Comparator;
import java.util.Optional;

import com.mojang.serialization.Dynamic;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.network.NetworkHooks;
import skytheory.lib.block.DataSync;
import skytheory.lib.capability.itemhandler.InventoryHandler;
import skytheory.lib.capability.itemhandler.ItemHandlerListener;
import skytheory.lib.network.EntityMessage;
import skytheory.lib.network.SkyTheoryLibNetwork;
import skytheory.lib.util.ItemHandlerStream;
import skytheory.lib.util.ItemHandlerStream.ItemHandlerSlot;
import skytheory.lib.util.ItemHandlerUtils;
import skytheory.mystique.container.ElementalContainerMenu;
import skytheory.mystique.entity.ai.ElementalAI;
import skytheory.mystique.init.MystiqueContracts;
import skytheory.mystique.init.MystiqueRegistries;
import skytheory.mystique.item.MystiqueContract;
import skytheory.mystique.recipe.PreferenceRecipe;

public class AbstractElemental extends PathfinderMob implements MenuProvider, DataSync, ItemHandlerListener, IEntityAdditionalSpawnData {

	public static final float HITBOX_WIDTH = 0.5f;
	public static final float HITBOX_HEIGHT = 1.40625f;
	public static final float EYE_HEIGHT_BASE = 1.171875f;
	public static final float EYE_HEIGHT_MODIFIER = EYE_HEIGHT_BASE / HITBOX_HEIGHT;

	public static final int SLOT_CONTRACT = 0;
	public static final int SLOT_FOOD = 1;
	public static final int FILTER_SLOTS = 5;

	public static final EntityDataAccessor<Boolean> DATA_IS_EATING = SynchedEntityData.defineId(AbstractElemental.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Integer> DATA_EATING_TICKS = SynchedEntityData.defineId(AbstractElemental.class, EntityDataSerializers.INT);

	private InventoryHandler itemHandler;
	private InventoryHandler filters;
	private Player interactingPlayer;

	protected AbstractElemental(EntityType<? extends AbstractElemental> p_21368_, Level p_21369_) {
		super(p_21368_, p_21369_);
		this.itemHandler = new InventoryHandler(2);
		this.itemHandler.addListener(this);
		this.filters = new InventoryHandler(FILTER_SLOTS);
		this.filters.addListener(this);
		this.setPersistenceRequired();
	}

	protected Brain.Provider<AbstractElemental> brainProvider() {
		return ElementalAI.brainProvider();
	}

	protected Brain<?> makeBrain(Dynamic<?> pDynamic) {
		Brain<AbstractElemental> brain = (this.brainProvider().makeBrain(pDynamic));
		ElementalAI.initBrain(this, brain);
		return brain;
	}

	@SuppressWarnings("unchecked")
	public Brain<AbstractElemental> getBrain() {
		return (Brain<AbstractElemental>) super.getBrain();
	}

	protected PathNavigation createNavigation(Level pLevel) {
		PathNavigation navigation = super.createNavigation(pLevel);
		navigation.setCanFloat(true);
		return navigation;
	}

	protected void customServerAiStep() {
		this.getBrain().tick((ServerLevel)this.level, this);
	}

	@Override
	protected void defineSynchedData() {
		this.entityData.define(DATA_IS_EATING, false);
		this.entityData.define(DATA_EATING_TICKS, 0);
		super.defineSynchedData();
	}

	@Override
	public void addAdditionalSaveData(CompoundTag pCompound) {
		pCompound.putInt("EatingTicks", this.entityData.get(DATA_EATING_TICKS));
		pCompound.put("ElementalItems", this.itemHandler.serializeNBT());
		pCompound.put("ItemFilters", this.filters.serializeNBT());
		super.addAdditionalSaveData(pCompound);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag pCompound) {
		this.entityData.set(DATA_EATING_TICKS, pCompound.getInt("EatingTicks"));
		this.itemHandler.deserializeNBT(pCompound.getCompound("ElementalItems"));
		this.filters.deserializeNBT(pCompound.getCompound("ItemFilters"));
		super.readAdditionalSaveData(pCompound);
	}

	@Override
	public float getEyeHeight(Pose pose) {
		return this.getDimensions(pose).height * EYE_HEIGHT_MODIFIER;
	}

	@Override
	public double getEyeY() {
		return this.getY() + (double)this.getEyeHeight();
	}

	@Override
	public float getSpeed() {
		return super.getSpeed() * getScale();
	}

	@Override
	public float getScale() {
		return 0.5f;
	}

	@Override
	public double getFluidJumpThreshold() {
		return 0.2d;
	}

	/**
	 * 正の走光性を持つように指定
	 */
	public float getWalkTargetValue(BlockPos pPos, LevelReader pLevel) {
		return pLevel.getPathfindingCostFromLightLevels(pPos);
	}

	/*
	 * 右クリック時の処理
	 */

	@Override
	protected InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
		if (!this.level.isClientSide() && pPlayer instanceof ServerPlayer serverPlayer) {
			if (pHand == InteractionHand.MAIN_HAND) {
				// Contractの右クリック処理
				InteractionResult contractInteractionResult = this.getContract().interaction(this, pPlayer);
				if (contractInteractionResult.consumesAction()) return contractInteractionResult;
				// 食べ物アイテムを与える処理
				InteractionResult feedingInteractionResult = this.feeding(pPlayer);
				if (feedingInteractionResult.consumesAction()) return feedingInteractionResult;
				// GUIを開く
				this.openGUI(serverPlayer);
				return InteractionResult.CONSUME;
			}
		}
		return super.mobInteract(pPlayer, pHand);
	}

	public InteractionResult feeding(Player pPlayer) {
		ItemStack stack = pPlayer.getItemInHand(InteractionHand.MAIN_HAND).copy();
		Optional<PreferenceRecipe> recipe = PreferenceRecipe.getRecipe(this, stack);
		if (recipe.isPresent() && this.itemHandler.getStackInSlot(SLOT_FOOD).isEmpty()) {
			setEatingItem(stack.split(1));
			if (!pPlayer.isCreative()) {
				if (stack.isEmpty()) stack = ItemStack.EMPTY;
				pPlayer.setItemInHand(InteractionHand.MAIN_HAND, stack);
			}
			return InteractionResult.CONSUME;
		}
		return InteractionResult.PASS;
	}

	public void openGUI(ServerPlayer pPlayer) {
		NetworkHooks.openScreen(pPlayer, this, buf -> buf.writeInt(this.getId()));
		this.setInteractingPlayer(pPlayer);
	}

	/*
	 * GUI関連
	 */

	@Override
	public ElementalContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
		return new ElementalContainerMenu(pContainerId, pPlayerInventory, pPlayer, this);
	}

	public void setInteractingPlayer(Player player) {
		this.interactingPlayer = player;
	}

	public void resetInteractingPlayer() {
		setInteractingPlayer(null);
	}

	public Player getInteractingPlayer() {
		return interactingPlayer;
	}

	public boolean isInteractingByPlayer() {
		return interactingPlayer != null;
	}

	/*
	 * Tick関連
	 */

	@Override
	public void tick() {
		if (!this.level.isClientSide()) {
//			LogUtils.getLogger().debug(MystiqueRegistries.CONTRACTS.getKey(getContract()).toString());
		}
		super.tick();
	}

	/*
	 * AI関連
	 */

	public boolean canBeTempted() {
		return !this.isEatingItem();
	}

	public boolean isTemptingItem(ItemStack stack) {
		Optional<PreferenceRecipe> recipe = PreferenceRecipe.getRecipe(this, stack);
		return recipe.isPresent();
	}

	public void setEatingItem(ItemStack stack) {
		ItemHandlerUtils.setStackInSlot(itemHandler, SLOT_FOOD, stack);
	}

	public boolean isEatingItem() {
		return this.entityData.get(DATA_IS_EATING);
	}

	public ItemStack getEatingItem() {
		return this.itemHandler.getStackInSlot(SLOT_FOOD);
	}

	protected void pickUpItem(ItemEntity pItemEntity) {
	}

	public boolean isValidItem(ItemStack stack) {
		if (ItemHandlerUtils.isEmpty(filters)) return true;
		return ItemHandlerStream.create(filters).map(ItemHandlerSlot::getStackInSlot).anyMatch(stack::sameItem);
	}

	/*
	 * Contract関連
	 */

	public ItemStack getContractItem() {
		return this.itemHandler.getStackInSlot(SLOT_CONTRACT);
	}

	public void setContractItem(ItemStack stack) {
		this.itemHandler.setStackInSlot(SLOT_CONTRACT, stack);
	}

	public MystiqueContract getContract() {
		return MystiqueRegistries.CONTRACTS.getValues().stream()
				.sorted(Comparator.comparingInt(MystiqueContract::getPriority))
				.filter(c -> c.canApplyContract(this))
				.findFirst().orElse(MystiqueContracts.DEFAULT);
	}

	/*
	 * ItemHandler関連
	 */

	public InventoryHandler getMainInventory() {
		return this.itemHandler;
	}

	public InventoryHandler getFilterInventory() {
		return this.filters;
	}

	/*
	 * データ同期関連：インベントリ操作時
	 */

	public CompoundTag writeSyncTag() {
		CompoundTag tag = new CompoundTag();
		tag.put("Items", itemHandler.serializeNBT());
		tag.put("Filters", filters.serializeNBT());
		return tag;
	}

	@Override
	public void readSyncTag(CompoundTag tag) {
		this.itemHandler.deserializeNBT(tag.getCompound("Items"));
		this.filters.deserializeNBT(tag.getCompound("Filters"));
	}

	@Override
	public void onItemHandlerChanged(IItemHandler handler, int slot) {
		if (!this.level.isClientSide()) {
			SkyTheoryLibNetwork.sendToClient(this, new EntityMessage(this));
		}
	}

	/*
	 * データ同期関連：スポーン時
	 */

	/** 覚書：IEntityAdditionalSpawnDataを利用するためには、getAddEntityPacketをオーバーライドする必要がある */
	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	public void writeSpawnData(FriendlyByteBuf buffer) {
		buffer.writeNbt(this.itemHandler.serializeNBT());
		buffer.writeNbt(this.filters.serializeNBT());
	}

	@Override
	public void readSpawnData(FriendlyByteBuf additionalData) {
		this.itemHandler.deserializeNBT(additionalData.readNbt());
		this.filters.deserializeNBT(additionalData.readNbt());
	}

}