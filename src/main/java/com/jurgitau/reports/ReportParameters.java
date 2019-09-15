package com.jurgitau.reports;

import java.io.Serializable;

public class ReportParameters implements Serializable {
    public ReportTemplate reportTemplate;
    public ReportFormat format;

    public ReportParameters() {
    }

    public ReportParameters(ReportTemplate reportTemplate,
                            ReportFormat format) {
        this.reportTemplate = reportTemplate;
        this.format = format;
    }
}