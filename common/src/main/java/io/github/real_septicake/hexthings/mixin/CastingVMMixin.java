package io.github.real_septicake.hexthings.mixin;

import at.petrak.hexcasting.api.casting.eval.ExecutionClientView;
import at.petrak.hexcasting.api.casting.eval.ResolvedPatternType;
import at.petrak.hexcasting.api.casting.eval.vm.CastingImage;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.NullIota;
import at.petrak.hexcasting.api.casting.iota.PatternIota;
import at.petrak.hexcasting.api.casting.math.HexAngle;
import at.petrak.hexcasting.api.casting.mishaps.MishapTooManyCloseParens;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import io.github.real_septicake.hexthings.casting.eval.SpecialPatterns;
import io.github.real_septicake.hexthings.registry.HexthingsActions;
import kotlin.Pair;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Mixin(at.petrak.hexcasting.api.casting.eval.vm.CastingVM.class)
public abstract class CastingVMMixin {
    @Shadow(remap = false)
    private CastingImage image;

    @Unique
    private int hexThings$depth = 0;

    @Inject(
            method = "queueExecuteAndWrapIotas(Ljava/util/List;Lnet/minecraft/server/level/ServerLevel;)Lat/petrak/hexcasting/api/casting/eval/ExecutionClientView;",
            at = @At(
                    value = "INVOKE",
                    target = "Lat/petrak/hexcasting/api/casting/eval/CastingEnvironment;postCast(Lat/petrak/hexcasting/api/casting/eval/vm/CastingImage;)V",
                    shift = At.Shift.AFTER,
                    remap = false
            ),
            remap = false
    )
    private void queueExecuteAndWrapIotas(List<? extends Iota> iotas, ServerLevel world, CallbackInfoReturnable<ExecutionClientView> cir, @Local LocalBooleanRef isStackClear) {
        isStackClear.set(isStackClear.get() && hexThings$depth == 0);
        Logger.getAnonymousLogger().info("" + isStackClear.get());
        Logger.getAnonymousLogger().info("" + hexThings$depth);
    }

    @Inject(method = "handleParentheses(Lat/petrak/hexcasting/api/casting/iota/Iota;)Lkotlin/Pair;", at = @At("HEAD"), cancellable = true, remap = false)
    private void handleParentheses(Iota iota, CallbackInfoReturnable<Pair<CastingImage, ResolvedPatternType>> cir) throws MishapTooManyCloseParens {
        List<HexAngle> sig = null;
        if(iota instanceof PatternIota p) {
            sig = p.getPattern().getAngles();
        }

        int displayDepth = this.image.getParenCount();
        if(!image.getEscapeNext() && sig != null) {
            if(sig.equals(SpecialPatterns.INSTANCE.getESCAPE_STOP().getAngles())) {
                if(displayDepth == 0)
                    throw new MishapTooManyCloseParens();
                hexThings$depth++;
                CompoundTag image2 = image.serializeToNbt();
                CompoundTag newUserData = image.getUserData().copy();
                newUserData.put("hexthings_prev", image2);
                CastingImage newImage = image.copy(List.of(new NullIota()), 0, new ArrayList<>(),
                        false, image.getOpsConsumed(), newUserData);
                cir.setReturnValue(new Pair<>(newImage, ResolvedPatternType.EVALUATED));
                cir.cancel();
            }
            if(sig.equals(HexthingsActions.INSTANCE.getESCAPE_RESUME().getValue().prototype().getAngles())) {
                hexThings$depth--;
            }
        }
    }
}
