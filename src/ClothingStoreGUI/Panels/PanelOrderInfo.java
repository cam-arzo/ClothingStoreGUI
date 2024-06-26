/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ClothingStoreGUI.Panels;

import ClothingStoreGUI.Controller;
import ClothingStoreGUI.InteractivePanel;
import javax.swing.JLabel;
import javax.swing.JTable;

/**
 *
 * @author annek
 */
public class PanelOrderInfo extends javax.swing.JPanel implements InteractivePanel {

    /**
     * Creates new form PanelOrderInfo
     */
    public PanelOrderInfo(Controller controller) {
        initComponents();
        initConnections(controller);
        OrderTable.setEnabled(false);
    }
    
    @Override
    public void initConnections(Controller controller) {
        BackButton.addActionListener(e -> controller.backButtonClicked());
    }

    public JTable getOrderTable() {
        return OrderTable;
    }

    public JLabel getTotalRevenueLabel() {
        return TotalRevenueLabel;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        OrderTable = new javax.swing.JTable();
        Message = new javax.swing.JLabel();
        Title = new javax.swing.JLabel();
        TotalRevenueLabel = new javax.swing.JLabel();
        BackButton = new javax.swing.JButton();

        OrderTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Order no.", "Total qty", "Total price"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        OrderTable.getTableHeader().setResizingAllowed(false);
        OrderTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(OrderTable);
        if (OrderTable.getColumnModel().getColumnCount() > 0) {
            OrderTable.getColumnModel().getColumn(0).setResizable(false);
            OrderTable.getColumnModel().getColumn(1).setResizable(false);
            OrderTable.getColumnModel().getColumn(2).setResizable(false);
        }

        Message.setText("Summary of all order information to date:");

        Title.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        Title.setText("Order information");

        TotalRevenueLabel.setText("Total revenue: $20.00");

        BackButton.setText("Back");
        BackButton.setInheritsPopupMenu(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(65, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(TotalRevenueLabel)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(BackButton)
                                .addGap(70, 70, 70)
                                .addComponent(Title)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Message)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 83, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(86, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BackButton)
                    .addComponent(Title))
                .addGap(18, 18, 18)
                .addComponent(Message)
                .addGap(19, 19, 19)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(TotalRevenueLabel)
                .addContainerGap(91, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BackButton;
    private javax.swing.JLabel Message;
    private javax.swing.JTable OrderTable;
    private javax.swing.JLabel Title;
    private javax.swing.JLabel TotalRevenueLabel;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

}
