package xyz.aikoyori.backatitagain.mixins;

import net.minecraft.structure.StructureTemplate;
import net.minecraft.world.gen.structure.Structure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(StructureTemplate.class)
public interface StructureAccessor {
    @Accessor
    List<StructureTemplate.PalettedBlockInfoList> getBlockInfoLists();
}