package skytheory.mystique.block;

import java.util.function.Function;
import java.util.function.Supplier;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import skytheory.lib.block.BEWLRBlock;
import skytheory.lib.block.TickerEntityBlock;
import skytheory.mystique.Mystique;
import skytheory.mystique.client.model.ManaInfuserModel;

public class ManaInfuserBlock extends Block implements TickerEntityBlock, BEWLRBlock {

	public static final ResourceLocation TEXTURE = new ResourceLocation(Mystique.MODID, "textures/entity/mana_infuser.png");

	public ManaInfuserBlock(Properties pProperties) {
		super(pProperties);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
		return new ManaInfuserEntity(pPos, pState);
	}

	@Override
	public Function<ModelPart, Model> getModelProvider() {
		return ManaInfuserModel::new;
	}

	@Override
	public Supplier<LayerDefinition> getLayerDefinitionProvider() {
		return ManaInfuserModel::createBodyLayer;
	}

	@Override
	public ResourceLocation getTextureLocation() {
		return TEXTURE;
	}

}
