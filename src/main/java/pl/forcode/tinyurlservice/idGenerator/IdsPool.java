package pl.forcode.tinyurlservice.idGenerator;

import java.util.Collection;

public record IdsPool(int poolNumber, Collection<String> ids) {

}
