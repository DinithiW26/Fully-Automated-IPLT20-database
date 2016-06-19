import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
/**
 * A complete Java program that demonstrates how to
 * extract a tag from a line of HTML using the Pattern
 * and Matcher classes.
 */
public class PatternMatcherGroupHtml
{
	  public static String codeGroup; 
	 
	  public void checkPatternMatch(String stringToSearch,String patt)
	  {
		// the pattern we want to search for
		Pattern p = Pattern.compile(patt);
		Matcher m = p.matcher(stringToSearch);
		// if we find a match, get the group 
		if (m.find())
		{
		  // get the matching group		  
		  codeGroup = m.group(1);
		   
		  // print the group
		  //System.out.println(codeGroup);
		  if(patt.equals(GetURLContent.overNumPatt))
		  {
			  GetURLContent.overLineNumber = GetURLContent.count;
			  //GetURLContent.overLineNum.add(GetURLContent.count);
			  //System.out.println(GetURLContent.count);
			  
		  }
		   if(patt.equals(GetURLContent.titlePatt))
		  {
			  GetURLContent.titleLineNumber = GetURLContent.count;
			  //System.out.println(GetURLContent.titleLineNumber);
		  }
		}
	 }
}