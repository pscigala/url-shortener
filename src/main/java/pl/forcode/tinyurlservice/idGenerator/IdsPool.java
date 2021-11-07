package pl.forcode.tinyurlservice.idGenerator;

import java.util.Collection;

public record IdsPool(long poolNumber, Collection<String> ids) {

}
