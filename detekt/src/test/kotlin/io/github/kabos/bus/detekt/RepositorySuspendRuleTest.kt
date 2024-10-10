package io.github.kabos.bus.detekt

import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.rules.KotlinCoreEnvironmentTest
import io.gitlab.arturbosch.detekt.test.compileAndLintWithContext
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

@Suppress("NonAsciiCharacters")
@KotlinCoreEnvironmentTest
internal class RepositorySuspendRuleTest(private val env: KotlinCoreEnvironment) {

    @Test
    fun `公開された関数がsuspendになっている場合、Issueを検知しない`() {
        val code = """
        class FooRepository {
            suspend fun get() {
                // suspendなのでok
            }

            private fun save() {
                // privateなのでsuspendがなくてもok
            }
        }
        """.trim()
        val result = RepositorySuspendRule(Config.empty).compileAndLintWithContext(env, code)
        assertEquals(0, result.size)
    }


    @Test
    fun `公開された関数がsuspendになっていない場合、Issueを検知する`() {
        val code = """
        class FooRepository : Repository {
            fun get() {
                // suspendになっていないのでNG
            }

            overrider fun save() {
                // suspendになっていないのでNG
            }
        }
        """.trim()
        val result = RepositorySuspendRule(Config.empty).compileAndLintWithContext(env, code)
        assertEquals(2, result.size)
    }
}
