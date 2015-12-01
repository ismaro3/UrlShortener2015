package urlshortener2015.common.repository;

import java.util.List;

import urlshortener2015.common.domain.Click;

public interface ClickRepository {

	User findByName(String name);

	Click save(Click cl);

	void update(Click cl);

	void delete(Long id);

	void deleteAll();

	Long count();

	List<Click> list(Long limit, Long offset);
}
