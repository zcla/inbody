package zcla71.chartjs;

import java.util.List;

import lombok.Data;

@Data
public class Dataset {
	private String label;
	private List<Float> data;
	private Float tension;
	private String borderColor;

	public Dataset(String label, List<Float> data, Float tension) {
		this.label = label;
		this.data = data;
		this.tension = tension;
	}

	public Dataset(String label, List<Float> data, Float tension, String borderColor) {
		this.label = label;
		this.data = data;
		this.tension = tension;
		this.borderColor = borderColor;
	}
}
