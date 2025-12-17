
fun main() {
    val sample = "day01sample"
    val actual = "day01"

    val lines = readInput(actual)

    //part1(lines)

    // PART 2
    part2(lines)
}

fun part2(lines: List<String>) {
    var dialAt = 50
    var numZeros = 0
    var numZeroCrossings = 0

    fun rotateDialLeft(rotations: Int) {
        repeat(rotations) {
            if (dialAt == 0) {
                numZeroCrossings += 1
            }
            dialAt -= 1
            // reset to 99
            if (dialAt<0) {
                dialAt = 99
            }
        }
    }

    fun rotateDialRight(rotations: Int) {
        repeat(rotations) {
            if (dialAt == 0) {
                numZeroCrossings += 1
            }
            dialAt += 1
            // reset to 0
            if (dialAt>99) {
                dialAt = 0
            }
        }
    }

    lines.forEach {
        numZeroCrossings = 0
        val direction = it[0]
        val rotations = it.drop(1).toInt()

        when (direction) {
            'L' -> rotateDialLeft(rotations)
            'R' -> rotateDialRight(rotations)
        }

        println("The dial is rotated $it to point at $dialAt. ($numZeroCrossings zeros crossed)")
        if (numZeroCrossings>0) {
            numZeros += numZeroCrossings
        }
    }

    println("part 2: numzeros = $numZeros")
}



fun part1(lines: List<String>) {
    var pointingVal = 50
    var numZeros = 0
    println("The dial starts by pointing at $pointingVal")
    lines.forEach {
        val direction = it[0]
        //println(it)
        if (direction == 'L') {
            pointingVal -= it.drop(1).toInt()
            pointingVal = pointingVal.zeroCorrection()
            println("The dial is rotated $it to point at $pointingVal.")
        } else if (direction == 'R') {
            pointingVal += it.drop(1).toInt()
            pointingVal = pointingVal.zeroCorrection()
            println("The dial is rotated $it to point at $pointingVal.")
        }

        if (pointingVal == 0) numZeros++
    }
    println("part 1 - numzeros = $numZeros")
}

private fun Int.zeroCorrection(): Int {
    val correctedValue = this%100
    return if (correctedValue < 0) {
        100 + correctedValue
    } else if (correctedValue > 99){
        correctedValue - 100
    } else correctedValue
}

