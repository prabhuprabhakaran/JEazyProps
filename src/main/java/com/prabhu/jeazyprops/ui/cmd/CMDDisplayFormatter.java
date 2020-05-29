/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prabhu.jeazyprops.ui.cmd;

import java.util.Formatter;

/**
 * CMD Display Formatter Class is used to display strings with design like a report.
 *
 * @author Prabhu Prabhakaran
 */
public abstract class CMDDisplayFormatter
{

    char lHeaderStartChar = '+';
    char lHeaderMidChar = '-';
    char lHeaderEndChar = '+';
    char lLineStartChar = '|';
    char lLineEndChar = '|';
    int lScreenLength;
    int lRightPad;

    /**
     * Creating Display Object by Specifying the Screen Size and Padding
     *
     * @param pScreenLength Length of the screen to be print
     * @param pRightPad     Padding size on right side
     */
    protected CMDDisplayFormatter(int pScreenLength, int pRightPad)
    {
        setScreenLength(pScreenLength);
        lRightPad = pRightPad;
    }

    /**
     * Creating Display Object by Specifying with default Screen size and padding
     */
    protected CMDDisplayFormatter()
    {
        setScreenLength(80);
        lRightPad = 5;
    }

    /**
     * Print empty second column
     *
     * @return The printed String.
     */
    protected String printColumn2()
    {
        return printColumn2("", "");
    }

    /**
     * Print empty first column
     *
     * @return The printed String.
     */
    protected String printColumn1()
    {
        return printColumn1("", "");
    }

    /**
     * Print empty single column
     *
     * @return The printed String.
     */
    protected String printColumn()
    {
        return printColumn("", "");
    }

    /**
     * Print text as single column at center
     *
     * @param PrintText Text to be printed
     *
     * @return The printed String
     */
    protected String printColumn(String PrintText)
    {
        Formatter lFormatter = new Formatter();
        String lStringToPrint;
        int length = PrintText.length();
        int lBalLength = (((lScreenLength - length - 2) % 2 == 0) ? (lScreenLength - length - 2) / 2 : (lScreenLength - length - 2 + 1) / 2);
        String format = "%" + -lBalLength + "s" + "%" + length + "s" + "%" + (lBalLength + 1) + "s";
        lFormatter.format(format, lLineStartChar, PrintText, lLineEndChar);
        lStringToPrint = lFormatter.toString();
        System.out.println(lStringToPrint);
        return lStringToPrint;
    }

    /**
     * Print text as single column at right aligned
     *
     * @param PrintText Text to be printed
     *
     * @return The printed String
     */
    protected String print(String PrintText)
    {
        Formatter lFormatter = new Formatter();
        String lStringToPrint;
        int length = PrintText.length();
        int lBalLength = (lScreenLength - length);
        lFormatter.format("%" + "s" + "%" + (lBalLength - 1) + "s", lLineStartChar + PrintText, lLineEndChar);
        lStringToPrint = lFormatter.toString();
        System.out.println(lStringToPrint);
        return lStringToPrint;
    }

    /**
     * Print the text on either first or second column
     *
     * @param columnNo   even - prints on first column odd - prints on second column
     * @param PrintText1 Key text
     * @param PrintText2 value text
     *
     * @return The printed String
     */
    protected String printColumn(int columnNo, String PrintText1, String PrintText2)
    {
        if ((columnNo % 2) == 0)
        {
            return printColumn1(PrintText1, PrintText2);
        }
        else
        {
            return printColumn2(PrintText1, PrintText2);
        }
    }

    /**
     * Print the text on either first column
     *
     * @param PrintText1 Key text
     * @param PrintText2 value text
     *
     * @return The printed String
     */
    protected String printColumn1(String PrintText1, String PrintText2)
    {
        int lMidPoint = (lScreenLength % 2 == 0) ? lScreenLength / 2 : (lScreenLength + 1) / 2;
        int lColMidPoint = (lMidPoint % 2 == 0) ? lMidPoint / 2 : (lMidPoint + 1) / 2;
        int lPrintText2 = (PrintText2 != null) ? (PrintText2.length() > 0) ? PrintText2.length() : 1 : 1;
        Formatter lFormatter = new Formatter();
        String format = "%" + -lRightPad + "s" + "%" + -(lColMidPoint - lRightPad) + "s" + "%" + 2 + "s" + "%" + lPrintText2 + "s" + "%" + (lColMidPoint - 2 - lPrintText2) + "s";
        lFormatter.format(format, lLineStartChar, PrintText1, ": ", "" + PrintText2, " ");
        String lStringToPrint = lFormatter.toString();
        System.out.print(lStringToPrint);
        return lStringToPrint;
    }

    /**
     * Print the text on either second column
     *
     * @param PrintText1 Key text
     * @param PrintText2 value text
     *
     * @return The printed String
     */
    protected String printColumn2(String PrintText1, String PrintText2)
    {
        int lMidPoint = (lScreenLength % 2 == 0) ? lScreenLength / 2 : (lScreenLength + 1) / 2;
        int lColMidPoint = ((lMidPoint % 2 == 0) ? lMidPoint / 2 : (lMidPoint + 1) / 2);
        int lPrintText2 = (PrintText2 != null) ? (PrintText2.length() > 0) ? PrintText2.length() : 1 : 1;
        Formatter lFormatter = new Formatter();
        String format = "%" + -lRightPad + "s" + "%" + -(lColMidPoint - lRightPad) + "s" + "%" + 2 + "s" + "%" + lPrintText2 + "s" + "%" + (lColMidPoint - 2 - lPrintText2) + "s";
        lFormatter.format(format, " ", PrintText1, ": ", "" + PrintText2, lLineEndChar);
        String lStringToPrint = lFormatter.toString();
        System.out.println(lStringToPrint);
        return lStringToPrint;
    }

    /**
     * Print the text as Single column
     *
     * @param PrintText1 Key text
     * @param PrintText2 value text
     *
     * @return The printed String
     */
    protected String printColumn(String PrintText1, String PrintText2)
    {
        Formatter lFormatter = new Formatter();
        String lStringToPrint;
        int lMidPoint = (lScreenLength % 2 == 0) ? lScreenLength / 2 : (lScreenLength + 1) / 2;
        int lPrintText2 = (PrintText2 != null) ? (PrintText2.length() > 0) ? PrintText2.length() : 1 : 1;
        String format = "%" + -lRightPad + "s" + "%" + -(lMidPoint - (lRightPad)) + "s" + "%" + 2 + "s" + "%" + lPrintText2 + "s" + "%" + (lScreenLength - (lPrintText2 + lMidPoint + 2)) + "s";
        lFormatter.format(format, lLineStartChar, PrintText1, ": ", "" + PrintText2, lLineEndChar);
        lStringToPrint = lFormatter.toString();
        System.out.println(lStringToPrint);
        return lStringToPrint;
    }

    /**
     * Prints the header strip for the screen length
     *
     * @return the same header printed
     */
    protected String printHeader()
    {
        String lStringToPrint;
        lStringToPrint = lHeaderStartChar + repeat(lHeaderMidChar, lScreenLength - 2) + lHeaderEndChar;
        System.out.println(lStringToPrint);
        return lStringToPrint;
    }

    private String repeat(char str, int times)
    {
        return new String(new char[times]).replace("\0", "" + str);
    }

    /**
     * To get the screen length
     *
     * @return returns the screen length of the CMD Window
     */
    protected int getScreenLength()
    {
        return lScreenLength;
    }

    /**
     * To set the screen length
     *
     * @param lScreenLength Max length of the CMD Window screen (Default = 80)
     */
    protected void setScreenLength(int lScreenLength)
    {
        this.lScreenLength = (lScreenLength % 4 == 0) ? lScreenLength : (lScreenLength - (lScreenLength % 4));
    }
}
