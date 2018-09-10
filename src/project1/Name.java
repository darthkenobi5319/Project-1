package project1;

/**
 * This is a public class that can instantiate a class Name
 * @author Zhenghan Zhang
 */
public class Name implements Comparable<Name>{
	private String name;
	private String gender;
	private int count;

	/**
	 * Constructs a new Name object with specified name,gender and count. 
	 * @param name String name.
	 * @param gender Gender of the name.
	 * @param count int value of occurrence of a name.
	 * @throws IllegalArgumentException if any of the parameters are invalid
	 */
	public Name(String name, String gender, int count) throws IllegalArgumentException {
		setName(name);
		setGender(gender);
		setCount(count);
	}
	
	/**
	 * get the name
	 * @return the name of the Name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * get the count
	 * @return the count of the Name
	 */
	public int getCount() {
		return this.count;
	}
	
	/**
	 * Set the name 
	 * @param name the name.
	 * @throws IlleagalArgumentException if name is empty
	 */
	private void setName(String name) throws IllegalArgumentException{
		if ((name != null && name.equals("")) || name == null) {
			throw new IllegalArgumentException("Invalid Name. Name should not be empty");
		}
		this.name = name;
	}
	
	/**
	 * Set the gender 
	 * @param gender the gender of the name.Should be f or m
	 * @throws IlleagalArgumentException if count is not f or m
	 */
	private void setGender(String gender) throws IllegalArgumentException{
		if ((gender != null && !gender.equalsIgnoreCase("f") && !gender.equalsIgnoreCase("m") )|| gender == null) {
			throw new IllegalArgumentException("Invalid Gender. Gender should be either 'f' or 'm' ");
		}
		this.gender = gender;
	}
	
	/**
	 * Set the count 
	 * @param count the count of the name.Should be positive
	 * @throws IlleagalArgumentException if count is negative
	 */	
	private void setCount(int count) throws IllegalArgumentException{
		if (count < 0) {
			throw new IllegalArgumentException("Invalid Count. Count should not be negative");
		}
		this.count = count;
	}
	
	//override the equals method
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Name))
			return false;
		Name other = (Name) obj;
		if (this.name.equalsIgnoreCase(other.name) && 
				this.gender.equalsIgnoreCase(other.gender) && 
				this.count == (other.count))
			return true;
		return false;
	}
	
	//override the compareTo method
	
	@Override
	public int compareTo(Name n) {
		if (this.name.compareToIgnoreCase(n.name) != 0) {
			return this.name.compareToIgnoreCase(n.name);
		}else {
			if (this.count != n.count) {
				return this.count - n.count;
			}
			else {
				if (this.gender.compareToIgnoreCase(n.gender) != 0) {
					return this.gender.compareToIgnoreCase(n.gender);
				}
				return 0;
			}
		}
	}
	
	//The toString method
	
	public String toString () { 
			return String.format("%s,%s,%d",name,gender,count); 
		}
	
}