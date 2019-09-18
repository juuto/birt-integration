package com.jurgitau;

import com.jurgitau.download.DownloadReportAjaxSubmitLink;
import com.jurgitau.exceptions.FieldRequiredException;
import com.jurgitau.reports.ReportFormat;
import com.jurgitau.reports.ReportParameters;
import com.jurgitau.reports.ReportRenderer;
import com.jurgitau.reports.ReportTemplate;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.util.ArrayList;
import java.util.EnumSet;

public class ReportsPage extends WebPage {

    private IModel<ReportParameters> model;

    public ReportsPage() {
        model = new LoadableDetachableModel<ReportParameters>() {
            @Override
            protected ReportParameters load() {
                return new ReportParameters();
            }
        };
        setDefaultModel(CompoundPropertyModel.of(model));
    }

    public ReportsPage(final PageParameters parameters) {
        super(parameters);

        model = new LoadableDetachableModel<ReportParameters>() {
            @Override
            protected ReportParameters load() {
                return new ReportParameters();
            }
        };
        setDefaultModel(CompoundPropertyModel.of(model));
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        Form<ReportParameters> form = new Form<>("form");
        final FeedbackPanel feedback = new FeedbackPanel("feedback");

        add(form.add(
                new DropDownChoice("reportTemplate", new ArrayList(EnumSet.allOf(ReportTemplate.class)),
                    new ChoiceRenderer<ReportTemplate>("reportName")),
                new DropDownChoice("format", new ArrayList(EnumSet.allOf(ReportFormat.class))),

                feedback
                    .setOutputMarkupId(true)
                    .setOutputMarkupPlaceholderTag(true),

                new AjaxSubmitLink("generate") {
                    @Override
                    public void onSubmit(AjaxRequestTarget target) {
                        ReportParameters backer = model.getObject();
                        try {
                            validateInput(backer);

                            final ReportRenderer renderer = new ReportRenderer();
                            String result = renderer.generateReport(
                                backer.reportTemplate.getTemplateId(),
                                backer.format.toString()
                            );

                            feedback.add(new AttributeModifier("class", "success"));
                            feedback.success("Report successfully saved in " + result);
                            target.add(feedback);
                        } catch(FieldRequiredException e) {
                            feedback.add(new AttributeModifier("class", "error"));
                            feedback.warn(e.getMessage());
                            target.add(feedback);
                        } catch(Exception e) {
                            feedback.add(new AttributeModifier("class", "error"));
                            feedback.warn("Did not succeed to generate the report");
                            target.add(feedback);
                        }
                    }
                },

                new DownloadReportAjaxSubmitLink("download") {
                    @Override
                    public void onSubmit(AjaxRequestTarget target) {
                        ReportParameters backer = model.getObject();
                        try {
                            validateInput(backer);

                            setTemplate(backer.reportTemplate);
                            setFormat(backer.format);
                            super.onSubmit(target);

                        } catch(FieldRequiredException e) {
                            feedback.add(new AttributeModifier("class", "error"));
                            feedback.warn(e.getMessage());
                            target.add(feedback);
                        } catch(Exception e) {
                            feedback.add(new AttributeModifier("class", "error"));
                            feedback.warn("Did not succeed to generate the report");
                            target.add(feedback);
                        }
                    }
                }
        ));

        add(new NavigationPanel("menu"));
    }

    public void validateInput(ReportParameters backer) {
        if (backer.reportTemplate == null) {
            throw new FieldRequiredException(getString("reportName"));
        }
        if (backer.format == null) {
            throw new FieldRequiredException(getString("format"));
        }
    }
}
