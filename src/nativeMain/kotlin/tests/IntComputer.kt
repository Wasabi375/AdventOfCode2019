package tests

import intcomputer.*

class IntComputer {
	fun test() {
		println("IntComputer Tests")
		testOpCodeParsing()
		testAdd()
		testMul()
		testParaMode()
		testJumpIfTrue()
		testJumpIfFalse()
		testLessThan()
		testEualsOp()
	}

	private fun testEualsOp() {
		println("test Equals")
		var comp = Computer(listOf(1108, 3, 3, 3, 99))
		comp.run()
		assert(comp[3] == 1)

		comp = Computer(listOf(1108, 4, 3, 3, 99))
		comp.run()
		assert(comp[3] == 0)
	}

	private fun testLessThan() {
		println("test Less Than")
		var comp = Computer(listOf(1107, 1, 3, 3, 99))
		comp.run()
		assert(comp[3] == 1)

		comp = Computer(listOf(1107, 3, 3, 3, 99))
		comp.run()
		assert(comp[3] == 0)

		comp = Computer(listOf(1107, 6, 3, 3, 99))
		comp.run()
		assert(comp[3] == 0)
	}

	private fun testJumpIfFalse() {
		println("test Jump If False")
		var comp = Computer(listOf(1106, 0, -1))
		comp.executeNext()
		assert(comp.instructionPointer == -1)

		comp = Computer(listOf(1106, 1, -1))
		comp.executeNext()
		assert(comp.instructionPointer == 3)
	}

	private fun testJumpIfTrue() {
		println("test Jump If True")
		var comp = Computer(listOf(1105, 1, -1))
		comp.executeNext()
		assert(comp.instructionPointer == -1)

		comp = Computer(listOf(1105, 0, -1))
		comp.executeNext()
		assert(comp.instructionPointer == 3)
	}

	private fun testOpCodeParsing() {
		println("test OpCode parsing")
		var code = Computer.OpCode.from(1001)
		assert(code.opCode == 1)
		assert(code.modes[1] == Computer.ParameterMode.Position)
		assert(code.modes[2] == Computer.ParameterMode.Immediate)

		code = Computer.OpCode.from(1102)
		assert(code.opCode == 2)
		assert(code.modes[1] == Computer.ParameterMode.Immediate)
		assert(code.modes[2] == Computer.ParameterMode.Immediate)

		code = Computer.OpCode.from(101)
		assert(code.opCode == 1)
		assert(code.modes[1] == Computer.ParameterMode.Immediate)
		assert(code.modes[2] == Computer.ParameterMode.Position)
	}

	private fun testAdd() {
		println("test Add")
		val comp = Computer(listOf(1, 1, 2, 3, 99))
		comp.run()
		assert(comp[3] == 3)
	}

	private fun testMul() {
		println("test Mul")
		val comp = Computer(listOf(2, 1, 2, 3, 99))
		comp.run()
		assert(comp[3] == 2)
	}

	private fun testParaMode() {
		println("test Para Mode")
		var comp = Computer(listOf(1101, 5, 10, 3, 99))
		comp.run()
		assert(comp[3] == 15)

		comp = Computer(listOf(1001, 1, 10, 3, 99))
		comp.run()
		assert(comp[3] == 11)

		comp = Computer(listOf(101, 5, 2, 3, 99))
		comp.run()
		assert(comp[3] == 7)
	}
}