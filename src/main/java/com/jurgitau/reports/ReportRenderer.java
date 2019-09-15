package com.jurgitau.reports;

import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.EngineConstants;
import org.eclipse.birt.report.engine.api.IRenderOption;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.RenderOption;
import org.eclipse.birt.report.engine.api.ReportEngine;
import org.eclipse.birt.report.model.api.DesignElementHandle;
import org.eclipse.birt.report.model.api.OdaDataSourceHandle;
import org.eclipse.birt.report.model.api.ReportDesignHandle;
import org.eclipse.birt.report.model.api.SlotHandle;
import org.eclipse.birt.report.model.api.SlotIterator;

import java.net.URL;

public class ReportRenderer {

    private static final IReportEngine ENGINE = new ReportEngine(new EngineConfig()) ;

    public String generateReport(String templateId, String format) {
        try {
            URL url = getClass().getResource("/templates/" + templateId + ".rptdesign");
            URL resourceLocation = getClass().getResource("/templates/");

            IReportRunnable design = ENGINE.openReportDesign(url.getPath());

            final IRunAndRenderTask task = ENGINE.createRunAndRenderTask(design);
            task.getAppContext().put(EngineConstants.APPCONTEXT_CLASSLOADER_KEY, ReportRenderer.class.getClassLoader());

            ReportDesignHandle report = (ReportDesignHandle) design.getDesignHandle();
            SlotHandle dataSources = report.getDataSources();
            SlotIterator slotIterator = (SlotIterator) dataSources.iterator();
            while (slotIterator.hasNext()) {
                DesignElementHandle dsHandle = (DesignElementHandle) slotIterator.next();
                if ((dsHandle instanceof OdaDataSourceHandle)) {
                    String type = ((OdaDataSourceHandle) dsHandle).getExtensionID();
                    if ("org.eclipse.birt.report.data.oda.excel".equalsIgnoreCase(type)) {
                        String uri = dsHandle.getProperty("URI").toString().replace("\\", "/");
                        String[] uriSplitted =  uri.split("/");
                        String dataSourceName = uriSplitted[uriSplitted.length - 1];

                        dsHandle.setProperty("URI", resourceLocation + dataSourceName);
                    }
                }
            }

            final IRenderOption options = new RenderOption();
            options.setOutputFormat(format);
            String fileName = "C:/tmp/" + templateId + "." + options.getOutputFormat();
            options.setOutputFileName(fileName);

            task.setRenderOption(options);

            task.run();

            task.close();
            return fileName;

        } catch(Exception ex) {
            ex.printStackTrace();
            return ex.getMessage();
        }

    }

}
