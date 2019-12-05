package day5

import intcomputer.*

fun star9() {
	println("day 5 star 9")
	val comp = Computer(input)
	comp.inputFun = { 1 }
	comp.outputFun = { println("Computer out: $it") }
	comp.run()
}