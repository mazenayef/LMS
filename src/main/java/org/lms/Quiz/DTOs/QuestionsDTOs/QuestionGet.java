package org.lms.Quiz.DTOs.QuestionsDTOs;

import org.lms.Quiz.Models.Question;

public class QuestionGet {
	public Integer id;
	public String body;
	public String[] choices;
	public Integer courseId;
	public QuestionGet(Integer id, String body, String[] choices, Integer courseId){
		this.id = id;
		this.body = body;
		this.choices = choices;
		this.courseId = courseId;
	}

	public static QuestionGet fromQuestion(Question question) {
		return new QuestionGet(question.getId(), question.getBody(), question.getChoices(), question.getCourseId());
	}
}
