package checker.infrastructure;

import java.util.concurrent.LinkedBlockingQueue;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import urlshortener2015.candypink.domain.ShortURL;

@Component
public class URLQueueImpl implements URLQueue{
	
	@Autowired
	private LinkedBlockingQueue<ShortURL> queue;


	@Override
	public ShortURL take(){
		try{
			return queue.take();
		}catch (InterruptedException e){
			return null;
		}
	}

	@Override
	public boolean add(ShortURL element){
		try{
			return queue.add(element);
		}catch(IllegalStateException e){
			return false;
		}
	}
}