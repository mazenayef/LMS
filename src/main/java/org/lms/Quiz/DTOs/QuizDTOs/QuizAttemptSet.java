package org.lms.Quiz.DTOs.QuizDTOs;

import org.lms.Quiz.DTOs.Pair;

import java.time.LocalDateTime;
import java.util.List;

public class QuizAttemptSet {
	public Integer attemptId;
	public List<Pair<Integer,Integer>> answers;    //two foriegn keys one for the question the second for the answer
}
