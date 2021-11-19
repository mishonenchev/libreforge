package com.willfp.libreforge.internal.effects

import com.willfp.eco.core.config.interfaces.JSONConfig
import com.willfp.libreforge.api.ConfigViolation
import com.willfp.libreforge.api.effects.Effect
import org.bukkit.entity.Arrow
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageByEntityEvent

class EffectBowDamageMultiplier : Effect("bow_damage_multiplier", supportsFilters = true) {
    override fun onArrowDamage(
        attacker: Player,
        victim: LivingEntity,
        arrow: Arrow,
        event: EntityDamageByEntityEvent,
        config: JSONConfig
    ) {
        if (!this.getFilter(config).matches(victim)) {
            return
        }

        event.damage *= config.getDouble("multiplier")
    }

    override fun validateConfig(config: JSONConfig): List<ConfigViolation> {
        val violations = mutableListOf<ConfigViolation>()

        config.getDoubleOrNull("multiplier")
            ?: violations.add(
                ConfigViolation(
                    "multiplier",
                    "You must specify the bow damage multiplier!"
                )
            )

        return violations
    }
}