plugins {
	kotlin("multiplatform") version "1.3.61"
}

repositories {
	mavenCentral()
}

kotlin {
	macosX64("native") {
		binaries {
			executable("all"){
				entryPoint = "main"
			}
			executable("star1") {
				entryPoint = "day1.star1.main"
			}
			executable("star2") {
				entryPoint = "day1.star2.main"
			}
			executable("star3") {
				entryPoint = "day2.star3.star3"
			}
			executable("star4") {
				entryPoint = "day2.star4.star4"
			}
			executable("star5") {
				entryPoint = "day3.star5.star5"
			}
			executable("star6") {
				entryPoint = "day3.star6.star6"
			}
		}
	}
}

tasks.withType<Wrapper> {
	gradleVersion = "5.6.2"
	distributionType = Wrapper.DistributionType.ALL
}