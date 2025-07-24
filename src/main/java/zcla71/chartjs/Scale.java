package zcla71.chartjs;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Scale {
	private String type;
	private List<String> labels;
	private String position;
	private Boolean reverse;

	public Scale(String type, String position) {
		this(type, null, position, false);
	}
}
