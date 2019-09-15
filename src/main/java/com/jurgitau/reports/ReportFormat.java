package com.jurgitau.reports;

public enum ReportFormat {
    PDF, XLS, HTML;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
