package github.baka943.simplewine.theoneprobe;

import com.google.common.base.Function;
import mcjty.theoneprobe.api.*;

import javax.annotation.Nullable;

public class TheOneProbeLoader implements Function<ITheOneProbe, Void> {
    @Nullable
    @Override
    public Void apply(ITheOneProbe theOneProbe) {
        theOneProbe.registerProvider(new BarrelTheOneProbeProvider());
        theOneProbe.registerProvider(new PresserTheOneProbeProvider());
        return null;
    }
}
