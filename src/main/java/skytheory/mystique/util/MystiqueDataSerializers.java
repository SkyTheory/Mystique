package skytheory.mystique.util;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import skytheory.mystique.init.MystiqueRegistries;
import skytheory.mystique.item.MystiqueContract;

public class MystiqueDataSerializers {

	public static final EntityDataSerializer<MystiqueContract> SERIALIZER_CONTRACT =
			EntityDataSerializer.simple(
					MystiqueDataSerializers::writeContract,
					MystiqueDataSerializers::readContract);

	static {
		EntityDataSerializers.registerSerializer(SERIALIZER_CONTRACT);
	}
	
	private static void writeContract(FriendlyByteBuf buf, MystiqueContract contract) {
		buf.writeResourceLocation(MystiqueRegistries.CONTRACTS.getKey(contract));
	}

	private static MystiqueContract readContract(FriendlyByteBuf buf) {
		return MystiqueRegistries.CONTRACTS.getValue(buf.readResourceLocation());
	}

}
