package day2.star4

import day2.star3.*
import intcomputer.*

val initState: List<Int> = Computer(input).getState()

const val targetOutput = 19690720

fun star4() {

	println("day 2 star 4")

	for(noun in 0..99) {
		for(verb in 0 .. 99){
			if(check(noun, verb)){
				println("program with noun $noun and verb $verb produces $targetOutput")
				if(check(verb, noun)) {
					println("Switching verb and noun is possible")
				}
				println("Final result: ${100 * noun + verb}")
			}
		}
	}

}

fun check(noun: Int, verb: Int): Boolean {
	val prog = Computer(initState)
	prog[1] = noun
	prog[2] = verb
	prog.run()

	return prog[0] == targetOutput
}