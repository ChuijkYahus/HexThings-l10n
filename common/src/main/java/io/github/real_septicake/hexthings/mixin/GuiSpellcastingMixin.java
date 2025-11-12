package io.github.real_septicake.hexthings.mixin;

import at.petrak.hexcasting.api.casting.eval.ExecutionClientView;
import at.petrak.hexcasting.client.gui.GuiSpellcasting;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import io.github.real_septicake.hexthings.mxin_interface.ECVMixinInterface;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiSpellcasting.class)
public class GuiSpellcastingMixin {
    @Unique
    private int depth;

    @Inject(
            method = "recvServerUpdate",
            at = @At(value = "TAIL"),
            remap = false
    )
    public void getDepth(ExecutionClientView info, int index, CallbackInfo ci) {
        this.depth = ((ECVMixinInterface) (Object) info).hexThings$getDepth();
    }

    @Inject(method = "render", at = @At(value = "TAIL"), remap = false)
    public void render(GuiGraphics graphics, int pMouseX, int pMouseY, float pPartialTick, CallbackInfo ci, @Local Font font, @Local PoseStack ps) {
        if(depth > 0) {
            ps.pushPose();
            ps.translate(10, ((GuiSpellcasting)(Object)this).height - 10, 0);
//            ps.scale(0.75f, 0.75f, 1);
            graphics.drawString(font, "Introjection Depth: " + depth, 0, 0, 0xffffff, true);
        }
    }
}
