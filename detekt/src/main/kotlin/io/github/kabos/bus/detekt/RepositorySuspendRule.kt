package io.github.kabos.bus.detekt

import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity
import org.jetbrains.kotlin.lexer.KtTokens
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtNamedFunction
import org.jetbrains.kotlin.psi.psiUtil.isPublic

class RepositorySuspendRule(config: Config) : Rule(config) {
    override val issue = Issue(
        id = javaClass.simpleName,
        severity = Severity.CodeSmell,
        description = "Public function in repository should be suspend.",
        debt = Debt.FIVE_MINS,
    )

    override fun visitClass(klass: KtClass) {
        super.visitClass(klass)

        if (klass.name?.contains("Repository") == true) {
            klass.body?.functions?.forEach { function ->
                if (function.isPublic && !function.hasModifier(KtTokens.SUSPEND_KEYWORD)) {
                    sendReport(function)
                }
            }
        }
    }

    private fun sendReport(function: KtNamedFunction) {
        report(
            finding = CodeSmell(
                issue = issue,
                entity = Entity.from(function),
                message = "Public function in repository should be suspend.",
            )
        )
    }
}
