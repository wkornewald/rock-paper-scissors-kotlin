package com.ensody.rockpaperscissors

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

enum class RuleItem {
    A, B, C, D
}

class IntransitiveRuleSetTest {
    companion object {
        val completeRuleSet = arrayOf(
            RuleItem.A to RuleItem.B,
            RuleItem.A to RuleItem.D,
            RuleItem.B to RuleItem.C,
            RuleItem.B to RuleItem.D,
            RuleItem.C to RuleItem.A,
            RuleItem.D to RuleItem.C
        )
    }

    @Test
    fun validateRulesComplete() {
        for (rule in completeRuleSet) {
            val subset = completeRuleSet.toMutableList().apply { remove(rule) }
            assertThrows<RuleSetValidationError> {
                intransitiveRuleSet(*subset.toTypedArray())
            }
        }
    }

    @Test
    fun validateRulesAntiSymmetric() {
        for (rule in completeRuleSet) {
            assertThrows<RuleSetValidationError> {
                intransitiveRuleSet(
                    *completeRuleSet,
                    rule.second to rule.first
                )
            }
        }
    }

    @Test
    fun validateRulesIrreflexive() {
        for (item in RuleItem.values()) {
            assertThrows<RuleSetValidationError> {
                intransitiveRuleSet(
                    *completeRuleSet,
                    item to item
                )
            }
        }
    }

    @Test
    fun completeness() {
        val rules = intransitiveRuleSet(*completeRuleSet)
        for (left in RuleItem.values()) {
            for (right in RuleItem.values()) {
                val result = rules.getRelation(left, right)
                when {
                    left == right -> assertThat(result).isEqualTo(Relation.EQUAL)
                    left to right in completeRuleSet -> assertThat(result).isEqualTo(Relation.LEFT_TO_RIGHT)
                    else -> assertThat(result).isEqualTo(Relation.RIGHT_TO_LEFT)
                }
            }
        }
    }
}
