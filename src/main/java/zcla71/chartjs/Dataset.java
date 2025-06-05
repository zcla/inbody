package zcla71.chartjs;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Dataset {
	private String label;
	private List<Float> data;
	private Float tension;
}
