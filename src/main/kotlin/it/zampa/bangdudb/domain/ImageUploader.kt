package it.zampa.bangdudb.domain

import java.io.File
import java.io.InputStream

interface ImageUploader {

	fun uploadCard(img: File, imageName: String): String
	fun uploadCard(img: InputStream, imageName: String): String
	fun uploadBanner(img: File, imageName: String): String
	fun uploadBanner(img: InputStream, imageName: String): String

}