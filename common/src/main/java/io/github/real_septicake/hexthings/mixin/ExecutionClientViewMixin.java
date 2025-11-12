package io.github.real_septicake.hexthings.mixin;

import io.github.real_septicake.hexthings.mxin_interface.ECVMixinInterface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(at.petrak.hexcasting.api.casting.eval.ExecutionClientView.class)
public class ExecutionClientViewMixin implements ECVMixinInterface {
    @Unique
    private int depth;

    public void hexThings$setDepth(int depth) {
        this.depth = depth;
    }

    public int hexThings$getDepth() {
        return depth;
    }
}
