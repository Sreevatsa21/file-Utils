/**
 *	Utilities for handling HTML
 *
 *	@author	Sreevatsa Pervela
 *	@since	10/30/2023
 */
public class HTMLUtilities {

	/**
	 *	Break the HTML string into tokens. The array returned is
	 *	exactly the size of the number of tokens in the HTML string.
	 *	Example:	HTML string = "Goodnight moon goodnight stars"
	 *				returns { "Goodnight", "moon", "goodnight", "stars" }
	 *	@param str			the HTML string
	 *	@return				the String array of tokens
	 */
	public String[] tokenizeHTMLString(String str) {
        int tokenCount = 0;
        String[] result = new String[10000];
        boolean withinTag = false;
        String currentToken = "";

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);

            if (c == '<') {
                if (!currentToken.isEmpty()) {
                    result[tokenCount++] = currentToken;
                    currentToken = "";
                }
                currentToken += '<';
                withinTag = true;
            } else if (c == '>') {
                currentToken += '>';
                result[tokenCount++] = currentToken;
                currentToken = "";
                withinTag = false;
            } else if (!withinTag && (Character.isWhitespace(c) || isPunctuation(c))) {
                if (!currentToken.isEmpty()) {
                    result[tokenCount++] = currentToken;
                    currentToken = "";
                }
                currentToken += c;
            } else {
                currentToken += c;
            }
        }

        if (!currentToken.isEmpty()) {
            result[tokenCount++] = currentToken;
        }

        String[] finalResult = new String[tokenCount];
        for (int j = 0; j < tokenCount; j++) {
            finalResult[j] = result[j];
        }

        return finalResult;
    }
	
	/**
	 * Checks if a given character is a punctuation character.
	 *
	 * @param c The character to check for punctuation.
	 * @return True if the character is a punctuation character, otherwise false.
	 */
	public boolean isPunctuation(char c) {
		char[] punctuationChars = { '.', ',', ';', ':', '(', ')', '?', '!', '=', '&', '~', '+', '-' };
        for (char p : punctuationChars) {
            if (c == p) {
                return true;
            }
        }
        return false;
    }


	/**
	 *	Print the tokens in the array to the screen
	 *	Precondition: All elements in the array are valid String objects.
	 *				(no nulls)
	 *	@param tokens		an array of String tokens
	 */
	public void printTokens(String[] tokens) {
		if (tokens == null) return;
		for (int a = 0; a < tokens.length; a++) {
			if (a % 5 == 0) System.out.print("\n  ");
			System.out.print("[token " + a + "]: " + tokens[a] + " ");
		}
		System.out.println();
	}

}
