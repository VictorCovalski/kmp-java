/*
 * This is a Java implementation of the Knuth Morris Pratt Algorithm for string matching
 */

package kmp.pattern;

/**
 *
 * @author Victor Renan Covalski Junes {covalski@ualberta.ca}
 * @author Wladimir de Araújo Neto {arajonet@ualberta.ca}
 * @date 30/March/2014
 */
public class KMPPattern 
{
    /**
     * 
     * @param pattern - is the pattern that is going to generate the finite automaton
     * @return finite automata with failure links already set 
     */
    public int[] kmpfinite(String pattern)
    {
        System.out.println("-----------Calculating failure links for " + pattern);
        int fail[] = new int[pattern.length()]; //allocates memory for failure links array

        for(int loc_k = 2; loc_k < pattern.length()  ; loc_k++) //iterates over the pattern and generates fail links
        {
            System.out.println("    Checking for a pattern in itself for position " + loc_k);
            int backCheck = fail[loc_k-1]; //gets the last known fail link
            System.out.println("        Previous mismatch went to " + backCheck);
            
            //stops if backcheck is 0, which means it's on the beginning of the pattern
            //or if the character at backCheck position is the same as the character to the left. This means we found a better fail link
            while((backCheck > 0) && (pattern.charAt(backCheck) != pattern.charAt(loc_k -1))) 
            {
                backCheck = fail[backCheck];
            }
            
            if(pattern.charAt(backCheck) == pattern.charAt(loc_k - 1)) //Print only if there was a match
            {
                System.out.println("        Compare " + pattern.charAt(backCheck) + " to " + pattern.charAt(loc_k-1) + " MATCH\n\n");
            }
            
            fail[loc_k] = backCheck + 1; //sets fail link to its position based on backchecks
            
            System.out.println("        Setting fail link at position " + loc_k + " to " + fail[loc_k]);
        }
        
        //prints out the fail links
        System.out.println("-----------Finished failure links");
        System.out.print("The failure links are: ");
        for(int i = 1 ; i < fail.length; i++)
        {
            System.out.print(fail[i] + " ");
        }
        System.out.print("\n");
        
        return fail; //returns the array
        
    }
    /**
     * 
     * @param text text that is being checked for pattern matches
     * @param pattern pattern which we are looking for
     * @param fail fail array which has the fail links in case a comparison fails based on the KMP algorithm
     */
    public void kmpfind(String text,String pattern,int fail[])
    {
        int k = 1; //starts with 1 because the string pattern has one extra character at its beginning
        int t = 0; //text starts at the beginning
        while(t < text.length())
        {
            if(k == pattern.length()) //if k index is the size of the pattern, then a match is found
            {
                k = 1;
                System.out.println("-------------------------------------------Found a match at: " + (t-pattern.length()+1));
                /**
                 * Due to the shift to the right in the pattern, we added an extra character at the beginning so when calculating where the pattern
                 * is found within the text we must add a +1.
                 */
                continue;
            }
            if(k == 0) //back to beginning of pattern
            {
                t++; //move text forward
                k = 1; //pattern goes to first state
            }
            else if (text.charAt(t) == pattern.charAt(k)) //if chars are equal
            {
                System.out.println("At pattern location: " + k + " & text location: " + t + " Comparing: " + pattern.charAt(k) + " & " + text.charAt(t));
                k++; //moves pattern forward
                t++; //moves text forward
            }
            else //if chars are not equal then get the fail link
            {
                System.out.println("At pattern location: " + k + " & text location: " + t + " Comparing: " + pattern.charAt(k) + " & " + text.charAt(t));
                k = fail[k];
                System.out.println("    Followed fail link: " + k);
            }
        }
    }
    
    /**
     * 
     * @param text - the text which the pattern is trying to be found
     * @param pattern - the pattern that we are looking for in the text
     */
    public void kmpmatch(String text,String pattern)
    {  
        System.out.println("\n======================In Knuth-Morris-Pratt Matching ======================== \n" +
" The text is: " + text + "\n The pattern is " + pattern);
        
        pattern = " " + pattern; //shifting the pattern to the right so the index matches the ones in fail link array
        int fail[] = kmpfinite(pattern); //creates fail link array to be used in kmpfind method
        
        System.out.println(" -----------Now doing the comparisons through the text.");
        kmpfind(text,pattern,fail); //looks for pattern matches within the text and using the fail link array
        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        KMPPattern kmp = new KMPPattern();
        System.out.println("Starting string matching tests...");

        //the text strings
	String str1 = "Hi there, this is a test.  thethere Scary. Bye there";
	String str2 = "abracadabra allacazam ababababba abb aaaaaaabaab aaab aaaab";
	String str3 = "aaaaaaaaaaaacaaaaaaaaabaaaaaabba";

        //the pattern strings
	String pat1 = "there";
	String pat2 = "their";
	String pat3 = "ab";
	String pat4 = "abba";
	String pat5 = "ac";
	String pat6 = "a";
	String pat7 = "aab";
	String pat8 = "ababcb";
	String pat9 = "aaaaab";
	String pat10 = "abcdabcd";

        //find matches of there/their
        //kmp.kmpmatch(str1,pat1);
        //kmp.kmpmatch(str1, pat2);

        //matches of patterns of a b c
         kmp.kmpmatch(str2, pat3);
        kmp.kmpmatch(str2, pat4);
        kmp.kmpmatch(str2, pat5);
        kmp.kmpmatch(str2, pat6);
        kmp.kmpmatch(str2, pat7);
        kmp.kmpmatch(str2, pat8);
        kmp.kmpmatch(str2, pat9);
        kmp.kmpmatch(str2, pat10);

        kmp.kmpmatch(str3, pat4);
        kmp.kmpmatch(str3, pat8);
        kmp.kmpmatch(str3, pat9);

        //no match
        kmp.kmpmatch(str1, pat8);

        //match the entire thing
        kmp.kmpmatch(str3, str3);


        System.out.println("Finished string matching tests!");

    }
    
}
