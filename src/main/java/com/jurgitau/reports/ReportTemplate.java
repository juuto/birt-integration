package com.jurgitau.reports;

public enum ReportTemplate {
    R1("r-lithuania-cities", "Lithuanian cities");

    private String templateId;
    private String reportName;

    ReportTemplate(String templateId, String reportName) {
        this.templateId = templateId;
        this.reportName = reportName;
    }

    public String getTemplateId() {
        return templateId;
    }

    public String getReportName() {
        return reportName;
    }
}
