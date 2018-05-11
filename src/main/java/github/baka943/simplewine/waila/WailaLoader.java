package github.baka943.simplewine.waila;

import github.baka943.simplewine.block.BlockBarrel;
import github.baka943.simplewine.block.BlockPresser;
import github.baka943.simplewine.tiltentity.TileEntityBarrel;
import github.baka943.simplewine.tiltentity.TileEntityPresser;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.IWailaRegistrar;
import mcp.mobius.waila.api.WailaPlugin;

@WailaPlugin
public class WailaLoader implements IWailaPlugin {
    @Override
    public void register(IWailaRegistrar registrar){
        registrar.registerBodyProvider(WailaPresser.INSTANCE, BlockPresser.class);
        registrar.registerBodyProvider(WailaBarrel.INSTANCE, BlockBarrel.class);
        registrar.registerNBTProvider(WailaPresser.INSTANCE, TileEntityPresser.class);
        registrar.registerNBTProvider(WailaBarrel.INSTANCE, TileEntityBarrel.class);
    }
}
