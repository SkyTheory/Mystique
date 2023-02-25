package skytheory.mystique.client.model;

import skytheory.lib.util.FloatUtils;

public class RotateParameters {

	// Walk Parameters
	public static final float ARM_SWING_AMOUNT = FloatUtils.toRadian(60.0f);
	public static final float LEG_SWING_AMOUNT = FloatUtils.toRadian(65.0f);
	public static final float LIMB_SWING_WEIGHT = 0.85f;

	// Sit Parameters
	public static final float SIT_ARM_X = FloatUtils.toRadian(-30.0f);
	public static final float SIT_ARM_Y = 0.0f;
	public static final float SIT_ARM_Z = 0.0f;
	public static final float SIT_LEG_X = FloatUtils.toRadian(-90.0f);
	public static final float SIT_LEG_Y = FloatUtils.toRadian(20.0f);
	public static final float SIT_LEG_Z = FloatUtils.toRadian(4.5f);

	// Swing Parameters
	public static final float SWING_START_X = FloatUtils.toRadian(-80.0f);
	public static final float SWING_PEAK_X = FloatUtils.toRadian(-120.0f);
	public static final float SWING_END_X = FloatUtils.toRadian(-40.0f);

	public static final float SWING_BASE_Y = FloatUtils.toRadian(-5.0f);
	public static final float SWING_RANGE_Y = FloatUtils.toRadian(-5.0f);

	public static final float SWING_BASE_Z = FloatUtils.toRadian(20.0f);
	public static final float SWING_RANGE_Z = FloatUtils.toRadian(25.0f);

	public static final float SWING_TURN = 0.3f;
	public static final float TWIST_AMOUNT = FloatUtils.toRadian(10.0f);
}