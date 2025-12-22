

fun main() {
    val sample = "day03sample"
    val actual = "Day03"

//    val lines = readInput(sample)
    val lines = readInput(actual)

    //part1(lines)

    // PART 2
    part2(lines, "")
//    part2(listOf("6319","63195"),"")
}

fun part1(lines: List<String>) {
    var sum  = 0
    lines.forEach {
        val cells = it
        val (highest, highestIndex) = cells.highestCharAndIndex()

        println(cells)

        val lhsHighest = cells.substring(0 until highestIndex).highestCharAndIndex().first
        var left = "0"
        if (lhsHighest!='x') {
            left = "$lhsHighest$highest"
            println("left 2 digits are $left")
        }
        var right = "0"
        val rhsHighest = cells.substring(highestIndex+1 until cells.length).highestCharAndIndex().first
        if (rhsHighest!='x') {
            right ="$highest$rhsHighest"
            println("right 2 digits are $right")
        }
         if (left.toInt() != 0 && right.toInt() != 0) {
             if (left.toInt() > right.toInt()) sum += left.toInt() else sum += right.toInt()
         } else {
             sum += (left.toInt()+right.toInt())
         }

    }
    println("part 1 - sum = $sum")
}

fun String.highestCharAndIndex(): Pair<Char, Int> {
    if (this.isEmpty()) return Pair('x', -1)
    var highest = first()
    var highestIndex = 0
    forEachIndexed { index, ch ->
        if (ch > highest) {
            highest = ch
            highestIndex = index
        }
    }
    return Pair(highest, highestIndex)
}


fun part2(lines: List<String>,t: String) {
    var sum  = 0L
    val maxDigits = 12
    lines.forEach {
        val input = it
        println(input)

        val list = input.getCombinationOfDigits(maxDigits)
        println("largest = ${list.findLargest()}")
        sum += list.findLargest()
    }
    println("part 2 - sum = $sum")
}

private fun List<String>.findLargest(): Long {
    var largest = this[0].toLong()
    for (i in 1 until this.size) {
        if (this[i].toLong() > largest) {
            largest = this[i].toLong()
        }
    }
    return largest
}

private fun String.getCombinationOfDigits(maxDigits: Int): List<String> {
    val result = mutableListOf<String>()

    fun backtrack(start: Int, current: StringBuilder) {
        if (current.length == maxDigits) {
            result.add(current.toString())
            return
        }

        // Recursive case: try each remaining position
        for (i in start until this.length) {
            current.append(this[i])
            backtrack(i + 1, current)
            current.deleteCharAt(current.length - 1) // backtrack
        }
    }

    backtrack(0, StringBuilder())
    return result
}
