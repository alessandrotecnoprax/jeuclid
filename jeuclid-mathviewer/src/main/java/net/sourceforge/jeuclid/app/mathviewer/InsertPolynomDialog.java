/*
 * Copyright 2009 - 2010 JEuclid, http://jeuclid.sf.net
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

/* $Id $ */

package net.sourceforge.jeuclid.app.mathviewer;

// CHECKSTYLE:OFF
public class InsertPolynomDialog extends javax.swing.JDialog {
    // CHECKSTYLE:OFF
    /**
     * Current mathML text.
     */
    private String mathMLText;

    /** Creates new form InsertTableDialog */
    public InsertPolynomDialog(final java.awt.Frame parent, final boolean modal) {
        super(parent, modal);
        this.initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed"
    // desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        this.cancelButton = new javax.swing.JButton();
        this.okButton = new javax.swing.JButton();
        this.degreeSpinner = new javax.swing.JSpinner();
        this.degreeLabel = new javax.swing.JLabel();

        this
                .setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        this.cancelButton.setText("Abbrechen");
        this.cancelButton
                .addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(
                            final java.awt.event.ActionEvent evt) {
                        InsertPolynomDialog.this
                                .cancelButtonActionPerformed(evt);
                    }
                });

        this.okButton.setText("OK");
        this.okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(final java.awt.event.ActionEvent evt) {
                InsertPolynomDialog.this.okButtonActionPerformed(evt);
            }
        });

        this.degreeSpinner.setModel(new javax.swing.SpinnerNumberModel(3, 0,
                25, 1));

        this.degreeLabel.setText("Grad");

        final javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this
                .getContentPane());
        this.getContentPane().setLayout(layout);
        layout
                .setHorizontalGroup(layout
                        .createParallelGroup(
                                javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(
                                layout
                                        .createSequentialGroup()
                                        .addGap(24, 24, 24)
                                        .addGroup(
                                                layout
                                                        .createParallelGroup(
                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(
                                                                layout
                                                                        .createSequentialGroup()
                                                                        .addGap(
                                                                                10,
                                                                                10,
                                                                                10)
                                                                        .addComponent(
                                                                                this.okButton,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                93,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addGap(
                                                                                18,
                                                                                18,
                                                                                18)
                                                                        .addComponent(
                                                                                this.cancelButton))
                                                        .addGroup(
                                                                layout
                                                                        .createSequentialGroup()
                                                                        .addGap(
                                                                                11,
                                                                                11,
                                                                                11)
                                                                        .addComponent(
                                                                                this.degreeLabel)
                                                                        .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                        .addComponent(
                                                                                this.degreeSpinner,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(41, 41, 41)));
        layout
                .setVerticalGroup(layout
                        .createParallelGroup(
                                javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(
                                layout
                                        .createSequentialGroup()
                                        .addGap(25, 25, 25)
                                        .addGroup(
                                                layout
                                                        .createParallelGroup(
                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(
                                                                this.degreeLabel)
                                                        .addComponent(
                                                                this.degreeSpinner,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(
                                                layout
                                                        .createParallelGroup(
                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(
                                                                this.cancelButton)
                                                        .addComponent(
                                                                this.okButton))
                                        .addContainerGap(
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                Short.MAX_VALUE)));

        this.pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelButtonActionPerformed(
            final java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cancelButtonActionPerformed
        this.mathMLText = null;
        this.setVisible(false);
    }// GEN-LAST:event_cancelButtonActionPerformed

    public String getMathMLText() {
        return this.mathMLText;
    }

    private void okButtonActionPerformed(final java.awt.event.ActionEvent evt) {// GEN-FIRST:event_okButtonActionPerformed

        this.updateMathMLText();
        this.setVisible(false);
    }// GEN-LAST:event_okButtonActionPerformed

    private void updateMathMLText() {
        final int degree = (Integer) this.degreeSpinner.getValue();

        String s = "";
        char c = 'a';
        for (int i = degree; i >= 0; --i) {
            s += "<mi>" + c + "</mi>" + Helper.nl();

            if (i > 0) {
                if (i != 1) {
                    s += "<msup><mi>x</mi><mn>" + i + "</mn></msup>"
                            + Helper.nl();
                } else {
                    s += "<mi>x</mi>" + Helper.nl();
                }
                s += "<mo>+</mo>" + Helper.nl();
            }
            c++;
        }
        this.mathMLText = s;
    }

    /**
     * @param args
     *            the command line arguments
     */
    public static void main(final String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                final InsertPolynomDialog dialog = new InsertPolynomDialog(
                        new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(final java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel degreeLabel;
    private javax.swing.JSpinner degreeSpinner;
    private javax.swing.JButton okButton;
    // End of variables declaration//GEN-END:variables

}
