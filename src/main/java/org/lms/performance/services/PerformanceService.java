package org.lms.performance.services;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.lms.Quiz.Models.Quiz;
import org.lms.Quiz.Models.QuizAttempt;
import org.lms.Quiz.Services.QuizService;
import org.lms.course.CourseService;
import org.lms.lesson.models.Lesson;
import org.lms.lesson.services.LessonService;
import org.lms.user.User;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class PerformanceService {
	private QuizService quizService;
	private CourseService courseService;
	private LessonService lessonService;

	public PerformanceService(QuizService quizService, CourseService courseService, LessonService lessonService) {
		this.quizService = quizService;
		this.courseService = courseService;
		this.lessonService = lessonService;
	}

	public Resource generateExcel(Integer courseId) throws Exception {
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("Performance");
		List<String> columnHeadings = new ArrayList<>();
		columnHeadings.add("Student ID");
		List<Quiz> totalQuizzes = (List<Quiz>) this.quizService.getAll().get().data;
		for (int i = 0; i < totalQuizzes.size(); i++) {
			columnHeadings.add("Quiz " + totalQuizzes.get(i).getId() + " (" + totalQuizzes.get(i).getWeight() + ")");
		}

		List<Integer> totalLessons = this.lessonService.getLessonOfCourse(courseId);
		for (int i = 0; i < totalLessons.size(); i++) {
			columnHeadings.add("Lesson " + totalLessons.get(i));
		}

		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 12);
		headerFont.setColor(IndexedColors.BLACK.index);

		CellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setFont(headerFont);
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);

		Row headerRow = sheet.createRow(0);
		for (int i = 0; i < columnHeadings.size(); i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columnHeadings.get(i));
			cell.setCellStyle(headerStyle);
		}

		List<Integer> userIds = this.courseService.getStudents(courseId);

		for (int i = 0; i < userIds.size(); i++) {
			Row row = sheet.createRow(i + 1);
			Cell cell = row.createCell(0);
			cell.setCellValue(userIds.get(i));

			List<QuizAttempt> attempts = (List<QuizAttempt>) this.quizService.getStudentAttempt(userIds.get(i)).data;
			List<Integer> attendances = this.lessonService.getStudentAttendance(courseId, userIds.get(i));

			for (int j = 0; j < totalQuizzes.size(); j++) {
				Cell cell1 = row.createCell(j + 1);
				Quiz quiz = totalQuizzes.get(j);
				QuizAttempt attempt = attempts.stream()
						.filter(qa -> qa.getQuizId() == quiz.getId())
						.findFirst()
						.orElse(null);
				if (attempt != null) {
					if (attempt.getGrade() == -1) {
						cell1.setCellValue("Not Graded");
					} else {
						Integer totalQuestions = quiz.getNumberOfQuestions();

						Integer totalGrade = attempt.getGrade();

						Float percentage = (float) totalGrade / totalQuestions;

						cell1.setCellValue(quiz.getWeight() * percentage);
					}
				} else {
					cell1.setCellValue(0); // or any other default value
				}
			}

			for (int j = totalQuizzes.size(); j < totalQuizzes.size() + totalLessons.size(); j++) {
				Cell cell1 = row.createCell(j + 1);
				Integer lesson = totalLessons.get(j - totalQuizzes.size());
				Integer attendance = attendances.stream()
						.filter(n -> n == lesson)
						.findFirst()
						.orElse(null);

				if (attendance != null) {
					cell1.setCellValue(1);
				} else {
					cell1.setCellValue(0); // or any other default value
				}
			}
		}

		CreationHelper creationHelper = workbook.getCreationHelper();
		CellStyle dateStyle = workbook.createCellStyle();
		dateStyle.setDataFormat(creationHelper.createDataFormat().getFormat("dd/MM/yyyy"));
		int rowNum = 1;


		String folderPath = "performance_tracker";
		Files.createDirectories(Paths.get(folderPath));

		String filePath = folderPath + "/Performance.xlsx";
		FileOutputStream fileOutputStream = new FileOutputStream(filePath);
		workbook.write(fileOutputStream);
		fileOutputStream.close();
		workbook.close();

		FileInputStream fileInputStream = new FileInputStream(filePath);
		return new InputStreamResource(fileInputStream);
	}
}
