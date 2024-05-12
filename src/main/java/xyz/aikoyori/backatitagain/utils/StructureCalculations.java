package xyz.aikoyori.backatitagain.utils;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.NetherrackBlock;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.structure.StructureTemplateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3i;
import xyz.aikoyori.backatitagain.BackAtItAgain;
import xyz.aikoyori.backatitagain.mixins.StructureAccessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;



public class StructureCalculations {

    public static Optional<BlockState> getSpireStructureBlockStateWithStage(int x, int y, int z,int stage,StructureTemplate.StructureBlockInfo[][][] spireStructure){
        if(y>32 || y < -3 || MathHelper.abs(x)>8|| MathHelper.abs(z)>8) return Optional.empty();
        if(stage < 900){
            if(Objects.nonNull(spireStructure[x+8][y+3][z+8])){
                BlockState state = spireStructure[x+8][y+3][z+8].state();
                if(state.getBlock() == Blocks.NETHERRACK)
                    return Optional.of(Blocks.NETHERRACK.getDefaultState());
                else
                    return Optional.empty();
            }
        }
        return Optional.empty();
    }

    public static boolean checkIfReactorValid(int x,int y, int z, BlockState stateCheck)
    {
        return switch (reactorTemplate[y + 1][x + 1][z + 1]) {
            case 0 -> stateCheck.getBlock() == Blocks.AIR;
            case 1 -> stateCheck.getBlock() == Blocks.COBBLESTONE;
            case 2 -> stateCheck.getBlock() == Blocks.GOLD_BLOCK;
            case 3 -> stateCheck.getBlock() == BackAtItAgain.NETHER_REACTOR_CORE;
            default -> false;
        };
    }
    // reactor definition
    // 0 for air
    // 1 for cobble
    // 2 for gold
    // 3 for reactor core
    public static int[][][] reactorTemplate = {
            {
                {2,1,2},
                {1,1,1},
                {2,1,2},
            },
            {
                {1,0,1},
                {0,3,0},
                {1,0,1},
            },
            {
                {0,1,0},
                {1,1,1},
                {0,1,0},
            },
    };
    // Thanks to daniwhale
    public static List<StructureTemplate.StructureBlockInfo> getAllBlocks(StructureTemplate structure) {
        ArrayList<StructureTemplate.StructureBlockInfo> blocks = new ArrayList<>();

        StructureAccessor mixin = (StructureAccessor) structure;

        List<StructureTemplate.PalettedBlockInfoList> infoLists = mixin.getBlockInfoLists();

        for (StructureTemplate.PalettedBlockInfoList list : infoLists) {
            blocks.addAll(list.getAll());
        }

        return blocks;
    }
    public static StructureTemplate.StructureBlockInfo[][][] getAllBlocksAsArray(StructureTemplate structure) {
        List<StructureTemplate.StructureBlockInfo> blocks = getAllBlocks(structure);
        Vec3i size = structure.getSize();
        StructureTemplate.StructureBlockInfo[][][] blocksArray = new StructureTemplate.StructureBlockInfo[size.getX()][size.getY()][size.getZ()];

        for (StructureTemplate.StructureBlockInfo block : blocks) {
            BlockPos pos = block.pos();
            blocksArray[pos.getX()][pos.getY()][pos.getZ()] = block;
        }

        return blocksArray;
    }
}
