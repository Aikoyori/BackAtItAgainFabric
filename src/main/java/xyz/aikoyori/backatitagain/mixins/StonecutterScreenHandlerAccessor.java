package xyz.aikoyori.backatitagain.mixins;

import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.StonecutterScreenHandler;
import net.minecraft.structure.StructureTemplateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(StonecutterScreenHandler.class)
public interface StonecutterScreenHandlerAccessor {
    @Accessor
    ScreenHandlerContext getContext();

}
