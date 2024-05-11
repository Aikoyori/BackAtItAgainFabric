package xyz.aikoyori.backatitagain.contents.blocks.withentities.netherreactor;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import xyz.aikoyori.backatitagain.BackAtItAgain;
import xyz.aikoyori.backatitagain.utils.StructureCalculations;

import java.util.Optional;

public class NetherReactorCoreBlockEntity extends BlockEntity {
    public NetherReactorCoreBlockEntity(BlockPos pos, BlockState state) {
        super(BackAtItAgain.NETHER_REACTOR_CORE_BLOCK_ENTITY, pos, state);
    }
    public int progress = 0;
    public int time_passed = 0;
    public boolean active = false;
    public boolean done = false;
    private Random random = Random.create();
    @Override
    protected void writeNbt(NbtCompound nbt) {
        nbt.putInt("progress",progress);
        nbt.putInt("time_passed",time_passed);
        nbt.putBoolean("active",active);
        nbt.putBoolean("done",done);

        super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);


        progress = nbt.getInt("progress");
        time_passed = nbt.getInt("time_passed");
        active = nbt.getBoolean("active");
        done = nbt.getBoolean("done");
    }
    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }


    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }
    public static void tick(World world, BlockPos pos, BlockState state, NetherReactorCoreBlockEntity be) {
        if(be.active && !be.done){

            // core blocks
            switch(be.time_passed){
                case 1:
                    world.setBlockState(pos,state.with(NetherReactorCoreBlock.STAGE,1));
                    break;
                case 20:
                    // glowing obsidian base
                    world.setBlockState(pos.add(0,-1,0),BackAtItAgain.GLOWING_OBSIDIAN.getDefaultState());
                    world.setBlockState(pos.add(-1,-1,0),BackAtItAgain.GLOWING_OBSIDIAN.getDefaultState());
                    world.setBlockState(pos.add(1,-1,0),BackAtItAgain.GLOWING_OBSIDIAN.getDefaultState());
                    world.setBlockState(pos.add(0,-1,-1),BackAtItAgain.GLOWING_OBSIDIAN.getDefaultState());
                    world.setBlockState(pos.add(0,-1,1),BackAtItAgain.GLOWING_OBSIDIAN.getDefaultState());
                    break;
                case 40:
                    // glowing obsidian pillars
                    world.setBlockState(pos.add(-1,0,-1),BackAtItAgain.GLOWING_OBSIDIAN.getDefaultState());
                    world.setBlockState(pos.add(1,0,-1),BackAtItAgain.GLOWING_OBSIDIAN.getDefaultState());
                    world.setBlockState(pos.add(-1,0,1),BackAtItAgain.GLOWING_OBSIDIAN.getDefaultState());
                    world.setBlockState(pos.add(1,0,1),BackAtItAgain.GLOWING_OBSIDIAN.getDefaultState());
                    break;
                case 60:
                    // glowing obsidian top
                    world.setBlockState(pos.add(0,1,0),BackAtItAgain.GLOWING_OBSIDIAN.getDefaultState());
                    world.setBlockState(pos.add(-1,1,0),BackAtItAgain.GLOWING_OBSIDIAN.getDefaultState());
                    world.setBlockState(pos.add(1,1,0),BackAtItAgain.GLOWING_OBSIDIAN.getDefaultState());
                    world.setBlockState(pos.add(0,1,-1),BackAtItAgain.GLOWING_OBSIDIAN.getDefaultState());
                    world.setBlockState(pos.add(0,1,1),BackAtItAgain.GLOWING_OBSIDIAN.getDefaultState());
                    break;

                case 120:
                    // gold blocks gone
                    world.setBlockState(pos.add(-1,-1,-1),BackAtItAgain.GLOWING_OBSIDIAN.getDefaultState());
                    world.setBlockState(pos.add(1,-1,-1),BackAtItAgain.GLOWING_OBSIDIAN.getDefaultState());
                    world.setBlockState(pos.add(-1,-1,1),BackAtItAgain.GLOWING_OBSIDIAN.getDefaultState());
                    world.setBlockState(pos.add(1,-1,1),BackAtItAgain.GLOWING_OBSIDIAN.getDefaultState());
                    break;
                case 200:case 400:
                    if(world.isClient) break;

                    LootContext bld = new LootContext.Builder(new LootContextParameterSet.Builder((ServerWorld) world).build(LootContextType.create().build())).build(Optional.empty());
                    BlockPos.Mutable mut = new BlockPos.Mutable();
                    LootTable loot =
                            world.getServer().getLootManager().getLootTable(BackAtItAgain.NETHER_LOOT_TABLE);

                    for (int i=0;i<32;i++){
                        loot.generateLoot(bld,itemStack -> {

                            mut.set(pos.add(be.random.nextInt(15)-8,-1,be.random.nextInt(17)-8));
                            ItemEntity itemEntity = new ItemEntity(world, mut.getX(), mut.getY(), mut.getZ(), itemStack);
                            itemEntity.setToDefaultPickupDelay();
                            world.spawnEntity(itemEntity);
                        });

                    }
                    break;
                case 920:
                case 940:
                case 960:
                    // gold blocks gone
                    for (int i = -1;i<2;i++){
                        for (int j = -1;j<2;j++){
                            if(be.time_passed == 940 && i ==0 && j == 0){
                                //System.out.println("condition is checked 940");
                                world.setBlockState(pos,state.with(NetherReactorCoreBlock.STAGE,2));
                            }
                            else{
                                world.setBlockState(pos.add(i,(be.time_passed-940)/20,j),Blocks.OBSIDIAN.getDefaultState());
                            }
                        }
                    }
                    break;
                case 961:
                    be.done = true;
            }
            if(be.time_passed < 144){

                for(int i=-8;i<=8;i++){
                    for(int j=-2;j<=Math.min(31,be.time_passed/4);j++){
                        for(int k=-8;k<=8;k++){
                            Optional<BlockState> prepState =
                                    StructureCalculations.getSpireStructureBlockStateWithStage(i,j,k,0);
                            if(prepState.isPresent()){
                                world.setBlockState(
                                        pos.add(i,j,k),prepState.get());
                            }
                        }
                    }
                }
            }
            be.time_passed++;
        }

    }
}

