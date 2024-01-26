package ttv.alanorMiga.jeg.event;

/*@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class GunNoSprintEvent
{

    @SubscribeEvent
    public static void noSprintie(TickEvent.ServerTickEvent event) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        boolean shooting = mc.options.keyAttack.isDown();

        if (shooting && player.isSprinting()) {
            player.setSprinting(false);
        }
        /*if (shooting) {
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 2, 1, false, false));
        }*/
    /*}

}*/
