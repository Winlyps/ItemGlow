package winlyps.itemGlow

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerItemHeldEvent
import org.bukkit.event.player.PlayerToggleSneakEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.plugin.Plugin
import org.bukkit.enchantments.Enchantment

class ItemGlowListener(private val plugin: Plugin) : Listener {

    @EventHandler
    fun onPlayerToggleSneak(event: PlayerToggleSneakEvent) {
        val player: Player = event.player
        val itemInMainHand: ItemStack? = player.inventory.itemInMainHand
        val itemInOffHand: ItemStack? = player.inventory.itemInOffHand

        handleItemGlow(itemInMainHand, event.isSneaking)
        handleItemGlow(itemInOffHand, event.isSneaking)
    }

    @EventHandler
    fun onPlayerItemHeld(event: PlayerItemHeldEvent) {
        val player: Player = event.player

        // Check if the player is sneaking
        if (player.isSneaking) {
            // Cancel the event to prevent the player from switching items
            event.isCancelled = true
        }
    }

    private fun handleItemGlow(item: ItemStack?, isSneaking: Boolean) {
        item?.let {
            val itemMeta: ItemMeta? = it.itemMeta

            if (isSneaking) {
                // Check if the item already has any enchantments
                if (itemMeta?.hasEnchants() == true) {
                    // Item already has enchantments, do nothing
                    return
                }

                // Add the glowing effect
                itemMeta?.addEnchant(Enchantment.ARROW_INFINITE, 1, true)
                itemMeta?.addItemFlags(org.bukkit.inventory.ItemFlag.HIDE_ENCHANTS)
            } else {
                // Remove the glowing effect if it was previously added
                if (itemMeta?.hasEnchant(Enchantment.ARROW_INFINITE) == true) {
                    itemMeta.removeEnchant(Enchantment.ARROW_INFINITE)
                }
            }

            it.itemMeta = itemMeta
        }
    }
}