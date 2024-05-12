package xyz.aikoyori.backatitagain;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityType;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.StonecutterBlock;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.GameRules;
import xyz.aikoyori.backatitagain.contents.blocks.LegacyStonecutterBlock;
import xyz.aikoyori.backatitagain.contents.blocks.withentities.netherreactor.NetherReactorCoreBlock;
import xyz.aikoyori.backatitagain.contents.blocks.withentities.netherreactor.NetherReactorCoreBlockEntity;
import xyz.aikoyori.backatitagain.utils.RegistryHelper;

public class BackAtItAgain implements ModInitializer {

    public static final NetherReactorCoreBlock NETHER_REACTOR_CORE = new NetherReactorCoreBlock(Block.Settings.create().hardness(3.0f).resistance(6.0f));
    public static final BlockEntityType<NetherReactorCoreBlockEntity> NETHER_REACTOR_CORE_BLOCK_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
    RegistryHelper.makeID("nether_reactor_core_block_entity"),
            BlockEntityType.Builder.create(NetherReactorCoreBlockEntity::new,NETHER_REACTOR_CORE).build());
    public static final Block GLOWING_OBSIDIAN = new Block(Block.Settings.create().hardness(50.0f).resistance(1200.0f).luminance(value -> 12));
    public static final LegacyStonecutterBlock STONECUTTER_LEGACY = new LegacyStonecutterBlock(Block.Settings.create().hardness(3.5f).resistance(3.5f));
    public static final Block UPDATE_BLOCK = new Block(Block.Settings.create().hardness(0.5f).resistance(0.5f));
    public static final Block UPDATE_BLOCK2 = new Block(Block.Settings.create().hardness(0.5f).resistance(0.5f));
    private static final ItemGroup RETRO_ITEM_GROUP = FabricItemGroup.builder()
            .icon(()->new ItemStack(GLOWING_OBSIDIAN))
            .displayName(Text.translatable("itemGroup.backatitagain.retro"))
            .entries((displayContext, entries) -> {
                entries.add(NETHER_REACTOR_CORE);
                entries.add(GLOWING_OBSIDIAN);
                entries.add(STONECUTTER_LEGACY);
                entries.add(UPDATE_BLOCK);
                entries.add(UPDATE_BLOCK2);
            }).build();

    public static final Identifier NETHER_LOOT_TABLE = RegistryHelper.makeID("gameplay/reactor_spawns");
    public static final GameRules.Key<GameRules.BooleanRule> SHOULD_SPAWN_SPIRE =
            GameRuleRegistry.register("backAtItAgain.doSpireSpawn", GameRules.Category.MISC, GameRuleFactory.createBooleanRule(false));
    public static final GameRules.Key<GameRules.BooleanRule> SHOULD_SPIRE_REPLACE_AIR    =
            GameRuleRegistry.register("backAtItAgain.shouldSpireSpawnReplaceAir", GameRules.Category.MISC, GameRuleFactory.createBooleanRule(false));
    @Override
    public void onInitialize() {
        Registry.register(Registries.BLOCK, RegistryHelper.makeID("nether_reactor_core"),NETHER_REACTOR_CORE);
        Registry.register(Registries.ITEM, RegistryHelper.makeID("nether_reactor_core"),new BlockItem(NETHER_REACTOR_CORE, new FabricItemSettings()));
        Registry.register(Registries.BLOCK, RegistryHelper.makeID("glowing_obsidian"),GLOWING_OBSIDIAN);
        Registry.register(Registries.ITEM, RegistryHelper.makeID("glowing_obsidian"),new BlockItem(GLOWING_OBSIDIAN, new FabricItemSettings()));
        Registry.register(Registries.BLOCK, RegistryHelper.makeID("stonecutter"),STONECUTTER_LEGACY);
        Registry.register(Registries.ITEM, RegistryHelper.makeID("stonecutter"),new BlockItem(STONECUTTER_LEGACY, new FabricItemSettings()));
        Registry.register(Registries.BLOCK, RegistryHelper.makeID("info_update"),UPDATE_BLOCK);
        Registry.register(Registries.ITEM, RegistryHelper.makeID("info_update"),new BlockItem(UPDATE_BLOCK, new FabricItemSettings()));
        Registry.register(Registries.BLOCK, RegistryHelper.makeID("info_update2"),UPDATE_BLOCK2);
        Registry.register(Registries.ITEM, RegistryHelper.makeID("info_update2"),new BlockItem(UPDATE_BLOCK2, new FabricItemSettings()));
        Registry.register(Registries.ITEM_GROUP, RegistryHelper.makeID("retro"), RETRO_ITEM_GROUP);
    }
}
