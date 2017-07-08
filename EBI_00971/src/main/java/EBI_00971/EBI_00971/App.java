package EBI_00971.EBI_00971;
import java.util.*;
import java.lang.String;
import java.io.*;

public class App 
{
	/*
	 * This function checks the remaining characters in the string
	 * are consecutive.
	 */
	public static boolean consequentNumber(String lhs, String rhs) {
		int i = 0, j = 0;
		boolean ret = false;
		if (rhs.length() > 1) {
			if ((int)rhs.charAt(0) == (int)(lhs.charAt(0)+1)) {
				++i;++j;
				while (i < lhs.length() && j < rhs.length()) {
					if (lhs.charAt(i) != '9' || rhs.charAt(j) != '0') {
						ret = false;
						break;
					}
					++i; ++j;
				}
			} else {
				ret = false;
			}
		} else {
			ret = (int)rhs.charAt(0) == (int)(lhs.charAt(0)+1);
		}
		return ret;
	}
	/*
	 * This function checks that the characters in the lhs and rhs
	 * have same prefixes.
	 */	
	public static boolean consequent(String lhs, String rhs) {
		int i = 0, j = 0;
		boolean ret = false;
		while (lhs.charAt(i) == rhs.charAt(j)) {
			if (!Character.isDigit(lhs.charAt(i))) {
				++i;
			} else {
				break;
			}
			if (!Character.isDigit(rhs.charAt(j))) {
				++j;
			} else {
				break;
			}
		}
		if (i != j) {
			return false;
		}
		if (rhs.length() - j == lhs.length() - i) {
			String lhs_str = lhs.substring(i);
			String rhs_str = rhs.substring(j);
			// lhs_str,rhs_str will contain numerals
			i = 0; j = 0;
			while (i < lhs_str.length() && j < rhs_str.length() &&
					lhs_str.charAt(i) == rhs_str.charAt(j)) {
				++i;
				++j;
			}
			if (i == lhs_str.length()) {
				return true;
			}
			String lhs_sstr = lhs_str.substring(i);
			String rhs_sstr = rhs_str.substring(j);
			ret = consequentNumber(lhs_sstr, rhs_sstr);
		}
		return ret;
	}
	/*
	 * This function find the range of items 
	 * have same prefixes and consecutive suffixes.
	 */		
	public static String findRange(SortedSet<String> setA, String cur) {
		// save the current position
		String temp_cur = cur, ret;
		// access via Iterator
		Iterator<String> iterator = setA.tailSet(cur).iterator();
		String next_elem = cur;
		String elem = cur;
		while (iterator.hasNext()) {
			elem = (String)iterator.next();
			if (elem.equals(cur)) {
				continue;
			}
			next_elem = elem;
			break;
		}
		// End of the list
		if (next_elem.equals(cur)) {
			return cur;
		}
		iterator = setA.tailSet(cur).iterator();
		while (iterator.hasNext()) {
			elem = (String)iterator.next();
			// consequent elements are not same length
			if (elem.length() != next_elem.length()) {
				break;
			}
			if (consequent(elem,next_elem)) {
				Iterator<String> itr = setA.tailSet(next_elem).iterator();
				while (itr.hasNext()) {
					String e = (String)itr.next();
					if (e.equals(next_elem)) {
						continue;
					}
					next_elem = e;
					break;
				}
			} else {
				break;
			}
		}
		if (temp_cur.equals(elem)) {
			// no consecutive numbers
			ret = elem;
		} else {
			ret = temp_cur + " - " + elem;
		}
		return ret;
	}
	public static boolean checkValidItem(String item) {
		int i = 0, dig = 0, non_dig = 0;
		// first character should not be a number
		if (Character.isDigit(item.charAt(0))) {
			return false;
		}
		// Find all continuous non-digits 
		while (i < item.length() && !Character.isDigit(item.charAt(i))) {
			++non_dig;
			++i;
		}
		// Make sure the remaining are all digits
		while (i < item.length() && Character.isDigit(item.charAt(i))) {
			++dig;
			++i;
		}
		if (non_dig == 0 || dig == 0 || i != item.length()) {
			return false;
		}
		return true;
	}
	public static ArrayList<String> accessionNumbers(String input) {
		String[] token = input.split(",");
        SortedSet<String> setA = new TreeSet<String>();
        ArrayList<String> output = new ArrayList<String>();
        for (String s: token) {
        	if (checkValidItem(s)) {
        		String temp_s = s.toUpperCase();
        		setA.add(temp_s);
        	}
        }
        if (setA.isEmpty()) {
        	System.out.println("No Valid Items in the input");
        	return output;
        }
        String cur_item = (String)setA.first();
        Iterator<String> iterator = setA.tailSet(cur_item).iterator();
        while (iterator.hasNext()) {
        	String temp = (String)iterator.next();
        	if (!temp.equals(setA.first()) &&
        			cur_item.equals(temp)) {
        		continue;
        	} else {
        		cur_item = temp;
        	}

        	String ret = findRange(setA, cur_item);
        	output.add(ret);
        	// check if cur_item and ret are same
        	// if they are same then there is no range
        	if (!ret.equals(cur_item)) {
        		// find the range and 
        		ret = ret.trim();
        		ret = ret.replaceAll("\\s+", "");
        		String[] tok = ret.split("-");
        		cur_item = tok[1];
        		// increment the iterator to point to cur_item
        		iterator = setA.tailSet(cur_item).iterator();
        	}
        }
        return output;
	}
    public static void main( String[] args ) throws IOException
    {
        System.out.println( "Enter Accession Numbers" );
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = br.readLine();
        input = input.trim();
        input = input.replaceAll("\\s+", "");
        ArrayList<String> output = accessionNumbers(input);
        System.out.println(output);
    }
}
