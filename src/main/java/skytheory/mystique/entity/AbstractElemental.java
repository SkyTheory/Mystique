package skytheory.mystique.entity;

import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;

import com.mojang.serialization.Dynamic;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
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
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;
import skytheory.lib.block.DataSync;
import skytheory.lib.capability.itemhandler.InventoryHandler;
import skytheory.lib.network.EntityMessage;
import skytheory.lib.network.SkyTheoryLibNetwork;
import skytheory.lib.util.ItemHandlerStream;
import skytheory.lib.util.ItemHandlerStream.ItemHandlerSlot;
import skytheory.lib.util.ItemHandlerUtils;
import skytheory.mystique.entity.ai.ElementalAI;
import skytheory.mystique.entity.ai.contract.MystiqueContract;
import skytheory.mystique.gui.ElementalContainerMenu;
import skytheory.mystique.init.ContractManager;
import skytheory.mystique.init.MystiqueContracts;
import skytheory.mystique.init.MystiqueRegistries;
import skytheory.mystique.recipe.PreferenceRecipe;
import skytheory.mystique.util.MystiqueDataSerializers;

public class AbstractElemental extends PathfinderMob implements MenuProvider, DataSync, IEntityAdditionalSpawnData {

	public static final float HITBOX_WIDTH = 0.5f;
	public static final float HITBOX_HEIGHT = 1.40625f;
	public static final float EYE_HEIGHT_BASE = 1.171875f;
	public static final float EYE_HEIGHT_MODIFIER = EYE_HEIGHT_BASE / HITBOX_HEIGHT;

	public static final int SLOT_CONTRACT = 0;
	public static final int FILTER_SLOTS = 5;

	public static final EntityDataAccessor<MystiqueContract> DATA_CONTRACT = SynchedEntityData.defineId(AbstractElemental.class, MystiqueDataSerializers.SERIALIZER_CONTRACT);

	private InventoryHandler itemHandler;
	private InventoryHandler filters;
	private Optional<Player> interactingPlayer;

	protected AbstractElemental(EntityType<? extends AbstractElemental> p_21368_, Level p_21369_) {
		super(p_21368_, p_21369_);
		this.itemHandler = new InventoryHandler(1);
		this.itemHandler.addChangedListener(this::dataSync);
		this.filters = new InventoryHandler(FILTER_SLOTS);
		this.filters.addChangedListener(this::dataSync);
		this.interactingPlayer = Optional.empty();
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
		this.entityData.define(DATA_CONTRACT, MystiqueContracts.CORE);
		MystiqueRegistries.CONTRACTS.getEntries().stream()
		.sorted(Comparator.comparing(entry -> entry.getKey().location()))
		.map(Entry::getValue)
		.map(MystiqueContract::createDataSyncChannel)
		.flatMap(List::stream)
		.distinct()
		.forEach(this::defineFromEntry);
		super.defineSynchedData();
	}
	
	private <T> void defineFromEntry(MystiqueContract.EntityDataEntry<T> entry) {
		this.entityData.define(entry.accessor(), entry.initialValue());
	}

	@Override
	public void addAdditionalSaveData(CompoundTag pCompound) {
		pCompound.put("ElementalItems", this.itemHandler.serializeNBT());
		pCompound.put("ItemFilters", this.filters.serializeNBT());
		CompoundTag contractNbt = new CompoundTag();
		MystiqueRegistries.CONTRACTS.getEntries().forEach(entry -> {
			CompoundTag data = new CompoundTag();
			entry.getValue().saveAdditionalData(this, data);
			if (!data.isEmpty()) {
				contractNbt.put(entry.getKey().location().toString(), data);
			}
		});
		pCompound.put("ContractData", contractNbt);
		super.addAdditionalSaveData(pCompound);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag pCompound) {
		this.itemHandler.deserializeNBT(pCompound.getCompound("ElementalItems"));
		this.filters.deserializeNBT(pCompound.getCompound("ItemFilters"));
		CompoundTag contractNbt = pCompound.getCompound("ContractData");
		MystiqueRegistries.CONTRACTS.getEntries().forEach(entry -> {
			entry.getValue().loadAdditionalData(this, contractNbt.getCompound(entry.getKey().location().toString()));
		});
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
				MystiqueContract activeContract = this.getContract();
				// Contractの右クリック処理
				InteractionResult result1 = activeContract.activeInteract(this, pPlayer);
				if (result1.consumesAction()) return result1;
				for (MystiqueContract contract : ContractManager.getAllContracts()) {
					if (contract == activeContract) continue;
					InteractionResult result2 = contract.nonActiveInteract(this, pPlayer);
					if (result2.consumesAction()) return result2;
				}
				// GUIを開く
				this.openGUI(serverPlayer);
				return InteractionResult.CONSUME;
			}
		}
		return super.mobInteract(pPlayer, pHand);
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
		this.interactingPlayer = Optional.of(player);
	}

	public void resetInteractingPlayer() {
		this.interactingPlayer = Optional.empty();
	}

	public Optional<Player> getInteractingPlayer() {
		return interactingPlayer;
	}

	public boolean isInteractingByPlayer() {
		return interactingPlayer.isPresent();
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

	public boolean isTemptingItem(ItemStack stack) {
		Optional<PreferenceRecipe> recipe = PreferenceRecipe.getRecipe(this, stack);
		return recipe.isPresent();
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

	public void setContract(MystiqueContract contract) {
		this.entityData.set(DATA_CONTRACT, contract);
	}

	public MystiqueContract getContract() {
		return this.entityData.get(DATA_CONTRACT);
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

	public void dataSync() {
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