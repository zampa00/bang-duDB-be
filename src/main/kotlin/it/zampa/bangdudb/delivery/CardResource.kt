package it.zampa.bangdudb.delivery

import it.zampa.bangdudb.domain.Card
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["http://localhost:8080", "http://localhost:8100", "http://localhost:21472"])
class CardResource(val service: CardService) {


	@GetMapping("/cards")
	fun index(): List<Card> = service.findMessages()

	@PostMapping("/addCard")
	fun post(@RequestBody card: Card) {
		service.post(card)
	}

}