package it.zampa.bangdudb.domain.service

import java.io.File
import java.io.InputStream

interface ImageUploader {
	fun upload(img: File, imageName: String): String
	fun upload(img: InputStream, imageName: String): String
}