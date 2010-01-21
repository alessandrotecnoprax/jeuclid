/*
 *  Copyright 2009 Mario.
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */

/*
 * InsertTableDialog.java
 *
 * Created on 05.12.2009, 23:46:36
 */

package net.sourceforge.jeuclid.app.mathviewer;

// CHECKSTYLE:OFF
public class InsertTableDialog extends javax.swing.JDialog {
    // CHECKSTYLE:OFF
    private String mathMLText;
    /** Creates new form InsertTableDialog */
    public InsertTableDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroupType = new javax.swing.ButtonGroup();
        tableRadioButton = new javax.swing.JRadioButton();
        matrixRadioButton = new javax.swing.JRadioButton();
        determinantRadioButton = new javax.swing.JRadioButton();
        cancelButton = new javax.swing.JButton();
        okButton = new javax.swing.JButton();
        columnsLabel = new javax.swing.JLabel();
        rowsSpinner = new javax.swing.JSpinner();
        columnsSpinner = new javax.swing.JSpinner();
        typeLabel = new javax.swing.JLabel();
        rowsLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        buttonGroupType.add(tableRadioButton);
        tableRadioButton.setText("Tabelle");

        buttonGroupType.add(matrixRadioButton);
        matrixRadioButton.setSelected(true);
        matrixRadioButton.setText("Matrix");

        buttonGroupType.add(determinantRadioButton);
        determinantRadioButton.setText("Determinante");

        cancelButton.setText("Abbrechen");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        okButton.setText("OK");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        columnsLabel.setText("Spalten");

        rowsSpinner.setModel(new javax.swing.SpinnerNumberModel(3, 1, 99, 1));

        columnsSpinner.setModel(new javax.swing.SpinnerNumberModel(3, 1, 99, 1));

        typeLabel.setText("Typ");

        rowsLabel.setText("Zeilen");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(typeLabel)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tableRadioButton)
                            .addComponent(matrixRadioButton)
                            .addComponent(determinantRadioButton)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(rowsLabel)
                        .addGap(14, 14, 14)
                        .addComponent(rowsSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(columnsLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(columnsSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cancelButton)))
                .addContainerGap(44, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rowsLabel)
                    .addComponent(columnsLabel)
                    .addComponent(rowsSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(columnsSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(typeLabel)
                    .addComponent(matrixRadioButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tableRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(determinantRadioButton)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton)
                    .addComponent(okButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        mathMLText = null;
        this.setVisible(false);
}//GEN-LAST:event_cancelButtonActionPerformed


    public String getMathMLText()
    {
        return mathMLText;
    }
    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed

        this.updateMathMLText();
        this.setVisible(false);
}//GEN-LAST:event_okButtonActionPerformed

    private String buildText(int lines, int columns, String type) {
        //String s = "<" + type + ">" + Helper.nl();
        String s = "<mrow>" + Helper.nl() + "<mfenced" + type + ">" + Helper.nl() +"<mtable>" + Helper.nl();

        for (int i = 0; i < lines; ++i) {
            //s += "<matrixrow>" + Helper.nl();
            s += "<mtr>" + Helper.nl();
            for (int j = 0; j < columns; ++j) {
                s += "<mtd><mn>1</mn></mtd>" + Helper.nl();
                //s+="\t<cn>1</cn>" + Helper.nl();
            }
            //s+= "</matrixrow>" + Helper.nl();
            s += "</mtr>" + Helper.nl();
        }
        //s+="</" + type + ">" + Helper.nl();
        s += "</mtable>" + Helper.nl() + "</mfenced>" + Helper.nl() + "</mrow>" + Helper.nl();
        return s;
    }

    private void updateMathMLText()
    {
        int m = (Integer)this.rowsSpinner.getValue();
        int n = (Integer)this.columnsSpinner.getValue();

        //matrix
        if(matrixRadioButton.isSelected())
        {
            this.mathMLText = buildText(m,n,"");
        }
        else if(determinantRadioButton.isSelected())
        {
            this.mathMLText = buildText(m,n," open=\"|\" close=\"|\"");
        }
        else if(tableRadioButton.isSelected())
        {
            this.mathMLText = buildText(m, n, " open=\"\" close=\"\"");
        }
        else
            mathMLText = null;
    }

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                InsertTableDialog dialog = new InsertTableDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroupType;
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel columnsLabel;
    private javax.swing.JSpinner columnsSpinner;
    private javax.swing.JRadioButton determinantRadioButton;
    private javax.swing.JRadioButton matrixRadioButton;
    private javax.swing.JButton okButton;
    private javax.swing.JLabel rowsLabel;
    private javax.swing.JSpinner rowsSpinner;
    private javax.swing.JRadioButton tableRadioButton;
    private javax.swing.JLabel typeLabel;
    // End of variables declaration//GEN-END:variables

}
