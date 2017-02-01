package xyz.hedo.xmlAnalyzer.analyzer;

/**
 * Information about each node in xml file
 */
public class NodeInfo {

    private NodeType nodeType;
    private String nodeContent;

    NodeInfo(NodeType nodeType, String nodeContent) {
        this.nodeType = nodeType;
        this.nodeContent = nodeContent;
    }

    public NodeType getNodeType() {
        return nodeType;
    }

    public String getNodeContent() {
        return nodeContent;
    }

    public boolean isOpenTag(){
        return (nodeType == NodeType.valueOf("OPEN_TAG"));
    }

    public boolean isCloseTag(){
        return (nodeType == NodeType.valueOf("CLOSE_TAG"));
    }

    public boolean isText(){
        return (nodeType == NodeType.valueOf("TEXT"));
    }

    public boolean isSelfClosingTag(){
        return (nodeType == NodeType.valueOf("SELF_CLOSING_TAG"));
    }
}
