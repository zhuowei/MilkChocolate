package net.zhuoweizhang.milkchocolate;
import org.spout.api.plugin.CommonPlugin;
import org.spout.api.plugin.PluginDescriptionFile;
public class MilkChocolatePlugin extends CommonPlugin {
	private PluginDescriptionFile pdfFile;

	@Override
	public void onDisable() {
		getLogger().info(pdfFile.getFullName() + " has been disabled!");
	}

	@Override
	public void onEnable() {
		pdfFile = getDescription();
		getLogger().info(pdfFile.getFullName() + " has been enabled!");
	}
}
