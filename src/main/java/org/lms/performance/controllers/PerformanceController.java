package org.lms.performance.controllers;

import org.lms.performance.services.PerformanceService;
import org.lms.shared.annotations.ExcludeFromCommonResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/courses/{id}/performance")
public class PerformanceController {
	private PerformanceService performanceService;

	public PerformanceController(PerformanceService performanceService) {
		this.performanceService = performanceService;
	}

	@ExcludeFromCommonResponse
	@GetMapping("/")
	public Resource generateExcel(@PathVariable("id") String id) throws Exception {
		return this.performanceService.generateExcel(Integer.parseInt(id));
	}
}
