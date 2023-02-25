package skytheory.mystique.client.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import skytheory.mystique.Mystique;
import skytheory.mystique.client.model.GnomeModel;
import skytheory.mystique.entity.Gnome;

public class GnomeRenderer extends AbstractElementalRenderer<Gnome, GnomeModel> {

	public static ResourceLocation TEXTURE = new ResourceLocation(Mystique.MODID, "textures/entity/gnome.png");

	public GnomeRenderer(EntityRendererProvider.Context ctx) {
		super(ctx, new GnomeModel(ctx.bakeLayer(GnomeModel.LAYER_LOCATION)));
	}
	
	@Override
	public ResourceLocation getTextureLocation(Gnome entity) {
		return TEXTURE;
	}

}
