package com.ensody.rockpaperscissors

/** Comparison results for an [IntransitiveRuleSet]. */
enum class Relation {
    LEFT_TO_RIGHT, RIGHT_TO_LEFT, EQUAL
}

/**
 * Syntax sugar for creating an [IntransitiveRuleSet] from an enum.
 *
 * This will validate the rules for completeness (all enum values are mentioned) and consistency.
 *
 * @param [relations] All [Relation.LEFT_TO_RIGHT] relations for all enum values.
 *
 * @throws [RuleSetValidationError] if the rule set is invalid.
 */
inline fun <reified T : Enum<T>> intransitiveRuleSet(vararg relations: Pair<T, T>) =
    IntransitiveRuleSet(enumValues<T>().toSet(), relations.toSet())

/**
 * Defines a complete set of rules based on an anti-reflexive, asymmetric, intransitive relation.
 *
 * For example, if you have two elements a and b: a > b, b > c, c > a).
 *
 * The provided pairs must be complete and map all possible [Relation.LEFT_TO_RIGHT] relations.
 * The inverse pairs automatically become [Relation.RIGHT_TO_LEFT] relations.
 * Equal values become [Relation.EQUAL] relations.
 *
 * @param [values] The set of values for which we want to define relations.
 * @param [relations] All [Relation.LEFT_TO_RIGHT] relations for the given values.
 *
 * @throws [RuleSetValidationError] if the rule set is invalid.
 */
class IntransitiveRuleSet<T>(values: Set<T>, private val relations: Set<Pair<T, T>>) {
    init {
        validateIntransitiveRuleSet(values, relations)
    }

    /** Returns the [Relation] between [left] and [right]. */
    fun getRelation(left: T, right: T): Relation =
        when {
            left == right -> Relation.EQUAL
            left to right in relations -> Relation.LEFT_TO_RIGHT
            else -> Relation.RIGHT_TO_LEFT
        }
}

// --------------------------------------------------------------------------------------------------------------------
// Validation
// --------------------------------------------------------------------------------------------------------------------

/** This exception is raised when validation of the [IntransitiveRuleSet] fails. */
class RuleSetValidationError(description: String) : IllegalArgumentException(description)

/**
 * Validates an intransitive rule set for completeness and consistency.
 *
 * @param [values] The set of values for which we want to define relations.
 * @param [relations] All [Relation.LEFT_TO_RIGHT] relations for the given values.
 *
 * @throws [RuleSetValidationError] if the rule set is invalid.
 */
fun <T> validateIntransitiveRuleSet(values: Set<T>, relations: Set<Pair<T, T>>) {
    val valuesList = values.toMutableList()
    while (valuesList.isNotEmpty()) {
        val left = valuesList.removeAt(0)
        if (left to left in relations) {
            throw RuleSetValidationError("Relation between equal elements: ${left to left}")
        }
        for (right in valuesList) {
            if (left to right in relations && right to left in relations) {
                throw RuleSetValidationError("Conflicting relations: ${left to right} and ${right to left}")
            }
            if (left to right !in relations && right to left !in relations) {
                throw RuleSetValidationError("Missing relation between $left and $right")
            }
        }
    }
}
