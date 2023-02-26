package skytheory.mystique.entity;

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
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PacketDistributor;
import skytheory.lib.block.DataSync;
import skytheory.lib.capability.itemhandler.ItemHandler;
import skytheory.lib.capability.itemhandler.ItemHandlerListener;
import skytheory.lib.network.EntityMessage;
import skytheory.lib.network.SkyTheoryLibNetwork;
import skytheory.lib.util.ItemHandlerUtils;
import skytheory.mystique.entity.ai.ElementalAI;
import skytheory.mystique.recipe.PreferenceRecipe;

public class AbstractElemental extends PathfinderMob implements DataSync, ItemHandlerListener, IEntityAdditionalSpawnData {

	public static final float HITBOX_WIDTH = 0.5f;
	public static final float HITBOX_HEIGHT = 1.40625f;
	public static final float EYE_HEIGHT_BASE = 1.171875f;
	public static final float EYE_HEIGHT_MODIFIER = EYE_HEIGHT_BASE / HITBOX_HEIGHT;

	public static final int SLOT_CONTRACT = 0;
	public static final int SLOT_FOOD = 1;

	public static final EntityDataAccessor<Boolean> DATA_IS_EATING = SynchedEntityData.defineId(AbstractElemental.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Integer> DATA_EATING_TICKS = SynchedEntityData.defineId(AbstractElemental.class, EntityDataSerializers.INT);

	protected ItemHandler itemHandler;

	protected AbstractElemental(EntityType<? extends AbstractElemental> p_21368_, Level p_21369_) {
		super(p_21368_, p_21369_);
		this.itemHandler = new ItemHandler(2);
		this.itemHandler.addListener(this);
	}

	protected Brain.Provider<AbstractElemental> brainProvider() {
		return ElementalAI.brainProvider();
	}

	protected Brain<?> makeBrain(Dynamic<?> pDynamic) {
		Brain<AbstractElemental> brain = (this.brainProvider().makeBrain(pDynamic));
		if (!this.level.isClientSide) {
			ElementalAI.initBrain(brain);
		}
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
		super.customServerAiStep();
	}

	@Override
	protected void defineSynchedData() {
		this.entityData.define(DATA_IS_EATING, false);
		this.entityData.define(DATA_EATING_TICKS, 0);
		super.defineSynchedData();
	}

	@Override
	public void addAdditionalSaveData(CompoundTag pCompound) {
		pCompound.putBoolean("Eating", this.entityData.get(DATA_IS_EATING));
		pCompound.putInt("EatingTicks", this.entityData.get(DATA_EATING_TICKS));
		pCompound.put("ElementalItems", this.itemHandler.serializeNBT());
		super.addAdditionalSaveData(pCompound);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag pCompound) {
		this.entityData.set(DATA_IS_EATING, pCompound.getBoolean("Eating"));
		this.entityData.set(DATA_EATING_TICKS, pCompound.getInt("EatingTicks"));
		this.itemHandler.deserializeNBT(pCompound.getCompound("ElementalItems"));
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
	 * 距離が離れたことによる自然デスポーンを無効化
	 */
	@Override
	public boolean isPersistenceRequired() {
		return true;
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
		if (pHand == InteractionHand.MAIN_HAND) {
			ItemStack stack = pPlayer.getItemInHand(InteractionHand.MAIN_HAND).copy();
			Optional<PreferenceRecipe> recipe = PreferenceRecipe.getRecipe(this, stack);
			if (recipe.isPresent() && this.itemHandler.getStackInSlot(SLOT_FOOD).isEmpty()) {
				setEatingItem(stack.split(1));
				if (!pPlayer.isCreative()) {
					if (stack.isEmpty()) stack = ItemStack.EMPTY;
					pPlayer.setItemInHand(InteractionHand.MAIN_HAND, stack);
				}
			}
		}
		return super.mobInteract(pPlayer, pHand);
	}

	/*
	 * Tick関連
	 */

	@Override
	public void tick() {
		super.tick();
	}

	public boolean canBeTempted() {
		return !this.isEatingItem();
	}

	public ItemStack getContractItem() {
		return this.itemHandler.getStackInSlot(SLOT_CONTRACT);
	}

	public void setContractItem(ItemStack stack) {
		this.itemHandler.setStackInSlot(SLOT_CONTRACT, stack);
	}

	public boolean isTemptingItem(ItemStack stack) {
		Optional<PreferenceRecipe> recipe = PreferenceRecipe.getRecipe(this, stack);
		return recipe.isPresent();
	}

	public ItemStack getEatingItem() {
		return this.itemHandler.getStackInSlot(SLOT_FOOD);
	}

	public void setEatingItem(ItemStack stack) {
		ItemHandlerUtils.setStackInSlot(itemHandler, SLOT_FOOD, stack);
	}

	public boolean isEatingItem() {
		return this.entityData.get(DATA_IS_EATING);
	}

	public CompoundTag writeSyncTag() {
		return this.itemHandler.serializeNBT();
	}

	@Override
	public void readSyncTag(CompoundTag tag) {
		this.itemHandler.deserializeNBT(tag);
	}

	@Override
	public void onItemHandlerChanged(IItemHandler handler, int slot) {
		if (!this.level.isClientSide()) {
			SkyTheoryLibNetwork.INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> this), new EntityMessage(this));
		}
	}

	/*
	 * 覚書：IEntityAdditionalSpawnDataを利用するためには、getAddEntityPacketをオーバーライドする必要がある
	 */

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	public void writeSpawnData(FriendlyByteBuf buffer) {
		buffer.writeNbt(this.writeSyncTag());
	}

	@Override
	public void readSpawnData(FriendlyByteBuf additionalData) {
		this.readSyncTag(additionalData.readNbt());
	}

}