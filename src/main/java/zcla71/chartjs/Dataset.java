package zcla71.chartjs;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Dataset {
	private String type;
	private String label;
	private List<Object> data;
	private Float tension;
	private String borderColor;
	@JsonProperty("yAxisID")
	private String yAxisID;

	public Dataset(String label, List<Object> data, Float tension) {
		this(label, data, tension, null);
	}

	public Dataset(String label, List<Object> data, Float tension, String borderColor) {
		this(null /* type */, label, data, tension, borderColor, "y");
	}

	public Dataset(String label, List<Object> data, Float tension, String borderColor, String yAxisID) {
		this(null /* type */, label, data, tension, borderColor, yAxisID);
	}

	public Dataset(String type, String label, List<Object> data, String yAxisID) {
		this(type, label, data, null/* tension */, null /* borderColor */, yAxisID);
	}
}
