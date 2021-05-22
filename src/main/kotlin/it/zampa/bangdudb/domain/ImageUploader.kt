package it.zampa.bangdudb.domain

import org.springframework.web.multipart.MultipartFile

interface ImageUploader {

	fun uploadCard(img: MultipartFile, imageName: String): String

}