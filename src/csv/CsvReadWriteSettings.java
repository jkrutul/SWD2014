package csv;

public class CsvReadWriteSettings {
	private String delimeter = ",";
	private boolean remove_white_spaces = true;
	private boolean firstRowHasColumnNames = false;
	
	public CsvReadWriteSettings(){
		
	}
	
	public CsvReadWriteSettings(String delimeter, boolean remove_white_spaces,	boolean firstRowHasColumnNames) {
		this.delimeter = delimeter;
		this.remove_white_spaces = remove_white_spaces;
		this.firstRowHasColumnNames = firstRowHasColumnNames;
	}



	public String getDelimeter() {
		return delimeter;
	}

	public void setDelimeter(String delimeter) {
		this.delimeter = delimeter;
	}

	public boolean isRemove_white_spaces() {
		return remove_white_spaces;
	}

	public void setRemove_white_spaces(boolean remove_white_spaces) {
		this.remove_white_spaces = remove_white_spaces;
	}

	public boolean isFirstRowHasColumnNames() {
		return firstRowHasColumnNames;
	}

	public void setFirstRowHasColumnNames(boolean firstRowHasColumnNames) {
		this.firstRowHasColumnNames = firstRowHasColumnNames;
	}

}
