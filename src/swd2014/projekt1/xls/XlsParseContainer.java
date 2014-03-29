package swd2014.projekt1.xls;

import java.util.List;

import swd2014.projekt1.models.Matrix;

public class XlsParseContainer {
	List<String> columnNames;
	Matrix values;

	public List<String> getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(List<String> columnNames) {
		this.columnNames = columnNames;
	}

	public Matrix getValues() {
		return values;
	}

	public void setValues(Matrix values) {
		this.values = values;
	}

}
