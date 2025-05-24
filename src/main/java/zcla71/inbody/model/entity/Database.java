package zcla71.inbody.model.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import zcla71.inbody.controller.model.dto.Pessoa;

@Data
public class Database {
	private List<Pessoa> pessoas;

	public Database() {
		this.pessoas = new ArrayList<>();
	}
}
