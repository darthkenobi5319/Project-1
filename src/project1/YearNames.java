package project1;
import java.util.ArrayList;
import project1.Name;

/**
 * @author Zhenghan Zhang
 */

public class YearNames{
	public ArrayList<Name> nameList;
	public int year;
	
	/**
	 * YearNames that accept a int year and output an arraylist
	 * @param year int value of a certain year
	 * @throws IllegalArgumentException if year if out of range of 1900-2018
	 */
	public YearNames (int year)  throws IllegalArgumentException {
		//validate the range of the year
		if (year < 1900 || year > 2018 )
			throw new IllegalArgumentException("Invalid value for year. "
					+ "Valid range is 1900-2018.");
		
		this.year = year;
		this.nameList = new ArrayList<Name>();
	}
	
	/**
	 * add a name to the ArrayList
	 * @param name a Name Object
	 */
	public void add (Name name){
		//System.out.println(name);
		this.nameList.add(name);
	}
	
	/**
	 * get the year
	 * @return the year of the YearNames
	 */
	public int getYear(){
		return this.year;
	}
	
	/**
	 * get the count of the babies with that name that year
	 * @param name a String of name
	 * @return the int value of the number of occurrences of that name
	 */
	public int getCountByName( String name ) {
		int i = 0;
		for (Name n : this.nameList) {
			if (n.getName().equalsIgnoreCase(name)) {
			i += n.getCount();
			}
		}
		return i;
	}
	
	/**
	 * get the fraction of the babies of the name divided by the total of babies that year
	 * @param name a String of name
	 * @return a double value of the fraction
	 */
	public double getFractionByName(String name) {
		int m = this.getCountByName(name);
		int i = 0;
		for (Name n : this.nameList) {
			i += n.getCount();
		}
		return ((double) m/i);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof YearNames))
			return false;
		YearNames other = (YearNames) obj;
		if (this.year == other.year)
			return true;
		return false;
	}
	
	public String toString () { 
		return String.format("%d",year); 
	}
}

