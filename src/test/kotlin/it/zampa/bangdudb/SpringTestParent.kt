package it.zampa.bangdudb

import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(
	classes = [BangdudbApplication::class, BangdudbTestConfiguration::class],
	webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class SpringTestParent
