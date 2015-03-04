package webcrawler.core.crawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webcrawler.core.listeners.NewLinkListener;
import webcrawler.core.tasks.PageDomTask;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.List;

/**
 *
 * @author Madalin Ilie
 */
public class Webcrawler {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private String startURl;

    private LinkCrawlingState linkCrawlingState;
    private List<PageDomTask> pageDomTasks;
    private WebCrawlingMainTask webCrawlingMainTask;


    public Webcrawler(List<PageDomTask> pageDomTasks, String startUrl) {
        this.pageDomTasks = pageDomTasks;
        this.startURl = startUrl;
        this.webCrawlingMainTask = new WebCrawlingMainTask();
        linkCrawlingState = new LinkCrawlingState();
        initTrustAllCertificates();
    }

    private void initTrustAllCertificates() {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager(){
            public X509Certificate[] getAcceptedIssuers(){return null;}
            public void checkClientTrusted(X509Certificate[] certs, String authType){}
            public void checkServerTrusted(X509Certificate[] certs, String authType){}
        }};

// Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            ;
        }
    }

    public void startCrawling() {
        VisitUrlTask start = new VisitUrlTask(webCrawlingMainTask, this.pageDomTasks, null, this.startURl, linkCrawlingState);
        webCrawlingMainTask.submitTask(start);
    }

    public void addNewLinkListener(NewLinkListener listener) {
        this.linkCrawlingState.addNewLinkListener(listener);
    }

    public void stopCrawling() {
        logger.info("shutting down");
        webCrawlingMainTask.shutdown();

    }
}