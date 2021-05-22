package it.zampa.bangdudb.delivery

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import it.zampa.bangdudb.delivery.data.InputCard
import it.zampa.bangdudb.domain.Card
import it.zampa.bangdudb.domain.usecase.AddCardUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile


@RestController
@CrossOrigin
class CardResource(val service: CardService, val addCardUseCase: AddCardUseCase) {

	private val mapper = ObjectMapper()
		.registerModule(KotlinModule())
		.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

	@GetMapping("/cards")
	fun index(): List<Card> = service.findCards()

	@GetMapping("/searchCards")
	@ResponseBody
	fun getCardsFrom(
		@RequestParam(name = "characters") characters: List<String>?,
		@RequestParam(name = "bands") bands: List<String>?,
		@RequestParam(name = "rarities") rarities: List<String>?,
		@RequestParam(name = "attributes") attributes: List<String>?,
		@RequestParam(name = "skill_session_types") skill_session_types: List<String>?,
		@RequestParam(name = "allowGacha") is_gacha: Boolean?,
		@RequestParam(name = "allowUnavailableGacha") is_unavailable_gacha: Boolean?,
		@RequestParam(name = "allowEvent") is_event: Boolean?,
		@RequestParam(name = "allowEvent") is_birthday: Boolean?,
		@RequestParam(name = "allowPromo") is_promo: Boolean?,
	): List<Card> {
		System.out.println("${rarities}")
		return service.findCards(
			characters, bands, rarities?.map { e -> e.toString() }, attributes, skill_session_types, is_gacha, is_unavailable_gacha, is_event, is_birthday, is_promo
		)
	}

	@GetMapping("/card/{cardId}")
	@ResponseBody
	fun getCardFromId(@PathVariable cardId: String): Card? {
		return service.findCard(cardId)
	}

	@PostMapping("/addCard")
	fun post(@RequestBody card: Card) {
		service.post(card)
	}

	@PostMapping("/addSingleCard")
	fun test(@RequestParam cardDetails: String, @RequestParam imgBase: MultipartFile, @RequestParam imgIdl: MultipartFile): ResponseEntity<String> {
		return try {
			val card = mapper.readValue(cardDetails, InputCard::class.java)

			addCardUseCase.execute(card, imgBase, imgIdl)

			ResponseEntity<String>(HttpStatus.OK)
		} catch (e: MissingKotlinParameterException) {
			println("the missing parameter is: ${e.parameter.name}")
			ResponseEntity("""{
						"missing parameter": "${e.parameter.name}"
					}""".trimIndent(), HttpStatus.BAD_REQUEST)
		}
	}

}