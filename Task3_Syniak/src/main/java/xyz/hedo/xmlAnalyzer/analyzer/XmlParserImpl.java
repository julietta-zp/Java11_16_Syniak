package xyz.hedo.xmlAnalyzer.analyzer;

import xyz.hedo.xmlAnalyzer.analyzer.exception.XmlParserException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parser finds {@link NodeInfo} elements in xml file lines
 */
public class XmlParserImpl implements XmlParser {

    private BufferedReader reader;

    private StringBuilder xmlText;

    private String lineText;

    private List<NodeInfo> tempNodeList = new ArrayList<>();

    // counts number of elements returned
    private int counter;

    // saves position of last found element
    private int position;

    XmlParserImpl() {

    }

    /**
     * initializes input stream
     * @param file is a {@link File} object, xml file to parse
     * @throws XmlParserException
     */
    @Override
    public void init(File file) throws XmlParserException {

        try {
            counter = 0;
            position = 0;
            reader = new BufferedReader(new FileReader(file));
            startDocument();
            xmlText = new StringBuilder();
        } catch (IOException e){
            throw new XmlParserException(e);
        }

    }

    /**
     * checks if file has next line
     * @return boolean
     * @throws XmlParserException
     */
    @Override
    public boolean hasNext() throws XmlParserException {
        try {
            return (lineText = reader.readLine()) != null;
        }catch (IOException e){
            throw new XmlParserException(e);
        }
    }


    /**
     * gets nodes out of the text line and returns one of them on each method call
     * @return {@link NodeInfo}
     * @throws XmlParserException
     */
    @Override
    public NodeInfo next() throws XmlParserException {

        while (tempNodeList.size() <= counter){
            if (hasNext()){
                xmlText.append(lineText);
                findNodes(xmlText.toString());
            } else {
                return null;
            }
        }
        NodeInfo node = tempNodeList.get(counter);
        counter++;

        return node;
    }

    /**
     * notifies that document was opened and parsing started
     */
    @Override
    public void startDocument() {
        System.out.println("****************");
        System.out.println("Parsing started");
        System.out.println("****************");
    }

    /**
     * notifies that parsing ended
     */
    @Override
    public void endDocument() {
        System.out.println("****************");
        System.out.println("Parsing ended");
        System.out.println("****************");
    }

    /**
     * closes the stream
     * @throws IOException
     */
    public void close() throws IOException{
        endDocument();
        if (reader != null){
            reader.close();
        }
    }

    /**
     * finds nodes in a string using regex, creates {@link NodeInfo} objects,
     * adds them to temporary list from which they are returned one by one
     * @param xmlLine a String that was read from xml file
     */
    private void findNodes(String xmlLine){

        String fullElementRegEx= "<([^?]\\S+?)([^<>]*?)>([^<>]*?)<\\/(\\1)>";
        String singleTagRegEx = "<([^?]\\S+)([^</>]*?)>";
        String contentRegEx = ">?([^<>\\n]*)<?";

        String slash = "/";

        Pattern fullElementPattern = Pattern.compile(fullElementRegEx);
        Pattern singleTagPattern = Pattern.compile(singleTagRegEx);
        Pattern contentPattern = Pattern.compile(contentRegEx);

        Matcher fullElementMatcher = fullElementPattern.matcher(xmlLine);
        Matcher singleTagMatcher = singleTagPattern.matcher(xmlLine);
        Matcher contentMatcher = contentPattern.matcher(xmlLine);

        if (fullElementMatcher.find(position)) {

            // if an open tag
            if (fullElementMatcher.group(1) != null && !fullElementMatcher.group(1).isEmpty()){

                // if has any attributes
                if (fullElementMatcher.group(2) != null && !fullElementMatcher.group(2).isEmpty()){

                    tempNodeList.add(new NodeInfo(NodeType.OPEN_TAG,
                            fullElementMatcher.group(1).trim() + " " + fullElementMatcher.group(2).trim()));

                } else {

                    tempNodeList.add(new NodeInfo(NodeType.OPEN_TAG, fullElementMatcher.group(1).trim()));

                }

            }

            // if a content
            if (fullElementMatcher.group(3) != null && !fullElementMatcher.group(3).isEmpty()){

                tempNodeList.add(new NodeInfo(NodeType.TEXT, fullElementMatcher.group(3).trim()));

            }

            // if a close tag
            if (fullElementMatcher.group(4) != null && !fullElementMatcher.group(4).isEmpty()){

                tempNodeList.add(new NodeInfo(NodeType.CLOSE_TAG, fullElementMatcher.group(4).trim()));

            }

            position = fullElementMatcher.end();

        } else if(singleTagMatcher.find(position)) {

            // if a single tag name
            if (singleTagMatcher.group(1) != null && !singleTagMatcher.group(1).isEmpty()) {

                // if a close tag or a self-closing tag
                if (singleTagMatcher.group(1).startsWith(slash)) {

                    tempNodeList.add(new NodeInfo(NodeType.CLOSE_TAG, singleTagMatcher.group(1).trim().substring(1, singleTagMatcher.group(1).trim().length())));

                } else if (singleTagMatcher.group(1).endsWith(slash)) {

                    tempNodeList.add(new NodeInfo(NodeType.SELF_CLOSING_TAG, singleTagMatcher.group(1).trim().substring(0, singleTagMatcher.group(1).trim().length() - 1)));

                } else {

                    // if has any attributes
                    if (singleTagMatcher.group(2) != null && !singleTagMatcher.group(2).isEmpty()) {

                        tempNodeList.add(new NodeInfo(NodeType.OPEN_TAG,
                                singleTagMatcher.group(1).trim() + " " + singleTagMatcher.group(2).trim()));

                    } else {

                        tempNodeList.add(new NodeInfo(NodeType.OPEN_TAG, singleTagMatcher.group(1).trim()));

                    }
                }
            }

            position = singleTagMatcher.end();
        }

        else if(contentMatcher.find(position)){

            // if a content
            if (contentMatcher.group(1) != null && !contentMatcher.group(1).isEmpty()) {

                tempNodeList.add(new NodeInfo(NodeType.TEXT, contentMatcher.group(1).trim()));

            }

            position = contentMatcher.end();
        }

    }

}
