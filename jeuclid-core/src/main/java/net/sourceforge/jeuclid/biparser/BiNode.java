package net.sourceforge.jeuclid.biparser;

import org.w3c.dom.Node;

public class BiNode extends ABiNode {

    private ABiNode child;
    private int childOffset;

    // Node
    public BiNode(int childOffset, Node node) {
        super(node);
        this.childOffset = childOffset;
    }

    public String getNodeName() {
        return getNode().getNodeName();
    }

    /**
     * @param offset to parent
     */
    public ABiNode addChild(ABiNode abiNode) {
        if (child == null) {                                    // 1st child
            return setChild(abiNode);
        } else {                                                // 2nd - nth child
            if (abiNode.getType() == Type.TEXT) {
                throw new RuntimeException("addChild: cannot add textnode");
            }

            child.addSibling(abiNode);

            if (abiNode.getType() == Type.NODE) {
                return abiNode;
            } else {
                return this;
            }
        }
    }

    public ABiNode setChild(ABiNode abiNode) {
        abiNode.setPrevious(this);
        child = abiNode;
        return child;
    }

    public boolean hasChild() {
        return child != null;
    }

    @Override
    public Type getType() {
        return Type.NODE;
    }

    @Override
    public ABiNode getABiNodeAt(int offset, int length, int totalOffset) {
        System.out.println("getABi Node offset=" + offset + " with length=" + length + " at node '" + getNodeName() + "' with length=" + getLength());

        ABiNode result;

        setTotalOffset(totalOffset);

        if (offset == 0) {                      // position before node


            return getParent();    


         //   return getPrevious(); // ????


        } else if (offset < childOffset) {      // start position in open tag
            if (offset + length < childOffset) {
                return this;                    // end position in open tag
            }

            return getParent();                 // end position outside open tag
        }

        if (offset < getLength()) {                 // start position in a child node or this

            if (offset + length < getLength()) {    // end position in a child node or this
                result = child.getABiNodeAt(offset - childOffset, length, totalOffset + childOffset);

                if (result == null) {               // position not in (text-) child
                    return this;
                }

                return result;
            }

            return getParent();                     // end position in close tag
        } else {                                    // start position not in this node
            if (getSibling() != null) {
                return getSibling().getABiNodeAt(offset - getLength(), length, totalOffset + getLength());   // forward to sibling
            } else {
                return null;                        // position is not in this node
            }
        }
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("[NODE ");

        sb.append("length: ");
        sb.append(getLength());
        sb.append(" <");
        sb.append(getNodeName());
        sb.append(">]");

        return sb.toString();
    }

    @Override
    public String toString(int level) {
        StringBuffer sb = new StringBuffer(formatLength());
        String nl = System.getProperty("line.separator");

        sb.append(":");
        for (int i = 0; i <= level; i++) {
            sb.append(" ");
        }

        sb.append("<");
        sb.append(getNodeName());
        sb.append(">");

        sb.append(" tag: ");
        if (childOffset < 100) {
            sb.append("0");
            if (childOffset < 10) {
                sb.append("0");
            }
        }
        sb.append(childOffset);
        sb.append(nl);

        if (child != null) {
            sb.append(child.toString(level + 1));
            if (getSibling() != null) {
                sb.append(nl);
            }
        }

        if (getSibling() != null) {
            sb.append(getSibling().toString(level));
        }

        return sb.toString();
    }
}
