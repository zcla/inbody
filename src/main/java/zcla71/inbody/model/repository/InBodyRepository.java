package zcla71.inbody.model.repository;

import org.springframework.stereotype.Component;

import zcla71.inbody.model.entity.Database;
import zcla71.repository.JsonRepository;

@Component
public class InBodyRepository extends JsonRepository<Database> {
	private static final String JSON_FILE_LOCATION = "data/baudoze.json";

	public InBodyRepository() throws Exception {
		super(Database.class, JSON_FILE_LOCATION, Boolean.TRUE);
	}
}
