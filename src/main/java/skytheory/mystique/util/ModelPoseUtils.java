package skytheory.mystique.util;

import org.joml.Vector3f;

import net.minecraft.client.model.geom.ModelPart;

public class ModelPoseUtils {

	public static void mirrorMotion(ModelPart model1, ModelPart model2, Vector3f vec3f) {
		mirrorMotion(model1, model2, vec3f, 1.0f);
	}

	public static void mirrorMotion(ModelPart model1, ModelPart model2, Vector3f vec3f, float multiplier) {
		vec3f = new Vector3f(vec3f);
		vec3f.mul(multiplier);
		Vector3f mirror = new Vector3f(vec3f);
		mirror.negate();
		model1.offsetRotation(vec3f);
		model2.offsetRotation(mirror);
	}
}
