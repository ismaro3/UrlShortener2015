package checker.infrastructure;

import org.springframework.scheduling.annotation.Async;

import urlshortener2015.common.domain.ShortURL;
import checker.domain.Checker;

public abstract class CheckerImpl implements Checker{

	@Override
	@Async
    public void checkURL(ShortURL url) {
        checkInternal(url);
    }

    protected abstract void checkInternal(ShortURL url);

}