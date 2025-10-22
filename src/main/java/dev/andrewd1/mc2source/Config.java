package dev.andrewd1.mc2source;

import org.apache.commons.lang3.SystemUtils;

import java.io.File;

public class Config {
    public static final int hammerUnitsPerBlock = 48;
    public static final double hammerTextureScale = 0.375;
    public static final boolean shouldUseWine = SystemUtils.IS_OS_LINUX;
    public static File vbsp = new File(Plugin.instance.getDataFolder(),  "vbsp.exe");
    public static File vvis = new File(Plugin.instance.getDataFolder(),  "vvis.exe");
    public static File vrad = new File(Plugin.instance.getDataFolder(),  "vrad.exe");
}
