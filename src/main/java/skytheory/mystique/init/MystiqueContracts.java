package skytheory.mystique.init;

import skytheory.mystique.entity.ai.contract.CollectContract;
import skytheory.mystique.entity.ai.contract.CoreContract;
import skytheory.mystique.entity.ai.contract.DefaultContract;
import skytheory.mystique.entity.ai.contract.EatItemContract;
import skytheory.mystique.entity.ai.contract.InteractingPlayerContract;
import skytheory.mystique.entity.ai.contract.MystiqueContract;

public class MystiqueContracts {

	public static final MystiqueContract CORE = new CoreContract();
	public static final MystiqueContract DEFAULT = new DefaultContract();
	public static final MystiqueContract EAT = new EatItemContract();
	public static final MystiqueContract INTERACTING = new InteractingPlayerContract();
	public static final MystiqueContract COLLECT = new CollectContract();
	
}
