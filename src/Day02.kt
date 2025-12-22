import java.sql.RowId

fun main() {
    val sample = "11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124"
    val actual = "day02"

    val lines = readInput(actual)

//    part1(sample)
   // part1(lines[0])

    // PART 2
//    part2(sample)
    part2(lines[0])
}
fun String.toSequence(delimiter: Char) = split(delimiter).let { it[0].toLong()..it[1].toLong() }.asSequence()

fun part2(input: String) {
    var sumOfInvalidIds = 0L
    val ranges = input.split(",")
    ranges.forEach { range ->
        range.toSequence('-').filter { x-> isMultiPattern(x) }.also {
//            print("[x]For range $range invalid: ")
            it.forEach {it2 -> print("$it2,")}
            println()
        }

        val (firstId, lastId) = range.split("-")
        val listOfInvalidIds = getListOfRepeatingMultipleItems(firstId.toLong(), lastId.toLong())
//        print("[m]For range $range invalid: ")
        listOfInvalidIds.forEach {it2 -> print("$it2,")}
        println()
        sumOfInvalidIds += listOfInvalidIds.sum()
    }
    println("sumOfInvalidIds = $sumOfInvalidIds")
}

fun isMultiPattern(number: Long): Boolean {
    if (number < 10) return false
    val s = number.toString()
    val n = s.length
    val lps = IntArray(n)
    var length = 0
    var i = 1
    while (i < n) {
        if (s[i] == s[length]) {
            length++
            lps[i] = length
            i++
        } else {
            if (length != 0) {
                length = lps[length - 1]
            } else {
                lps[i] = 0
                i++
            }
        }
    }
    val lastLps = lps[n - 1]
    val patternLen = n - lastLps
    return lastLps > 0 && n % patternLen == 0
}

private fun String.isAllSame(): Boolean {
    return this.length>1 && chars().distinct().count() == 1L
}

fun getListOfRepeatingMultipleItems(firstId: Long, lastId: Long): MutableList<Long> {
    val listOfInvalid = mutableListOf<Long>()
    for (i in firstId..lastId) {
        val numString = i.toString()
        if (numString.isAllSame()) {
//            println("$numString has all same")
            listOfInvalid.add(i)
        } else {
            val numDigits = numString.length
            if (numDigits % 2 == 0) {
                // check from 2 till numDigit/2
                for (j in 2..numDigits/2) {
                    if (numString.chunked(j).distinct().count() == 1) {
                       // println("numString $numString has repeating ${numString.take(j)} pattern")
                        listOfInvalid.add(i)
                        break
                    }
                }
            } else if (numDigits % 3 == 0) { // for checking xyzxyzxyz cases
                // check from 2 till numDigit/2
                for (j in 3..numDigits/3) {
                    if (numString.chunked(j).distinct().count() == 1) {
                       // println("numString $numString has repeating ${numString.take(j)} pattern")
                        listOfInvalid.add(i)
                    }
                }
            }
        }
    }
    return listOfInvalid
}


fun part1(input: String) {
    var sumOfInvalidIds = 0L
    val ranges = input.split(",")
    ranges.forEach { range ->
        val (firstId, lastId) = range.split("-")
        println("firstId, lastId = [$firstId, $lastId]")
        val listOfInvalidIds = getListOfRepeatingItems(firstId.toLong(), lastId.toLong())
        println("listOfInvalidIds $listOfInvalidIds")
        sumOfInvalidIds += listOfInvalidIds.sum()
    }
    println("sumOfInvalidIds = $sumOfInvalidIds")
}

fun getListOfRepeatingItems(firstId: Long, lastId: Long): List<Long> {
    val listOfInvalid = mutableListOf<Long>()
    for (i in firstId..lastId) {
        val numString = i.toString()
        val numDigits = numString.length
        if (numDigits % 2 == 0) {
            // only check numbers of multiple of 2
            if (numString.take(numDigits/2) == numString.takeLast(numDigits/2)) {
                listOfInvalid.add(i)
            }
        }
    }
    return listOfInvalid
}
