package checker.infrastructure;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by david on 2/01/16.
 */
@Component
public class ScheduledChecker {

    private String[] urls = {"http://www.unizar.es","http://www.google.es","http://www.twitter.com"};
    @Resource
    protected LinkedBlockingQueue<String> sharedQueue;

    /*
    * This method is executed every 12 (43200000ms)hours after the previous
    * excution
    * */
    @Scheduled(fixedDelay = 10000)//in order to probe every 10 secs
    private void checkingDaemon(){
        String url = urls[new Random().nextInt(3)];
        try {
            sharedQueue.put(url);   //This needs to be blocking
        } catch (InterruptedException e) {}
    }
}
