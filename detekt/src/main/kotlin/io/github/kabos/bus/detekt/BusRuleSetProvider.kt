package io.github.kabos.bus.detekt

import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.RuleSet
import io.gitlab.arturbosch.detekt.api.RuleSetProvider

class BusRuleSetProvider : RuleSetProvider {
    override val ruleSetId: String = "bus-rule"

    override fun instance(config: Config): RuleSet {
        return RuleSet(
            id = ruleSetId,
            rules = listOf(
                RepositorySuspendRule(config),
            ),
        )
    }
}
