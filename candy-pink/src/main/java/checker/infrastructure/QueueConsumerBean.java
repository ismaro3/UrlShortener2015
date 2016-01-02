package checker.infrastructure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by david on 1/01/16.
 */
@Component
public class QueueConsumerBean {

    protected final Logger LOG = LoggerFactory.getLogger(getClass());

    @Resource
    protected LinkedBlockingQueue<String> sharedQueue;

    @Autowired
    protected AdapterToExtern adapter;

    @Async
    public void extractAndCheck(){
        LOG.info("La cola esta esperando a que haya algo metido");
        while(true){
        try {
            String url = sharedQueue.take();
            LOG.info("Se ha metido algo en la cola");
            LOG.info("Comprobando " + url);
            Map<String,Boolean> map = adapter.checkUrl(url);
            LOG.info("La url es segura: " + map.get("Safe"));
            LOG.info("La url es alcanzable: " + map.get("Reachable"));
         } catch (InterruptedException e) {}

        }
    }
}
