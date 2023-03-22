package skytheory.mystique.init;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import skytheory.mystique.block.ManaChannelerBlock;
import skytheory.mystique.block.ManaInfuserBlock;
import skytheory.mystique.block.WallShelfBlock;
import skytheory.mystique.block.WritingChestBlock;
import skytheory.mystique.block.WritingTableBlock;

public class MystiqueBlocks {
	
	public static final Block ORE_STONE_FIRE = new Block(stoneProperties());
	public static final Block ORE_STONE_AIR = new Block(stoneProperties());
	public static final Block ORE_STONE_WATER = new Block(stoneProperties());
	public static final Block ORE_STONE_EARTH = new Block(stoneProperties());

	public static final Block ORE_DEEPSLATE_FIRE = new Block(deepslateProperties());
	public static final Block ORE_DEEPSLATE_AIR = new Block(deepslateProperties());
	public static final Block ORE_DEEPSLATE_WATER = new Block(deepslateProperties());
	public static final Block ORE_DEEPSLATE_EARTH = new Block(deepslateProperties());

	public static final Block DEVICE_MANA_CHANNELER = new ManaChannelerBlock(metalDeviceProperties());
	public static final Block DEVICE_MANA_INFUSER = new ManaInfuserBlock(metalDeviceProperties());
	public static final Block DEVICE_WRITING_TABLE = new WritingTableBlock(woodenDeviceProperties());
	public static final Block DEVICE_WRITING_CHEST = new WritingChestBlock(woodenDeviceProperties());
	public static final Block DEVICE_WALL_BOOKSHELF = new WallShelfBlock(woodenDeviceProperties());
	
	public static BlockBehaviour.Properties stoneProperties() {
		return BlockBehaviour.Properties
				.of(Material.STONE)
				.requiresCorrectToolForDrops()
				.strength(3.0f, 3.0f)
				.sound(SoundType.STONE);
	}
	
	public static BlockBehaviour.Properties deepslateProperties() {
		return BlockBehaviour.Properties
				.of(Material.STONE)
				.requiresCorrectToolForDrops()
				.strength(4.5f, 3.0f)
				.sound(SoundType.DEEPSLATE);
	}

	public static BlockBehaviour.Properties woodenDeviceProperties(){
		return BlockBehaviour.Properties
				.of(Material.WOOD)
				.strength(3.0f,  3.0f)
				.sound(SoundType.WOOD)
				.noOcclusion();
	}
	
	public static BlockBehaviour.Properties metalDeviceProperties(){
		return BlockBehaviour.Properties
				.of(Material.METAL)
				.strength(3.0f,  3.0f)
				.sound(SoundType.STONE)
				.noOcclusion();
	}
	
}
