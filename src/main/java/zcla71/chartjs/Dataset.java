package zcla71.chartjs;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Dataset {
	private String label;
	private List<Object> data;
	private Float tension;
	private String borderColor;
	private Boolean stepped;
	@JsonProperty("yAxisID")
	private String yAxisID;

	public Dataset(String label, List<Object> data, Float tension) {
		this(label, data, tension, null);
	}

	public Dataset(String label, List<Object> data, Float tension, String borderColor) {
		this(label, data, tension, borderColor, null, "y");
	}
}
