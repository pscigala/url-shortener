package pl.forcode.tinyurlservice.shortener;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShortUrlRepository extends CrudRepository<ShortUrl, String> {

}
