package day2.star3

import intcomputer.*

fun star3() {
	println("day 2 star 3")
	val prog = Computer(input, setOf(1, 2, 99))
	prog[1] = 12
	prog[2] = 2
	prog.run()
	println("Program state[0] = ${prog[0]}")

}