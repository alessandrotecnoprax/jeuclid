/*
 * Copyright 2002 - 2007 JEuclid, http://jeuclid.sf.net
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package net.sourceforge.jeuclid.biparser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.Attributes;

/**
 * this class is used to store specific information about a composite xml-node.
 * the node can have one child, many attributes and can be invalid
 *
 * @version $Revision$
 */
public final class BiNode extends AbstractBiNode {

    /** child node. */
    private AbstractBiNode child;
    /** offset to child from node begin (length of open tag). */
    private final int childOffset;
    /** if false, node is a valid xml-node. */
    private boolean invalid;
    /** DOM-info: namespaceURI. */
    private String namespaceURI;
    /** DOM-info: tag-name. */
    private String eName;
    /** DOM-info: attributes of node. */
    private Attributes attrs;

    /**
     * creates a new BiNode, length must be set afterwards.
     * constructor does not create a DOM-node
     * @param co offset to child from node begin (length of open tag)
     * @param ns DOM-info
     * @param n DOM-info
     * @param a DOM-info
     */
    public BiNode(final int co, final String ns, final String n,
            final Attributes a) {
        this.childOffset = co;
        this.namespaceURI = ns;
        this.eName = n;
        this.attrs = a;
    }

    /**
     * get the name of the node (tagname).
     * @return nodename
     */
    public String getNodeName() {
        if (this.eName == null) {
            if (this.getNode() == null) {
                return null;
            } else {
                return this.getNode().getNodeName();
            }
        } else {
            return this.eName;
        }
    }

    /**
     * add a child to this node, if node has already a child, forward to child.
     * @param c new child for this node
     */
    public void addChild(final AbstractBiNode c) {
        // 1st child
        if (this.child == null) {                    
            this.setChild(c);
        } else {
            // 2nd - nth child
            this.child.addSibling(c);
        }
    }

    /**
     * get the child of the node.
     * @return child
     */
    public AbstractBiNode getChild() {
        return this.child;
    }

    /**
     * set child for this node.
     * @param c new child for this node
     */
    public void setChild(final AbstractBiNode c) {
        if (c != null) {
            c.setPrevious(this);
        }

        this.child = c;
    }

    /**
     * get the type of node.
     * @return NODE
     */
    @Override
    public BiType getType() {
        return BiType.NODE;
    }

    /** {@inheritDoc} */
    @Override
    public void insert(final BiTree biTree, final int offset,
            final int length, final int totalOffset)
            throws ReparseException {
       //  System.out.println("insert " + toString() + " offset=" + offset +
         //" length=" + length);


        // ---------------- end of this or SIBLING ----------------
        if (offset >= this.getLength()) {

            // reparse if node is invalid and start position is at node-end
            if (offset == this.getLength() && this.invalid) {
                throw new ReparseException();
            }

            // forward to sibling
            this.forwardToSibling(true, biTree, offset - this.getLength(),
                    length, totalOffset + this.getLength());

        } else if (this.child != null && !this.invalid
                && offset >= this.childOffset
                && offset <= this.childOffset + this.getLengthOfChildren()) {
                // ---------------- CHILDREN ----------------
            try {
                this.child.insert(biTree, offset - this.childOffset, length,
                        totalOffset + this.childOffset);
            } catch (ReparseException ex) {
                this.parseAndReplace(biTree,
                        biTree.getText().substring(totalOffset,
                        totalOffset + this.getLength() + length), length);
            }
        } else if (offset == 0) {
            // ---------------- before THIS ----------------
            throw new ReparseException();
        } else {
            // ---------------- THIS ----------------
            this.parseAndReplace(biTree, biTree.getText().substring(totalOffset,
                    totalOffset + this.getLength() + length), length);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void remove(final BiTree biTree, final int offset,
            final int length, final int totalOffset) throws ReparseException {
       //  System.out.println("remove " + toString() + " offset=" +
         //offset + " length=" + length);

        // ---------------- REMOVE THIS ----------------
        if (offset == 0 && length >= this.getLength()) {
            throw new ReparseException();

        } else if (offset >= this.getLength()) {
            // ---------------- SIBLING ----------------
            this.forwardToSibling(false, biTree, offset - this.getLength(),
                    length, totalOffset + this.getLength());

        } else if (this.child != null && !this.invalid
                && offset >= this.childOffset
                && offset + length <= this.childOffset
                + getLengthOfChildren()) {
                // ---------------- CHILDREN ----------------
            try {
                this.child.remove(biTree, offset - this.childOffset, length,
                        totalOffset + this.childOffset);
            } catch (ReparseException ex) {
                this.parseAndReplace(biTree, biTree.getText().substring(totalOffset,
                        totalOffset + this.getLength() - length), -length);
            }
        } else {
            // ---------------- THIS ----------------
            this.parseAndReplace(biTree, biTree.getText().substring(totalOffset,
                    totalOffset + this.getLength() - length), -length);
        }
    }

    /**
     * set the node as invalid, remove all children.
     * replace node in DOM-tree with a red '#'
     * @param doc Document to insert the invalid mark '#'
     */
    private void makeInvalidNode(final Document doc) {
        Element element;

        // create INVALID-textnode in DOM tree
        element = doc.createElement("mi");
        element.setAttribute("mathcolor", "#F00");
        element.appendChild(doc.createTextNode("#"));

        if (this.getNode().getParentNode() == null) {
            doc.replaceChild(element, this.getNode());
        } else {
            this.getNode().getParentNode().replaceChild(element,
                    this.getNode());
        }

        // remove bi-subtree
        this.setNode(element);
        this.child = null;
        this.invalid = true;
    }

    /**
     * try to parse the text, if valid replace this node with parsed tree.
     * else replace this node with invalid mark '#'
     * @param biTree reference to BiTree to which this node contains
     * @param text to parse
     * @param length change length of this node
     */
    private void parseAndReplace(final BiTree biTree, final String text,
            final int length)
            throws ReparseException {
        BiTree treePart;
        Node domValid;
        BiNode parent;

        treePart = SAXBiParser.getInstance().parse(text);

        // parse unsuccessfull
        if (treePart == null) {
            // if node & previous or node & sibling are invalid - reparse parent
            if ((this.getPrevious() != null
                    && this.getPrevious().getType() == BiType.NODE
                    && ((BiNode) this.getPrevious()).invalid)
                    || (this.getSibling() != null
                    && this.getSibling().getType() == BiType.NODE
                    && ((BiNode) this.getSibling()).invalid)) {
                throw new ReparseException();
            }

            if (!this.invalid) {
                this.makeInvalidNode((Document) biTree.getDocument());
            }

            this.changeLengthRec(length);

        } else {

            parent = (BiNode) this.getParent();
            domValid = treePart.getDOMTree((Document) biTree.getDocument());
            treePart.getRoot().addSibling(this.getSibling());

            // node is root
            if (parent == null) {          

                // no emtpy text
                if (this.getPrevious() == null) {
                    biTree.setRoot(treePart.getRoot());
                } else {
                    // empty text on left side of root
                    this.getPrevious().setSibling(treePart.getRoot());
                }

                // replace invalid DOM node
                biTree.getDocument().replaceChild(domValid, this.getNode());

            } else {
                if (this.getPrevious() == parent) {
                    // invalid node is 1st child
                    ((BiNode) this.getParent()).setChild(treePart.getRoot());
                } else {
                    // 2nd - nth child
                    this.getPrevious().setSibling(treePart.getRoot());
                }

                // replace invalid DOM node
                parent.getNode().replaceChild(domValid, this.getNode());
                parent.changeLengthRec(length);
            }

            this.invalid = false;
        }
    }

    /**
     * calculate the length of all children.
     * @return length of children
     */
    public int getLengthOfChildren() {
        int length = 0;
        AbstractBiNode childTmp;

        if (this.child != null) {
            // length of first child
            length += this.child.getLength();

            childTmp = this.child.getSibling();
            while (childTmp != null) {
                // length of 2nd - nth children
                length += childTmp.getLength();
                childTmp = childTmp.getSibling();
            }

        }

        return length;
    }

    /**
     * create a DOM-tree from node and all children (recursive).
     * @param doc Document to create DOM-tree
     * @return root of DOM-tree
     */
    @Override
    public Node createDOMSubtree(final Document doc) {
        int i;
        String aName;
        Node childNode;
        Element element;
        AbstractBiNode tmp;

        element = doc.createElementNS(this.namespaceURI, this.eName);

        // add attributes
        if (this.attrs != null) {
            for (i = 0; i < this.attrs.getLength(); i++) {
                aName = this.attrs.getLocalName(i);

                if ("".equals(aName)) {
                    aName = this.attrs.getQName(i);
                }

                element.setAttribute(aName, this.attrs.getValue(i));
            }
        }

        // create DOM-tree of children
        if (this.child != null) {
            tmp = this.child;

            while (tmp != null) {
                childNode = tmp.createDOMSubtree(doc);

                if (childNode != null) {
                    element.appendChild(childNode);
                }

                tmp = tmp.getSibling();
            }
        }

        this.namespaceURI = null;
        this.eName = null;
        this.attrs = null;

        this.setNode(element);
        return element;
    }

    /** {@inheritDoc} */
    @Override
    public SearchResult searchNode(final Node node,
            final int totalOffset) {
        SearchResult result;

        // check if node is this
        result = super.searchNode(node, totalOffset);
        if (result != null) {
            return result;
        }

        // forward to child
        if (this.child != null) {
            result = this.child.searchNode(node, totalOffset
                    + this.childOffset);
            if (result != null) {
                return result;
            }
        }

        // forward to sibling
        if (this.getSibling() != null) {
            return this.getSibling().searchNode(node,
                    totalOffset + this.getLength());
        }

        return null;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer(32);

        sb.append('[');
        sb.append(this.invalid ? "INVALID " : "");
        sb.append("NODE length: ");
        sb.append(this.getLength());

        if (!this.invalid) {
            sb.append(" <");
            sb.append(this.getNodeName());
            sb.append("> tag: ");
            sb.append(this.childOffset);
        }

        sb.append(']');

        return sb.toString();
    }

    @Override
    public String toString(final int level) {
        final StringBuffer sb = new StringBuffer(32);
        final String nl = System.getProperty("line.separator");

        sb.append(this.formatLength());
        sb.append(':');
        for (int i = 0; i <= level; i++) {
            sb.append(' ');
        }

        if (this.invalid) {
            sb.append("INVALID ");
        }

        if (!invalid) {
            sb.append('<');
            sb.append(this.getNodeName());
            sb.append("> tag: ");
            if (this.childOffset < 100) {
                sb.append('0');
                if (this.childOffset < 10) {
                    sb.append('0');
                }

            }
            sb.append(this.childOffset);
        }

        sb.append(nl);

        if (this.child != null) {
            sb.append(this.child.toString(level + 1));
            if (this.getSibling() != null) {
                sb.append(nl);
            }

        }

        if (this.getSibling() != null) {
            sb.append(this.getSibling().toString(level));
        }

        return sb.toString();
    }
}
