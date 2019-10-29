class Play(val name: String, val type: Type)

enum class Type {
    Tragedy, Comedy
}

class Performance(val playId: String, val audience: Int)

class Invoice(val customer: String, val performances: List<Performance>)

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


fun statement(invoice: Invoice, plays: List<Play>): String {

    var totalAmount = 0
    var volumeCredits = 0
    var result = "Statement for ${invoice.customer}\n"

    invoice.performances.forEachIndexed { index, performance ->

        val play = plays[index]
        var thisAmount = 0

        when(play.type){
            Type.Tragedy -> {
                thisAmount = 40000
                if (performance.audience > 30) {
                    thisAmount += 1000 * (performance.audience - 30)
                }
            }
            Type.Comedy -> {
                thisAmount = 30000
                if ( performance.audience > 20) {
                    thisAmount += 10000 + 500 * (performance.audience - 20)
                }
                thisAmount += 300 * performance.audience
            }
        }

        // add volume credits
        volumeCredits += Math.max(performance.audience - 30, 0)
        // add extra credit for every ten comedy attendees
        if (Type.Comedy == play.type) volumeCredits += Math.floor(performance.audience.toDouble() / 5).toInt()

        result += "${play.name}: \$${thisAmount/100} (${performance.audience} seats)\n"
        totalAmount += thisAmount
    }

    result += "Amount owed is \$${totalAmount/100}\n"
    result += "You earned $volumeCredits credits"
    return result
}

fun main() {

    print(statement(INVOICE, PLAYS))
}