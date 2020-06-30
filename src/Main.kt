import kotlin.math.floor

enum class Type {
    Tragedy, Comedy
}

class Play(val name: String, val type: Type)

class Performance(val playId: String, val audience: Int)

class Invoice(val customer: String, val performances: List<Performance>)

val PLAYS = hashMapOf(
    "hamlet" to Play("Hamlet", Type.Tragedy),
    "as-like" to Play("As you Like it", Type.Comedy),
    "othello" to Play("Othello", Type.Tragedy)
)

val INVOICE = Invoice(
    "BigCo", listOf(
        Performance("hamlet", 55),
        Performance("as-like", 35),
        Performance("othello", 40)
    )
)

fun statement(invoice: Invoice, plays: Map<String, Play>): String {

    var totalAmount = 0.0
    var result = "Statement for ${invoice.customer}\n"
    print(result)

    invoice.performances.forEach { aPerformance ->
        println("     ${playFor(aPerformance).name}: \$ ${amountFor(aPerformance) / 100} (${aPerformance.audience} seats)")
    }

    invoice.performances.forEach { aPerformance ->
        totalAmount += amountFor(aPerformance)
    }

    result += "Amount owed is \$${totalAmount / 100}\n"
    result += "You earned ${totalVolumeCredits(invoice)} credits"
    return result
}

fun totalVolumeCredits(invoice: Invoice): Double {

    var result = 0.0
    invoice.performances.forEach { aPerformance ->
        result += volumeCreditsFor(aPerformance)
    }
    return result
}

fun volumeCreditsFor(performance: Performance): Double {

    var result = 0.0
    result += Math.max(performance.audience - 30, 0)
    if (playFor(performance).type == Type.Comedy) result += floor(performance.audience.toDouble() / 5.0)
    return result
}

fun playFor(performance: Performance): Play {
    return PLAYS[performance.playId] ?: error("Unable to parse ${performance.playId} to a Play")
}

fun amountFor(performance: Performance): Int {

    var result = 0

    when (playFor(performance).type) {
        Type.Tragedy -> {
            result = 40000
            if (performance.audience > 30) {
                result += 1000 * (performance.audience - 30)
            }
        }
        Type.Comedy -> {
            result = 30000
            if (performance.audience > 20) {
                result += 10000 + 500 * (performance.audience - 20)
            }
            result += 300 * performance.audience
        }
    }

    return result
}

fun main() {
    print(statement(INVOICE, PLAYS))
}
