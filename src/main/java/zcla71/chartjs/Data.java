package zcla71.chartjs;

import java.util.List;

import lombok.AllArgsConstructor;

@lombok.Data
@AllArgsConstructor
// https://www.chartjs.org/docs/latest/general/data-structures.html
public class Data {
	List<String> labels;
	List<Dataset> datasets;
}
