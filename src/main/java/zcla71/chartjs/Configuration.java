package zcla71.chartjs;

import lombok.AllArgsConstructor;

@lombok.Data
@AllArgsConstructor
// https://www.chartjs.org/docs/latest/configuration/
public class Configuration {
	private String type;
	private Data data;
}
