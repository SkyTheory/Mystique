package skytheory.mystique.capability;

/**
 * マナの消費時に不足する属性分を他の属性のマナを2倍消費することで補うManaHandler
 */
public class GenericManaHandler extends ManaHandlerBase {
	
	public GenericManaHandler(int capacity) {
		super(capacity);
	}
	
	@Override
	public boolean consume(ElementComponent cost, ManaHandlerMode mode) {
		if (this.has(cost)) {
			if (mode == ManaHandlerMode.EXECUTE) {
				this.sub(cost);
			}
			return true;
		}
		ElementComponent primary = new ElementComponent();
		int remain = 0;
		for (ElementQuality quality : ElementQuality.values()) {
			primary.setAmount(quality, Math.min(cost.getAmount(quality), this.getAmount(quality)));
			remain += Math.max(0, cost.getAmount(quality) - this.getAmount(quality));
		}
		ElementComponent forSimulate = this.copy();
		forSimulate.sub(primary);
		int penalty = penalize(remain);
		int remainTotal = forSimulate.getTotal();
		if (penalty > remainTotal) return false;
		ElementComponent secondary = new ElementComponent();
		for (ElementQuality quality : ElementQuality.values()) {
			if (forSimulate.getAmount(quality) > 0) {
				float qPenalty = (float) penalty * (float) forSimulate.getAmount(quality) / (float) remainTotal;
				qPenalty = Math.min(qPenalty, 1.0f);
				secondary.add(quality, (int) qPenalty);
			}
		}
		if (mode == ManaHandlerMode.EXECUTE) {
			this.sub(primary);
			this.sub(secondary);
		}
		return true;
	}
	
	public int penalize(int base) {
		return base *= 2;
	}
	
}