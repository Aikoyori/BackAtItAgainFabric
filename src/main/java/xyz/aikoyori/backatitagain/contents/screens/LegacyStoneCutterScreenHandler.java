package xyz.aikoyori.backatitagain.contents.screens;

import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.StonecutterScreenHandler;
import xyz.aikoyori.backatitagain.BackAtItAgain;
import xyz.aikoyori.backatitagain.mixins.StonecutterScreenHandlerAccessor;

public class LegacyStoneCutterScreenHandler extends StonecutterScreenHandler {
    public LegacyStoneCutterScreenHandler(int syncId, PlayerInventory playerInventory) {
        super(syncId, playerInventory);
    }

    public LegacyStoneCutterScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(syncId, playerInventory, context);
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return canUse(((StonecutterScreenHandlerAccessor)this).getContext(), player, BackAtItAgain.STONECUTTER_LEGACY);
    }
}
