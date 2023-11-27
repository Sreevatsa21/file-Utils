import java.util.ArrayList;
import java.util.Scanner;

/**
 * HTMLRender
 * This program renders HTML code into a JFrame window.
 * It requires your HTMLUtilities class and
 * the SimpleHtmlRenderer and HtmlPrinter classes.
 * <p>
 * The tags supported:
 * <html>, </html> - start/end of the HTML file
 * <body>, </body> - start/end of the HTML code
 * <p>, </p> - Start/end of a paragraph.
 * Causes a newline before and a blank line after. Lines are restricted
 * to 80 characters maximum.
 * <hr>	- Creates a horizontal rule on the following line.
 * <br>	- newline (break)
 * <b>, </b> - Start/end of bold font print
 * <i>, </i> - Start/end of italic font print
 * <q>, </q> - Start/end of quotations
 * <hX>, </hX> - Start/end of heading with size X = 1, 2, 3, 4, 5, 6
 * <pre>, </pre> - Preformatted text
 *
 * @author Sreevatsa Pervela
 * @since 11/21/2023
 */
public class HTMLRender
{

    // All punctuations
    private static final String PUNCTUATIONS = ".,;:()?!=&~-+";

    // SimpleHtmlRenderer fields
    private SimpleHtmlRenderer render;

    // HTML Printer
    private HtmlPrinter browser;

    // All possible tags
    private enum TAG {NONE, HTML, BODY, PARAGRAPH, PREFORMAT, QUOTE, H1, H2, H3, H4, H5, H6, BOLD, ITALIC};

    // the current tokenizer stat
    private TAG currentTag = TAG.NONE;

    public HTMLRender()
    {
        // Initialize Simple Browser
        render = new SimpleHtmlRenderer();
        browser = render.getHtmlPrinter();
    }


    public static void main(String[] args)
    {
        HTMLUtilities util = new HTMLUtilities();
        HTMLRender hf = new HTMLRender();
        ArrayList<String> tokens = new ArrayList<>();



        // Open the HTML file
        Scanner input = FileUtils.openToRead(args[0]);

        // Read each line of the HTML file, tokenize, then print tokens
        while (input.hasNext())
        {
            String line = input.nextLine();
            //System.out.println("\n" + line);
            String[] tokenArr = util.tokenizeHTMLString(line);
            for (int i = 0; i < tokenArr.length; i++)
            {
                tokens.add(tokenArr[i]);
            }
        }
        input.close();
        System.out.println(tokens);

        //print html
        hf.runHtml(tokens);
    }

    /**
     * This method prints the html using HTML printer
     * @param tokenList list of all tokens
     *
     */
    public void runHtml(ArrayList<String> tokenList)
    {
        String value = "";
        currentTag = TAG.NONE;

        // check each token
        for (String token : tokenList)
        {
            // this is the ending token
            if (token.startsWith("</"))
            {
                String tagName = token.substring(2, token.indexOf(">"));

                // process each type
                switch (tagName.toLowerCase())
                {
                    case "html":
                    case "body":
                    case "h1":
                    case "h2":
                    case "h3":
                    case "h4":
                    case "h5":
                    case "h6":
                    {
                        browser.printBreak();
                        printValue(value);
                        currentTag = TAG.NONE;
                        value = "";
                        break;
                    }
                    case "pre":
                    {
                        currentTag = TAG.NONE;
                        value = "";
                        break;
                    }
                    case "p":
                    {
                        // if value exists - print value and break
                        if (!value.equals(""))
                        {
                            printValue(value);
                            browser.printBreak();
                        }

                        currentTag = TAG.NONE;
                        value = "";
                        break;
                    }
                    case "q":
                    {
                        browser.print(" \"");
                        printValue(value);
                        browser.print("\" ");
                        currentTag = TAG.NONE;
                        value = "";
                        break;
                    }
                    case "b":
                    case "i":
                    {
                        browser.print(" ");
                        printValue(value);
                        browser.print(" ");
                        value = "";
                        currentTag = TAG.NONE;
                        break;
                    }
                    default:
                }
            }
            else if (token.startsWith("<"))
            {
                // before processing new tag - print already parsed value
                printValue(value);

                // tag
                String tagName = token.substring(1, token.indexOf(">"));

                switch (tagName.toLowerCase())
                {
                    case "html":
                    case "body":
                    {
                        break;
                    }
                    case "h1":
                    {
                        currentTag = TAG.H1;
                        value = "";
                        break;
                    }
                    case "h2":
                    {
                        currentTag = TAG.H2;
                        value = "";
                        break;
                    }
                    case "h3":
                    {
                        currentTag = TAG.H3;
                        value = "";
                        break;
                    }
                    case "h4":
                    {
                        currentTag = TAG.H4;
                        value = "";
                        break;
                    }
                    case "h5":
                    {
                        currentTag = TAG.H5;
                        value = "";
                        break;
                    }
                    case "h6":
                    {
                        currentTag = TAG.H6;
                        value = "";
                        break;
                    }
                    case "pre":
                    {
                        currentTag = TAG.PREFORMAT;
                        value = "";
                        break;
                    }
                    case "p":
                    {
                        browser.println();
                        currentTag = TAG.PARAGRAPH;
                        value = "";
                        break;
                    }
                    case "b":
                    {
                        currentTag = TAG.BOLD;
                        value = "";
                        break;
                    }
                    case "i":
                    {
                        currentTag = TAG.ITALIC;
                        value = "";
                        break;
                    }
                    case "hr":
                    {
                        browser.printHorizontalRule();
                        value = "";
                        currentTag = TAG.NONE;
                        break;
                    }
                    case "br":
                    {
                        browser.printBreak();
                        value = "";
                        currentTag = TAG.NONE;
                        break;
                    }
                    case "q":
                    {
                        currentTag = TAG.QUOTE;
                        value = "";
                        break;
                    }
                    default:
                }
            }
            else
            {
                // for preformat - we need to print entire line at any time
                if (currentTag == TAG.PREFORMAT)
                {
                    browser.printPreformattedText(token);
                    browser.printBreak();
                }
                else
                {
                    // else print all of them together
                    if (value.equals(""))
                    {
                        value = token;
                    }
                    else
                    {
                        // Punctuations are special - must not have extra space
                        if (PUNCTUATIONS.indexOf(token) != -1)
                        {
                            value = value + token;
                        }
                        // other tokens must have space for printing
                        else
                        {
                            value = value + " " + token;
                        }
                    }
                }
            }
        }
    }

    // prints given value based on current tag in process
    //   - also checks the size of print based on current tag
    private void printValue(String value)
    {
        if (!value.equals(""))
        {
            // default size is 80
            int size = 80;

            // H1 size is 40
            if (currentTag == TAG.H1)
            {
                size = 40;
            }
            // H2 size is 50
            else if (currentTag == TAG.H2)
            {
                size = 50;
            }
            // H3 size is 60
            else if (currentTag == TAG.H3)
            {
                size = 60;
            }
            // H4 size is 80
            else if (currentTag == TAG.H4)
            {
                size = 80;
            }
            // H5 size is 100
            else if (currentTag == TAG.H5)
            {
                size = 100;
            }
            // H6 size is 120
            else if (currentTag == TAG.H6)
            {
                size = 120;
            }

            // print each character
            for (int i = 0; i < value.length(); i++)
            {
                // check size limit
                if (i > 0 && ((i + 1) % size == 0))
                {
                    browser.println();
                }

                // print formatted value
                if (currentTag == TAG.NONE)
                {
                    browser.print("" + value.charAt(i));
                }
                else if (currentTag == TAG.BOLD)
                {
                    browser.printBold("" + value.charAt(i));
                }
                else if (currentTag == TAG.ITALIC)
                {
                    browser.printItalic("" + value.charAt(i));
                }
                else if (currentTag == TAG.H1)
                {
                    browser.printHeading1("" + value.charAt(i));
                }
                else if (currentTag == TAG.H2)
                {
                    browser.printHeading2("" + value.charAt(i));
                }
                else if (currentTag == TAG.H3)
                {
                    browser.printHeading3("" + value.charAt(i));
                }
                else if (currentTag == TAG.H4)
                {
                    browser.printHeading4("" + value.charAt(i));
                }
                else if (currentTag == TAG.H5)
                {
                    browser.printHeading5("" + value.charAt(i));
                }
                else if (currentTag == TAG.H6)
                {
                    browser.printHeading6("" + value.charAt(i));
                }
                else if (currentTag == TAG.PREFORMAT)
                {
                    browser.printPreformattedText("" + value.charAt(i));
                }
                else if (currentTag == TAG.QUOTE)
                {
                    browser.print("" + value.charAt(i));
                }
                else if (currentTag == TAG.PARAGRAPH)
				{
					browser.print("" + value.charAt(i));
				}
            }
        }
    }

    public void run()
    {
        // Sample renderings from HtmlPrinter class

        // Print plain text without line feed at end
        browser.print("First line");

        // Print line feed
        browser.println();

        // Print bold words and plain space without line feed at end
        browser.printBold("bold words");
        browser.print(" ");

        // Print italic words without line feed at end
        browser.printItalic("italic words");

        // Print horizontal rule across window (includes line feed before and after)
        browser.printHorizontalRule();

        // Print words, then line feed (printBreak)
        browser.print("A couple of words");
        browser.printBreak();
        browser.printBreak();

        // Print a double quote
        browser.print("\"");

        // Print Headings 1 through 6 (Largest to smallest)
        browser.printHeading1("Heading1");
        browser.printHeading2("Heading2");
        browser.printHeading3("Heading3");
        browser.printHeading4("Heading4");
        browser.printHeading5("Heading5");
        browser.printHeading6("Heading6");

        // Print pre-formatted text (optional)
        browser.printPreformattedText("Preformat Monospace\tfont");
        browser.printBreak();
        browser.print("The end");

    }

}
