package dev.andrewd1.mc2source;

import org.apache.commons.lang3.SystemUtils;

import java.io.File;

public class Config {
    public static final int hammerUnitsPerBlock = 42;
    public static final double hammerTextureScale = 0.375;
    public static final boolean shouldUseWine = SystemUtils.IS_OS_LINUX;  // TODO: Use wine on Linux
    public static final boolean use64BitTools = true;
    public static final String game = "hl2mp";
    public static final boolean debug = true;

    public static final File executableDirectory = new File("");
    public static final File gameDirectory = new File(executableDirectory, game);
    public static final File toolsDir = new File(executableDirectory, use64BitTools ? "bin/x64" : "bin");

    public static File vbsp = new File(toolsDir,  "vbsp.exe");
    public static File vvis = new File(toolsDir,  "vvis.exe");
    public static File vrad = new File(toolsDir,  "vrad.exe");
}
