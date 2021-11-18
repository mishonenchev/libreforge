package com.willfp.libreforge.internal.effects

import com.willfp.eco.core.config.interfaces.JSONConfig
import com.willfp.libreforge.api.effects.ConfigViolation
import com.willfp.libreforge.api.effects.Effect
import com.willfp.libreforge.api.effects.getEffectAmount
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.entity.Player

class EffectBonusHealth : Effect("bonus_health") {
    override fun handleEnable(player: Player, config: JSONConfig) {
        val attribute = player.getAttribute(Attribute.GENERIC_MAX_HEALTH) ?: return
        val uuid = this.getUUID(player.getEffectAmount(this))
        attribute.removeModifier(AttributeModifier(uuid, this.id, 0.0, AttributeModifier.Operation.MULTIPLY_SCALAR_1))

        attribute.addModifier(
            AttributeModifier(
                uuid,
                this.id,
                config.getInt("health").toDouble(),
                AttributeModifier.Operation.ADD_NUMBER
            )
        )
    }

    override fun handleDisable(player: Player) {
        val attribute = player.getAttribute(Attribute.GENERIC_MAX_HEALTH) ?: return
        attribute.removeModifier(
            AttributeModifier(
                this.getUUID(player.getEffectAmount(this)),
                this.id,
                0.0,
                AttributeModifier.Operation.ADD_NUMBER
            )
        )
    }

    override fun validateConfig(config: JSONConfig): List<ConfigViolation> {
        val violations = mutableListOf<ConfigViolation>()

        config.getDoubleOrNull("health")
            ?: violations.add(
                ConfigViolation(
                    "health",
                    "You must specify the bonus health to give!"
                )
            )

        return violations
    }
}