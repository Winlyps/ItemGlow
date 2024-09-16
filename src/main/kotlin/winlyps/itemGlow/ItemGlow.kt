package winlyps.itemGlow

import org.bukkit.plugin.java.JavaPlugin

class ItemGlow : JavaPlugin() {

    override fun onEnable() {
        // Register the event listener
        server.pluginManager.registerEvents(ItemGlowListener(this), this)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}