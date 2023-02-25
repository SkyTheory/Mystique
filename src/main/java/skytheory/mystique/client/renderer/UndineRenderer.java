package skytheory.mystique.client.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import skytheory.mystique.Mystique;
import skytheory.mystique.client.model.UndineModel;
import skytheory.mystique.entity.Undine;

public class UndineRenderer extends AbstractElementalRenderer<Undine, UndineModel> {

	public static ResourceLocation TEXTURE = new ResourceLocation(Mystique.MODID, "textures/entity/undine.png");

	public UndineRenderer(EntityRendererProvider.Context ctx) {
		super(ctx, new UndineModel(ctx.bakeLayer(UndineModel.LAYER_LOCATION)));
	}

	@Override
	public ResourceLocation getTextureLocation(Undine entity) {
		return TEXTURE;
	}

}
