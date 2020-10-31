package xyz.darke.anticreepergrief.mixin;

import net.minecraft.entity.data.TrackedData;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.entity.mob.CreeperEntity;

@Mixin(CreeperEntity.class)
class CreeperGriefDisableMixin {
    @Shadow @Final private static TrackedData<Boolean> CHARGED;

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/CreeperEntity;explode()V"))
    private void nullifyExplosion(CreeperEntity target) {
        World world = target.getEntityWorld();
        Explosion.DestructionType destructionType = Explosion.DestructionType.NONE;

        float mod = target.getDataTracker().get(CHARGED) ? 2.0F : 1.0F;
        world.createExplosion(target, target.getX(), target.getY(), target.getZ(), (float)3 * mod, destructionType);

        target.remove();
    }
}
