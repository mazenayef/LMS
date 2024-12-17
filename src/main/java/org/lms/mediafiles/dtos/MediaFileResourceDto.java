package org.lms.mediafiles.dtos;

import org.lms.mediafiles.models.MediaFile;
import org.springframework.core.io.Resource;

public class MediaFileResourceDto {
	private Integer id;
	private String title;
	private String type;
	private String path;
	private Resource resource;

	public MediaFileResourceDto(Integer id, String title, String type, String path, Resource resource) {
		this.id = id;
		this.title = title;
		this.type = type;
		this.path = path;
		this.resource = resource;
	}

	public MediaFileResourceDto(MediaFile mediaFile, Resource resource) {
		this.id = mediaFile.getId();
		this.title = mediaFile.getTitle();
		this.type = mediaFile.getType();
		this.path = mediaFile.getPath();
		this.resource = resource;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}
}
