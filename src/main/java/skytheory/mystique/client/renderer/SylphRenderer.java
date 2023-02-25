package skytheory.mystique.client.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import skytheory.mystique.Mystique;
import skytheory.mystique.client.model.SylphModel;
import skytheory.mystique.entity.Sylph;

public class SylphRenderer extends AbstractElementalRenderer<Sylph, SylphModel> {

	public static ResourceLocation TEXTURE = new ResourceLocation(Mystique.MODID, "textures/entity/sylph.png");
	
	public SylphRenderer(EntityRendererProvider.Context ctx) {
		super(ctx, new SylphModel(ctx.bakeLayer(SylphModel.LAYER_LOCATION)));
	}

	@Override
	public ResourceLocation getTextureLocation(Sylph entity) {
		return TEXTURE;
	}

}
