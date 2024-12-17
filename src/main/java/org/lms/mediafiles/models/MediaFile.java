package org.lms.mediafiles.models;


public class MediaFile {
	private Integer id;
	private String title;
	private String type;
	private String path;

	public MediaFile(Integer id, String title, String type, String path) {
		this.id = id;
		this.title = title;
		this.type = type;
		this.path = path;
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
}
