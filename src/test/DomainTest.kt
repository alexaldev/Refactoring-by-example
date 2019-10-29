package test

import Play
import Invoice
import Performance
import org.junit.jupiter.api.Assertions.assertTrue
import statement
import org.junit.jupiter.api.Test

val PLAYS = listOf(
    Play("Hamlet", Type.Tragedy),
    Play("As You Like it", Type.Comedy),
    Play("Othello", Type.Tragedy)
)

val INVOICE = Invoice(
    "BigCo", listOf(
        Performance("hamlet", 55),
        Performance("as-like", 35),
        Performance("othello", 40)
    )
)

const val EXPECTED_RESULT = "Statement for BigCo\n" +
        "Hamlet: \$650 (55 seats)\n" +
        "As You Like it: \$580 (35 seats)\n" +
        "Othello: \$500 (40 seats)\n" +
        "Amount owed is \$1730\n" +
        "You earned 47 credits"

class DomainTest {

    @Test
    fun the_example_invoice_of_the_book_works_as_expected() {

        val testResult = statement(INVOICE, PLAYS)

        assertTrue(testResult == EXPECTED_RESULT)
    }

}