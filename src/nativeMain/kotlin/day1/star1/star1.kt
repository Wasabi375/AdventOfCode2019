
package day1.star1

fun calcFuel(mass: Int): Int = (mass / 3) - 2

fun main() {
	println("Day 1, Star 1")
	val solution = input.map (::clacFuel).sum()
	println("Solution: $solution")
}