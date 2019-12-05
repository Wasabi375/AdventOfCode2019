package day5

import intcomputer.*

fun star10() {
	println("day 5 star 10")
	val comp = Computer(input)
	comp.inputFun = { 5 }
	comp.outputFun = { println("Computer out: $it") }
	comp.run()
}