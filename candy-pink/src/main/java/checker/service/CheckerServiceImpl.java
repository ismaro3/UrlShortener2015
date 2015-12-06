package checker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import urlshortener2015.candypink.domain.ShortURL;
import checker.domain.Checker;

@Service
public class CheckerServiceImpl implements CheckerService{

	@Autowired
	Checker checker;

	@Override
	public void checkURL(ShortURL url){
		checker.checkURL(url);
	}
}
