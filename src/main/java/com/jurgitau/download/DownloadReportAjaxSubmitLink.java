package com.jurgitau.download;

import com.jurgitau.reports.ReportFormat;
import com.jurgitau.reports.ReportRenderer;
import com.jurgitau.reports.ReportTemplate;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.util.resource.AbstractResourceStream;
import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.util.resource.ResourceStreamNotFoundException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class DownloadReportAjaxSubmitLink extends AjaxSubmitLink {
    protected AjaxDownloadBehavior download;
    protected  ReportTemplate template;
    protected ReportFormat format;

    public DownloadReportAjaxSubmitLink(String id) {
        super(id);

        download = new AjaxDownloadBehavior() {
            @Override
            protected IResourceStream getResourceStream() {
                return new AbstractResourceStream() {
                    private ByteArrayInputStream stream;

                    @Override
                    public InputStream getInputStream() throws ResourceStreamNotFoundException {
                        try {
                            final ReportRenderer renderer = new ReportRenderer();
                            byte[] reportData = renderer.downloadReport(template.getTemplateId(), format.toString());
                            if (reportData != null) {
                                stream = new ByteArrayInputStream(reportData);
                            }
                        } catch (Exception e) {
                            throw new ResourceStreamNotFoundException(e);
                        }
                        return stream;
                    }

                    @Override
                    public void close() throws IOException {
                        if (stream != null) {
                            stream.close();
                        }
                    }
                };
            }

            @Override
            protected String getFileName() {
                return template.getReportName() + "." + format.toString().toLowerCase();
            }
        };

        add(download);
    }

    @Override
    protected void onSubmit(AjaxRequestTarget target) {
        download.initiate(target);
    }

    protected void setTemplate(ReportTemplate template) {
        this.template = template;
    }

    protected void setFormat(ReportFormat format) {
        this.format = format;
    }
}
