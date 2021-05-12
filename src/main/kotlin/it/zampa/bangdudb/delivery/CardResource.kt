package it.zampa.bangdudb.delivery

import it.zampa.bangdudb.domain.Card
import org.springframework.web.bind.annotation.*


@RestController
@CrossOrigin
class CardResource(val service: CardService) {

	@GetMapping("/cards")
	fun index(): List<Card> = service.findCards()

	@GetMapping("/searchCards")
	@ResponseBody
	fun getCardsFrom(
		@RequestParam(name = "characters") characters: List<String>
	): List<Card> = service.findCards(characters)

	@GetMapping("/card/{cardId}")
	@ResponseBody
	fun getCardFromId(@PathVariable cardId: String): Card? {
		return service.findCard(cardId)
	}

	@PostMapping("/addCard")
	fun post(@RequestBody card: Card) {
		service.post(card)
	}

}