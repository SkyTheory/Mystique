package skytheory.mystique.client.model.pose;

import skytheory.mystique.client.model.AbstractElementalModel;
import skytheory.mystique.entity.AbstractElemental;

@FunctionalInterface
public interface ElementalPoseTransformer {

	void transform(AbstractElementalModel<? extends AbstractElemental> model, ElementalRenderContext ctx);
	
}
