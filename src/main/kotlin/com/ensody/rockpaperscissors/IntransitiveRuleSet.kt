package com.ensody.rockpaperscissors

/** Comparison results for an [IntransitiveRuleSet]. */
enum class Relation {
    LEFT_TO_RIGHT, RIGHT_TO_LEFT, EQUAL
}

/**
 * Defines a complete, consistent set of rules between element pairs.
 *
 * For example, if you have elements a, b, c you could define these relations:
 *
 * ```
 * a > b
 * b > c
 * c > a
 * ```
 *
 * Note that this relation is intransitive because it allows `c > a`.
 * In contrast, the greater-than relation on numbers is transitive,
 * which means `a > b` and `b > c` implies `a > c` (the inverse of our rule).
 *
 * The corresponding code would be:
 *
 * ```kotlin
 * val rules = IntransitiveRuleSet(
 *     setOf(a, b, c),
 *     setOf(a to b, b to c, c to a)
 * )
 * rules.getRelation(a,  b)  // => Relation.LEFT_TO_RIGHT
 * rules.getRelation(b,  a)  // => Relation.RIGHT_TO_LEFT
 * rules.getRelation(a,  a)  // => Relation.EQUAL
 * ```
 *
 * The provided pairs must be complete and map all possible [Relation.LEFT_TO_RIGHT] relations.
 * The inverse pairs automatically become [Relation.RIGHT_TO_LEFT] relations.
 * Equal values become [Relation.EQUAL] relations.
 *
 * To be more precise, the rules are based on an asymmetric, intransitive relation.
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

/**
 * Syntax sugar for creating an [IntransitiveRuleSet] from an enum.
 *
 * The set of values is automatically inferred from the enum.
 * You only need to provide the actual rules between value pairs.
 *
 * Example:
 *
 * ```kotlin
 * enum V { a, b, c }
 *
 * val rules = intransitiveRuleSet(
 *     V.a to V.b,
 *     V.b to V.c,
 *     V.c to V.a
 * )
 * ```
 *
 * This will validate the rules for completeness (all enum values are mentioned) and consistency.
 *
 * @param [relations] All [Relation.LEFT_TO_RIGHT] relations for all enum values.
 *
 * @throws [RuleSetValidationError] if the rule set is invalid.
 */
inline fun <reified T : Enum<T>> intransitiveRuleSet(vararg relations: Pair<T, T>) =
    IntransitiveRuleSet(enumValues<T>().toSet(), relations.toSet())

// --------------------------------------------------------------------------------------------------------------------
// Validation
// --------------------------------------------------------------------------------------------------------------------

/** This exception is raised when validation of the [IntransitiveRuleSet] fails. */
class RuleSetValidationError(description: String) : IllegalArgumentException(description)

/**
 * Validates an intransitive rule set for completeness and consistency.
 *
 * Completeness means that all distinct element pairs have a corresponding relation.
 * Consistency means that the relation is asymmetric (anti-symmetric and irreflexive).
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
        // Ensure relation is irreflexive
        if (left to left in relations) {
            throw RuleSetValidationError("Relation between equal elements: ${left to left}")
        }
        for (right in valuesList) {
            // Ensure relation is anti-symmetric
            if (left to right in relations && right to left in relations) {
                throw RuleSetValidationError("Conflicting relations: ${left to right} and ${right to left}")
            }
            // Ensure completeness
            if (left to right !in relations && right to left !in relations) {
                throw RuleSetValidationError("Missing relation between $left and $right")
            }
        }
    }
}
