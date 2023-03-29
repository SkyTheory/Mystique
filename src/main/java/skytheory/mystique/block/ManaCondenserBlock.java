package skytheory.mystique.block;

import java.util.function.Function;
import java.util.function.Supplier;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import skytheory.lib.block.BEWLRBlock;
import skytheory.mystique.Mystique;
import skytheory.mystique.blockentity.ManaCondenserEntity;
import skytheory.mystique.client.model.ManaCondenserModel;

public class ManaCondenserBlock extends Block implements EntityBlock, BEWLRBlock {

	public static final ResourceLocation TEXTURE = new ResourceLocation(Mystique.MODID, "textures/entity/mana_condenser.png");
	
	public ManaCondenserBlock(Properties pProperties) {
		super(pProperties);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
		return new ManaCondenserEntity(pPos, pState);
	}

	@Override
	public Function<ModelPart, Model> getModelProvider() {
		return ManaCondenserModel::new;
	}

	@Override
	public Supplier<LayerDefinition> getLayerDefinitionProvider() {
		return ManaCondenserModel::createBodyLayer;
	}

	@Override
	public ResourceLocation getTextureLocation() {
		return TEXTURE;
	}

}
