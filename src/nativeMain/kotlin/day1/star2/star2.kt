package day1.star2

import kotlin.math.max

fun calcFuelOnce(mass: Int) = day1.star1.calcFuel(mass)

fun calcFuel(mass: Int): Int {
	val fuel = max(calcFuelOnce(mass), 0)
	return if(fuel == 0) 0
	else fuel + calcFuel(fuel)
}

fun main() {
	println("Day 1, Star 2")
	val solution = input.map (::calcFuel).sum()
	println("Solution: $solution")
}