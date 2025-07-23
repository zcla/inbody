package zcla71.chartjs;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Scale {
	private String type;
	private List<String> labels;
	private String stack;
	private Integer stackWeight;
}
