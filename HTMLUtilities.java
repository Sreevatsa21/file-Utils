/**
 * HTML,	or	Hypertext	Markup	Language,	is	the	code
 * behind	every	web	page.	HTML	is	made	up	of	“tags”,	or	formaGng
 * commands,	inserted	into	a	text	document	that	specify	how	text
 * and	graphics	will	be	formaHed.
 *
 * @author Sreevatsa PErvela
 * @since 11/10/2023
 */
public class HTMLUtilities
{

    // All punctuations
    private static final String PUNCTUATIONS = ".,;:()?!=&~-+";

    // NONE = not nested in a block, COMMENT = inside a comment block
    // PREFORMAT = inside a pre-format block
    private enum TokenState {NONE, COMMENT, PREFORMAT};

    // the current tokenizer stat
    private TokenState state = TokenState.NONE;

    /**
     * Break the HTML string into tokens. The array returned is
     * exactly the size of the number of tokens in the HTML string.
     * Example:	HTML string = "Goodnight moon goodnight stars"
     * returns { "Goodnight", "moon", "goodnight", "stars" }
     *
     * @param str the HTML string
     * @return the String array of tokens
     */
    public String[] tokenizeHTMLString(String str)
    {

        // result array
        String[] result = new String[1000];
        int count = 0;
        int index = 0;

        // token tag
        String tag = null;

        for (int i = 0; i < str.length(); i++)
        {

            // check if comment or preformat block is starting
            if (state == TokenState.NONE)
            {
                // check if we are starting with a comment block
                // Comments start with <!--
                if (checkCommentToken(str, i))
                {
                    state = TokenState.COMMENT;
                    i += 2;
                }
                // preformat
                else if (checkPreformatToken(str, i))
                {
                    state = TokenState.PREFORMAT;
                    tag = "<pre>";
                    i += 3;

                }
            }
            else
            {
                // check for comment block ending if in comment block
                if (state == TokenState.COMMENT)
                {
                    if (checkUncommentToken(str, i))
                    {
                        state = TokenState.NONE;
                        //-->
                        i += 2;
                    }
                }
                // check for pre block ending if in preformat block
                else if (state == TokenState.PREFORMAT)
                {
                    if (checkPreformatTokenEnds(str, i))
                    {
                        state = TokenState.NONE;
                        //</PRE>
                        i += 5;
                        System.out.println("END OF TOKEN");
                        tag = "</pre>";
                        result[index++] = tag;
                        count++;
                        continue;
                    }
                    // if in preformat and not ended - take full string in line as token
                    else
                    {
                        //return entire string
                        tag = str.substring(i);
                        i = i + tag.length() - 1;
                        count++;
                    }
                }
            }

            // standard checks for tokens
            if (state == TokenState.NONE)
            {
                // tokenize tags
                if (str.charAt(i) == '<')
                {
                    tag = "" + str.charAt(i);

                    //look ahead and get all till >
                    for (int j = i + 1; j < str.length(); j++)
                    {
                        if (str.charAt(j) != '>')
                        {
                            tag = tag + str.charAt(j);
                        }
                        else
                        {
                            tag = tag + ">";
                            break;
                        }
                    }
                    i = i + tag.length() - 1;
                    count++;
                }
                // tokenize words
                else if (Character.isLetter(str.charAt(i)))
                {
                    tag = "" + str.charAt(i);

                    //look ahead and get all letters including - e.g hello-world
                    for (int j = i + 1; j < str.length(); j++)
                    {
                        if (Character.isLetter(str.charAt(j)) || str.charAt(j) == '-')
                        {
                            tag = tag + str.charAt(j);
                        }
                        else
                        {
                            break;
                        }
                    }
                    i = i + tag.length() - 1;
                    count++;
                }
                // tokenize numbers (including -ve numbers)
                else if (Character.isDigit(str.charAt(i)) || (str.charAt(i) == '-' && Character.isDigit(str.charAt(i + 1))))
                {
                    tag = "" + str.charAt(i);

                    // check for -ve number
                    if (i > 0 && str.charAt(i - 1) == '-')
                    {
                        tag = "-" + tag;
                    }
                    // look ahead and get all numbers till there is a digit or . (for decimals) or e (e.g 4e-2)
                    for (int j = i + 1; j < str.length(); j++)
                    {
                        if (Character.isDigit(str.charAt(j)) || str.charAt(j) == '.' || str.charAt(j) == 'e' || str.charAt(j) == '-')
                        {
                            tag = tag + str.charAt(j);
                        }
                        else
                        {
                            break;
                        }
                    }
                    i = i + tag.length() - 1;
                    count++;
                }
                // tokenize punctuations
                else if (PUNCTUATIONS.indexOf(str.charAt(i)) != -1)
                {
                    tag = "" + str.charAt(i);
                    count++;
                }
                else
                {
                    tag = null;
                }
            }

            //System.out.println("tag=" + tag);
            if (tag != null)
            {
                result[index++] = tag;
            }
        }

        // return array for only not null count values
        String[] filtered = new String[count];
        for (int i = 0; i < count; i++)
        {
            filtered[i] = result[i];
        }

        return filtered;
    }

    // checks if the comment block starts
    private boolean checkCommentToken(String str, int i)
    {
        // Comments start with <!--, check if valid comment string
        if ((i + 3 <= str.length()) && (str.charAt(i) == '<' && str.charAt(i + 1) == '!' && str.charAt(i + 2) == '-' && str.charAt(i + 3) == '-'))
        {
            return true;
        }
        else
        {
            return false;
        }

    }

    // checks if the uncomment token exists
    private boolean checkUncommentToken(String str, int i)
    {
        // Comments end with -->, check if valid comment string
        if ((i + 2 <= str.length()) && (str.charAt(i) == '-' && str.charAt(i + 1) == '-' && str.charAt(i + 2) == '>' && state == TokenState.COMMENT))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    // checks if <PRE> token exists
    private boolean checkPreformatToken(String str, int i)
    {
        String upperCase = str.toUpperCase();
        if ((i + 4) <= str.length() && (upperCase.charAt(i) == '<' && upperCase.charAt(i + 1) == 'P' && upperCase.charAt(i + 2) == 'R' && upperCase.charAt(i + 3) == 'E' && upperCase.charAt(i + 4) == '>'))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    // checks if </PRE> token
    private boolean checkPreformatTokenEnds(String str, int i)
    {
        String upperCase = str.toUpperCase();
        if ((i + 5 <= str.length()) && (state == TokenState.PREFORMAT) && (upperCase.charAt(i) == '<' && upperCase.charAt(i + 1) == '/' && upperCase.charAt(i + 2) == 'P' && upperCase.charAt(i + 3) == 'R' && upperCase.charAt(i + 4) == 'E' && upperCase.charAt(i + 5) == '>'))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Print the tokens in the array to the screen
     * Precondition: All elements in the array are valid String objects.
     * (no nulls)
     *
     * @param tokens an array of String tokens
     */
    public void printTokens(String[] tokens)
    {
        if (tokens == null) return;
        for (int a = 0; a < tokens.length; a++)
        {
            if (a % 5 == 0) System.out.print("\n  ");
            System.out.print("[token " + a + "]: " + tokens[a] + " ");
        }
        System.out.println();
    }

}
