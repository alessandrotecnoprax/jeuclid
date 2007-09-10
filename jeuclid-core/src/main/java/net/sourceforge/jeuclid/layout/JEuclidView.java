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

/* $Id$ */

package net.sourceforge.jeuclid.layout;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.HashMap;
import java.util.Map;

import net.sourceforge.jeuclid.DOMBuilder;
import net.sourceforge.jeuclid.LayoutContext;
import net.sourceforge.jeuclid.LayoutContext.Parameter;

import org.w3c.dom.Node;
import org.w3c.dom.views.AbstractView;
import org.w3c.dom.views.DocumentView;

/**
 * @author Max Berger
 * @version $Revision$
 */
public class JEuclidView implements AbstractView, LayoutView {

    private final LayoutableDocument document;

    private final Map<Node, LayoutInfo> layoutMap;

    private final LayoutContext context;

    private final Graphics2D graphics;

    /**
     * Default Constructor.
     * 
     * @param node
     *            document to layout.
     * @param layoutGraphics
     *            Graphics context to use for layout calculations. This should
     *            be compatible to the context used for painting, but does not
     *            have to be the same.
     * @param layoutContext
     *            layoutContext to use.
     */
    public JEuclidView(final Node node, final LayoutContext layoutContext,
            final Graphics2D layoutGraphics) {
        if (node instanceof LayoutableDocument) {
            this.document = (LayoutableDocument) node;
        } else {
            this.document = DOMBuilder.getDOMBuilder().createJeuclidDom(node);
        }
        this.graphics = layoutGraphics;
        this.context = layoutContext;
        this.layoutMap = new HashMap<Node, LayoutInfo>();
    }

    /** {@inheritDoc} */
    public DocumentView getDocument() {
        return this.document;
    }

    /**
     * Draw this view onto a Graphics context.
     * 
     * @param x
     *            x-offset for left edge
     * @param y
     *            y-offset for baseline
     * @param g
     *            Graphics context for painting. Should be compatible to the
     *            context used during construction, but does not have to be
     *            the same.
     */
    public void draw(final Graphics2D g, final float x, final float y) {
        this.layout(this.document, LayoutStage.STAGE2, this.context);

        // final RenderingHints hints = g.getRenderingHints();
        // if ((Boolean) (this.rootElement.getCurrentLayoutContext()
        // .getParameter(Parameter.ANTIALIAS))) {
        // hints.add(new RenderingHints(RenderingHints.KEY_ANTIALIASING,
        // RenderingHints.VALUE_ANTIALIAS_ON));
        // }
        // hints.add(new RenderingHints(RenderingHints.KEY_STROKE_CONTROL,
        // RenderingHints.VALUE_STROKE_NORMALIZE));
        // hints.add(new RenderingHints(RenderingHints.KEY_RENDERING,
        // RenderingHints.VALUE_RENDER_QUALITY));
        // g.setRenderingHints(hints);

        final boolean debug = (Boolean) (this.context
                .getParameter(Parameter.DEBUG));
        this.drawNode(this.document, g, x, y, debug);

    }

    private void drawNode(final LayoutableNode node, final Graphics2D g,
            final float x, final float y, final boolean debug) {

        final LayoutInfo myInfo = this.getInfo(node);
        if (debug) {
            final float x1 = x;
            final float x2 = x + myInfo.getWidth(LayoutStage.STAGE2);
            final float y1 = y - myInfo.getAscentHeight(LayoutStage.STAGE2);
            final float y2 = y + myInfo.getDescentHeight(LayoutStage.STAGE2);
            g.setColor(Color.BLUE);
            g.draw(new Line2D.Float(x1, y1, x2, y1));
            g.draw(new Line2D.Float(x1, y1, x1, y2));
            g.draw(new Line2D.Float(x2, y1, x2, y2));
            g.draw(new Line2D.Float(x1, y2, x2, y2));
            g.setColor(Color.RED);
            g.draw(new Line2D.Float(x1, y, x2, y));
        }
        for (final GraphicsObject go : myInfo.getGraphicObjects()) {
            go.paint(x, y, g);
        }

        for (final LayoutableNode child : node.getChildrenToDraw()) {
            final LayoutInfo childInfo = this.getInfo(child);
            this.drawNode(child, g,
                    x + childInfo.getPosX(LayoutStage.STAGE2), y
                            + childInfo.getPosY(LayoutStage.STAGE2), debug);
        }
    }

    private LayoutInfo layout(final LayoutableNode node,
            final LayoutStage toStage, final LayoutContext parentContext) {
        final LayoutInfo info = this.getInfo(node);
        if (LayoutStage.NONE.equals(info.getLayoutStage())) {
            LayoutStage childMinStage = LayoutStage.STAGE2;
            int count = 0;
            for (final LayoutableNode l : node.getChildrenToLayout()) {
                final LayoutInfo in = this.layout(l, LayoutStage.STAGE1, node
                        .getChildLayoutContext(count, parentContext));
                count++;
                if (LayoutStage.STAGE1.equals(in.getLayoutStage())) {
                    childMinStage = LayoutStage.STAGE1;
                }
            }
            node.layoutStage1(this, info, childMinStage, parentContext);
        }
        if (LayoutStage.STAGE1.equals(info.getLayoutStage())
                && LayoutStage.STAGE2.equals(toStage)) {
            int count = 0;
            for (final LayoutableNode l : node.getChildrenToLayout()) {
                this.layout(l, LayoutStage.STAGE2, node
                        .getChildLayoutContext(count, parentContext));
                count++;
            }
            node.layoutStage2(this, info, parentContext);
        }
        return info;
    }

    /** {@inheritDoc} */
    public LayoutInfo getInfo(final LayoutableNode node) {
        if (node == null) {
            return null;
        }
        LayoutInfo info = this.layoutMap.get(node);
        if (info == null) {
            info = new LayoutInfoImpl();
            this.layoutMap.put(node, info);
        }
        return info;
    }

    /**
     * @return width of this view.
     */
    public float getWidth() {
        final LayoutInfo info = this.layout(this.document,
                LayoutStage.STAGE2, this.context);
        return info.getWidth(LayoutStage.STAGE2);
    }

    /**
     * @return ascent height.
     */
    public float getAscentHeight() {
        final LayoutInfo info = this.layout(this.document,
                LayoutStage.STAGE2, this.context);
        return info.getAscentHeight(LayoutStage.STAGE2);
    }

    /**
     * @return descent height.
     */
    public float getDescentHeight() {
        final LayoutInfo info = this.layout(this.document,
                LayoutStage.STAGE2, this.context);
        return info.getDescentHeight(LayoutStage.STAGE2);
    }

    /** {@inheritDoc} */
    public Graphics2D getGraphics() {
        return this.graphics;
    }
}
