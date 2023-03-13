package skytheory.mystique.init;

import skytheory.mystique.entity.ai.contract.CollectContract;
import skytheory.mystique.entity.ai.contract.EatItemContract;
import skytheory.mystique.entity.ai.contract.InteractingPlayerContract;
import skytheory.mystique.entity.ai.contract.MystiqueContract;

public class MystiqueContracts {

	/**
	 * Default value
	 */
	public static final MystiqueContract DEFAULT = MystiqueContract.DEFAULT;
	
	public static final MystiqueContract EAT = new EatItemContract();
	public static final MystiqueContract INTERACTING = new InteractingPlayerContract();
	public static final MystiqueContract COLLECT = new CollectContract();
	
}
