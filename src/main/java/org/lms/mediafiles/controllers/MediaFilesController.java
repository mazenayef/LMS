package org.lms.mediafiles.controllers;

import org.lms.mediafiles.dtos.MediaFileResourceDto;
import org.lms.mediafiles.services.MediaFilesService;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/media")
public class MediaFilesController {
	private MediaFilesService mediaFilesService;

	public MediaFilesController(MediaFilesService mediaFilesService) {
		this.mediaFilesService = mediaFilesService;
	}

	@GetMapping("/{id}")
	public ResponseEntity<Resource> getMediFile(@PathVariable("id") String id) throws Exception {
		MediaFileResourceDto mediaFileResourceDto = this.mediaFilesService.getMediaResource(Integer.parseInt(id));

		return ResponseEntity.ok().body(mediaFileResourceDto.getResource());
	}
}
