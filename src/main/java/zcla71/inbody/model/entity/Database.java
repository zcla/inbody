package zcla71.inbody.model.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Database {
	private List<Pessoa> pessoas;

	public Database() {
		this.pessoas = new ArrayList<>();
	}
}
