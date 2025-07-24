package zcla71.chartjs;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // backgroundColor muda de cor sem isso
public class Dataset {
	private String type;
	private String label;
	private List<Object> data;
	private Float tension;
	private String borderColor;
	private String backgroundColor;
	@JsonProperty("yAxisID")
	private String yAxisID;

	public Dataset(String label, List<Object> data, Float tension) {
		this(label, data, tension, null);
	}

	public Dataset(String label, List<Object> data, Float tension, String borderColor) {
		this(null /* type */, label, data, tension, borderColor, null /* backgroundColor */, "y");
	}

	public Dataset(String label, List<Object> data, Float tension, String borderColor, String yAxisID) {
		this(null /* type */, label, data, tension, borderColor, null /* backgroundColor */, yAxisID);
	}

	public Dataset(String type, String label, List<Object> data, String backgroundColor, String yAxisID) {
		this(type, label, data, null/* tension */, null /* borderColor */, backgroundColor, yAxisID);
	}
}
