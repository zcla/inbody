package zcla71.chartjs;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Options {
	private Map<String, Scale> scales;
}
