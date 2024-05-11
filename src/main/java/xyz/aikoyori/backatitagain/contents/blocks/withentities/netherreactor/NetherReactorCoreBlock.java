package xyz.aikoyori.backatitagain.contents.blocks.withentities.netherreactor;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.event.listener.GameEventListener;
import org.jetbrains.annotations.Nullable;
import xyz.aikoyori.backatitagain.BackAtItAgain;
import xyz.aikoyori.backatitagain.utils.StructureCalculations;

public class NetherReactorCoreBlock extends BlockWithEntity {
    public static final MapCodec<NetherReactorCoreBlock> CODEC = createCodec(NetherReactorCoreBlock::new);
    public static final IntProperty STAGE = IntProperty.of("stage",0,2);

    public NetherReactorCoreBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(STAGE, 0));
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return null;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, BackAtItAgain.NETHER_REACTOR_CORE_BLOCK_ENTITY, NetherReactorCoreBlockEntity::tick);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(world.isClient) return ActionResult.CONSUME;
        if(world.getBlockEntity(pos) instanceof NetherReactorCoreBlockEntity nrc){
            if(pos.getY() + 33 >= world.getHeight()) {

                player.sendMessage(Text.translatable("block.backatitagain.nether_reactor_core.too_high"));
                return ActionResult.CONSUME;
            }
            if(nrc.active) return ActionResult.CONSUME;
            BlockPos.Mutable mutB = new BlockPos.Mutable();

            for(int i = -1;i<=1;i++){
                for(int j = -1;j<=1;j++){
                    for(int k = -1;k<=1;k++){
                        mutB.set(pos.add(i,j,k));
                        if(!StructureCalculations.checkIfReactorValid(i,j,k,world.getBlockState(mutB))){
                            player.sendMessage(Text.translatable("block.backatitagain.nether_reactor_core.invalid"));
                            return ActionResult.CONSUME;
                        }
                    }
                }
            }
            player.sendMessage(Text.translatable("block.backatitagain.nether_reactor_core.active"));
            nrc.active = true;
            return ActionResult.SUCCESS;
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(STAGE);
        super.appendProperties(builder);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new NetherReactorCoreBlockEntity(pos,state);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        super.randomDisplayTick(state, world, pos, random);
    }
}
