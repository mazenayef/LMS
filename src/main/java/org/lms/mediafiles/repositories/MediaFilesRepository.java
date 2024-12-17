package org.lms.mediafiles.repositories;

import org.lms.mediafiles.dtos.MediaFileResourceDto;
import org.lms.mediafiles.models.MediaFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MediaFilesRepository {
	private static final List<MediaFile> mediaFileList = new ArrayList<>();
	private static Integer mediaFileIdIncrementer = 1;
	private static final Path rootLocation = Paths.get("media_files");


	public MediaFile create(String fileName, String fileType, String path) {
		MediaFile mediaFile = new MediaFile(mediaFileIdIncrementer, fileName, fileType, path);
		mediaFileList.add(mediaFile);
		mediaFileIdIncrementer++;
		return mediaFile;
	}

	public MediaFileResourceDto findOne(Integer id) throws Exception {
		MediaFile mediaFile = null;
		for (MediaFile it : mediaFileList) {
			if (it.getId() == id) {
				mediaFile = it;
				break;
			}
		}

		if (mediaFile == null) {
			throw new Exception("No MediaFile with the given Id!");
		}

		Resource resource = loadAsResource(mediaFile.getPath());

		return new MediaFileResourceDto(mediaFile, resource);
	}

	public String store(MultipartFile file) throws Exception {
		LocalDateTime now = LocalDateTime.now();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH.mm.ss");

		String formattedNow = now.format(formatter);

		if (file.isEmpty()) {
			throw new Exception("Failed to store empty file.");
		}
		Path destinationFile = rootLocation.resolve(Paths.get(formattedNow + " " + file.getOriginalFilename()))
				.normalize().toAbsolutePath();
		if (!destinationFile.getParent().equals(rootLocation.toAbsolutePath())) {
			// This is a security check
			throw new Exception(
					"Cannot store file outside current directory.");
		}
		try (InputStream inputStream = file.getInputStream()) {
			Files.copy(inputStream, destinationFile,
					StandardCopyOption.REPLACE_EXISTING);
		}
		return destinationFile.toAbsolutePath().toString();
	}

	public Path load(String path) {
		return Paths.get(path);
	}

	public Resource loadAsResource(String path) throws Exception {
		Path file = load(path);
		Resource resource = new UrlResource(file.toUri());
		if (resource.exists() || resource.isReadable()) {
			return resource;
		}
		else {
			throw new Exception("Could not read file: " + path);
		}
	}
}
