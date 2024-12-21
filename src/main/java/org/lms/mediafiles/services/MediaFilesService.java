package org.lms.mediafiles.services;

import org.lms.mediafiles.dtos.MediaFileResourceDto;
import org.lms.mediafiles.models.MediaFile;
import org.lms.mediafiles.repositories.MediaFilesRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MediaFilesService {
	private MediaFilesRepository mediaFilesRepository;

	public MediaFilesService(MediaFilesRepository mediaFilesRepository) {
		this.mediaFilesRepository = mediaFilesRepository;
	}

	public MediaFile createMedia(MultipartFile file) throws Exception{
		String path = this.mediaFilesRepository.store(file);
		MediaFile mediaFile = this.mediaFilesRepository.create(file.getOriginalFilename(), file.getContentType(), path);

		return mediaFile;
	}

	public MediaFile getMedia(Integer id) throws Exception {
		MediaFileResourceDto mediaFileResourceDto = this.mediaFilesRepository.findOne(id);
		return new MediaFile(mediaFileResourceDto.getId(), mediaFileResourceDto.getTitle(), mediaFileResourceDto.getType(), mediaFileResourceDto.getPath());
	}

	public MediaFileResourceDto getMediaResource(Integer id) throws Exception {
		return this.mediaFilesRepository.findOne(id);
	}
}
