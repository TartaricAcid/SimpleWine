package github.baka943.simplewine.block;

import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockLoader {
    @ObjectHolder("simple_wine:block_presser")
    public static BlockPresser blockPresser;
    @ObjectHolder("simple_wine:block_barrel")
    public static BlockBarrel blockBarrel;

    public BlockLoader() {
    }

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        blockPresser.initModel();
        blockBarrel.initModel();
    }
}
