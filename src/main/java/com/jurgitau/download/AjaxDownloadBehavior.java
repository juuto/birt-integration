package com.jurgitau.download;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AbstractAjaxBehavior;
import org.apache.wicket.request.handler.resource.ResourceStreamRequestHandler;
import org.apache.wicket.request.resource.ContentDisposition;
import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.util.time.Duration;

/**
 * AJAX Download Behavior
 * <p>
 * Based on https://cwiki.apache.org/confluence/display/WICKET/AJAX+update+and+file+download+in+one+blow
 * </p>
 *
 * @author Sven Meier
 * @author Ernesto Reinaldo Barreiro (reiern70@gmail.com)
 * @author Jordi Deu-Pons (jordi@jordeu.net)
 */
public abstract class AjaxDownloadBehavior extends AbstractAjaxBehavior {
    private final boolean addTimestamp;

    public AjaxDownloadBehavior() {
        this(true);
    }

    public AjaxDownloadBehavior(boolean addTimestamp) {
        super();
        this.addTimestamp = addTimestamp;
    }

    /**
     * Call this method to initiate the download.
     */
    public void initiate(AjaxRequestTarget target) {
        String url = getCallbackUrl().toString();

        if (addTimestamp) {
            url += (url.contains("?") ? "&" : "?") + "t=" + System.currentTimeMillis();
        }

        // the timeout is needed to let Wicket release the channel
        target.appendJavaScript("setTimeout(\"window.location.href='" + url + "'\", 100);");
    }

    public void onRequest() {
        ResourceStreamRequestHandler handler = new ResourceStreamRequestHandler(getResourceStream(), getFileName());
        handler.setCacheDuration(Duration.NONE);
        handler.setContentDisposition(ContentDisposition.ATTACHMENT);
        getComponent().getRequestCycle().scheduleRequestHandlerAfterCurrent(handler);
    }

    /**
     * Override this method for a file name which will let the browser prompt with a save/open dialog.
     */
    protected String getFileName() {
        return null;
    }

    /**
     * Hook method providing the actual resource stream.
     */
    protected abstract IResourceStream getResourceStream();
}
