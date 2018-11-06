package org.sklsft.generator.bc.checker;

/**
 * A project report is handled by a ThreadLocal
 * 
 * @author Nicolas Thibault
 *
 */
public class ProjectBuildupReportHolder {

	private static ThreadLocal<ProjectBuildupReport> reports = new ThreadLocal<>();

	public static void bind(ProjectBuildupReport report) {
		if (report == null) {
			throw new NullPointerException("Cannot bind report : provided report is null");
		}

		ProjectBuildupReport currentReport = getReportOrNull();
		if (currentReport != null) {
			throw new IllegalStateException("Report has already been bound to the Thread");
		}

		reports.set(report);
	}

	
	public static void unbind() {
		reports.remove();
	}

	public static ProjectBuildupReport getReportOrNull() {
		return reports.get();
	}

	

	public static ProjectBuildupReport getReport() {
		ProjectBuildupReport report = getReportOrNull();
		if (report == null) {
			throw new IllegalStateException("No report bound to Thread");
		}
		return report;
	}
}
